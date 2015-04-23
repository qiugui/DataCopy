package com.topit.datacopy.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;

import com.topit.datacopy.dao.DataCopy;
import com.topit.datacopy.main.MainJFrame;
import com.topit.datacopy.utils.SqlUtil;

/**
 * 
 * @ClassName: AutoTask
 * @Description: 定时任务
 * @author gaodachuan
 * @date 2015年3月10日 下午5:56:27
 *
 */
public class AutoTask implements Job {

	SimpleDateFormat format = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss.SSS");
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		if (!Constants.isRunning) {
			beforeTask(context);

			DataCopy dataCopy;
			try {
				dataCopy = new DataCopy();
				MainJFrame.log.removeAll();
				dataCopy.updateTargetDB();
			} catch (Exception e) {
				Constants.logger.error("拷贝数据库出现错误！" + e.getMessage(), e);
				MainJFrame.log.append("\n拷贝数据库出现错误！\n");
				MainJFrame.progressBar.setValue(0);
			}
			if (!SqlUtil._interrupted){ 
		        afterTask();
	        } else {
	        	Constants.logger.info("手动停止！");
				MainJFrame.log.append("手动停止！");
	        }
			afterTaskReset();
			
		}
	}

	/**   
	 * @Title: beforeTask   
	 * @Description: 用于任务开始前的一些初始化  
	 * @param context        
	 */
	 
	private void beforeTask(JobExecutionContext context) {
		JobDetail jobDetail = context.getJobDetail();
		String jobname = ((JobDetailImpl) jobDetail).getName();
		Constants.isRunning = true;
		
		Constants.logger.info("数据库开始拷贝，时间："
				+ format.format(context.getFireTime()));
		MainJFrame.log.setText("");
		MainJFrame.log.append("数据库开始拷贝，时间："
				+ format.format(context.getFireTime()) + "\n");
		// 判断是手动任务还是自动任务
		if ("立即运行".equals(jobname)) {
			MainJFrame.stateLabel.setText("当前状态：手动运行中");
			Constants.logger.info("执行方式：手动执行。");
		} else {
			MainJFrame.stateLabel.setText("当前状态：自动运行中");
			Constants.logger.info("执行方式：" + jobname + "自动执行。");
		}
	}
	
	/**   
	 * @Title: afterTask   
	 * @Description: 用于任务结束后的操作          
	 */
	 
	private void afterTask() {
		MainJFrame.progressBar.setValue(0);
		Constants.logger.info("\n增加记录" + SqlUtil.insertNums + "条"
				+ "\n更新记录" + SqlUtil.updateNums + "条。");
		MainJFrame.log.append("\n增加记录" + SqlUtil.insertNums + "条"
				+ "\n更新记录" + SqlUtil.updateNums + "条。\n");
		Constants.logger.info("数据库结束拷贝，时间：" + format.format(new Date()));
		MainJFrame.log.append("数据库结束拷贝，时间：" + format.format(new Date())
				+ "\n");
	}
	
	/**   
	 * @Title: afterTaskReset   
	 * @Description: 用于任务结束后，将参数重置           
	 */
	 
	private void afterTaskReset() {
		SqlUtil._interrupted = false;
		SqlUtil.insertNums = 0;
		SqlUtil.updateNums = 0;
		Constants.isRunning = false;
		MainJFrame.stateLabel.setText("当前状态：定时任务");
		MainJFrame.evaluateNextTime();
	}
}
