package com.liaierto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.liaierto.bean.IDataItem;
import com.liaierto.utils.JsonObjectTools;

import net.sf.json.JSONObject;


public class TDeleteMysqlPugin {
	private static Log log = LogFactory.getLog(TDeleteMysqlPugin.class);
   
    public  boolean delete(IDataItem item,String content) throws Exception {
        JSONObject filter = null;
        PreparedStatement statement = null;
        String[] valus = null;
        try {
        	Connection con = (Connection) item.getValue("connection");
        	
        	JSONObject cont = JsonObjectTools.getJSObj(content);
        	filter = JsonObjectTools.getJSObj(cont.getString("filter"));
        	String value = filter.getString("value");
        	String fileterKey = item.getString("filter");//过滤参数
        	if(!"".equals(value)){
        		valus = value.split(",");
        	}
        	
            StringBuffer sql  =  new StringBuffer();
            sql.append("delete from " + item.getString("tableName"));
            sql.append(" where 1=1 ");
            if(!"".equals(fileterKey)){
            	sql.append(" and ");
            	sql.append(fileterKey);
            }
            statement = con.prepareStatement(sql.toString());
        	for(int i=0;i<valus.length;i++){
    			statement.setObject(i+1,valus[i]);
    		}
            int pResult = statement.executeUpdate();
            if(pResult>=1){
               return true;
            }else{
                return false;
            }
            
            
        } catch (SQLException e) {
            log.error(e);
            return false;
        }finally{
        	if(statement!=null)statement.close();
        }
    }

  public static TDeleteMysqlPugin getInstance(){
	  return new TDeleteMysqlPugin();
  }
}
