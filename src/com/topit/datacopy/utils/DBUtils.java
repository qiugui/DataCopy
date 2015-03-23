package com.topit.datacopy.utils;

import java.sql.Connection;

import org.apache.commons.dbcp.BasicDataSource;

import com.topit.datacopy.config.ConnectConfig;
import com.topit.datacopy.config.Constants;
import com.topit.datacopy.manager.ConfigManager;

/**
 * 
 * @ClassName: DBUtils
 * @Description:数据库操作工具类
 * @author gaodachuan
 * @date 2015年3月11日 下午2:28:31
 *
 */
public class DBUtils {

	private static BasicDataSource sourceDS = null;
	private static BasicDataSource targetDs = null;
	private static Connection sourceCon = null;
	private static Connection targetCon = null;

	/**
	 * 
	 * @Title: initDataSource
	 * @Description: 初始化数据源
	 * @param manager
	 * @throws Exception 
	 */
	public static void initDataSource() throws Exception {

		ConnectConfig sourceDB = ConfigManager
				.getDBConfig(Constants.DBConnection.SOURCEDB);
		ConnectConfig targetDB = ConfigManager
				.getDBConfig(Constants.DBConnection.TARGETDB);
		sourceDS = setDataSourceByConfig(sourceDS, sourceDB);
		targetDs = setDataSourceByConfig(targetDs, targetDB);
		sourceCon = sourceDS.getConnection();
		targetCon = targetDs.getConnection();
	}

	private static BasicDataSource setDataSourceByConfig(
			BasicDataSource dataSource, ConnectConfig config) throws Exception {
		if (dataSource == null) {
			dataSource = new BasicDataSource();
		} else {
			dataSource.close();
			dataSource = null;
			dataSource = new BasicDataSource();
			shutDownConnection(sourceCon);
			shutDownConnection(targetCon);
		}
		dataSource.setDriverClassName("com.sybase.jdbc3.jdbc.SybDriver");
		dataSource.setUrl("jdbc:sybase:Tds:" + config.getIp() + ":"
				+ config.getPort() + "/" + config.getDbName());
		dataSource.setUsername(config.getUser());
		dataSource.setPassword(config.getPassword());
		
		return dataSource;
	}

	public static Connection getDBConnection(String which) throws Exception {
		if (which.equals(Constants.DBConnection.SOURCEDB)) {
			if (sourceCon == null) {
				sourceCon = sourceDS.getConnection();
			}
			return sourceCon;
		} else {
			if (targetCon == null) {
				targetCon = targetDs.getConnection();
			}
			return targetCon;
		}

	}

	public static void shutDownConnection(Connection connection) throws Exception {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	public static BasicDataSource getSourceDS() {
		return sourceDS;
	}

	public static void setSourceDS(BasicDataSource sourceDS) {
		DBUtils.sourceDS = sourceDS;
	}

	public static BasicDataSource getTargetDs() {
		return targetDs;
	}

	public static void setTargetDs(BasicDataSource targetDs) {
		DBUtils.targetDs = targetDs;
	}
}
