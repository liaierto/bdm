package com.liaierto.service;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.liaierto.bean.IQueryDB;
import com.liaierto.bean.InputForm;
import com.liaierto.beanImpl.TInputForm;
import com.liaierto.beanImpl.TQueryDB;
import com.liaierto.service.interfaces.IObjectMethodService;
import com.liaierto.utils.ResultMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TMethodService extends TService implements IObjectMethodService {
    
	private static Log log = LogFactory.getLog(TMethodService.class);
    public TMethodService(){}


	public String queryPage(String content) {
	       IQueryDB     queryDB    = null;
	       JSONObject   obj         = new JSONObject();
	       int          currentPage = 1;
	       int          pageRow     = 10;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_method"}); 
	            queryDB.setQueryField(new String[]{"id","name","description","type","clomn","parameter","filter","tableName"});
	            queryDB.setWhereFileds("group_id", contObj.getString("group_id"));
	            if(contObj.containsKey("currentpage")){
	                currentPage = Integer.valueOf(contObj.getString("currentpage"));
	            }
	            if(contObj.containsKey("pageRow")){
	                pageRow = Integer.valueOf(contObj.getString("pageRow"));
	            }
	            queryDB.setOrderSql("id asc");
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
            String id            = rowData.getString("id");
            
            inputForm = TInputForm.getInstance();
            
            inputForm.setTableName("t_method");
            inputForm.setWhereFileds("id", id);
            
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
       
        Map<String ,Object> dataItem = new HashMap<String,Object>();
        JSONObject rowData = JSONObject.parseObject(content);
        String     response  = "";
        try{
                inputForm.setTableName("t_method");
                String id        = rowData.getString("id")==null?"":rowData.getString("id");
                String name      = rowData.getString("name");
                String descript  = rowData.getString("description");
                String type      = rowData.getString("type");
                String clomn     = rowData.getString("clomn");
                String parameter = rowData.getString("parameter");
                String filter    = rowData.getString("filter");
                String tableName = rowData.getString("tableName");
                String group_id  = rowData.getString("group_id");
            	inputForm.setStrVal("name", name);
            	inputForm.setStrVal("description", descript);     
            	inputForm.setStrVal("type", type); 
            	inputForm.setStrVal("clomn", clomn); 
            	inputForm.setStrVal("parameter", parameter);
            	inputForm.setStrVal("filter", filter);     
            	inputForm.setStrVal("tableName", tableName); 
            	inputForm.setStrVal("group_id", group_id); 
                if(!"".equals(id)){
                    inputForm.setWhereFileds("id", id);
                    super.update(inputForm);
                }else{
                    super.insert(inputForm);
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
	       Map<String,Object> obj   = new HashMap<String,Object>();
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
	            return JSON.toJSONString(obj);
	        } catch (Exception e) {
	            log.error(e);
	            return "false";
	        }
	    
	} 
}
