package com.topit.datacopy.config;

public class ConnectConfig {

	private String ip;// ip地址
	private String port;// 端口号
	private String user;// 登录用户名
	private String password;// 登录密码
	private String dbName;// 数据库名

	public ConnectConfig() {
	}

	public ConnectConfig(String ip, String port, String user, String password,
			String dbName) {
		super();
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		this.dbName = dbName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
