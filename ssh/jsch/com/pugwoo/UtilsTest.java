package com.pugwoo;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	private static final String host = "192.168.56.103";
	private static final int port = 22;
	private static final String username = "root";
	private static final String password = "123456";

	public void testMd5() throws Exception {
		ExpectClient client = new ExpectClient(host, port, username, password);
		String fn = "/etc/passwd";
		System.out.print(Utils.md5sum(client, fn));
	}

	public void testScp() throws Exception {
		Host hostSrc = new Host("192.168.56.102", 22, "root", "123456");
		Host hostDest = new Host("192.168.56.103", 22, "root", "123456");
		String fileSrc = "*";
		String fileDest = "~";

		ExpectClient client = new ExpectClient(hostSrc);
		String result = Utils.scp(client, hostSrc, fileSrc, hostDest, fileDest);
		System.out.println(result);
	}
	
	public void testLs() throws Exception{
		ExpectClient client = new ExpectClient(host, port, username, password);
		Utils.ls(client, "");
	}
}
