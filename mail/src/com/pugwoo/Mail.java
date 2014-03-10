package com.pugwoo;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * 2012-12-31 下午12:56:06
 * 封装好登录，接收和发送邮件的工具
 * 主要支持QQ邮箱
 * 
 * 关于：读取邮件的Failed to load IMAP envelope错误，好像是因为读取到了腾讯的系统邮件导致的，好像又不是
 */
public class Mail extends Authenticator {

	private Session session;
	private PasswordAuthentication authentication;

	/**
	 * 以QQ邮箱为例
	 * @param protocol "imap"
	 * @param host "imap.qq.com"
	 * @param port "143"
	 * @param username "***@qq.com"
	 * @param password "***"
	 */
	public Mail(String protocol, String host, String port, String username,
			String password) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", port);
		// 设置这个才可以正常登录QQ邮箱
		props.setProperty("mail.imap.auth.login.disable", "true");

		authentication = new PasswordAuthentication(username, password);
		session = Session.getInstance(props, this);
		//session.setDebug(true);
	}

	public Session getSession() {
		return session;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return this.authentication;
	}

	public static void main(String[] args) {

	}

}
