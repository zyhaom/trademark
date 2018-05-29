package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.vo.SBTypeVO;

/**
 * SELECT CONCAT('vo.set',
		              UPPER(SUBSTRING(column_name, 1, 1)),
		              SUBSTRING(column_name, 2, LENGTH(column_name)),          
		              CASE WHEN data_type = 'varchar' THEN '(rs.getString("'
		                   WHEN data_type = 'timedtamp' THEN '(rs.getString("'
		                   WHEN data_type = 'int' THEN '(rs.getInt("'
		                   WHEN data_type = 'float' THEN '(rs.getFloat("'
		                   ELSE '' END ,
		              column_name,
		              '"));') "result"
		  FROM information_schema.COLUMNS
		 WHERE table_schema = 'pic_search' 
		 AND table_name = 't_sbtype'
 *
 */
public class SBTypeDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(SBTypeDao.class);
	
	public List<SBTypeVO> fetchSBTypeVOList(String dependTypeID) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		// 拼接SQL
		StringBuffer sql = new StringBuffer();
		
		if(dependTypeID!=null){
			sql.append("SELECT * from t_sbtype WHERE dependTypeID = ? order by typeID asc ");
		}else{
			sql.append("SELECT * from t_sbtype WHERE typeid <> '00' order by typeID asc ");
		}

		// set ?
		List<Object> lt = new ArrayList<Object>();
		if(dependTypeID!=null){
			lt.add(dependTypeID);
		}
		setValues(lt);
		
		List<SBTypeVO> list = new ArrayList<SBTypeVO>();
		setSqlValue(sql.toString());
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				SBTypeVO  vo= new SBTypeVO();
				vo.setTypeID(rs.getString("typeID"));
				vo.setDependTypeID(rs.getString("dependTypeID"));
				vo.setTypeName(rs.getString("typeName"));
//				vo.setTypeFolder(rs.getString("typeFolder"));
				list.add(vo);
			}
		} catch (Exception e) {
			log.error("SBTypeDao-fetchSBTypeVOList:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return list;
	}
}
