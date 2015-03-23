package com.topit.datacopy.manager;

import java.util.HashMap;

import org.w3c.dom.Element;

import com.topit.datacopy.config.ConnectConfig;
import com.topit.datacopy.config.Constants;
import com.topit.datacopy.utils.XmlUtils;

/**
 * 
 * @ClassName: ConfigUtils
 * @Description:配置文件的操作类
 * @author gaodachuan
 * @date 2015年3月11日 上午11:39:02
 *
 */
public class ConfigManager {
	private static XmlUtils xmlUtils=new XmlUtils();

//	public ConfigManager() {
//		xmlUtils = new XmlUtils();
//	}

	// 初始化配置文件
	public void initConfigXml() throws Exception {
		xmlUtils.createXml();
	}

	// 根据tagName获取连接配置信息
	public static ConnectConfig getDBConfig(String which) throws Exception {
		Element element = (Element) xmlUtils.getElementByTag(which);
		String ip = element.getElementsByTagName(Constants.DBConnection.IP)
				.item(0).getTextContent();
		String port = element.getElementsByTagName(Constants.DBConnection.PORT)
				.item(0).getTextContent();
		String dbName = element
				.getElementsByTagName(Constants.DBConnection.DBNAME).item(0)
				.getTextContent();
		String user = element.getElementsByTagName(Constants.DBConnection.USER)
				.item(0).getTextContent();
		String password = element
				.getElementsByTagName(Constants.DBConnection.PASSWORD).item(0)
				.getTextContent();
		ConnectConfig config = new ConnectConfig(ip, port, user, password,
				dbName);
		return config;

	}

	// 获取定时任务的时间
	public static HashMap<Integer, String> getAutoTasks() throws Exception {
		HashMap<Integer, String> tasks = new HashMap<Integer, String>();
		Element element = (Element) xmlUtils
				.getElementByTag(Constants.AutoTask.AUTORUNTIMES);
		String autoruntime1 = element.getElementsByTagName(Constants.AutoTask.AUTORUNTIME1)
				.item(0).getTextContent().trim();
		String autoruntime2 = element.getElementsByTagName(Constants.AutoTask.AUTORUNTIME2)
				.item(0).getTextContent().trim();
		String autoruntime3 = element.getElementsByTagName(Constants.AutoTask.AUTORUNTIME3)
				.item(0).getTextContent().trim();
		tasks.put(1, autoruntime1);
		tasks.put(2, autoruntime2);
		tasks.put(3, autoruntime3);
		return tasks;
	}

	/**
	 * 
	 * @Title: setConfig
	 * @Description: 跟新配置文件的值
	 * @param parentTag
	 * @param targetTag
	 * @param tagetValue
	 * @throws Exception 
	 */
	public static void setConfig(String parentTag,String targetTag,String tagetValue) throws Exception {
		xmlUtils.updateXML(parentTag, targetTag, tagetValue);
	}
}
