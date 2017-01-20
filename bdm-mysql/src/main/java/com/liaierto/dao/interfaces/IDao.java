package com.liaierto.dao.interfaces;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.liaierto.bean.InputForm;
import com.liaierto.bean.IQueryDB;


public interface IDao {
	/****
	 *新增数据
	 * @return boolean
	 */
	boolean  insert(InputForm inputForm);
	/****
	 * 删除数据
	 * @return boolean
	 */
    boolean  delete(InputForm inputForm);
    /****
	 *更新数据
	 * @return boolean
	 */
    boolean  update(InputForm inputForm);
    /****
	 * 查询数据
	 * @return IFormData
	 */
	List<Map<String,Object>> query(IQueryDB queryDBManager);
    /****
	 * 查询分页数据
	 * @return IFormData
	 */
	List<Map<String,Object>> queryByPage(IQueryDB queryDBManager, Integer pageRow,int curentPage);
    /****
	 * 查询数据行数
	 * @return int
	 */
    int queryOfRows(IQueryDB qeryDBManager);
    
    boolean excute(String sql);
    
    void setDataSource(Statement statement);
    Statement getDataSource();
}
