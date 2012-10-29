package com.pugwoo.simulate;

import java.util.Date;

public class Computer extends AsynMsgDrivenDevice {

	public Computer() {
		super();
	}

	public Computer(String name, Integer ip) {
		super(name);
		this.ip = ip;
	}

	// IP地址
	private Integer ip;

	// 连接的互联网
	private Internet internet;

	@Override
	protected void handle(Object message) {
		System.out.println("Computer<" + getDeviceName() + "," + ip
				+ "> recv msg:" + message);
	}

	/**
	 * 向目标地址dst发送消息content
	 */
	public void send(String content, Integer dst) {
		if (internet == null) {
			new Exception("Computer <" + getDeviceName() + "," + ip
					+ "> not link internet.").printStackTrace();
			return;
		}
		InternetMessage message = new InternetMessage();
		message.time = new Date();
		message.src = ip;
		message.dst = dst;
		message.content = content;
		internet.sendMsg(message);
	}

	public Integer getIp() {
		return ip;
	}

	public void setIp(Integer ip) {
		this.ip = ip;
	}

	public Internet getInternet() {
		return internet;
	}

	public void setInternet(Internet internet) {
		this.internet = internet;
	}

}
