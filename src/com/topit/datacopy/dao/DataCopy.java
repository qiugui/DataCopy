package com.topit.datacopy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topit.datacopy.config.UpdatedInfo;
import com.topit.datacopy.utils.SqlUtil;

public class DataCopy {

	private List<UpdatedInfo> updatedInfos = new ArrayList<UpdatedInfo>();
	private static int updateNum = 0;
	private UpdatedInfo updatedInfo;
	private List<String> sqls = new ArrayList<String>();
	List<List<Map<String, String>>> insertedRecords;

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
		for (int i = 0; i < updateNum; i++) {
			updatedInfo = updatedInfos.get(i);
			String sql = "";
			String[] updatedInfoPrimaryKeys = updatedInfo.getPrimarykey()
					.split("&");
			String[] updatedInfoPrimaryKeyvalues = updatedInfo
					.getPrimarykeyvalue().split("&");
			String conditions = "";
			for (int j = 0; j < updatedInfoPrimaryKeys.length; j++) {
				String condition = updatedInfoPrimaryKeys[j] + "='"
						+ updatedInfoPrimaryKeyvalues[j] + "' AND ";
				conditions += condition;
			}
			conditions = conditions.substring(0, conditions.length() - 4);
			if("dbo.cltypep".equals(updatedInfo.getTablename())){
				insertedRecords = SqlUtil.getInsertedRecords("dbo.cltypep", "colthno = 'SSW'");
				String del_sql = "DELETE FROM dbo.cltypep WHERE colthno = 'SSW'";
				sqls.add(del_sql);
				addSqls(updatedInfo.getTablename(),insertedRecords,"colthno = 'SSW'");
			} else {
				if ("UPDATE".equals(updatedInfo.getOperation())) {
					sql = "UPDATE " + updatedInfo.getTablename() + " SET "
							+ updatedInfo.getColumn() + "=Convert("
							+ updatedInfo.getColumntype() + ",'"
							+ updatedInfo.getNewvalue() + "') WHERE " + conditions;
					sqls.add(sql);
				} else if ("INSERT".equals(updatedInfo.getOperation())) {
					insertedRecords = SqlUtil.getInsertedRecords(updatedInfo.getTablename(), conditions);
					addSqls(updatedInfo.getTablename(), insertedRecords, conditions);
				}
			}
			
		}
		SqlUtil.updata(sqls);
	}

	private void addSqls(String tablename,List<List<Map<String, String>>> insertedRecords,String conditions) {
		List<Map<String, String>> insertedRecord = new ArrayList<Map<String, String>>();
		String del_sql = "";
		String sql = "";
		for (int i = 0; i < insertedRecords.size(); i++){
			insertedRecord = insertedRecords.get(i);
			String insertedColumn = "";
			String insertedValue = "";
			for (int k = 0; k < insertedRecord.size(); k++) {
				HashMap<String, String> record = (HashMap<String, String>) insertedRecord
						.get(k);
				String valueOfColumn = record.get("valueOfColumn");
				String nameOfColumn = record.get("nameOfColumn");
				String typeOfColumn = record.get("typeOfColumn");
				if (valueOfColumn != null) {
					insertedColumn += nameOfColumn + ",";
					insertedValue += "Convert(" + typeOfColumn + ",'"
							+ valueOfColumn + "'),";
				}
			}
			// 插入前先删除的逻辑
			if (!"dbo.cltypep".equals(tablename)){
				del_sql = "DELETE FROM " + tablename
						+ " WHERE " + conditions;
				sqls.add(del_sql);
			}
			sql = "INSERT INTO "
					+ tablename
					+ " ("
					+ insertedColumn.substring(0,
							insertedColumn.length() - 1)
					+ ") VALUES ("
					+ insertedValue
							.substring(0, insertedValue.length() - 1) + ")";
			sqls.add(sql);
		}
	}

}
