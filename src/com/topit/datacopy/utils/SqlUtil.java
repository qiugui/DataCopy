package com.topit.datacopy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.UpdatedInfo;
import com.topit.datacopy.dao.DataCopy;
import com.topit.datacopy.main.MainJFrame;

/**
 * @ClassName: SqlUtil
 * @Description: sql工具类
 * @author qiugui
 * @date 2015年3月16日 下午10:54:27
 * 
 */
public class SqlUtil {

	public static int updateNums = 0;
	public static int insertNums = 0;
	private static PreparedStatement ps;
	private static ResultSet rs;
	public static boolean _interrupted = false;
	public static double currentIndex = 0;

	public static void init() throws Exception {
		DBUtils.initDataSource();
	}

	/**
	 * @Title: selectUpdatedInfo
	 * @Description: 获得更新的信息
	 * @return
	 * @throws Exception
	 */

	public static List<UpdatedInfo> getUpdatedInfo() throws Exception {
		init();
		Connection s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		List<UpdatedInfo> updatedInfos = new ArrayList<UpdatedInfo>();
		List<String> dboCltypep = new ArrayList<String>();
		try {
			ps = s_connection
					.prepareStatement("SELECT * FROM martjs.updated_info");
			rs = ps.executeQuery();
			while (rs.next()) {
				String tablename = rs.getString("tablename");
				String primarykey = rs.getString("primarykey");
				String primarykeyvalue = rs.getString("primarykeyvalue");
				String column = rs.getString("column");
				String columntype = rs.getString("columntype");
				String operation = rs.getString("operation");
				String oldvalue = rs.getString("oldvalue");
				String newvalue = rs.getString("newvalue");

				UpdatedInfo updatedInfo = new UpdatedInfo(tablename,
						primarykey, primarykeyvalue, column, columntype,
						operation, oldvalue, newvalue);

				if (!DataCopy.DBOCLTYPEP.equals(tablename)) {
					updatedInfos.add(updatedInfo);
				} else {
					boolean flag = false;
					String dboCltypepColothno = primarykeyvalue.split("&")[1];
					if (!dboCltypep.contains(dboCltypepColothno)) {
						dboCltypep.add(dboCltypepColothno);
						flag = true;
					}
					if (flag) {
						updatedInfos.add(updatedInfo);
					}
				}

			}
			return updatedInfos;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, ps, s_connection);
		}
	}

	
	/**
	 * @Title: getInsertedRecords
	 * @Description: 根据某一条件，查询被插入的记录，返回多条插入记录
	 * @param tablename
	 * @param condition
	 */

	public static List<List<Map<String, String>>> getInsertedRecords(
			String tablename, String condition) throws Exception {
		Connection s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		try {
			List<List<Map<String, String>>> allResults = new ArrayList<List<Map<String, String>>>();
			String sql = "SELECT * FROM " + tablename + " WHERE " + condition;
			ps = s_connection.prepareStatement(sql);
			rs = ps.executeQuery();
			if(DataCopy.DBOCLTYPEP.equals(tablename))
			{	    
			    getDBcurrentTime();
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while (rs.next()) {
				List<Map<String, String>> results = new ArrayList<Map<String, String>>();
				for (int i = 1; i <= numberOfColumns; i++) {
					Map<String, String> result = new HashMap<String, String>();
					String nameOfColumn = rsmd.getColumnName(i);
					String valueOfColumn = rs.getString(i);
					String typeOfColumn = rsmd.getColumnTypeName(i);
					result.put("nameOfColumn", nameOfColumn);
					result.put("valueOfColumn", valueOfColumn);
					result.put("typeOfColumn", typeOfColumn);
					results.add(result);
				}
				allResults.add(results);
			}
			return allResults;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(rs, ps, s_connection);
		}
	}
    /**
     * 
     * @Title: getDBcurrentTime   
     * @Description: 获取数据库的当前时间   
     * @param s_connection
     */
	private static void getDBcurrentTime() throws Exception{
		Connection s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		//记录上本次查询时间，也即是本次同步的时间
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String date="";
		String queryTime="select getdate()  currenTime";//获取当前数据库的时间
		PreparedStatement pstime = null;
		ResultSet timers=null;
		try {
			pstime = s_connection.prepareStatement(queryTime);
			 timers=pstime.executeQuery();
			while(timers.next())
			{
			   date=timers.getString("currenTime");
			}
			long time=format.parse(date).getTime()-1000*60*3;
			Constants.currentCopyTime=format.format(new Date(time));
		} catch (SQLException e) {
			 e.printStackTrace();
			 
		}finally{
			close(timers, pstime, s_connection);
		}
	
	   
	}

	/**
	 * @Title: updata
	 * @Description: 对待更新数据库进行更新/插入
	 * @param sqls
	 * @param parameters
	 * @throws Exception
	 * @throws Exception
	 */

	public static void updata(List<String> sqls, Connection connection,Statement statement)
			throws Exception {
		try {
			for (int i = 0; i < sqls.size(); i++) {
				String sql = sqls.get(i);
				if (sql.startsWith("UPDATE")) {
					updateNums++;
				} else {
					insertNums++;
				}
				statement.addBatch(sql);
				Constants.logger.info(sql);
				MainJFrame.log.append("\t" + sql + "\n");
				MainJFrame.log.setCaretPosition(MainJFrame.log.getText()
						.length());
			}
			statement.executeBatch();

		} catch (Exception e) {
			Constants.logger.error("数据拷贝出现错误！" + e.getMessage(), e);
			connection.rollback();
			throw new RuntimeException(e);
		}
	}

	public static void deleteAll(String tablename) throws Exception {

		Connection s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		try {
			s_connection.setAutoCommit(false);
			String sql = "DELETE FROM " + tablename;
			ps = s_connection.prepareStatement(sql);
			ps.executeUpdate();
			Constants.logger.info("删除更新记录表！\n" + sql);
			MainJFrame.log.append("\n删除更新记录表！\n" + sql + "\n\n");
			s_connection.commit();
		} catch (Exception e) {
			MainJFrame.log.append("\n删除更新记录表出现错误！\n");
			Constants.logger.error("删除更新记录表出现错误！" + e.getMessage(), e);
			s_connection.rollback();
			throw new RuntimeException(e);
		} finally {
			close(null, ps, s_connection);
		}
	}

	/**
	 * @Title: close
	 * @Description: 关闭资源
	 * @param rs
	 * @param ps
	 * @param cs
	 * @throws Exception
	 */

	public static void close(ResultSet rs, Statement ps, Connection cs)
			throws Exception {
		if (rs != null) {
			rs.close();
		}
		rs = null;
		if (ps != null) {
			ps.close();
		}
		ps = null;
		DBUtils.shutDownConnection(cs);
	}

	/**
	 * 
	 * @Title: openTransaction
	 * @Description: 开启事务
	 * @param connection
	 * @throws SQLException
	 */
	public static void openTransaction(Connection connection)
			throws SQLException {
		if (connection != null&&!connection.isClosed()) {
			connection.setAutoCommit(false);
		}
	}

	public static void commit(Connection connection) throws Exception {
			if (connection != null) {
				connection.commit();
				connection.setAutoCommit(true);
				close(null, null, connection);
			}
	}
}
