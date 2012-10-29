package com.pugwoo.simulate;

import java.util.HashMap;
import java.util.Map;

public class Internet extends AsynMsgDrivenDevice {

	// 存放联网的机器<IP,Computer>
	Map<Integer, Computer> computers = new HashMap<Integer, Computer>();

	// Internet延迟，ms
	private Integer delay = 10;

	/**
	 * 将机器添加到网络中，机器必须设置IP
	 */
	public void add(Computer computer) {
		if (computer.getIp() == null) {
			new Exception("Internet add Computer <" + computer.getDeviceName()
					+ "> fails: ip not set.").printStackTrace();
			return;
		}
		if (computers.containsKey(computer.getIp())) {
			new Exception("Internet add Computer <" + computer.getDeviceName()
					+ "," + computer.getIp() + "> fails: ip exists.")
					.printStackTrace();
			return;
		}
		computers.put(computer.getIp(), computer);
		computer.setInternet(this);
	}

	public void add(Computer computer, Integer ip) {
		computer.setIp(ip);
		add(computer);
	}

	/**
	 * 通过Internet发送消息
	 */
	public void sendMsg(InternetMessage message) {
		Integer dstIp = message.dst;
		if (!computers.containsKey(dstIp)) {
			new Exception("message.dst ip does not exist.").printStackTrace();
			return;
		}
		// 迟延ms
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		computers.get(dstIp).putMsg(message);
	}
}
