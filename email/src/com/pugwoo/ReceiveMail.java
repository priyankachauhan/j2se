package com.pugwoo;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class ReceiveMail {

	Properties props = new Properties();

	String imapHost;

	Store store;
	Folder folder;

	String username;
	String password;

	public String getImapHost() {
		return imapHost;
	}

	public void setImapHost(String imapHost) {
		this.imapHost = imapHost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 接受邮件，返回Message变量
	 * @return
	 */
	public Message[] receiveByIMAP() {

		Session session = Session.getDefaultInstance(props);
		Message message[] = null;
		Store store = null;
		Folder folder = null;
		try {
			// 取得imap协议的邮件服务器
			store = session.getStore("imap");
			// 连接imap邮件服务器
			store.connect(this.imapHost, this.username, this.password);
			
			// 返回文件夹对象
			folder = store.getFolder("INBOX");
			// 设置仅读
			folder.open(Folder.READ_ONLY);
			// 获取信息
			message = folder.getMessages();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 关闭folder和store，注意读取message的过程是动态的，必须保证这两个打开
	 * 用完之后记得关闭
	 */
	public void close() {
		try {
			if (this.folder != null)
				folder.close(true);
			if (this.store != null)
				this.store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单元测试
	 */
	public static void main(String[] args) throws Exception {

		ReceiveMail recvMail = new ReceiveMail();
		recvMail.setImapHost("imap.163.com");
		recvMail.setUsername("awooeng@163.com");
		recvMail.setPassword("iloveenglish");
		 Message[] message = recvMail.receiveByIMAP();
		for (int i = message.length - 1; i >=0; i--) {
			// 打印主题
			System.out.println(message[i].getSubject());
			System.out.println("-----------------------------------------");
			//System.out.println(message[i].getContent());
		}
		recvMail.close(); /*重要*/
	}

}
