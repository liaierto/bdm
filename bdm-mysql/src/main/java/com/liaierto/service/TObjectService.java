package com.liaierto.service;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.liaierto.service.interfaces.IObjectMetaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaierto.bean.IQueryDB;
import com.liaierto.bean.InputForm;
import com.liaierto.beanImpl.TInputForm;
import com.liaierto.beanImpl.TQueryDB;
import com.liaierto.service.interfaces.IObjectService;
import com.liaierto.utils.DateTools;
import com.liaierto.utils.ResultMsg;





public class TObjectService extends TService implements IObjectService {
	
	private static Log log = LogFactory.getLog(TObjectService.class);
    public TObjectService(){
    
    }

	public String add(String content) {
		InputForm inputForm = TInputForm.getInstance();
        JSONObject rowData   = null;
        boolean    result    = false ;
        String     response  = "";
        rowData = JSONObject.parseObject(content);
        String name          = rowData.getString("object");
        //String type          = rowData.getString("type");
    	String description   = rowData.getString("description");
    	String role   = rowData.getString("role");
    	inputForm.setTableName("t_object");
    	inputForm.setStrVal("name", name);
    	//inputForm.setStrVal("type", type);
    	inputForm.setStrVal("description", description);
    	inputForm.setStrVal("role", role);
    	try {
			inputForm.setStrVal("create_time",DateTools.getCurrentTime("yyyyMMDDhhmmss"));
		} catch (Exception e) {
			log.error(e);
		} 
    	result = super.insert(inputForm);
    	Map<String,Object> dataItem = new HashMap<String,Object>();
    	if(result){
    		dataItem.put("code", ResultMsg.sucessCode);
    		dataItem.put("msg", ResultMsg.sucessMsg);
    	}else{
    		dataItem.put("code", ResultMsg.errorCode);
    		dataItem.put("msg", ResultMsg.errorMsg);
    	}
    	response = JSON.toJSONString(dataItem,true);
		return response;
	}

	public String queryPage(String content) {
	       IQueryDB     queryDB    = null;
	       JSONObject   obj         = new JSONObject();
	       int          currentPage = 1;
	       int          pageRow     = 10;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_object"});
	            queryDB.setQueryField(new String[]{"o_id","name","description","type","create_time","update_time"});
	            queryDB.setWhereFileds("role", contObj.getString("role"));
	            if(contObj.containsKey("currentpage")){
	                currentPage = Integer.valueOf(contObj.getString("currentpage"));
	            }
	            if(contObj.containsKey("pageRow")){
	                pageRow = Integer.valueOf(contObj.getString("pageRow"));
	            }
	            queryDB.setOrderSql("o_id desc");
	            int totalRows = super.queryOfRows(queryDB);
	            int totalPage = totalRows/pageRow;
	            int i         = totalRows%pageRow;
	            if(i!=0){
	               totalPage = totalPage+1;
	            }
				List<Map<String,Object>> formData = super.queryByPage(queryDB, pageRow, currentPage);
	            obj.put("rows", JSON.toJSON(formData));
	            obj.put("total", totalPage);
	            return obj.toString();
	        } catch (Exception e) {
	            log.error(e);
	            return "false";
	        }
	    
	}

	public String remove(String content) {
		InputForm inputForm = null;
	    boolean    result    = false ;
	    String     response = "";
		Map<String,Object> dataItem = new HashMap<String,Object>();
        try {
        	
        	JSONObject rowData   = null;
            rowData = JSONObject.parseObject(content);
            String id            = rowData.getString("o_id");
            
            inputForm = TInputForm.getInstance();
            
            inputForm.setTableName("t_object");
            inputForm.setWhereFileds("o_id", id);
            
            result = super.delete(inputForm);
        	if(result){
        		dataItem.put("code", ResultMsg.sucessCode);
        		dataItem.put("msg", ResultMsg.sucessMsg);
        	}else{
        		dataItem.put("code", ResultMsg.errorCode);
        		dataItem.put("msg", ResultMsg.errorMsg);
        	}
        } catch (Exception e) {
            log.error(e);
            dataItem.put("code", ResultMsg.unknownCode);
    		dataItem.put("msg", ResultMsg.unknownMsg);
        }
        response = JSON.toJSONString(dataItem,true);
		return response;
       
	}

	public String update(String content) {
        InputForm inputForm = TInputForm.getInstance();
        JSONArray dataArray = null;
        JSONObject rowData = null;
		Map<String,Object> dataItem = new HashMap<String,Object>();
        JSONObject cont = JSONObject.parseObject(content);
        String role = (String) cont.get("role");
        dataArray = JSONArray.parseArray(cont.getString("rows"));
        Iterator iter = dataArray.iterator();
        String     response  = "";
        try{
            while (iter.hasNext()) {
                rowData = (JSONObject) iter.next();
                
                inputForm.setTableName("t_object");
                String id     = rowData.getString("o_id");
                String name   = rowData.getString("name");
                String type   = rowData.getString("type");
            	String description   = rowData.getString("description");
            	inputForm.setStrVal("name", name);
            	inputForm.setStrVal("type", type);
            	inputForm.setStrVal("description", description);   
            	inputForm.setStrVal("role", role);
                if(!"".equals(id)){
                	inputForm.setStrVal("update_time",DateTools.getCurrentTime("yyyyMMDDhhmmss"));
                    inputForm.setWhereFileds("o_id", id);
                    super.update(inputForm);
                }else{
                	inputForm.setStrVal("create_time",DateTools.getCurrentTime("yyyyMMDDhhmmss"));
                    super.insert(inputForm);
                }
                
            }
            dataItem.put("code", ResultMsg.sucessCode);
    		dataItem.put("msg", ResultMsg.sucessMsg);
        }catch(Exception e){
        	 dataItem.put("code", ResultMsg.unknownCode);
     		 dataItem.put("msg", ResultMsg.unknownMsg);
             log.error(e);
        }
        response = JSON.toJSONString(dataItem,true);
    	return response;
        
    }

	public String query(String content) {
	       IQueryDB     queryDB    = null;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_object"});
	            queryDB.setQueryField(new String[]{"name","description"});
	            queryDB.setWhereFileds("role", contObj.getString("role"));
	            queryDB.setOrderSql("name desc");
				List<Map<String,Object>> formData = super.query(queryDB);
	            return JSON.toJSONString(formData,true);
	        } catch (Exception e) {
	            log.error(e);
	            return "false";
	        }
	    
	}

	public String createObject(String content) {
	       IQueryDB     queryDB    = null;
		   Map<String,Object> dataItem = new HashMap<String,Object>();
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            String obj = contObj.getString("object");
	            String name = contObj.getString("name");
	            queryDB.setTableName(new String[]{"t_meta"});
	            queryDB.setQueryField(new String[]{"name","type","len","iskey"});
	            queryDB.setWhereFileds("role", contObj.getString("role"));
	            queryDB.setWhereFileds("object",obj );
				List<Map<String,Object>> formData = super.query(queryDB);
				if(formData==null || formData.size()==0){
					add(content);
					TObjectMetaService metaService = new TObjectMetaService();
					metaService.setStatement(this.getStatement());
					metaService.update(content);

				}
				formData = super.query(queryDB);
	            if(createTable(obj,formData)){
	            	dataItem.put("code", ResultMsg.sucessCode);
	        		dataItem.put("msg", ResultMsg.sucessMsg);
	            }else{
	            	dataItem.put("code", ResultMsg.errorCode);
	        		dataItem.put("msg", ResultMsg.errorMsg);
	            }
	            return JSON.toJSONString(dataItem);
	        } catch (Exception e) {
	            log.error(e);
	            dataItem.put("code", ResultMsg.unknownCode);
	     		dataItem.put("msg", ResultMsg.unknownMsg);
	     		return JSON.toJSONString(dataItem,true);
	        }
	        
	} 
	
	private boolean createTable(String obj, List<Map<String,Object>> formData){
		String key = "";
		StringBuffer sql = new StringBuffer();
    	sql.append("create table ");
    	sql.append(obj).append(" (");
    	int count = formData.size();
    	for(int i=0;i<count;i++){
			 Map<String,Object> dataItem = formData.get(i);
    		 String field = dataItem.get("name").toString();
    		 String type  = dataItem.get("type").toString();
    		 String len   = dataItem.get("len").toString();
    		 if("string".equals(type)){
    			 type = "varchar";
    		 }
    		 sql.append(field).append(" ");
    		 sql.append(type);
    		 if(len!=null && !"".equals(len)){
    			 sql.append("(").append(dataItem.get("len")).append(") ");
    		 }
    		 String isKey = dataItem.get("iskey").toString();
    		 if("Yes".equals(isKey)){
    			 sql.append("auto_increment");
    			 key = field;
    		 }
    		sql.append(",");
    	}
    	sql.append(" primary key("+key+"))");
    	return super.excute(sql.toString());
		
	}
}
