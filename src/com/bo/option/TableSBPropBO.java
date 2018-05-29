package com.bo.option;

import java.util.List;
import java.util.Set;

import com.dao.option.TableSBPropDao;

public class TableSBPropBO {

	TableSBPropDao dao = new TableSBPropDao();
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.TableSBPropDao.java
	 * @Date:@2017 2017-3-24 下午12:07:48
	 * @Return_type:int
	 * @Desc :初始化sql列表
	 */
	public void initSql(Set<String> lSql) {
		dao.initSql(lSql);
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.TableSBPropDao.java
	 * @Date:@2017 2017-3-24 下午12:05:34
	 * @Return_type:int
	 * @Desc :清空指定表
	 */
	public void trancateTable(String tName) {
		dao.trancateTable(tName);
	}
}
