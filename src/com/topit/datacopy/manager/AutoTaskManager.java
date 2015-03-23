package com.topit.datacopy.manager;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.topit.datacopy.config.AutoTask;

/**
 * 
 * @ClassName: AutoTaskManager
 * @Description: 定时任务的管理器
 * @author gaodachuan
 * @date 2015年3月11日 下午4:43:54
 *
 */
public class AutoTaskManager {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "TRIGGERGROUP_NAME";

	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param cls
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException 
	 * @throws Exception 
	 * 
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addJob(String jobName, Class cls, String time) throws Exception {
			Scheduler sched = gSchedulerFactory.getScheduler();
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(cls)
					.withIdentity(jobName, JOB_GROUP_NAME).build();
			// 触发器名,触发器组

			CronTrigger trigger = createCrotrigger(jobName, TRIGGER_GROUP_NAME,
					time);
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
	}

	// 新增一个立即运行的任务
	public static void addImmediateJob() throws Exception {
		Scheduler sched;

		sched = gSchedulerFactory.getScheduler();
		// 任务名，任务组，任务执行类
		JobDetail jobDetail = JobBuilder.newJob(AutoTask.class)
				.withIdentity("立即运行", JOB_GROUP_NAME).build();
		// 触发器名,触发器组

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("立即运行", TRIGGER_GROUP_NAME)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(0) // 时间间隔
								.withRepeatCount(0) // 重复次数(将执行6次)
				).build();
		sched.scheduleJob(jobDetail, trigger);
		// 启动
		if (!sched.isShutdown()) {
			sched.start();
		}
	}

	// 构建一个触发器
	private static CronTrigger createCrotrigger(String triggerName,
			String triggerGroupName, String time) {
		String[] times = time.split(":");
		CronTrigger trigger = TriggerBuilder
				.newTrigger()
				.startNow()
				.withIdentity(triggerName, triggerGroupName)
				.withSchedule(
						CronScheduleBuilder.dailyAtHourAndMinute(
								Integer.parseInt(times[0]),
								Integer.parseInt(times[1]))).build();
		return trigger;
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * @throws SchedulerException 
	 */
	@SuppressWarnings("rawtypes")
	public static void modifyJobTime(String jobName, String time) throws Exception {

		Scheduler sched = gSchedulerFactory.getScheduler();
		TriggerKey key = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
		CronTrigger trigger = (CronTrigger) sched.getTrigger(key);
		if (trigger == null) {
			return;
		}
		/*
		 * 0 1 18 ? * *
		 */
		String oldTime = trigger.getCronExpression();
		if (!oldTime.equalsIgnoreCase(time)) {
			JobKey key2 = new JobKey(jobName, JOB_GROUP_NAME);
			JobDetail jobDetail = sched.getJobDetail(key2);
			Class objJobClass = jobDetail.getJobClass();
			removeJob(jobName);
			addJob(jobName, objJobClass, time);
		}
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException 
	 * 
	 */
	public static void removeJob(String jobName) throws Exception {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(jobKey);// 删除任务
	}

	/**
	 * @throws SchedulerException 
	 * @Description:启动所有定时任务
	 * 
	 */
	public static void startJobs() throws Exception {

			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
	}

	/**
	 * @throws SchedulerException 
	 * @Description:关闭所有定时任务
	 * 
	 * 
	 * @Title: QuartzManager.java
	 * @Copyright: Copyright (c) 2014
	 */
	public static void shutdownJobs() throws Exception {

			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
	}
}
