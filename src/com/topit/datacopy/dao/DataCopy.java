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
 	 
	 public DataCopy() throws Exception{
		 updatedInfos = SqlUtil.getUpdatedInfo();
		 updateNum = updatedInfos.size();
	 }
	 
	 /**   
	 * @throws Exception 
	 * @Title: updateTargetDB   
	 * @Description: 对目标数据库进行更新\插入         
	 */
	 
	public void updateTargetDB() throws Exception{
		 for(int i = 0;i<updateNum;i++){
			 updatedInfo = updatedInfos.get(i);
			 String sql = "";
			 List<Map<String, String>> insertedRecord = new ArrayList<Map<String,String>>();
			 String[] updatedInfoPrimaryKeys = updatedInfo.getPrimarykey().split("&");
			 String[] updatedInfoPrimaryKeyvalues = updatedInfo.getPrimarykeyvalue().split("&");
			 String conditions = "";
			 for (int j=0;j<updatedInfoPrimaryKeys.length;j++){
				 String condition = updatedInfoPrimaryKeys[j]+"='"+updatedInfoPrimaryKeyvalues[j]+"' AND ";
				 conditions +=condition;
			 }
			 conditions = conditions.substring(0,conditions.length()-4);
			 if ("UPDATE".equals(updatedInfo.getOperation())){
				 sql = "UPDATE "+updatedInfo.getTablename()+" SET "
						 +updatedInfo.getColumn()+"=Convert("+updatedInfo.getColumntype()+",'"+updatedInfo.getNewvalue()
						 +"') WHERE "+conditions;
			 } else if ("INSERT".equals(updatedInfo.getOperation())){
				 insertedRecord = SqlUtil.getInsertedRecord(updatedInfo.getTablename(), conditions);
				 String insertedColumn = "";
				 String insertedValue = "";
				 for(int k=0;k<insertedRecord.size();k++){
					 HashMap<String, String> record = (HashMap<String, String>) insertedRecord.get(k);
					 String valueOfColumn = record.get("valueOfColumn");
					 String nameOfColumn = record.get("nameOfColumn");
					 String typeOfColumn = record.get("typeOfColumn");
					 if(valueOfColumn!=null){
						 insertedColumn += nameOfColumn+",";
						 insertedValue += "Convert("+typeOfColumn+",'"+valueOfColumn+"'),";
					 } 
				 }
				 sql = "INSERT INTO "+updatedInfo.getTablename()+" ("
				 +insertedColumn.substring(0, insertedColumn.length()-1)
				 +") VALUES ("+insertedValue.substring(0, insertedValue.length()-1)+")";
			 }
			 sqls.add(sql);
		 }
		 	SqlUtil.updata(sqls);
	 }
 }

 