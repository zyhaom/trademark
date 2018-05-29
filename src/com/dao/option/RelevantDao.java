package com.dao.option;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dao.DBConnectionUtil;

/**
 * 
 * @Desc :维护跨类的dao
 */
public class RelevantDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(RelevantDao.class);
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.RelevantDao.java
	 * @Date:@2018 2018-5-22 下午1:54:46
	 * @Return_type:List<String>
	 * @Desc :检索
	 */
	public List<String> getRelevantTypes(String typeID) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT relevantType FROM t_relevantType where typeid = ? ");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(typeID);
		setValues(lt);
		List<String> list = new ArrayList<String>();
		setSqlValue(sql.toString());
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				list.add(rs.getString("relevantType"));
			}
		} catch (Exception e) {
			log.error("RelevantDao-getRelevantTypes:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return list;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.RelevantDao.java
	 * @Date:@2018 2018-5-22 下午1:56:06
	 * @Return_type:int
	 * @Desc :增加跨类记录
	 */
	public int addRelevantType(String typeID,String relevantType) {

		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_relevantType(typeid,relevantType) VALUES (?,?)");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(typeID);
		lt.add(relevantType);
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			return executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("RelevantDao-addRelevantType:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn,ps,null);
		}
		return 0;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.RelevantDao.java
	 * @Date:@2018 2018-5-22 下午1:55:48
	 * @Return_type:int
	 * @Desc :删除跨类记录
	 */
	public int delRelevantType(String typeID,String relevantType) {
		
		PreparedStatement ps = null;
		Connection conn = null;
		
		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_relevantType where typeid=? and relevantType=?");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(typeID);
		lt.add(relevantType);
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			return executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("RelevantDao-delRelevantType:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn,ps,null);
		}
		return 0;
	}
	
}
