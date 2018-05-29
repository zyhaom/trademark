package com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;  
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource; //import com.mysql.*;

import org.apache.log4j.Logger;

/**
 * 通用的DAO 数据库访问类
 */
public class DBConnectionUtil {

	private final static Logger log = Logger.getLogger(DBConnectionUtil.class);

	@SuppressWarnings("unused")
	private String sqlValue; // sql 语句
	private List<?> values; // sql语句参数列表

	/**
	 * 在web 容器中配置连接池。 通用的连接方法。 请勿修改
	 * 
	 * @author haom
	 * @date 2011-10-26 22:18:53
	 */
	protected static Connection getConnection(Connection conn) {
		try {
			Context ctx = new InitialContext();
			// DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySql_teleinfomanager");
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySql_pic_search");
			if (ds != null) {
				conn = ds.getConnection();
//				log.info("conn_opened");
			}
		} catch (NamingException e) {
			log.error("DBConnectionUtil-getConnection-1:" + e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("DBConnectionUtil-getConnection-2:" + e.toString());
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-10-27 下午10:22:42
	 * @desc : 通用的关闭数据连接资源的方法，请勿修改。
	 */
	protected void closeDBSource(Connection conn, Statement s, ResultSet rs) {
		try {
			if (s != null) {
				s.close();
				s = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource Statement 不能正常关闭");
			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource ResultSet 不能正常关闭");
			e.printStackTrace();
		}
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource Connection 不能正常关闭");
			e.printStackTrace();
		}
		log.info("conn_closeed");
	}

	protected void closeDBSource(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource PreparedStatement 不能正常关闭");
			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource ResultSet 不能正常关闭");
			e.printStackTrace();
		}
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
//				log.info("conn_closeed");
			}
		} catch (Exception e) {
			log.error("closeDBsource Connection 不能正常关闭");
			e.printStackTrace();
		}
	}

	protected void closeDBSource(Connection conn, CallableStatement cs, ResultSet rs) {
		try {
			if (cs != null) {
				cs.close();
				cs = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource CallableStatement 不能正常关闭");
			e.printStackTrace();
		}
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource ResultSet 不能正常关闭");
			e.printStackTrace();
		}
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			log.error("closeDBsource Connection 不能正常关闭");
			e.printStackTrace();
		}
//		log.info("conn_closeed");
	}

	/**
	 * 设置SQL语句
	 * 
	 * @param sqlValue
	 *            写入
	 */
	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}

	/**
	 * 设置SQL语句参数列表 写入
	 * 
	 * @param values
	 */
	public void setValues(List<?> values) {
		this.values = values;
	}

	/**
	 * 设定SQL语句参数 读入ps
	 * 
	 * @param ps
	 * @param values
	 * @throws SQLException
	 */
	private void setValues(PreparedStatement ps, List<?> values) {
		if (!values.isEmpty()) {
			for (int i = 0; i < values.size(); i++) {
				Object v = values.get(i);
				try {
					ps.setObject(i + 1, v);
				} catch (SQLException e) {
					log.error("DBConnectionUtil-setValues:" + e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行查询
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet executeQuery(Connection conn, PreparedStatement ps, ResultSet rs) {

		try {
			// 如果sql语句参数 参数列表 不为空 并且 参数大于零
			if (values != null && values.size() > 0) {
				try {
					// conn = getConnection(conn);// 连接数据库
					if (conn != null) {
						// ps = conn.prepareStatement(sqlValue); // 传入SQL语句 执行SQL语句
						setValues(ps, values); // sql语句参数 加入到 ps 中
						rs = ps.executeQuery(); // 执行sql语句 ResultSet 接收
					}
				} catch (Exception e) {
					log.error("DBConnectionUtil-executeQuery-1:" + e.toString());
					e.printStackTrace();
				}

			} else {
				try {
					// conn = getConnection(conn); // 连接数据库
					if (conn != null) {
						// ps = conn.prepareStatement(sqlValue);
						rs = ps.executeQuery(); // 查询内容 返回结果集
					}
				} catch (Exception e) {
					log.error("DBConnectionUtil-executeQuery-2:" + e.toString());
					e.printStackTrace();
				}
			}

		} catch (RuntimeException e) {
			log.error("DBConnectionUtil-executeQuery-3:" + e.toString());
			e.printStackTrace();
		}

		return rs; // 返回结果集
	}

	/**
	 * 执行 增 删 改 返回影响结果
	 */
	public int executeUpdate(Connection conn, PreparedStatement ps) {
		int noOfRows = 0;
		try {
			if (values != null && values.size() > 0) {
				try {
					// conn = getConnection(conn); // 连接数据库
					if (conn != null) {
						// ps = conn.prepareStatement(sqlValue); // 传入sql语句
						setValues(ps, values); // 将参数列表 加入ps中
						noOfRows = ps.executeUpdate(); // 执行sql语句 返回影响数
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					// conn = getConnection(conn); // 连接数据库
					if (conn != null) {
						// ps = conn.prepareStatement(sqlValue);
						noOfRows = ps.executeUpdate();
					}
				} catch (Exception e) {
					log.error("DBConnectionUtil-executeUpdate-1:" + e.toString());
					e.printStackTrace();
				}
			}
		} catch (RuntimeException e) {
			log.error("DBConnectionUtil-executeUpdate-2:" + e.toString());
			e.printStackTrace();
		}
		return noOfRows; // 返回结果
	}

	/*********************** 以下是专门为MySql数据库写的事务处理方法 ***************/

	/**
	 * 事务开始方法
	 */
	public void beginTransaction(Connection conn, PreparedStatement ps) {
		try {
			// conn = getConnection(conn);
			ps = conn.prepareStatement("begin");
			ps.addBatch();
			ps.executeBatch();
		} catch (SQLException e) {
			log.error("DBConnectionUtil-beginTransction" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-2 下午05:07:50
	 * @desc :提交的方法.
	 */
	public void commitTransaction(Connection conn) {
		try {
			// conn = getConnection(conn);
			conn.commit();
		} catch (SQLException e) {
			log.error("DBConnectionUtil-commitTransction" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-2 下午05:07:50
	 * @desc :回滚的方法.
	 */
	public void rollbackTransaction(Connection conn) {
		try {
			// conn = getConnection(conn);
			conn.rollback();
		} catch (SQLException e) {
			log.error("DBConnectionUtil-rollbankTransction" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-2 下午05:03:16
	 * @desc :设置当前连接conn 的自动提交属性为假 的方法。
	 */
	public void setAutoCommit(Connection conn, boolean flag) {
		try {
			// conn.getAutoCommit();
			// conn = getConnection(conn);
			conn.setAutoCommit(flag);
		} catch (SQLException e) {
			log.error("DBConnectionUtil-setAutoCommit" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-8 下午03:44:53
	 * @desc : 获得数据库服务器的时间
	 */
	public String getDBserviceTime() {
		// select now();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		String now = "";
		
		try {
			conn = getConnection(conn);
			ps = conn.prepareStatement("select now() now");
			rs = (ResultSet) executeQuery(conn, ps, rs);
			if (rs != null && rs.next()) {
				now = rs.getString("now");
			}
		} catch (SQLException e) {
			log.error("DBConnectionUtil-getDBserviceTime:" + e.toString());
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
		return now;
	}
	
	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-8 下午03:46:20
	 * @desc :#根据 schema table_name 
		SELECT concat('app.set',
		              upper(substring(column_name, 1, 1)),
		              substring(column_name, 2, length(column_name)),          
		              case when data_type = 'varchar' then '(rs.getString("'
		                   when data_type = 'timedtamp' then '(rs.getString("'
		                   when data_type = 'int' then '(rs.getInt("'
		                   when data_type = 'float' then '(rs.getFloat("'
		                   else '' end ,
		              column_name,
		              '"));') "result"
		  FROM information_schema.COLUMNS
		 WHERE table_schema = 'haom' 
		 AND table_name = 'T_Application'
	 */
	private void generaModelSet(String schema,String tName){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		String driverName = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/haom";
		String userName = "root";
		String userPwd = "123456";
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT concat('app.set',                                                   ");
		sb.append("            upper(substring(column_name, 1, 1)),                           ");
		sb.append("            substring(column_name, 2, length(column_name)),                ");
		sb.append("            case when data_type = 'varchar' then '(rs.getString(\"'         ");
		sb.append("                 when data_type = 'timedtamp' then '(rs.getString(\"'       ");
		sb.append("                 when data_type = 'int' then '(rs.getInt(\"'                ");
		sb.append("                 when data_type = 'float' then '(rs.getFloat(\"'            ");
		sb.append("                 else '' end ,                                             ");
		sb.append("            column_name,                                                   ");
		sb.append("            '\"));') \"result\"                                               ");
		sb.append("FROM information_schema.COLUMNS                                                               ");
		sb.append("WHERE table_schema = ?                                                   ");
		sb.append("AND table_name = ?                                                       ");
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			ps = conn.prepareStatement(sb.toString());
			ps.setString(2, tName);
			ps.setString(1, schema);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				System.out.println(rs.getString("result"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
	}
	
	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-8 下午05:26:09
	 * @desc :#根据 schema table_name 生成model的各个私有属性
		select concat('private ',
		               case 
		                   when data_type = 'varchar' then 'String '
		                   when data_type = 'timetamp' then 'String ' 
		                   when data_type = 'int' then 'int '
		                   when data_type = 'float' then 'float '
		                   else '' end,
		               lower(substring(column_name,1,1)),substring(column_name,2,length(column_name)),              
		               case 
		                   when data_type = 'varchar' then ' = null ;'
		                   when data_type = 'timestamp' then ' = null ;'
		                   when data_type = 'int' then ' = 0 ;'
		                   when data_type = 'float' then ' = 0 ;'
		                   else '' end ,
		               '//',column_comment) "result"
		from information_schema.columns 
		WHERE table_schema = 'haom' 
			AND table_name = 't_mpinfo' 
	 */
	private void getModelProp(String schema,String tName){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		String driverName = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/haom";
		String userName = "root";
		String userPwd = "123456";
		
		StringBuffer sb = new StringBuffer();
		sb.append("select concat('private ',                          																						 ");
		sb.append("               case                                                                             ");
		sb.append("                   when data_type = 'varchar' then 'String '                                    ");
		sb.append("                   when data_type = 'timetamp' then 'String '                                   ");
		sb.append("                   when data_type = 'int' then 'int '                                           ");
		sb.append("                   when data_type = 'float' then 'float '                                       ");
		sb.append("                   else '' end,                                                                 ");
		sb.append("               lower(substring(column_name,1,1)),substring(column_name,2,length(column_name)),  ");            
		sb.append("               case                                                                             ");
		sb.append("                   when data_type = 'varchar' then ' = null ;'                                  ");
		sb.append("                   when data_type = 'timestamp' then ' = null ;'                                ");
		sb.append("                   when data_type = 'int' then ' = 0 ;'                                         ");
		sb.append("                   when data_type = 'float' then ' = 0 ;'                                       ");
		sb.append("                   else '' end ,                                                                ");
		sb.append("               '//',column_comment) 'result'                                                    ");
		sb.append("from information_schema.columns                                                                 ");
		sb.append("WHERE table_schema = ?                                                                     ");
		sb.append("	AND table_name = ?                                                                    ");
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			ps = conn.prepareStatement(sb.toString());
			ps.setString(2, tName);
			ps.setString(1, schema);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				System.out.println(rs.getString("result"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
	}
	
	/**
	 * 
	 * @author HaoMing
	 * @2011 2011-11-8 下午05:42:54
	 * @desc :#根据 schema table_name 向ps中放值
	 SELECT concat('ls.add(ct.get',
		              upper(substring(column_name, 1, 1)),
		              substring(column_name, 2, length(column_name)),          
		              '()',
		              ');') "result"
		  FROM information_schema.COLUMNS
		 WHERE table_schema = 'haom' 
		 AND table_name = 't_itemizect'
	 */
	private void preparePS(String schema,String tName){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		String driverName = "com.mysql.jdbc.Driver";
		String dbURL = "jdbc:mysql://localhost:3306/haom";
		String userName = "root";
		String userPwd = "123456";
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT concat('ls.add(ct.get',                                ");
		sb.append("              upper(substring(column_name, 1, 1)),            ");
		sb.append("              substring(column_name, 2, length(column_name)), ");         
		sb.append("              '()',                                           ");
		sb.append("              ');') 'result'                                  ");
		sb.append("  FROM information_schema.COLUMNS                             ");
		sb.append(" WHERE table_schema = ?                                  ");
		sb.append(" AND table_name = ?                               ");
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
			ps = conn.prepareStatement(sb.toString());
			ps.setString(2, tName);
			ps.setString(1, schema);
			rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				System.out.println(rs.getString("result"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBSource(conn, ps, rs);
		}
	}
	
	public static void main(String[] args) {
//		new DBConnectionUtil().generaModelSet("haom", "t_Compare");
//		new DBConnectionUtil().getModelProp("haom", "t_department");
//		new DBConnectionUtil().preparePS("haom", "t_application");
//		System.out.println(CommParmeter.getCommParmeter("telecomPath"));
	}
}
