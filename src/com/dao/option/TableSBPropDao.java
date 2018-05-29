package com.dao.option;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import com.dao.DBConnectionUtil;

public class TableSBPropDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(TableSBPropDao.class);
	
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

		PreparedStatement ps = null;
		Connection conn = null;

		try {
			Iterator<?> it = lSql.iterator();
			conn = getConnection(conn);
			String sql = "";
			while(it.hasNext()){
				// 拼接SQL
				sql = (String)it.next();
				// set ?
//				List<Object> lt = new ArrayList<Object>();
//				setValues(lt);
				
//				setSqlValue(sql);
				ps = conn.prepareStatement(sql);
				executeUpdate(conn, ps);
			}
		} catch (Exception e) {
			log.error("TableSBPropDao-initSql:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn,ps,null);
		}
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

		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("truncate table "+tName);

		// set ?
		List<Object> lt = new ArrayList<Object>();
		
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("TableSBPropDao-trancateTable:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn,ps,null);
		}
	}
}
