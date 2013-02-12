package com.unmi;

import java.rmi.Naming;

import com.pugwoo.remote.HelloInterface;

/**
 * @author nickxie
 * 2012-11-6 下午02:19:37
 */
public class HelloClientBenchmark {
	/**
	 * 查找远程对象并调用远程方法
	 */
	public static void main(String[] argv) {
		if (argv.length == 0) {
			System.out.println("usage: java HelloClientBenchmark times");
			return;
		}

		int times = Integer.valueOf(argv[0]);

		long start = System.currentTimeMillis();
		try {
			HelloInterface hello = (HelloInterface) Naming.lookup("Hello");

			// 如果要从另一台启动了RMI注册服务的机器上查找hello实例
			// HelloInterface hello =
			// (HelloInterface)Naming.lookup("//192.168.1.105:1099/Hello");

			// 调用远程方法
			for (int i = 0; i < times; i++) {
				hello.say("hello world");
			}
		} catch (Exception e) {
			System.out.println("HelloClient exception: " + e);
		}
		
		long stop = System.currentTimeMillis();
		System.out.println((stop - start) + "ms");
	}
}