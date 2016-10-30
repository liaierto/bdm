package com.liaierto.service;


import java.sql.Statement;

import com.liaierto.bean.IFormData;
import com.liaierto.bean.InputForm;
import com.liaierto.bean.IQueryDB;
import com.liaierto.dao.TDao;
import com.liaierto.dao.interfaces.IDao;
import com.liaierto.service.interfaces.IService;

public class TService implements IService {
	private IDao mDao;
	
	public TService(){
		mDao = new TDao();
	}
	public boolean insert(InputForm inputForm) {
		
		return this.mDao.insert(inputForm);
	}

	public boolean delete(InputForm inputForm) {
		return this.mDao.delete(inputForm);
	}

	public boolean update(InputForm inputForm) {
		return this.mDao.update(inputForm);
	}

	public IFormData query(IQueryDB queryDBManager) {
		return this.mDao.query(queryDBManager);
	}

	public IFormData queryByPage(IQueryDB queryDBManager, Integer pageRow,int curentPage) {
		return this.mDao.queryByPage(queryDBManager, pageRow, curentPage);
	}

	public int queryOfRows(IQueryDB queryDBManager) {
		return this.mDao.queryOfRows(queryDBManager);
	}
	
	public boolean excute(String sql) {
		return this.mDao.excute(sql);
	}
	
//	public void setDao(IDao dao){
//		this.mDao = dao;
//	}
//	public IDao getDao(){
//		return this.mDao;
//	}
	
	public void setStatement(Statement statement){
	   this.mDao.setDataSource(statement);
    }
	public Statement getStatement(){
		   return this.mDao.getDataSource();
	    }
	
}
