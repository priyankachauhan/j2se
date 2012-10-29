package com.pugwoo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * http://tech.163.com/06/0410/09/2EBABUD20009159T.html
 */
public class Client {

	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client(String host, int port) {
		try {
			socket = new Socket(host, port);

			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader line = new BufferedReader(new InputStreamReader(
					System.in));

			out.println(line.readLine());

			// 读取从服务器端发送过来的数据
			String recv;
			while ((recv = in.readLine()) != null) {
				System.out.println(recv);
			}
		} catch (IOException e) {
		} finally {
			out.close();
			try {
				in.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client("127.0.0.1", 8787);
	}
}