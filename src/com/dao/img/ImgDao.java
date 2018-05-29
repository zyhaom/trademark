package com.dao.img;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dao.DBConnectionUtil;
import com.util.CommUtil;
import com.util.prop.CommParameter;

public class ImgDao extends DBConnectionUtil {

	private final static Logger log = Logger.getLogger(ImgDao.class);
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-3-29 上午9:43:57
	 * @Return_type:Map<String,String>
	 * @Desc :根据typeID获得dat的全路径
	 */
	public Map<String,String> getDatPathByTypeID(String bigclass,String smallclass){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer();
		if("".equals(smallclass)){
			// 拼接SQL
			sql.append("SELECT * FROM t_sbtype WHERE dependTypeId = ? ");
			// set ?
			List<Object> lt = new ArrayList<Object>();
			lt.add(bigclass);
			setValues(lt);
		}else{
			// 拼接SQL
//			sql.append("SELECT * FROM t_sbtype WHERE typeid IN ('01','0101','0102') and dependTypeId = ? ");
			String[] typeIDs=CommUtil.strToArray(smallclass, "~");
			sql.append("SELECT * FROM t_sbtype");
			if(typeIDs.length>0){
				sql.append(" WHERE typeid IN ( ");
				for (int i = 0; i < typeIDs.length; i++) {
					sql.append("?");
					if(i!=typeIDs.length-1){
						sql.append(",");
					}
				}
				sql.append(" ) ");
			}
			sql.append(" and dependTypeId = ? ");
			// set ?
			List<Object> lt = new ArrayList<Object>();
			if(typeIDs.length>0){
				for (int i = 0; i < typeIDs.length; i++) {
					lt.add(typeIDs[i]);
				}
			}
			lt.add(bigclass);
			setValues(lt);
		}
			
//		setSqlValue(sql.toString());
		Map<String,String> map  = new HashMap<String, String>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				map.put(rs.getString("typeID"), rs.getString("typeFolder"));
			}
//			map.put("0701", "E:\\trademark\\dat\\07\\01\\");
		} catch (Exception e) {
			log.error("ImgDao-getDatPathByTypeID:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return map;
	}
	
	public Map<String,String> getKuaLei(String bigclass){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer();
		
			// 拼接SQL
			sql.append("SELECT * FROM t_sbtype WHERE dependTypeId in (select relevantType from t_relevantType where typeid = ?)");
			// set ?
			List<Object> lt = new ArrayList<Object>();
			lt.add(bigclass);
			setValues(lt);
		
//		setSqlValue(sql.toString());
		Map<String,String> map  = new HashMap<String, String>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				map.put(rs.getString("typeID"), rs.getString("typeFolder"));
			}
		} catch (Exception e) {
			log.error("ImgDao-getKuaLei:" + e.toString());
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
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-4-19 下午12:12:14
	 * @Return_type:Set<String>
	 * @Desc :获取在系统中的检索图片集合
	 */
	public Set<String> getSearchedImg(){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer();
		
		// 拼接SQL
		sql.append("SELECT DISTINCT pic FROM t_presearch");
		// set ?
		List<Object> lt = new ArrayList<Object>();
		setValues(lt);
		
			
//		setSqlValue(sql.toString());
		Set<String> set = new HashSet<String>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				set.add(rs.getString("pic"));
			}
		} catch (Exception e) {
			log.error("ImgDao-getSearchedImg:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		
		return set;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-4-19 下午12:09:11
	 * @Return_type:boolean
	 * @Desc :批量删除检索历史记录
	 */
	public boolean delSearchedPicList(String del){
		PreparedStatement ps = null;
		Connection conn = null;
		String[] dels = CommUtil.strToArray(del, "~");
		
		StringBuffer sql = new StringBuffer();
		
//		sql.append("DELETE FROM t_presearch WHERE recordid IN ('111','112')");
		sql.append("DELETE FROM t_presearch ");
		if(dels.length>0){
			sql.append(" WHERE recordid IN ( ");
			for (int i = 0; i < dels.length; i++) {
				sql.append("?");
				if(i!=dels.length-1){
					sql.append(",");
				}
			}
			sql.append(" ) ");
		}
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		if(dels.length>0){
			for (int i = 0; i < dels.length; i++) {
				lt.add(dels[i]);
			}
		}
		setValues(lt);
			
//		setSqlValue(sql.toString());
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			int num = executeUpdate(conn, ps);
			
			if(num>=1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error("ImgDao-delSearchedPicList:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, null);
		}
		
		return false;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-3-30 下午3:33:58
	 * @Return_type:Map<String,String>
	 * @Desc :根据用户ID获得检索完毕的图片
	 */
	public List<Map<String,String>> getSearchedPicList(String userID){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer();
		
		/**
		 * SELECT DISTINCT(picpath),timeglwz ,SUBSTR(datpathid,1,2),(SELECT typename FROM t_sbtype WHERE typeid = SUBSTR(datpathid,1,2))  recordclass FROM t_searchhistory 
WHERE userid = '13404144827'
ORDER BY timeglwz DESC
		 */
		// 拼接SQL
//		sql.append("SELECT DISTINCT(picpath),timeGLWZ,SUBSTR(datpathid,1,2) recordclass,(SELECT typename FROM t_sbtype WHERE typeid = SUBSTR(datpathid,1,2))  recordclassname FROM t_searchhistory  ");
//		sql.append("WHERE userid = ? ");
//		sql.append("order by timeGLWZ desc");
		sql.append("SELECT DISTINCT recordId, pic,bigclass,smallclass,timeglwz ,(SELECT typename FROM t_sbtype WHERE typeid = bigclass)  recordclassname ");
		sql.append("FROM t_presearch WHERE username = ? and issearched = 1 ");
		sql.append("order by timeGLWZ desc");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(userID);
		setValues(lt);
			
//		setSqlValue(sql.toString());
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				Map<String,String> map  = new HashMap<String, String>();
//				map.put("timeGLWZ", rs.getString("timeGLWZ"));
				map.put("recordclass", rs.getString("bigclass")+rs.getString("recordclassname"));
				map.put("findtime", CommUtil.timeFormat(Long.parseLong(rs.getString("timeGLWZ"))));
				map.put("recordId", rs.getString("recordId"));
				map.put("pic", rs.getString("pic"));
				map.put("findcan", "图片搜索");
				list.add(map);
			}
		} catch (Exception e) {
			log.error("ImgDao-getSearchedPicList:" + e.toString());
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
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-3-30 下午3:40:58
	 * @Return_type:Map<String,String>
	 * @Desc :根据图片查找相似商标的类别列表
	 * 
	 */
	public List<Map<String,String>> getSearchedTypeList(String recordId){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer();
		
		// 拼接SQL
		sql.append("SELECT ? rid,(SELECT typename FROM t_sbtype WHERE typeid = t_sbprop.typeid) tname,typeid,COUNT(*) num FROM t_sbprop  ");
		sql.append("WHERE datpathid IN (SELECT datpathid FROM t_searchhistory WHERE recordId = ?) GROUP BY typeid ");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(recordId);
		lt.add(recordId);
		setValues(lt);
			
//		setSqlValue(sql.toString());
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				Map<String,String> m = new HashMap<String, String>();
				m.put("clsid", rs.getString("typeid"));
				m.put("clsname", rs.getString("typeid")+rs.getString("tname"));
				m.put("nums", rs.getString("num"));
				m.put("findresultId", rs.getString("rid"));
				list.add(m);
			}
		} catch (Exception e) {
			log.error("ImgDao-getSearchedTypeList:" + e.toString());
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
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-3-30 下午3:56:58
	 * @Return_type:List<Map<String,String>>
	 * @Desc :根据图片和商标类型id检索商标特征文件列表
	 * 
	 */
	public List<Map<String,String>> getSearchedDatList(String recordId,String typeid,int pageSize, int pageNumber){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		int numper = 0;
		if (pageNumber > 1) {
			numper = (pageNumber - 1) * pageSize;
		}
		
		StringBuffer sql = new StringBuffer();
		
		// 拼接SQL
		sql.append("SELECT b.*,a.similardistance FROM t_searchhistory a, t_sbprop b ");
		sql.append("WHERE a.datpathid = b.datpathid ");
		sql.append("AND a.recordId = ? ");
		sql.append("AND b.typeid = ? ");
		sql.append("ORDER BY a.similardistance ");
		sql.append("limit " + numper + "," + pageSize + " ");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(recordId);
		lt.add(typeid);
		setValues(lt);
			
//		setSqlValue(sql.toString());
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				Map<String,String> m = new HashMap<String, String>();
				m.put("tradename", rs.getString("sbName"));
				m.put("id", rs.getString("datPathID"));
				m.put("status", rs.getString("stat"));
				m.put("registCode", rs.getString("reqID"));
				m.put("registPerson", rs.getString("reqMan"));
				m.put("imgurl", CommParameter.getCommParameterByKey("preUrl")+rs.getString("datPathID")+".jpg");
//				m.put("similardistance", rs.getString("similardistance"));
				list.add(m);
			}
		} catch (Exception e) {
			log.error("ImgDao-getSearchedDatList:" + e.toString());
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
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-4-19 上午11:42:25
	 * @Return_type:int
	 * @Desc : 获取记录总条数
	 */
	public int getSearchedDatListToTalnum(String recordId,String typeid){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer();
		
		// 拼接SQL
		sql.append("SELECT count(*) num FROM t_searchhistory a, t_sbprop b ");
		sql.append("WHERE a.datpathid = b.datpathid ");
		sql.append("AND a.recordId = ? ");
		sql.append("AND b.typeid = ? ");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(recordId);
		lt.add(typeid);
		setValues(lt);
			
//		setSqlValue(sql.toString());
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			if (rs != null && rs.next()) {
				return rs.getInt("num");
			}
		} catch (Exception e) {
			log.error("ImgDao-getSearchedDatListToTalnum:" + e.toString());
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
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-3-30 下午4:02:01
	 * @Return_type:List<Map<String,String>>
	 * @Desc :获取单个dat记录
	 */
	public Map<String,String> getSingleDat(String datpathid){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer();
		
		// 拼接SQL
		sql.append("SELECT *,(SELECT typename FROM t_sbtype WHERE typeid = t_sbprop.typeid) tname FROM t_sbprop WHERE datpathid = ? ");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
//		datpathid = datpathid.replaceAll("\\\\", "\\\\\\\\");
		lt.add(datpathid);
		setValues(lt);
			
//		setSqlValue(sql.toString());
		Map<String,String> m = new HashMap<String, String>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				
				m.put("datPathID", rs.getString("datPathID"));
				m.put("classID", rs.getString("typeID"));
				m.put("classname", rs.getString("tname"));
				m.put("tradename", rs.getString("sbName"));
				m.put("registCode", rs.getString("reqID"));
				m.put("reqStartDate", rs.getString("reqStartDate"));
				m.put("registPerson", rs.getString("reqMan"));
				m.put("reqFinishDate", rs.getString("reqFinishDate"));
				m.put("status", rs.getString("stat"));
				m.put("imgurl", CommParameter.getCommParameterByKey("preUrl")+rs.getString("datpathid")+".jpg");
				
			}
		} catch (Exception e) {
			log.error("ImgDao-getSingleDat:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		return m;
	}
	
	/**
	 * 
	 * @Author:HaoMing(郝明)
	 * @Project_name:trademark
	 * @Full_path:com.dao.img.ImgDao.java
	 * @Date:@2017 2017-4-10 下午12:32:37
	 * @Return_type:Map<String,String>
	 * @Desc :获得类型信息.
	 */
	public Map<String,String> getClassName(String typeid){
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		StringBuffer sql = new StringBuffer();
		
		// 拼接SQL
		sql.append("SELECT typeid smallclassid,typename smallclassname,SUBSTR(?,1,2) bigclassid,(SELECT typename FROM t_sbtype WHERE typeid = SUBSTR(?,1,2)) bigclassname FROM t_sbtype WHERE typeid = ?");
		
		// set ?
		List<Object> lt = new ArrayList<Object>();
		lt.add(typeid);
		lt.add(typeid);
		lt.add(typeid);
		setValues(lt);
		
		Map<String,String> m = new HashMap<String, String>();
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement(sql.toString());
			rs = (ResultSet) executeQuery(conn, ps, rs);
			
			while (rs != null && rs.next()) {
				
				m.put("smallclassid", rs.getString("smallclassid"));
				m.put("smallclassname", rs.getString("smallclassname"));
				m.put("bigclassid", rs.getString("bigclassid"));
				m.put("bigclassname", rs.getString("bigclassname"));
				
			}
		} catch (Exception e) {
			log.error("ImgDao-getSingleDat:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		return m;
	}
	
	
}
