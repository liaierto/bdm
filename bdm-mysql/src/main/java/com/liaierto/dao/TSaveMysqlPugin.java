package com.liaierto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class TSaveMysqlPugin {
	private static Log log = LogFactory.getLog(TSaveMysqlPugin.class);

    public  boolean update(Map<String,Object> item, String content) throws Exception {
        JSONArray  dataArray = null;
        JSONObject rowData   = null;
        JSONObject filter    = null;
        PreparedStatement statement = null;
        String[] valus = null;
        try {
        	Connection con = (Connection) item.get("connection");
        	
        	JSONObject cont = JSONObject.parseObject(content);
        	dataArray = JSONArray.parseArray(cont.getString("rows"));
        	filter = JSONObject.parseObject(cont.getString("filter"));
        	String value = filter.getString("value");
        	String fileterKey = item.get("filter").toString();//过滤参数
        	if(!"".equals(value)){
        		valus = value.split(",");
        	}
        	Iterator iter = dataArray.iterator();
        	while (iter.hasNext()) {
        		StringBuffer sql  =  new StringBuffer();
        		StringBuffer where = new StringBuffer();
                sql.append("update "+ item.get("tableName")+ " set ");
                where.append(" where 1=1");
                if(!"".equals(fileterKey)){
                	where.append(" and ");
                	where.append(fileterKey);
            	}else{
            		return false;//没有条件不更新
            	}
                StringBuffer columnNames = new StringBuffer();
                StringBuffer values      = new StringBuffer();
                
                rowData = (JSONObject) iter.next();
        		for (Iterator itr = rowData.keySet().iterator(); itr.hasNext();) {
                    String columnName  = itr.next().toString();
                    String columnValue =  rowData.get(columnName).toString();
                    
                    columnNames.append(columnName+",");
                    values.append("'").append( columnValue+"'").append(",");
                    
                    sql.append(columnName+"='"+columnValue+"'");
                    sql.append(",");
                }
                String Sql = sql.deleteCharAt(sql.length()-1).toString()+where.toString();
            	log.info(Sql);
            	statement = con.prepareStatement(Sql);
            	if(valus!=null){
            		for(int i=0;i<valus.length;i++){
            			statement.setObject(i+1,valus[i]);
            		}
            	}
            	statement.addBatch();
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

    public static TSaveMysqlPugin getInstance(){
    	return new TSaveMysqlPugin();
    }
   
}
