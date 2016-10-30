package com.liaierto.service.interfaces;

import com.liaierto.bean.IDataItem;

public interface IDataOperation extends IService {
	
	//新版方法
	String add(IDataItem item,String parameter);
	String save(IDataItem item,String parameter);
	String delete(IDataItem item,String parameter);
	String query(IDataItem item,String parameter);
	String querys(IDataItem item,String parameter);
	String queryPage(IDataItem item,String parameter);
	String queryPages(IDataItem item,String parameter);
}
