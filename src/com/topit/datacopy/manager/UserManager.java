package com.topit.datacopy.manager;

import org.w3c.dom.Element;

import com.topit.datacopy.config.Constants;
import com.topit.datacopy.config.User;
import com.topit.datacopy.utils.XmlUtils;

public class UserManager {
	private static XmlUtils xmlUtils;
	static {
		xmlUtils = new XmlUtils();
	}

	/**
	 * 
	 * @Title: resetUser
	 * @Description: 重置用户
	 * @param user
	 * @throws Exception 
	 */
	public void resetUser(User user) throws Exception {
		xmlUtils.updateXML(Constants.User.USER, Constants.User.USERNAME,
				user.getName());
		xmlUtils.updateXML(Constants.User.USER, Constants.User.PASSWORD,
				user.getPassword());

	}

	/**
	 * 
	 * @Title: exist
	 * @Description: 判断用户是否存在
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public boolean exist(User user) throws Exception {

		Element element = (Element) xmlUtils
				.getElementByTag(Constants.User.USER);
		String userName = element.getElementsByTagName(Constants.User.USERNAME)
				.item(0).getTextContent();
		if (user.getName().equals(userName)) {
			String password = element
					.getElementsByTagName(Constants.User.PASSWORD).item(0)
					.getTextContent();
			if (user.getPassword().equals(password)) {
				return true;
			}

		}
		return false;
	}

}
