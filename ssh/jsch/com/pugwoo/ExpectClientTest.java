package com.pugwoo;

import junit.framework.TestCase;

public class ExpectClientTest extends TestCase {

	/**
	 * 显示远程机器的内存的使用百分比
	 */
	public void testMonitor() throws Exception {
		ExpectClient expectClient = new ExpectClient("192.168.56.102", 22,
				"root", "123456");
		String cmd = "ps aux | awk '{sum+=$4}END{print sum\"%\"}'";
		while (true) {
			System.out.println(expectClient.execute(cmd));
			Thread.sleep(1000);
		}
	}

	public void testSCPRemote() throws Exception {
		ExpectClient expectClient = new ExpectClient("192.168.56.102", 22,
				"root", "123456");
		// 另一台机器的密码
		expectClient.addExpectInput("(yes/no)", "yes");
		expectClient.addExpectInput("password", "123456");
		System.out.println(expectClient
				.execute("scp robovm-0.0.1.tar.gz root@192.168.56.104:~"));
	}
	
	public void testExecuteOnce() throws Exception {
		ExpectClient expectClient = new ExpectClient("192.168.56.102", 22,
				"root", "123456");
		String r = expectClient.executeOnce("date");
		System.out.println(r);
	}
}
