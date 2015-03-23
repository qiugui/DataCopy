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

/**
 * 
 * @ClassName: AutoTask
 * @Description: 定时任务
 * @author gaodachuan
 * @date 2015年3月10日 下午5:56:27
 *
 */
public class AutoTask implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {

		if (!Constants.isRunning){
			JobDetail jobDetail = context.getJobDetail();
			String jobname = ((JobDetailImpl)jobDetail).getName();
			Constants.isRunning = true;
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			Constants.logger.info("数据库开始拷贝，时间："+format.format(context.getFireTime()));
			MainJFrame.log.setText("");
			MainJFrame.log.append("数据库开始拷贝，时间："+format.format(context.getFireTime())+"\n");
			//判断是手动任务还是自动任务
			if ("立即运行".equals(jobname)){
				MainJFrame.stateLabel.setText("当前状态：手动运行中");
				Constants.logger.info("执行方式：手动执行。");
			} else {
				MainJFrame.stateLabel.setText("当前状态：自动运行中");
				Constants.logger.info("执行方式："+jobname+"自动执行。");
			}
			
			DataCopy dataCopy;
			try {
				dataCopy = new DataCopy();
				dataCopy.updateTargetDB();
			} catch (Exception e) {
				 Constants.logger.error("拷贝数据库出现错误！"+e.getMessage(), e);
				 MainJFrame.log.append("\n拷贝数据库出现错误！\n");
			}

			Constants.logger.info("数据库结束拷贝，时间："+format.format(new Date()));
			MainJFrame.log.append("数据库结束拷贝，时间："+format.format(new Date())+"\n");
			Constants.isRunning = false;
			MainJFrame.stateLabel.setText("当前状态：定时任务");
			MainJFrame.evaluateNextTime();
		} 
	}
}

