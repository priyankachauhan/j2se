package com.pugwoo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Download from http://www.codefans.net
 * 2012年2月29日 17:09:00
 */
public class TelnetClient {

	/**
	 * @param host
	 *            Telnet服务器IP地址
	 * @param port
	 *            Telnet服务器端口
	 */
	public TelnetClient(String host, int port) throws IOException {
		System.out.println("connecting host " + host + ":" + port);

		// 实例化套接字
		Socket socket = new Socket(host, port);
		// 输出服务器信息到控制台
		new Listener(socket.getInputStream(), System.out).start();
		// 输出控制台信息到服务器
		new Listener(System.in, socket.getOutputStream()).start();

		System.out.println("Connected Success");
	}

	class Listener extends Thread {
		BufferedReader reader; // 输入流
		PrintStream ps; // 输出流

		Listener(InputStream is, OutputStream os) {
			reader = new BufferedReader(new InputStreamReader(is));
			ps = new PrintStream(os);
		}

		public void run() {
			String line;
			try {
				while ((line = reader.readLine()) != null) { // 读取数据
					ps.print(line);
					ps.print("\r\n");
					ps.flush();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] argv) throws IOException {
		new TelnetClient("127.0.0.1", 8787);
	}
}
