package com.liaierto.service;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.liaierto.bean.IDataItem;
import com.liaierto.bean.IFormData;
import com.liaierto.bean.IQueryDB;
import com.liaierto.bean.InputForm;
import com.liaierto.beanImpl.TDataItem;
import com.liaierto.beanImpl.TInputForm;
import com.liaierto.beanImpl.TQueryDB;
import com.liaierto.utils.JsonObjectTools;
import com.liaierto.utils.ResultMsg;
import net.sf.json.JSONObject;




public class TMethodGroupService extends TService  {
	
	private static Log log = LogFactory.getLog(TMethodGroupService.class);
    public TMethodGroupService(){
    
    }

	public String add(String content) {
		InputForm inputForm = TInputForm.getInstance();
        JSONObject rowData   = null;
        boolean    result    = false ;
        String     response  = "";
        rowData = JSONObject.fromObject(content);
        String name          = rowData.getString("group_name");
    	inputForm.setTableName("t_method_group");
    	inputForm.setStrVal("group_name", name);
    	result = super.insert(inputForm);
    	IDataItem dataItem = new TDataItem();
    	if(result){
    		dataItem.setValue("code", ResultMsg.sucessCode);
    		dataItem.setValue("msg", ResultMsg.sucessMsg);
    	}else{
    		dataItem.setValue("code", ResultMsg.errorCode);
    		dataItem.setValue("msg", ResultMsg.errorMsg);
    	}
    	response = dataItem.getJsonRow().toString();
		return response;
	}


	public String remove(String content) {
		InputForm inputForm = null;
	    boolean    result    = false ;
	    String     response = "";
	    IDataItem dataItem = new TDataItem();
        try {
        	
        	JSONObject rowData   = null;
            rowData = JsonObjectTools.getJSObj(content);
            String id            = rowData.getString("o_id");
            
            inputForm = TInputForm.getInstance();
            
            inputForm.setTableName("t_object");
            inputForm.setWhereFileds("o_id", id);
            
            result = super.delete(inputForm);
        	if(result){
        		dataItem.setValue("code", ResultMsg.sucessCode);
        		dataItem.setValue("msg", ResultMsg.sucessMsg);
        	}else{
        		dataItem.setValue("code", ResultMsg.errorCode);
        		dataItem.setValue("msg", ResultMsg.errorMsg);
        	}
        } catch (Exception e) {
            log.error(e);
            dataItem.setValue("code", ResultMsg.unknownCode);
    		dataItem.setValue("msg", ResultMsg.unknownMsg);
        }
        response = dataItem.getJsonRow().toString();
		return response;
       
	}


	public String query(String content) {
	       IQueryDB     queryDB    = null;
	        try {
	            queryDB = TQueryDB.getInstance();
	            
	            queryDB.setTableName(new String[]{"t_method_group"});
	            queryDB.setQueryField(new String[]{"group_id","group_name"});
	            queryDB.setOrderSql("group_id asc");
	            IFormData formData = super.query(queryDB);
	            return formData.getJsonArrayRows().toString();
	        } catch (Exception e) {
	            log.error(e);
	            return "false";
	        }
	    
	}

	
}
