package com.pugwoo.bio;

/*
 * Copyright 2010 CSsula.twgg.org. All Rights Reserved.
 * Author: Tony Jian
 */
import java.io.*;
import java.net.*;

public class JavaTelnet {

	public static void main(String[] args) {
		String hostName;
		int port;
		InetAddress address;

		if (args.length == 2) {
			hostName = args[0];
			port = Integer.parseInt(args[1]);
		} else {
			System.out.println("Usage: java JavaTelnet address port");
			return;
		}

		try {
			address = InetAddress.getByName(hostName);
			try {
				Socket socket = new Socket(address, port);
				System.out.println("Connected " + hostName + ":" + port
						+ " ok.");

				new SocketToOut(socket).start();
				new InToSocket(socket).start();
			} catch (IOException e) {
				System.err.println("Connection failed");
			}
		} catch (UnknownHostException e) {
			System.err.println("Unknown host");
		}
	}
}

// class SocketToOut extends Thread {
// private Socket socket;
// private InputStream socketIS;
//
// public SocketToOut(Socket socket) throws IOException {
// this.socket = socket;
// socketIS = socket.getInputStream();
// }
//
// /**
// * 最好是一个字节一个字节打出来,支持某些bbs
// */
// @Override
// public void run() {
// try {
// int k;
// while (!socket.isInputShutdown()) {
// k = socketIS.read();
// if (k != -1)
// System.out.write(k);
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
// try {
// socket.close();
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// }

class SocketToOut extends Thread {
	private Socket socket;
	private BufferedReader socketIn;

	public SocketToOut(Socket socket) throws IOException {
		this.socket = socket;
		socketIn = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
	}

	@Override
	public void run() {
		try {
			String line = null;
			while ((line = socketIn.readLine()) != null)
				System.out.println(line);
			socket.close();
			// 提醒用户按回车退出程序
			System.out.print("Host closed, press [Enter] to exit.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class InToSocket extends Thread {
	private Socket socket;
	private PrintStream socketOut;
	private BufferedReader in;

	public InToSocket(Socket socket) throws IOException {
		this.socket = socket;
		socketOut = new PrintStream(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				// 这里有个问题：当服务器端结束链接时，由于readLine()阻塞
				// 导致程序不会自动马上退出，要按一次回车才会退出
				socketOut.println(in.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
