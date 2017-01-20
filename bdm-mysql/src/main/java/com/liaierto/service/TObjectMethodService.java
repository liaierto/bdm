package com.liaierto.service;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liaierto.bean.IQueryDB;
import com.liaierto.bean.InputForm;
import com.liaierto.beanImpl.TInputForm;
import com.liaierto.beanImpl.TQueryDB;
import com.liaierto.service.interfaces.IObjectMethodService;
import com.liaierto.utils.DateTools;
import com.liaierto.utils.ResultMsg;




public class TObjectMethodService extends TService implements IObjectMethodService {
    
	private static Log log = LogFactory.getLog(TObjectMethodService.class);
    public TObjectMethodService(){}


	public String queryPage(String content) {
	       IQueryDB     queryDB    = null;
	       JSONObject   obj         = new JSONObject();
	       int          currentPage = 1;
	       int          pageRow     = 10;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_object_method"});
	            queryDB.setQueryField(new String[]{"om_id","method","filter","create_time","update_time"});
	            queryDB.setWhereFileds("object", contObj.getString("object"));
	            queryDB.setWhereFileds("role", contObj.getString("role"));
	            if(contObj.containsKey("currentpage")){
	                currentPage = Integer.valueOf(contObj.getString("currentpage"));
	            }
	            if(contObj.containsKey("pageRow")){
	                pageRow = Integer.valueOf(contObj.getString("pageRow"));
	            }
	            queryDB.setOrderSql("om_id asc");
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
	    Map<String,Object> dataItem = new HashMap<String, Object>();
        try {
        	
        	JSONObject rowData   = null;
            rowData = JSONObject.parseObject(content);
            String id            = rowData.getString("id");
            
            inputForm = TInputForm.getInstance();
            
            inputForm.setTableName("t_object_method");
            inputForm.setWhereFileds("om_id", id);
            
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
        response = JSON.toJSONString(dataItem);
		return response;
       
	}

	public String update(String content) {
        InputForm inputForm = TInputForm.getInstance();
        JSONArray dataArray = null;
        JSONObject rowData = null;
        Map<String,Object> dataItem = new HashMap<String,Object>();
        JSONObject cont = JSONObject.parseObject(content);
        String object = (String) cont.get("object");
        String role = (String) cont.get("role");
        dataArray = JSONArray.parseArray(cont.getString("rows"));
        Iterator iter = dataArray.iterator();
        String     response  = "";
        try{
            while (iter.hasNext()) {
                rowData = (JSONObject) iter.next();
                
                inputForm.setTableName("t_object_method");
                String id     = rowData.getString("om_id");
                String method   = rowData.getString("method");
                String filter     = rowData.getString("filter");
            	inputForm.setStrVal("method", method);
            	inputForm.setStrVal("filter", filter);     
            	inputForm.setStrVal("role", role); 
            	inputForm.setStrVal("object", object); 
                if(!"".equals(id)){
                	inputForm.setStrVal("update_time",DateTools.getCurrentTime("yyyy-MM-DD-hh-mm-ss"));
                    inputForm.setWhereFileds("om_id", id);
                    super.update(inputForm);
                }else{
                	inputForm.setStrVal("create_time",DateTools.getCurrentTime("yyyy-MM-DD-hh-mm-ss"));
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
        response = JSON.toJSONString(dataItem);
    	return response;
        
    }


	public String queryMethod(String content) {
	       IQueryDB     queryDB    = null;
	       JSONObject   obj         = new JSONObject();
	       int          currentPage = 1;
	       int          pageRow     = 10;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_object"});
	            queryDB.setQueryField(new String[]{"id","name"});
	            queryDB.setWhereFileds("role", contObj.getString("role"));
				List<Map<String,Object>> formData = super.queryByPage(queryDB, pageRow, currentPage);
	            obj.put("rows", JSON.toJSON(formData));
	            return obj.toString();
	        } catch (Exception e) {
	            log.error(e);
	            return "false";
	        }
	    
	} 
}
