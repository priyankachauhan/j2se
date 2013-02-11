package com.pugwoo;

/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program will demonstrate remote exec.
 *  $ CLASSPATH=.:../build javac Exec.java 
 *  $ CLASSPATH=.:../build java Exec
 * You will be asked username, hostname, displayname, passwd and command.
 * If everything works fine, given command will be invoked 
 * on the remote side and outputs will be printed out.
 *
 */
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 2013年2月8日 09:13:51
 */
public class Exec {
	public static void main(String[] arg) {
		long start = System.currentTimeMillis();
		try {
			String host = "192.168.56.102";
			String user = "root";
			String password = "123456";

			JSch jsch = new JSch();

			Session session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");

			session.connect();

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("echo hello");

			/**
			 * 最好不要2次setCommand,不然第二次的输出会覆盖第一次
			 */

			// 用于输出错误信息
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] buf = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int len = in.read(buf, 0, buf.length);
					if (len < 0)
						break;
					System.out.print(new String(buf, 0, len));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "
							+ channel.getExitStatus());
					break;
				}
				// 异步io轮询
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}

		long stop = System.currentTimeMillis();
		System.out.println((stop - start) + "ms");
	}

}
