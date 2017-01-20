package com.liaierto.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.liaierto.bean.IQueryDB;
import com.liaierto.bean.InputForm;
import com.liaierto.beanImpl.TInputForm;
import com.liaierto.beanImpl.TQueryDB;
import com.liaierto.utils.ResultMsg;




public class TMethodGroupService extends TService  {
	
	private static Log log = LogFactory.getLog(TMethodGroupService.class);
    public TMethodGroupService(){
    
    }

	public Object add(String content) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		InputForm inputForm = TInputForm.getInstance();
        JSONObject rowData   = null;
        boolean    result    = false ;
        
        rowData = JSONObject.parseObject(content);
        String name          = rowData.getString("group_name");
    	inputForm.setTableName("t_method_group");
    	inputForm.setStrVal("group_name", name);
    	
    	result = super.insert(inputForm);
    	
    	if(result){
    		resultMap.put("code", ResultMsg.sucessCode);
    		resultMap.put("msg", ResultMsg.sucessMsg);
    	}else{
    		resultMap.put("code", ResultMsg.errorCode);
    		resultMap.put("msg", ResultMsg.errorMsg);
    	}
		return resultMap;
	}


	public Object remove(String content) {
		InputForm inputForm = null;
	    boolean    result    = false ;
	    Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
        	
        	JSONObject rowData   = null;
            rowData = JSONObject.parseObject(content);
            String id            = rowData.getString("o_id");
            
            inputForm = TInputForm.getInstance();
            
            inputForm.setTableName("t_object");
            inputForm.setWhereFileds("o_id", id);
            
            result = super.delete(inputForm);
        	if(result){
        		resultMap.put("code", ResultMsg.sucessCode);
        		resultMap.put("msg", ResultMsg.sucessMsg);
        	}else{
        		resultMap.put("code", ResultMsg.errorCode);
        		resultMap.put("msg", ResultMsg.errorMsg);
        	}
        } catch (Exception e) {
            log.error(e);
            resultMap.put("code", ResultMsg.unknownCode);
            resultMap.put("msg", ResultMsg.unknownMsg);
        }
		return resultMap;
       
	}


	public Object query(String content) {
	       IQueryDB     queryDB    = null;
	        try {
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_method_group"});
	            queryDB.setQueryField(new String[]{"group_id","group_name"});
	            queryDB.setOrderSql("group_id asc");
	            List<Map<String,Object>> formData = super.query(queryDB);

	            return JSON.toJSONString(formData,true);
			} catch (Exception e) {
	            log.error(e);
	            return "false";
	        }
	    
	}

	
}
