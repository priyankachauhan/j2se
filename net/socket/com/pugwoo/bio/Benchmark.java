package com.pugwoo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 2012年3月
 * @author pugwoo
 *         多线程压力测试
 */
public class Benchmark {

	public static void main(String[] args) {
		BenchmarkClient[] bs = new BenchmarkClient[1000];
		for (int i = 0; i < bs.length; i++)
			bs[i] = new BenchmarkClient("127.0.0.1", 8787);
		for(BenchmarkClient b : bs) {
			b.start();
		}
	}
}

/**
 * 每条线程执行相同的请求
 */
class BenchmarkClient extends Thread {

	private String host;
	private int port;

	public BenchmarkClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		Socket socket = null;
		BufferedReader in;
		PrintWriter out;

		try {
			socket = new Socket(host, port);

			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			out.println("hello" + Math.random());
			String recv = in.readLine();
			System.out.println(recv);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
