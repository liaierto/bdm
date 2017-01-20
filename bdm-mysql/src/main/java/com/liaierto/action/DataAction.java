package com.liaierto.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.liaierto.bean.IQueryDB;
import com.liaierto.beanImpl.TQueryDB;
import com.liaierto.db.DBUtil;
import com.liaierto.service.TDataOperation;

public class DataAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(DataAction.class);
    public DataAction() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 String method    = request.getParameter("method");
		 String parameter = request.getParameter("parameter");
		 Connection connection = null;
		 Statement  statement  = null;
		 String result = "";
		 try {
			 request.setCharacterEncoding("UTF-8");
			 response.setCharacterEncoding("UTF-8");
			 
			 connection =  DBUtil.getConnection();

			 TDataOperation dataOperation  = new TDataOperation();
			 
			 statement = connection.createStatement();
			 dataOperation.setStatement(statement);
			 
			 IQueryDB     queryDB    = null;
		        try {
		            queryDB = TQueryDB.getInstance();
		            queryDB.setTableName(new String[]{"t_method"});
		            queryDB.setQueryField(new String[]{"type","clomn","parameter","filter","tableName"});
		            queryDB.setWhereFileds("name", method);
					List<Map<String,Object>> formData = dataOperation.query(queryDB);
		            Map<String ,Object> item= formData.get(0);
		            item.put("connection", connection);
		            
		            String type = item.get("type").toString();
		            if("add".equals(type)){
		            	result = dataOperation.add(item, parameter);
		            }
					if("save".equals(type)){
						queryDB = TQueryDB.getInstance();
			            queryDB.setTableName(new String[]{"meta"});
			            queryDB.setQueryField(new String[]{"name"});
			            queryDB.setWhereFileds("object", item.get("tableName").toString());
			            queryDB.setWhereFileds("iskey","Yes");
			            queryDB.setWhereFileds("auto","Yes");
						List<Map<String,Object>> metaData = dataOperation.query(queryDB);
			            if(metaData.size()>0){
			            	 item.put("keyName", metaData.get(0).get("name"));
			            }
			            result = dataOperation.save(item, parameter);
			         }
					if("delete".equals(type)){
						result = dataOperation.delete(item, parameter);
					}
					if("query".equals(type)){
						result = dataOperation.query(item, parameter);
					}
					if("queryPage".equals(type)){
						result = dataOperation.queryPage(item, parameter);
					}
					if("querys".equals(type)){
						result = dataOperation.querys(item, parameter);
					}
					if("queryPages".equals(type)){
						result = dataOperation.queryPages(item, parameter);
					}
		        } catch (Exception e) {
		            log.error(e);
		           
		        }
			 
			 log.info(result);
			 response.setHeader("Access-Control-Allow-Origin", "*");
             response.getWriter().print(result);
	         
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
				try {
					if(statement!=null){
					   statement.close();
					}
					if(connection!=null){
					   connection.close();
				    }
					
				} catch (SQLException e) {
					e.printStackTrace();
			    }
		}
	}
	

}
