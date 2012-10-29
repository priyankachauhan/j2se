package com.pugwoo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 2012年3月2日 上午10:27:42
 * http://tech.163.com/06/0410/09/2EBABUD20009159T.html
 */

public class Server {

	private ServerSocket ss;
	private Socket socket;

	public Server(int port) {
		try {
			ss = new ServerSocket(port);
			System.out.println("listening at " + port);

			byte[] buf = new byte[1024];
			// 不停接受客户端请求，单线程，同一时刻只能服务一个客户，另一种方案是采用线程
			while (true) {
				socket = ss.accept();
				// System.out.println("new client connected: " +
				// socket.getRemoteSocketAddress());

				// BufferedReader in = new BufferedReader(new
				// InputStreamReader(socket
				// .getInputStream()));
				// PrintWriter out = new PrintWriter(socket.getOutputStream(),
				// true);
				//
				// // 简单的回显服务器
				// String line = in.readLine();
				// // debug
				// //System.out.println("client say: " + line);
				// // 发送给客户端
				// out.println(line);
				//
				// out.close();
				// in.close();

				InputStream in = socket.getInputStream();
				int n = in.read(buf);
				OutputStream out = socket.getOutputStream();
				out.write(buf, 0, n);

				socket.close();
			}
		} catch (IOException e) {
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java com/pugwoo/bio/Server <port>");
			return;
		}
		new Server(Integer.valueOf(args[0]));
	}
}