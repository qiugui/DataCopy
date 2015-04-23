package com.topit.datacopy.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.main.MainJFrame;

/**
 * 
 * @ClassName: UpdateThread
 * @Description: 数据库操作线程
 * @author gaodachuan
 * @date 2015年4月21日 上午11:50:19
 *
 */
public class UpdateThread implements Runnable {

	private List<String> sqls;

	public UpdateThread(List<String> sqls) {
		this.sqls = sqls;
	}

	public void run() {
		Connection connection = null;
		try {
			connection = DBUtils
					.getDBConnection(Constants.DBConnection.TARGETDB);
			// 开启事务
			SqlUtil.openTransaction(connection);
			SqlUtil.updata(sqls, connection,null);
			sqls.clear();
			SqlUtil.commit(connection);
		} catch (Exception e) {
			try {
				connection.rollback();
				ThreadPool.getThreadPool().destroy(true);
				SqlUtil._interrupted = true;
				Constants.logger.error(e.getMessage(), e);
			} catch (SQLException e1) {
				Constants.logger.error(e1.getMessage(), e1);
			} finally {
				Constants.logger.error("\n数据拷贝出现错误！\n");
				MainJFrame.log.append("\n拷贝数据库出现错误！\n");
			}
		} 

	}
}
