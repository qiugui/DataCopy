 package com.topit.datacopy.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionException;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.UpdatedInfo;
import com.topit.datacopy.main.MainJFrame;
 /** 
* @ClassName: SqlUtil 
* @Description: sql工具类
* @author qiugui 
* @date 2015年3月16日 下午10:54:27 
*  
*/ 
public class SqlUtil {

	 private static Connection s_connection;
	 private static Connection t_connection;
	 private static PreparedStatement ps;
	 private static ResultSet rs;
	 public static boolean _interrupted = false;
	 
	 public static void init() throws Exception{
		 DBUtils.initDataSource();	 
	 }
	 
	 /**   
	 * @Title: selectUpdatedInfo   
	 * @Description: 获得更新的信息  
	 * @return        
	 * @throws Exception 
	 */
	 
	public static List<UpdatedInfo> getUpdatedInfo() throws Exception{
		 init();
		 s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		 List<UpdatedInfo> updatedInfos = new ArrayList<UpdatedInfo>();
		 try {
			ps = s_connection.prepareStatement("SELECT * FROM martjs.updated_info");
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

				UpdatedInfo updatedInfo = new 
						UpdatedInfo(tablename, primarykey, primarykeyvalue, column, columntype, operation, oldvalue, newvalue);
				
				updatedInfos.add(updatedInfo);
			}
			return updatedInfos;
		}catch (Exception e){
			throw new RuntimeException(e);
		}finally {
			close(rs, ps, s_connection);
		}
	 }
	 
	/**   
	 * @Title: getInsertedRecord   
	 * @Description: 根据主键，查询被插入的记录，返回一条插入记录   
	 * @param tablename 被插入的表名
	 * @param condition 主键条件       
	 * @throws Exception 
	 */
/*	 
	public static List<Map<String, String>> getInsertedRecord(String tablename,String condition) throws Exception{
		init();
		 s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		 try {
			List<Map<String, String>> results = new ArrayList<Map<String,String>>();
			String sql = "SELECT * FROM "+tablename+" WHERE "+condition;
			ps = s_connection.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
		    int numberOfColumns = rsmd.getColumnCount();
		    rs.next();
		    for (int i=1;i<=numberOfColumns;i++){
		    	Map<String, String> result = new HashMap<String, String>();
		    	String nameOfColumn = rsmd.getColumnName(i);
		    	String valueOfColumn = rs.getString(i);
		    	String typeOfColumn = rsmd.getColumnTypeName(i);
		    	result.put("nameOfColumn", nameOfColumn);
		    	result.put("valueOfColumn", valueOfColumn);
		    	result.put("typeOfColumn", typeOfColumn);
		    	results.add(result);
		    }
		    return results;
		}catch(Exception e){ 
			throw new RuntimeException(e);
		}finally {
			close(rs, ps, s_connection);
		}
	}
	*/
	
	/**   
	 * @Title: getInsertedRecords   
	 * @Description: 根据某一条件，查询被插入的记录，返回多条插入记录   
	 * @param tablename
	 * @param condition        
	 */
	 
	public static List<List<Map<String, String>>> getInsertedRecords(String tablename,String condition) throws Exception{
		init();
		 s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		 try {
			 List<List<Map<String, String>>> allResults = new ArrayList<List<Map<String,String>>>();
			 String sql = "SELECT * FROM "+tablename+" WHERE "+condition;
			 ps = s_connection.prepareStatement(sql);
			 rs = ps.executeQuery();
			 ResultSetMetaData rsmd = rs.getMetaData();
			 int numberOfColumns = rsmd.getColumnCount();
			 while(rs.next()){
				 List<Map<String, String>> results = new ArrayList<Map<String,String>>();
				 for (int i=1;i<=numberOfColumns;i++){
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
		 }catch(Exception e){ 
				throw new RuntimeException(e);
			}finally {
				close(rs, ps, s_connection);
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
	 
	public static void updata(List<String> sqls) throws Exception {
		
		init();
		t_connection = DBUtils.getDBConnection(Constants.DBConnection.TARGETDB);
		try {
			int updateNums = 0;
			int insertNums = 0;
			//控制事务，禁止自动提交
			t_connection.setAutoCommit(false);
			
			for (int i = 0; i < sqls.size(); i++){
				double count = ((double)(i+1)/sqls.size())*100;
				String sql = sqls.get(i);
				if(sql.startsWith("UPDATE")){
					updateNums++;
				} else {
					insertNums++;
				}
				if(_interrupted) {  
					_interrupted = false;
					MainJFrame.stateLabel.setText("当前状态：定时任务");
					MainJFrame.progressBar.setValue(0);
					Exception exception = new Exception("任务被强制中断！");
					throw new JobExecutionException(exception);
				}
				ps = t_connection.prepareStatement(sql);
				ps.executeUpdate();
				Constants.logger.info(sql);
				MainJFrame.progressBar.setValue((int)count);
				MainJFrame.log.append("\t"+sql+"\n");
				MainJFrame.log.setCaretPosition(MainJFrame.log.getText().length());
			}
			t_connection.commit();
			Constants.logger.info("\n增加记录"+insertNums+"条"+"\n更新记录"+updateNums+"条。");
			MainJFrame.log.append("\n增加记录"+insertNums+"条"+"\n更新记录"+updateNums+"条。\n");
			deleteAll("martjs.updated_info");
			MainJFrame.stateLabel.setText("运行结束！");
		} catch (Exception e){
			Constants.logger.error("数据拷贝出现错误！"+e.getMessage(),e);
			t_connection.rollback();
			throw new RuntimeException(e);
		} finally {
			close(null, null, t_connection);
		}
	}
	
	public static void deleteAll(String tablename) throws Exception{
		
		s_connection = DBUtils.getDBConnection(Constants.DBConnection.SOURCEDB);
		try{
			s_connection.setAutoCommit(false);
			String sql = "DELETE FROM "+tablename;
			ps = s_connection.prepareStatement(sql);
			ps.executeUpdate();
			Constants.logger.info("删除更新记录表！\n"+sql);
			MainJFrame.log.append("\n删除更新记录表！\n"+sql+"\n\n");
			s_connection.commit();
		} catch (Exception e){
			MainJFrame.log.append("\n删除更新记录表出现错误！\n");
			Constants.logger.error("删除更新记录表出现错误！"+e.getMessage(),e);
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
	 
	public static void close(ResultSet rs,PreparedStatement ps,Connection cs) throws Exception{
		 if (rs !=null){
			rs.close();
		 }
		 rs = null;
		 if (ps !=null){
			ps.close();
		 }
		 ps = null;
		 DBUtils.shutDownConnection(cs);
	 }
 }
 