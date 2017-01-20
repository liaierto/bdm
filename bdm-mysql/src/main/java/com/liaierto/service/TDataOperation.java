package com.liaierto.service;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liaierto.dao.TAddMysqlPugin;
import com.liaierto.dao.TDeleteMysqlPugin;
import com.liaierto.dao.TQueryMysqlPugin;
import com.liaierto.dao.TQueryPageMysqlPugin;
import com.liaierto.dao.TQueryPagesMysqlPugin;
import com.liaierto.dao.TQuerysMysqlPugin;
import com.liaierto.dao.TSaveMysqlPugin;
import com.liaierto.service.interfaces.IDataOperation;
import com.liaierto.utils.ResultMsg;

import java.util.HashMap;
import java.util.Map;

public class TDataOperation extends TService implements IDataOperation {

	private static Log log = LogFactory.getLog(TDataOperation.class);
	
	public TDataOperation(){}
	

	public String add(Map<String,Object> item, String parameter) {
		String     response  = "";
		Map<String,Object> restInfo = new HashMap<String,Object>();
		boolean result = false;
		try {
			result = TAddMysqlPugin.getInstance().insert(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result){
			restInfo.put("code", ResultMsg.sucessCode);
			restInfo.put("msg", ResultMsg.sucessMsg);
		}else{
			restInfo.put("code", ResultMsg.errorCode);
			restInfo.put("msg", ResultMsg.errorMsg);
		}
		JSONObject jsonObject =new JSONObject(restInfo);
		response = jsonObject.toJSONString();
		return response;
	}

	public String save(Map<String,Object> item, String parameter) {
		String     response  = "";
		Map<String,Object> restInfo = new HashMap<String,Object>();
		boolean result = false;
		try {
			result = TSaveMysqlPugin.getInstance().update(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result){
			restInfo.put("code", ResultMsg.sucessCode);
			restInfo.put("msg", ResultMsg.sucessMsg);
		}else{
			restInfo.put("code", ResultMsg.errorCode);
			restInfo.put("msg", ResultMsg.errorMsg);
		}
		JSONObject jsonObject =new JSONObject(restInfo);
		response = jsonObject.toJSONString();
		return response;
	}

	public String delete(Map<String,Object> item, String parameter) {
		String     response  = "";
		Map<String,Object> restInfo = new HashMap<String,Object>();
		boolean result = false;
		try {
			result = TDeleteMysqlPugin.getInstance().delete(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result){
			restInfo.put("code", ResultMsg.sucessCode);
			restInfo.put("msg", ResultMsg.sucessMsg);
		}else{
			restInfo.put("code", ResultMsg.errorCode);
			restInfo.put("msg", ResultMsg.errorMsg);
		}
		JSONObject jsonObject =new JSONObject(restInfo);
		response = jsonObject.toJSONString();
		return response;
	}

	public String query(Map<String,Object> item, String parameter) {
		try {
			return TQueryMysqlPugin.getInstance().query(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String querys(Map<String,Object> item, String parameter) {
		try {
			return TQuerysMysqlPugin.getInstance().query(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryPage(Map<String,Object> item, String parameter) {
		try {
			return TQueryPageMysqlPugin.getInstance().queryPage(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryPages(Map<String,Object> item, String parameter) {
		try {
			return TQueryPagesMysqlPugin.getInstance().queryPage(item, parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
