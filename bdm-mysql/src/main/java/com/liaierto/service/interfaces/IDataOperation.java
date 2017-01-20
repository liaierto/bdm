package com.liaierto.service.interfaces;


import java.util.Map;

public interface IDataOperation extends IService {
	
	//新版方法
	String add(Map<String,Object> item, String parameter);
	String save(Map<String,Object> item,String parameter);
	String delete(Map<String,Object> item,String parameter);
	String query(Map<String,Object> item,String parameter);
	String querys(Map<String,Object> item,String parameter);
	String queryPage(Map<String,Object> item,String parameter);
	String queryPages(Map<String,Object> item,String parameter);
}
