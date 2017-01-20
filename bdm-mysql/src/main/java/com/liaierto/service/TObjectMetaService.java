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
import com.liaierto.service.interfaces.IObjectMetaService;
import com.liaierto.utils.DateTools;
import com.liaierto.utils.ResultMsg;





public class TObjectMetaService extends TService implements IObjectMetaService {
    
	private static Log log = LogFactory.getLog(TObjectMetaService.class);
    public TObjectMetaService(){}

	public String queryPage(String content) {
	       IQueryDB     queryDB    = null;
	       JSONObject   obj         = new JSONObject();
	       int          currentPage = 1;
	       int          pageRow     = 10;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_meta"});
	            queryDB.setQueryField(new String[]{"m_id","name","description","len","type","iskey","auto"});
	            queryDB.setWhereFileds("object", contObj.getString("object"));
	            queryDB.setWhereFileds("role", contObj.getString("role"));
	            if(contObj.containsKey("currentpage")){
	                currentPage = Integer.valueOf(contObj.getString("currentpage"));
	            }
	            if(contObj.containsKey("pageRow")){
	                pageRow = Integer.valueOf(contObj.getString("pageRow"));
	            }
	            queryDB.setOrderSql("m_id asc");
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
	public String query(String content) {
	       IQueryDB     queryDB    = null;
	        try {
	            JSONObject contObj = JSONObject.parseObject(content);
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_meta"});
	            queryDB.setQueryField(new String[]{"name","description"});
	            queryDB.setWhereFileds("role", contObj.getString("role"));
	            queryDB.setWhereFileds("object", contObj.getString("object"));
	            queryDB.setOrderSql("name asc");
				List<Map<String,Object>> formData = super.query(queryDB);
	            return JSON.toJSONString(formData);
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
            
            inputForm.setTableName("t_meta");
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
                
                inputForm.setTableName("t_meta");
                String id     = rowData.getString("m_id");
                String name   = rowData.getString("name");
                String len    = rowData.getString("len");
                String type   = rowData.getString("type");
                String iskey  = rowData.getString("iskey");
                String auto   = rowData.getString("auto");
            	String description   = rowData.getString("description");
            	inputForm.setStrVal("name", name);
            	inputForm.setStrVal("description", description);     
            	inputForm.setStrVal("len", len);
            	inputForm.setStrVal("type", type); 
            	inputForm.setStrVal("role", role); 
            	inputForm.setStrVal("object", object); 
            	inputForm.setStrVal("iskey", iskey); 
            	inputForm.setStrVal("auto", auto); 
                if(!"".equals(id)){
                	inputForm.setStrVal("update_time",DateTools.getCurrentTime("yyyyMMDDhhmmss"));
                    inputForm.setWhereFileds("m_id", id);
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
        response = JSON.toJSONString(dataItem);
    	return response;
        
    } 
}
