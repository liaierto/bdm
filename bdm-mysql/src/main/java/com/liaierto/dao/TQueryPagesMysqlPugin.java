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


public class TQueryPagesMysqlPugin {
	private static Log log = LogFactory.getLog(TQueryPagesMysqlPugin.class);
   
    public  String queryPage(IDataItem item,String content) throws Exception{
        ResultSet  resultSet  = null;
        JSONObject filter     = null;
        JSONObject ret        = new JSONObject();
        Integer    curentPage = null;
        Integer    pageRow    = null;
        PreparedStatement statement = null;
        String[] valus = null;
        try {
        	Connection con = (Connection) item.getValue("connection");
        	
        	JSONObject cont = JsonObjectTools.getJSObj(content);
        	curentPage = Integer.parseInt(cont.get("curentPage").toString());
        	pageRow = Integer.parseInt(cont.get("pageRow").toString());
        	filter = JsonObjectTools.getJSObj(cont.getString("filter"));
        	String value = filter.getString("value");
        	String fileterKey = item.getString("filter");//过滤参数
        	String parameter  = item.getString("parameter");//字段转换参数
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
        	
        	StringBuffer sql = new StringBuffer("");
        	sql.append(fileterKey);
        	sql.append(" limit "+(pageRow*(curentPage-1))+","+pageRow);
        	
        	statement = con.prepareStatement(sql.toString());
        	if(valus!=null){
        		for(int i=0;i<valus.length;i++){
        			statement.setObject(i+1,valus[i]);
        		}
        	}
        	resultSet = statement.executeQuery();
        	ResultSetMetaData resultSetMeta = resultSet.getMetaData();
        	int columnCount = resultSetMeta.getColumnCount();
        	List<String> Columns = new ArrayList<String>();
        	for(int i=0;i<columnCount;i++){
        		Columns.add(resultSetMeta.getColumnName(i+1));
        	}
        	
        	JSONArray rows = new JSONArray();
        	while(resultSet.next()){
        		JSONObject row = new JSONObject();
        		for(int j=0;j<Columns.size();j++){
        			String fName =Columns.get(j);
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
        	int totalRows = queryOfRows(con,fileterKey,valus);
        	int totalPage = totalRows/pageRow;
            int i         = totalRows%pageRow;
            if(i!=0){
               totalPage = totalPage+1;
            }
            ret.put("totalPage", totalPage);
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
   
    
    public int queryOfRows(Connection con,String sql,String[] values) throws Exception {
    	PreparedStatement statement = null;
		ResultSet resultSet = null;
	    try {
	    	statement = con.prepareStatement(sql.toString());
	    	if(values!=null){
        		for(int i=0;i<values.length;i++){
        			statement.setObject(i+1,values[i]);
        		}
        	}
        	resultSet = statement.executeQuery();
        	resultSet.last();
        	return resultSet.getRow();
	        
	    } catch (Exception e) {
	        log.error(e);
	        return 0;
	    }finally{
	    	if(resultSet!=null)resultSet.close();
	    	if(statement!=null)statement.close();
	    }
	  }
    
   
    public static TQueryPagesMysqlPugin getInstance() {
   	   return new TQueryPagesMysqlPugin();
    }
}
