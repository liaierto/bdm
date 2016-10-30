package com.liaierto.dao.interfaces;

import java.sql.Statement;

import com.liaierto.bean.IFormData;
import com.liaierto.bean.InputForm;
import com.liaierto.bean.IQueryDB;


public interface IDao {
	/****
	 * �������ݿ�����
	 * @return boolean
	 */
	boolean  insert(InputForm inputForm);
	/****
	 * ɾ�����ݿ�����
	 * @return boolean
	 */
    boolean  delete(InputForm inputForm);
    /****
	 * �������ݿ�����
	 * @return boolean
	 */
    boolean  update(InputForm inputForm);
    /****
	 * ��ѯ���ݿ�����
	 * @return IFormData
	 */
    IFormData query(IQueryDB queryDBManager);
    /****
	 * ��ҳ��ѯ���ݿ�����
	 * @return IFormData
	 */
    IFormData queryByPage(IQueryDB queryDBManager, Integer pageRow,int curentPage);
    /****
	 * ��ѯ��������������Ŀ
	 * @return int
	 */
    int queryOfRows(IQueryDB qeryDBManager);
    
    boolean excute(String sql);
    
    void setDataSource(Statement statement);
    Statement getDataSource();
}
