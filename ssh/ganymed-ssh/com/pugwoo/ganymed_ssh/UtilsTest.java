package com.pugwoo.ganymed_ssh;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import junit.framework.TestCase;

/**
 * 2013年2月8日 22:23:40
 */
public class UtilsTest extends TestCase {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		long start = System.currentTimeMillis();

		Connection conn = Utils.getConnection(new Host("192.168.56.102",
				"root", "123456"));

		System.out.println(Utils.run(conn, "cd /tmp;cat `ls`"));
		// 没有输出也可以正常显示
//		System.out.println(Utils.run(conn, "sleep 1"));

		Utils.run(conn, "a=123");
		System.out.println(Utils.run(conn, "echo a=$a")); // 无法输出a=123

		System.out.println(Utils.md5(conn, "/etc/passwd"));

		conn.close();

		long stop = System.currentTimeMillis();
		System.out.println((stop - start) + "ms");
	}
	
	public void testRunFile() throws IOException {
		Connection conn = Utils.getConnection(new Host("192.168.56.102",
				"root", "123456"));
		
		String result = Utils.run(conn, new File("ganymed-ssh/hello.sh"));
		System.out.println(result);
		conn.close();
	}

	public void testInteractive() throws IOException, InterruptedException {
		Connection conn = Utils.getConnection(new Host("192.168.56.102",
				"root", "123456"));

		final Streams streams = Utils.getStreams(conn);
		// 启动一个thread专门显示消息,这里没有对退格键、上下键等进行处理
		new Thread() {
			@Override
			public void run() {
				while (true) {
					byte[] buff = new byte[4096];
					int len = -1;
					try {
						len = streams.getStdout().read(buff);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (len == -1)
						break;
					System.out.print(new String(buff, 0, len));
				}
			};
		}.start();

		Thread.sleep(1000);

		streams.getStdin().write('d');
		Thread.sleep(500);
		streams.getStdin().write('a');
		Thread.sleep(500);
		streams.getStdin().write('t');
		Thread.sleep(500);
		streams.getStdin().write('e');
		Thread.sleep(500);
		streams.getStdin().write('\n');
		//		streams.getStdinPrint().print("date\n"); // 不能用println

		Thread.sleep(1000);

		// 单线程的话，如果命令不输出，就会卡死在这里
		//		System.out.println(streams.getStdoutReader().readLine()); 

		conn.close();
	}
}
