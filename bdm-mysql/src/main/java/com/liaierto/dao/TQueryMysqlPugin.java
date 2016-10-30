package com.liaierto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liaierto.bean.IDataItem;
import com.liaierto.utils.JsonObjectTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class TQueryMysqlPugin {
	private static Log log = LogFactory.getLog(TQueryMysqlPugin.class);
    

    public  String query(IDataItem item,String content) throws Exception {
        ResultSet         resultSet    = null;
        JSONObject filter = null;
        JSONObject ret = new JSONObject();
        PreparedStatement statement = null;
        String[] valus = null;
        try {
        	Connection con = (Connection) item.getValue("connection");
        	
        	JSONObject cont = JsonObjectTools.getJSObj(content);
        	filter = JsonObjectTools.getJSObj(cont.getString("filter"));
        	String value = filter.getString("value");
        	String fileterKey = item.getString("filter");//过滤参数
        	String parameter  = item.getString("parameter");//字段转换参数
        	String queryClomn = item.getString("clomn");//查询字段
        	if(!"".equals(value)){
        		valus = value.split(",");
        	}
        	
        	Hashtable<String,String> isConvent = new Hashtable<String,String> ();
        	if(parameter!=null && !"".equals(parameter)){
        		String[] convernts = parameter.split(",");
            	int cLen = convernts.length;
            	for(int i=0;i<cLen;i++){
            		String[] kv = convernts[i].split(":");
            		isConvent.put(kv[0], kv[1]);
            	}
        	}
        	
        	StringBuffer sql = new StringBuffer("select ");
        	sql.append(queryClomn);
        	sql.append(" from ");
        	sql.append(item.getString("tableName"));
        	sql.append(" where 1=1 ");
        	if(!"".equals(fileterKey)){
        		sql.append(" and ");
        		sql.append(fileterKey);
        	}
        	
        	
        	
        	statement = con.prepareStatement(sql.toString());
        	if(valus!=null){
        		for(int i=0;i<valus.length;i++){
        			statement.setObject(i+1,valus[i]);
        		}
        	}
        	
        	resultSet = statement.executeQuery();
        	
        	ResultSetMetaData resultSetMeta = resultSet.getMetaData();
        	int columnCount = resultSetMeta.getColumnCount();
        	List<String> columns = new ArrayList<String>();
        	for(int i=0;i<columnCount;i++){
        		columns.add(resultSetMeta.getColumnName(i+1));
        	}
        	JSONArray rows = new JSONArray();
        	while(resultSet.next()){
        		JSONObject row = new JSONObject();
        		int columnSize = columns.size();
        		for(int j=0;j<columnSize;j++){
        			String fName =columns.get(j);
        			 if(resultSet.getObject(fName)!=null){
        				 String vcode = isConvent.get(fName);
        				 if(vcode!=null && !"".equals(vcode)){
        					 row.put(fName,new String((byte[])resultSet.getObject(fName),vcode)); 
        				 }else{
        					 row.put(fName,resultSet.getObject(fName)); 
        				 }
                     }else{
                    	 row.put(fName,""); 
                     }
        		}
        		rows.add(row);
        	}
        	ret.put("rows", rows);
            return  ret.toString();
            
        } catch (SQLException e) {
            log.error(e);
            return null;
            
        }finally {
            try {
                if (resultSet != null) {
                	resultSet.close();
                }
                if(statement!=null)statement.close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
    }
    
    
    public static TQueryMysqlPugin getInstance(){
    	return new TQueryMysqlPugin();
    }
    
}
