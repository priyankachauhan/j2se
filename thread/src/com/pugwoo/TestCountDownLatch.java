package com.pugwoo;

import java.util.concurrent.CountDownLatch;

/**
 * 2014-3-18 上午09:08:32
 * CountDownLatch的设计非常简单，像信号量
 * 给定一个n的信号总数，然后某个线程调用await()方法阻塞等待
 * 其它线程调用countDown()方法【倒计时】，当countDown()调用次数等于n时，await()就结束阻塞
 */
public class TestCountDownLatch {

	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(5);
		
		for(int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("thread start");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread " + Thread.currentThread().getName() + " count down");
					countDownLatch.countDown(); // -1
				}
			}).start();
		}
		
		System.out.println("main thread wait");
		countDownLatch.await(); // 等待，也可设置超时等待，【可以被中断】
		
		System.out.println("main thread end.");
	}

}
