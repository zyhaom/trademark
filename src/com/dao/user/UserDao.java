package com.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dao.DBConnectionUtil;
import com.vo.user.UserVO;

public class UserDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(UserDao.class);
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.user.UserDao.java
	 * @Date:@2017 2017-3-21 下午5:12:45
	 * @Return_type:UserPropVO
	 * @Desc :判断登录
	 */
	public UserVO fetchUserVOByID(String userID) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT *  FROM t_user WHERE userID = ? ");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(userID);
		setValues(lt);
		
		setSqlValue(sql.toString());
		UserVO userVO = null;
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				userVO = new UserVO();
				userVO.setUserID(rs.getString("userID"));
				userVO.setUserPSW(rs.getString("userPSW"));
				userVO.setUserName(rs.getString("userName"));
			}
		} catch (Exception e) {
			log.error("UserDao-fetchUserVOByID:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return userVO;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.user.UserDao.java
	 * @Date:@2017 2017-3-21 下午5:21:45
	 * @Return_type:int
	 * @Desc :注册
	 */
	public int addUser(UserVO vo) {

		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO t_user(userID,userPSW,userName) VALUES (?,?,?) ");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(vo.getUserID());
		lt.add(vo.getUserPSW());
		lt.add(vo.getUserName());
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			return executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("UserDao-addUser:" + e.toString());
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
	 * @Full_path:com.dao.user.UserDao.java
	 * @Date:@2017 2017-3-21 下午5:23:40
	 * @Return_type:int
	 * @Desc :修改用户属性
	 */
	public int modifyUser(UserVO vo) {

		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_user SET userPSW = ?,userName =? WHERE userID = ? ");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(vo.getUserPSW());
		lt.add(vo.getUserName());
		lt.add(vo.getUserID());
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			return executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("UserDao-modifyUser:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn,ps,null);
		}
		return 0;
	}
}
