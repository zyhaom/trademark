package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vo.PicPropVO;

public class PicPropDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(PicPropDao.class);
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:picsearch
	 * @Full_path:com.dao.PicPropDao.java
	 * @Date:@2014 2014-9-3 下午2:21:34
	 * @Return_type:PicPropVO
	 * @Desc : SELECT productName,brandName,companyName, originAddr,originTime, price FROM t_pic_prop WHERE picid = (SELECT _p_picid FROM t_pic WHERE picname = '24.jpg' AND pictypeid = 1)
	 */
	public PicPropVO fetchResultVOByPic(String searchPicName) {
		PicPropVO picPropVO = null;

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT productName,brandName,companyName, originAddr,originTime, price ");
		sql.append(" FROM t_pic_prop WHERE picid =  (SELECT t_p_picid FROM t_pic ");
		sql.append(" WHERE picname = ? ");
		sql.append("AND pictypeid = 1) ");
		sql.append(" LIMIT 0,1 ");

		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(searchPicName);
		setValues(lt);
		
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				picPropVO = new PicPropVO();

				picPropVO.setProductName(rs.getString("productName"));
				picPropVO.setBrandName(rs.getString("brandName"));
				picPropVO.setCompanyName(rs.getString("companyName"));
				picPropVO.setOriginAddr(rs.getString("originAddr"));
				picPropVO.setOriginTime(rs.getString("originTime"));
				picPropVO.setPrice(rs.getFloat("price"));
			}
		} catch (Exception e) {
			log.error("PicPropDao-fetchResultVOByPic:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return picPropVO;
	}
}
