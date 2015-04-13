package com.topit.datacopy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.topit.datacopy.config.AutoTask;
import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.UpdatedInfo;
import com.topit.datacopy.dao.DataCopy;
import com.topit.datacopy.utils.DBUtils;
import com.topit.datacopy.utils.MD5Encrypt;
import com.topit.datacopy.utils.SqlUtil;

public class TestCase {
	@Test
	public void test1() {

		JobDetail jobDetail = JobBuilder.newJob(AutoTask.class)
				.withIdentity("testJob_1", "group_1").build();

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger_1", "group_1")
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(0) // 时间间隔
								.withRepeatCount(0) // 重复次数(将执行6次)
				).build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;
		try {
			sched = sf.getScheduler();
			sched.scheduleJob(jobDetail, trigger);

			sched.start();
			// 主线程等待一分钟
			Thread.sleep(120L * 1000L);
			sched.shutdown(true);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Test
	public void test2() {

		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(AutoTask.class).storeDurably(true)
					.withIdentity("testJob_1", "group_1").build();
			

			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.forJob(jobDetail)
					.withIdentity("trigger_1", "group_1")
					.startNow()
					.withSchedule(
							CronScheduleBuilder.dailyAtHourAndMinute(15, 36))
					.build();
			CronTrigger trigger1 = TriggerBuilder
					.newTrigger()
					.forJob(jobDetail)
					.withIdentity("trigger_2", "group_1")
					.startNow()
					.withSchedule(
							CronScheduleBuilder.dailyAtHourAndMinute(15, 37))
					.build();

			sched.addJob(jobDetail, false);
			sched.scheduleJob(trigger);
			sched.scheduleJob(trigger1);
			sched.start();
			String oldTime = trigger.getCronExpression();
			System.out.println(oldTime.split(" ").length);
			// 主线程等待一分钟
			Thread.sleep(1800L * 1000L);
			sched.shutdown(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetConnection() throws Exception{
		DBUtils.initDataSource();
		Connection s_connection = DBUtils
				.getDBConnection(Constants.DBConnection.SOURCEDB);
		System.out.println(s_connection);

		Connection t_connection = DBUtils
				.getDBConnection(Constants.DBConnection.TARGETDB);
		System.out.println(t_connection);

	}

	@Test
	public void testConnect() {
		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver").newInstance();
			String url = "jdbc:sybase:Tds:172.24.241.199:51990/zbmarttest";// myDB为你的数据库名
			Properties sysProps = System.getProperties();
			sysProps.put("user", "martjs"); // 设置数据库访问用户名
			sysProps.put("password", "martjs"); // 密码
			Connection conn = DriverManager.getConnection(url, sysProps);
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	 @Test
	 public void test3() throws Exception{
		 List<UpdatedInfo> updatedInfos = SqlUtil.getUpdatedInfo();
		 for (UpdatedInfo updatedInfo : updatedInfos) {
			System.out.println("更新表名："+updatedInfo.getTablename()
					+" 表的主键："+updatedInfo.getPrimarykey()
					+" 更新列："+updatedInfo.getColumn()
					+" 更新后的值"+updatedInfo.getNewvalue());
		}
	 }
	 
	 @Test
	 public void test4() throws Exception{
		 DataCopy dataCopy  =new DataCopy();
		 dataCopy.updateTargetDB();
	 }

	 @Test
	 public void test5() throws Exception{
		 SqlUtil.getInsertedRecords("dbo.cltypep", "colth = 'SSW'");
	 }
	 
	 @Test
	 public void test6() throws Exception {
		System.out.println(MD5Encrypt.encipher("123456"));
		
	}
}
