package com.dao.option;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.dao.DBConnectionUtil;

public class PreSearchDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(PreSearchDao.class);
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.PreSearchDao.java
	 * @Date:@2017 2017-4-10 下午5:11:10
	 * @Return_type:Map<String,String>
	 * @Desc :获得最老的一条预检索记录
	 */
	public Map<String,String> getSinglePreSearch() {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_preSearch where isSearched = ? order by timeGLWZ asc limit 0,1");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(0);
		setValues(lt);
		Map<String,String> map = new HashMap<String, String>();
		setSqlValue(sql.toString());
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				map.put("recordId", rs.getString("recordId"));
				map.put("username", rs.getString("username"));
				map.put("pic", rs.getString("pic"));
				map.put("stat", rs.getString("stat"));
				map.put("bigclass", rs.getString("bigclass"));
				map.put("smallclass", rs.getString("smallclass"));
				map.put("timeGLWZ", rs.getString("timeGLWZ"));
				map.put("precisionNum", rs.getString("precisionNum"));
			}
		} catch (Exception e) {
			log.error("PreSearchDao-getSinglePreSearch:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return map;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.PreSearchDao.java
	 * @Date:@2017 2017-4-13 下午3:36:57
	 * @Return_type:String
	 * @Desc : 检查同一张图片是否已经提交过。
	 */
	public String IsThisImgSearched(String userID,String picPath,String bigclass,String smallclass,String precisionNum) {
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT timeGLWZ FROM t_presearch WHERE username = ? AND pic = ? AND bigclass = ? AND smallclass = ? AND precisionNum = ?");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(userID);
		lt.add(picPath);
		lt.add(bigclass);
		lt.add(smallclass);
		lt.add(precisionNum);
		setValues(lt);
		setSqlValue(sql.toString());
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				return rs.getString("timeGLWZ");
			}
		} catch (Exception e) {
			log.error("PreSearchDao-IsThisImgSearched:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.PreSearchDao.java
	 * @Date:@2017 2017-4-10 下午3:51:44
	 * @Return_type:int
	 * @Desc : 获得等待检索的图片的数量
	 */
	public int getPreSearchImgNum() {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(*) num FROM T_preSearch  where isSearched = ? ");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(0);
		setValues(lt);
		
		setSqlValue(sql.toString());
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				return rs.getInt("num");
			}
		} catch (Exception e) {
			log.error("PreSearchDao-getPreSearchImgNum:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return 0;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.option.PreSearchDao.java
	 * @Date:@2017 2017-4-10 下午4:20:26
	 * @Return_type:int
	 * @Desc :增加待检索的图片记录
	 */
	public int addPreSearch(String username,String pic,String stat,String bigclass,String smallclass,String precisionNum) {

		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO t_presearch(recordId,username,pic,stat,bigclass,smallclass,timeGLWZ,precisionNum) VALUES (?,?,?,?,?,?,?,?)");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(UUID.randomUUID().toString());
		lt.add(username);
		lt.add(pic);
		lt.add(stat);
		lt.add(bigclass);
		lt.add(smallclass);
		lt.add(String.valueOf(System.currentTimeMillis()));
		lt.add(precisionNum);
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			return executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("PreSearchDao-addPreSearch:" + e.toString());
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
	 * @Full_path:com.dao.option.PreSearchDao.java
	 * @Date:@2017 2017-4-10 下午4:19:52
	 * @Return_type:int
	 * @Desc : 删除检索过的图片记录
	 */
	public int updatePreSearch(String recordId) {

		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_presearch SET isSearched = ? WHERE recordId = ?");
//		sql.append("DELETE FROM t_presearch WHERE username = ? AND pic = ?");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(1);
		lt.add(recordId);
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			return executeUpdate(conn, ps);
		} catch (Exception e) {
			log.error("PreSearchDao-delPreSearch:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn,ps,null);
		}
		return 0;
	}
}
