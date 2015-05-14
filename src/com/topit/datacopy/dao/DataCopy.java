package com.topit.datacopy.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.UpdatedInfo;
import com.topit.datacopy.main.MainJFrame;
import com.topit.datacopy.utils.DBUtils;
import com.topit.datacopy.utils.SqlUtil;

public class DataCopy {

	private List<UpdatedInfo> updatedInfos = new ArrayList<UpdatedInfo>();
	private static int updateNum = 0;
	private static int dboCltypeNewNum = 0;
	private static int dboCltypebNewNum = 0;
	private static int dboCltypepNewNum=0;
	public static final String DBOCLTYPEP = "dbo.cltypep";
	private int batchSize = 100;
	private int operateNum = 0;
	private int currentIndex = 0;
	private UpdatedInfo updatedInfo;
	private List<String> sqls = new ArrayList<String>();
	private List<List<Map<String, String>>> insertedRecords;
	private List<List<Map<String, String>>> dboCltypeNewInsertedRecords;
	private List<List<Map<String, String>>> dboCltypebNewInsertedRecords;
	private List<List<Map<String, String>>> dboCltypepNewInsertedRecords;
	public DataCopy() throws Exception {
		updatedInfos = SqlUtil.getUpdatedInfo();
		updateNum = updatedInfos.size();
	}

	/**
	 * @throws Exception
	 * @Title: updateTargetDB
	 * @Description: 对目标数据库进行更新\插入
	 */                                   
	 
	public void updateTargetDB() throws Exception {

		/*
		 * 对dbo.cltype_new，dbo.cltypeb_new两张表进行全量拷贝
		 */
		dboCltypeNewInsertedRecords = SqlUtil.getInsertedRecords("dbo.cltype_new", "1=1");
		dboCltypeNewNum = dboCltypeNewInsertedRecords.size();
		dboCltypebNewInsertedRecords = SqlUtil.getInsertedRecords("dbo.cltypeb_new", "1=1");
		dboCltypebNewNum = dboCltypebNewInsertedRecords.size();
		/**
		 * 对dbo.cltypep进行时间段的增量拷贝
		 */
		dboCltypepNewInsertedRecords=SqlUtil.getInsertedRecords(DBOCLTYPEP, "  gxrq > '"+Constants.lastCopyTime+"'");
		dboCltypepNewNum=dboCltypepNewInsertedRecords.size();
		operateNum = updateNum + dboCltypeNewNum + dboCltypebNewNum+dboCltypepNewNum;
		
		sqls.add("DELETE FROM dbo.cltype_new WHERE 1=1");
		for (int k = 0; k < dboCltypeNewNum; k++){
			currentIndex++;			
			addSql("dbo.cltype_new", dboCltypeNewInsertedRecords.get(k), "1=1");
			if(sqls.size() > batchSize || k == (dboCltypeNewNum-1))
			{  
				batchUpdate();
			}
			if(SqlUtil._interrupted) {
				DBUtils.shutDownDataSource();
				return;
			}
			MainJFrame.progressBar.setValue((int)( ((double)currentIndex / (double)operateNum) * 100));
		}
		
		sqls.add("DELETE FROM dbo.cltypeb_new WHERE 1=1");
		for (int j = 0; j < dboCltypebNewNum; j++){
			currentIndex++;
			addSql("dbo.cltypeb_new", dboCltypebNewInsertedRecords.get(j), "1=1");
			if(sqls.size() > batchSize || j == (dboCltypebNewNum-1))
			{  
				batchUpdate();
			}
			if(SqlUtil._interrupted) {
				DBUtils.shutDownDataSource();
				return;
			}
			MainJFrame.progressBar.setValue((int)( ((double)currentIndex / (double)operateNum) * 100));
		}
		/**
		 * 增量复制cltypep的记录
		 */
		for (int  m= 0; m < dboCltypepNewNum; m++){
			currentIndex++;
			addSql(DBOCLTYPEP, dboCltypepNewInsertedRecords.get(m), "1=1");
			if(sqls.size() > batchSize || m == (dboCltypepNewNum-1))
			{  
				batchUpdate();
			}
			if(SqlUtil._interrupted) {
				DBUtils.shutDownDataSource();
				return;
			}
			MainJFrame.progressBar.setValue((int)( ((double)currentIndex / (double)operateNum) * 100));
		}
		for (int i = 0; i < updateNum; i++) {
			currentIndex++;
			updatedInfo = updatedInfos.get(i);
			String sql = "";
			String[] updatedInfoPrimaryKeys = updatedInfo.getPrimarykey()
					.split("&");
			String[] updatedInfoPrimaryKeyvalues = updatedInfo
					.getPrimarykeyvalue().split("&");
			StringBuffer buf=new StringBuffer("");
			for (int j = 0; j < updatedInfoPrimaryKeys.length; j++) {
				buf.append(updatedInfoPrimaryKeys[j] + "='"
						+ updatedInfoPrimaryKeyvalues[j] + "' AND ");
			}
			String conditions=buf.substring(0, buf.length()-4);
			if(DBOCLTYPEP.equals(updatedInfo.getTablename())){	
				/*String condition = "colthno = '"+updatedInfoPrimaryKeyvalues[1]+"'";
				insertedRecords = SqlUtil.getInsertedRecords(DBOCLTYPEP,condition );
				String del_sql = "DELETE FROM " + DBOCLTYPEP + " WHERE "+condition;
				sqls.add(del_sql);
				addSqls(updatedInfo.getTablename(),insertedRecords,condition);*/
			} else {
				if ("UPDATE".equals(updatedInfo.getOperation())) {
					sql = "UPDATE " + updatedInfo.getTablename() + " SET "
							+ updatedInfo.getColumn() + "=Convert("
							+ updatedInfo.getColumntype() + ",'"
							+ (updatedInfo.getNewvalue()==null?' ':updatedInfo.getNewvalue()) + "') WHERE " +conditions;
					sqls.add(sql);
				} else if ("INSERT".equals(updatedInfo.getOperation())) {
					insertedRecords = SqlUtil.getInsertedRecords(updatedInfo.getTablename(), conditions);
					addSqls(updatedInfo.getTablename(), insertedRecords, conditions);
				}
			}
			
			//批量跟新
			if(sqls.size()>batchSize||i==(updateNum-1))
			{  
				batchUpdate();
			}
			MainJFrame.progressBar.setValue((int)( ((double)currentIndex / (double)operateNum) * 100));
			if(SqlUtil._interrupted) {
				DBUtils.shutDownDataSource();
				return;
			}
		}
		//删除updateInfo
		SqlUtil.deleteAll("martjs.updated_info");
		
		DBUtils.shutDownDataSource();
		
	}

	//当sqls的批量大小增加到一定量时，进行批量更新
	private void batchUpdate() throws Exception, SQLException {
		Connection connection = DBUtils
				.getDBConnection(Constants.DBConnection.TARGETDB);
		Statement statement = connection.createStatement();
		// 开启事务
		SqlUtil.openTransaction(connection);
		SqlUtil.updata(sqls, connection,statement);
		connection.commit();
		statement.clearBatch();
		SqlUtil.close(null, statement, connection);
		sqls.clear();
	}

	private void addSqls(String tablename,List<List<Map<String, String>>> insertedRecords,String conditions) {
		List<Map<String, String>> insertedRecord = new ArrayList<Map<String, String>>();
		for (int i = 0; i < insertedRecords.size(); i++){
			insertedRecord = insertedRecords.get(i);
			addSql(tablename, insertedRecord, conditions);
		}
	}

	private void addSql(String tablename,
			List<Map<String, String>> insertedRecord, String conditions) {		
		String del_sql = "";
		String sql = "";
		StringBuffer insertedColumn = new StringBuffer("");
		StringBuffer insertedValue = new StringBuffer("");
		//cltypep安主键删除的条件语句
		StringBuffer del_conditions=new StringBuffer("");
		for (int k = 0; k < insertedRecord.size(); k++) {
			HashMap<String, String> record = (HashMap<String, String>) insertedRecord
					.get(k);
			String valueOfColumn = record.get("valueOfColumn");
			String nameOfColumn = record.get("nameOfColumn");
			String typeOfColumn = record.get("typeOfColumn");
			if (valueOfColumn != null) {
				insertedColumn.append(nameOfColumn + ",");
				insertedValue .append("Convert(" + typeOfColumn + ",'"
						+ valueOfColumn + "'),");
				if(DBOCLTYPEP.equals(tablename))
				{			
					if("typeno".equals(nameOfColumn)||"colthno".equals(nameOfColumn)||"color".equals(nameOfColumn))
					{
						del_conditions.append(nameOfColumn+" = "+"Convert(" + typeOfColumn + ",'"
								+ valueOfColumn + "')  AND ");
					}
				}
			}
			
		}
		// 插入前先删除的逻辑
		if (!DBOCLTYPEP.equals(tablename) && !"dbo.cltype_new".equals(tablename) 
				&& !"dbo.cltypeb_new".equals(tablename)){
			del_sql = "DELETE FROM " + tablename
					+ " WHERE " + conditions;
			sqls.add(del_sql);
		}else if(DBOCLTYPEP.equals(tablename)){
			//对cltypep进行先删除再插入
			del_sql = "DELETE FROM " + tablename
					+ " WHERE " + del_conditions.substring(0, del_conditions.toString().lastIndexOf("AND"));
			sqls.add(del_sql);		
		}
		
		
		sql = "INSERT INTO "
				+ tablename
				+ " ("
				+ insertedColumn.substring(0,
						insertedColumn.length() - 1).toString()
				+ ") VALUES ("
				+ insertedValue
						.substring(0, insertedValue.length() - 1).toString() + ")";
		
		sqls.add(sql);
	}

}
