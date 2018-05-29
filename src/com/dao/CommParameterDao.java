package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * SELECT DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 0 DAY),'%Y-%m-%d') '今天',
DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d') '昨天',DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 2 DAY),'%Y-%m-%d') '前天'
 *
 */
public class CommParameterDao extends DBConnectionUtil{
/**
 * 
 * @Author:HaoMing(郝明)
 * @Project_name:trademark
 * @Full_path:com.dao.CommParameterDao.java
 * @Date:@2017 2017-4-18 下午2:18:19
 * @Return_type:String[]
 * @Desc :获得公共的时间函数
 */
	public String[] fetchDayArra() {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 0 DAY),'%Y-%m-%d') 'todayStr0', ");
		sql.append(" DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d') 'todayStr1', ");
		sql.append(" DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 2 DAY),'%Y-%m-%d') 'todayStr2', ");
		sql.append(" DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 3 DAY),'%Y-%m-%d') 'todayStr3' ");
		// set ?
		List<Object> lt = new ArrayList<Object>();
		setValues(lt);
		
//		setSqlValue(sql.toString());
		String[] returnArra = new String[4];
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				returnArra[0] = rs.getString("todayStr0");
				returnArra[1] = rs.getString("todayStr1");
				returnArra[2] = rs.getString("todayStr2");
				returnArra[3] = rs.getString("todayStr3");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return returnArra;
	}
	
}
