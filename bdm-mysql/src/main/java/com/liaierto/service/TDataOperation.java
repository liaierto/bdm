package com.liaierto.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liaierto.bean.IDataItem;
import com.liaierto.beanImpl.TDataItem;
import com.liaierto.dao.TAddMysqlPugin;
import com.liaierto.dao.TDeleteMysqlPugin;
import com.liaierto.dao.TQueryMysqlPugin;
import com.liaierto.dao.TQueryPageMysqlPugin;
import com.liaierto.dao.TQueryPagesMysqlPugin;
import com.liaierto.dao.TQuerysMysqlPugin;
import com.liaierto.dao.TSaveMysqlPugin;
import com.liaierto.service.interfaces.IDataOperation;
import com.liaierto.utils.ResultMsg;

public class TDataOperation extends TService implements IDataOperation {

	private static Log log = LogFactory.getLog(TDataOperation.class);
	
	public TDataOperation(){}
	

	public String add(IDataItem item, String parameter) {
		String     response  = "";
		IDataItem dataItem = new TDataItem();
		boolean result = false;
		try {
			result = TAddMysqlPugin.getInstance().insert(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public String save(IDataItem item, String parameter) {
		String     response  = "";
		IDataItem dataItem = new TDataItem();
		boolean result = false;
		try {
			result = TSaveMysqlPugin.getInstance().update(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public String delete(IDataItem item, String parameter) {
		String     response  = "";
		IDataItem dataItem = new TDataItem();
		boolean result = false;
		try {
			result = TDeleteMysqlPugin.getInstance().delete(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public String query(IDataItem item, String parameter) {
		try {
			return TQueryMysqlPugin.getInstance().query(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String querys(IDataItem item, String parameter) {
		try {
			return TQuerysMysqlPugin.getInstance().query(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryPage(IDataItem item, String parameter) {
		try {
			return TQueryPageMysqlPugin.getInstance().queryPage(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryPages(IDataItem item, String parameter) {
		try {
			return TQueryPagesMysqlPugin.getInstance().queryPage(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
