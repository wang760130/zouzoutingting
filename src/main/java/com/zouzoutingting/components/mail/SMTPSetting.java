package com.zouzoutingting.components.mail;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月14日
 */
public class SMTPSetting {
	private String userName = "";

	private String pwd = "";
	private String smtpHost;
	private int port = 25;

	private SMTPEncrypt enc = SMTPEncrypt.NORMAL;

	public SMTPSetting(String userName, String pwd, String smtpHost, int port,
			SMTPEncrypt enc) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
		this.port = port;
		this.enc = enc;
	}

	public SMTPSetting(String userName, String pwd, String smtpHost, int port) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
		this.port = port;
	}

	public SMTPSetting(String userName, String pwd, String smtpHost,
			SMTPEncrypt enc) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
		this.enc = enc;
	}

	public SMTPSetting(String userName, String pwd, String smtpHost) {
		this.userName = userName;
		this.pwd = pwd;
		this.smtpHost = smtpHost;
	}

	public SMTPSetting() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSmtpHost() {
		return this.smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SMTPEncrypt getEnc() {
		return this.enc;
	}

	public void setEnc(SMTPEncrypt enc) {
		this.enc = enc;
	}

	public static enum SMTPEncrypt {
		NORMAL, SSL, TLS;
	}
}
