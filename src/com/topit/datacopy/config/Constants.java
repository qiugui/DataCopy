package com.topit.datacopy.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.topit.datacopy.utils.XmlUtils;

/**
 * 
 * @ClassName: Config
 * @Description:程序所用配置信息
 * @author gaodachuan
 * @date 2015年3月10日 上午10:11:37
 *
 */
public class Constants {

	public static Logger logger = Logger.getLogger(Constants.class);
	/**
	 * 上次同步的时间
	 */
	public static String lastCopyTime = "";
	static {
		XmlUtils xmlUtils = new XmlUtils();
		try {
			String temp=xmlUtils.getElementByTag(Constants.LastCopyTimeNode).getTextContent();
			if(temp.trim()=="")
			{
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_MONTH, -1);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String mDateTime = formatter.format(c.getTime());
				lastCopyTime =  mDateTime + " 00:00:00";
			}else {
				lastCopyTime=temp;
			}
						
		} catch (Exception e) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -1);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String mDateTime = formatter.format(c.getTime());
			lastCopyTime =mDateTime + " 00:00:00";
			e.printStackTrace();
		}

	}

	/**
	 * 本次同步的时间
	 */
	public static String currentCopyTime = "";
	public static boolean isRunning = false;
	/**
	 * @Fields loginUsername : 登陆用户的用户名
	 */
	public static String loginUsername;

	/**
	 * @Fields taskName : 改程序的任务名
	 */
	public static String taskName = "请设置任务名称！";

	/**
	 * 配置文件的根节点
	 */
	public static final String ROOT = "configs";
	/**
	 * 上次跟新的时间
	 */
	public static final String LastCopyTimeNode = "lastCopyTime";

	/**
	 * 
	 * @ClassName: DBConnection
	 * @Description: 数据库链接配置信息
	 * @author gaodachuan
	 * @date 2015年3月12日 上午9:53:00
	 *
	 */
	public static class DBConnection {
		/**
		 * 源数据库
		 */
		public static final String SOURCEDB = "sourcedb";
		/**
		 * 目标数据库
		 */
		public static final String TARGETDB = "targetdb";
		/**
		 * ip地址
		 */
		public static final String IP = "ip";
		/**
		 * 端口
		 */
		public static final String PORT = "port";
		/**
		 * 数据库名
		 */
		public static final String DBNAME = "dbname";
		/**
		 * 用户名
		 */
		public static final String USER = "user";
		/**
		 * 用户密码
		 */
		public static final String PASSWORD = "password";

	}

	/**
	 * 
	 * @ClassName: AutoTask
	 * @Description:定时任务配置
	 * @author gaodachuan
	 * @date 2015年3月12日 上午9:51:09
	 *
	 */
	public static class AutoTask {
		/**
		 * 根节点
		 */
		public static final String AUTORUNTIMES = "autoruntimes";
		/**
		 * 时间节点
		 */
		public static final String AUTORUNTIME1 = "autoruntime1";
		public static final String AUTORUNTIME2 = "autoruntime2";
		public static final String AUTORUNTIME3 = "autoruntime3";
	}

	/**
	 * 
	 * @ClassName: User
	 * @Description: 登录用户信息
	 * @author gaodachuan
	 * @date 2015年3月12日 上午9:54:05
	 *
	 */
	public static class User {
		/**
		 * 根节点
		 */
		public static final String USER = "loginUser";
		/**
		 * 用户名
		 */
		public static final String USERNAME = "username";
		/**
		 * 密码
		 */
		public static final String PASSWORD = "password";
	}
}
