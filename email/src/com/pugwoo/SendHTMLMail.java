package com.pugwoo;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 演示发送HTML邮件
 * 关键是使用MineMultipart类
 */
public class SendHTMLMail {
	
	Properties props = new Properties();
	
	Address sentAddress;
	Address recvAddress;
	
	String subject;
	String content;
	String password;
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setSTMPHost(String hosts){
		this.props.put("mail.smtp.host", hosts);
	}
	
	public void setAuth(String auth){
		this.props.put("mail.smtp.auth", auth);
	}

	public void setSentAddress(String addr){
		try {
			this.sentAddress = new InternetAddress(addr);
		} catch (AddressException e) {
			e.printStackTrace();
		}
	}
	
	public void setRecvAddress(String addr){
		try {
			this.recvAddress = new InternetAddress(addr);
		} catch (AddressException e) {
			e.printStackTrace();
		}
	}
	
	public void setSubject(String sub){
		this.subject = sub;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public void send(){
		Session session = null;
		Transport transport = null;
		try{
		    //基本的邮件会话
		    session = Session.getInstance(props);
		    //构造信息体
		    MimeMessage message = new MimeMessage(session); 
		    //设置发送地址
		    message.setFrom(this.sentAddress);
		    //设置收件地址，对于群发，这里可多次调用设置多个地址
		    message.setRecipient(MimeMessage.RecipientType.TO, this.recvAddress);
		    //主题
		    message.setSubject(this.subject);
		    
		    /**
		     * 接下来这一段是发送HTML邮件的关键
		     */
		    Multipart mul = new MimeMultipart(); //新建一个MineMultipart对象来存放多个BodyPart对象
		    BodyPart mdp = new MimeBodyPart(); //新建一个存放邮件内容的BodyPart对象
		    mdp.setContent(this.content, "text/html;charset=utf-8"); //设置正文
		    mul.addBodyPart(mdp); //将含有邮件内容的BodyPart对象加入到Multipart对象中
            		    
		    //设置正文
		    message.setContent(mul); //将Multipart对象设置为message的正文
		    
		    message.saveChanges(); //保证报头域与会话内容保持一致
		    
		    session.setDebug(true); //debug
		    
		    transport = session.getTransport("smtp");
		    //连接至STMP服务器
		    transport.connect((String)props.get("mail.smtp.host"), this.sentAddress.toString(), this.password);
		    //发送，如果有多封邮件要发送，这里可多次调用
		    transport.sendMessage(message, message.getAllRecipients());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    try {
				transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 单元测试
	 */
	public static void main(String[] args) {
		SendHTMLMail sendMail = new SendHTMLMail();
		sendMail.setSTMPHost("smtp.163.com");
		sendMail.setAuth("true");
		sendMail.setSentAddress("awooeng@163.com");
		sendMail.setPassword("iloveenglish");
		sendMail.setRecvAddress("nicktrey@qq.com");
		sendMail.setSubject("Hello");
		sendMail.setContent("<h1 style=\"color:red\">这是一封来自163的邮件</h1>");
        sendMail.send();
	}

}
