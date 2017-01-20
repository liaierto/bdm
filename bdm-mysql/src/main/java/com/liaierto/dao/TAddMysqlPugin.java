package com.liaierto.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



public class TAddMysqlPugin {
	private static Log log = LogFactory.getLog(TAddMysqlPugin.class);
    public  boolean insert(Map<String,Object> item, String content) throws Exception {
        JSONArray dataArray = null;
        JSONObject rowData  = null;
        Statement statement = null;
        try {
        	Connection con = (Connection) item.get("connection");
        	statement = con.createStatement();
        	JSONObject cont = JSONObject.parseObject(content);
        	dataArray = JSONArray.parseArray(cont.getString("rows"));
        	Iterator iter = dataArray.iterator();
        	while (iter.hasNext()) {
        		StringBuffer sql         = new StringBuffer();
                StringBuffer columnNames = new StringBuffer();
                StringBuffer values      = new StringBuffer();
                sql.append("insert into " + item.get("tableName")+"(");
        		rowData = (JSONObject) iter.next();
        		for (Iterator itr = rowData.keySet().iterator(); itr.hasNext();) {
                    String pColumnName  = itr.next().toString();
                    String pColumnValue = rowData.get(pColumnName).toString();
                    columnNames.append(pColumnName+",");
                    values.append("'").append( pColumnValue+"'").append(",");
                }
                sql.append(columnNames.deleteCharAt(columnNames.length()-1)+")values("+values.deleteCharAt(values.length()-1)+")");
                String Sql = sql.toString();
                log.info(Sql);
                statement.addBatch(Sql);
        	 }
            
        	statement.executeBatch();
            return true;
            
        } catch (SQLException e) {
            log.error(e);
            return false;
        }finally{
        	if(statement!=null)statement.close();
        }

    }
    
    public static TAddMysqlPugin getInstance(){
    	
    	return new TAddMysqlPugin();
    }
    

}
