package com.pugwoo;

import java.util.Date;

/**
 * Java守护线程：（java线程分为：用户线程和Daemon线程）
 * 
 * 简单来说，jvm会等待所有的用户线程执行完成之后再退出程序。
 * 也就是，jvm并不会等待Daemon线程是否执行完。
 * 
 * 举个例子，jvm中有2个线程，一个用户线程，一个daemon线程。
 * daemon线程永久循环输出信息，只要用户线程还没结束，daemon线程就会一直执行。
 * 而当用户线程结束时，daemon线程就自动结束了。
 * 
 * daemon线程中新建的线程都是daemon线程。
 * 
 * 2014-3-24 下午02:24:39
 */
public class TestDaemon {

	public static void main(String[] args) {

		// 首先main线程是一个用户线程
		
		// 创建一个线程并设置为daemon线程
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) { // 死循环
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("now is " + new Date());
				}
			}
		});
		
		thread.setDaemon(true); // 设置为daemon线程，必须在现场start()之前调用
		thread.start();
		
		try {
			Thread.sleep(10000); // main线程等待10秒后结束
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("main thread ends.");
		
		// 可以观察到，main线程结束后不久，整个jvm就退出了。
	}

}
