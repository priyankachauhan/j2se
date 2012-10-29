package com.pugwoo;

/**
 * 2010年12月6日 下午08:48:20
 * @author Administrator
 *
 */
public class ThreadSleep implements Runnable {

	@Override
	public void run() {
		System.out.println("Current Thread: " + Thread.currentThread().getName());
		System.out.println("Thread " + Thread.currentThread().getName() + " will sleep 20 second.");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			System.out.println("Thread " + Thread.currentThread().getName() + "is interrupted.");
			e.printStackTrace();
		}
		System.out.println("Thread" + Thread.currentThread().getName() + " is ending.");
	}

	public static void main(String args[]){
		ThreadSleep threadSleep = new ThreadSleep();
		Thread thread = new Thread(threadSleep);
		thread.start();
		try {
			Thread.sleep(1000); /*让主线程先睡眠1秒*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*叫醒睡眠中的线程*/
		thread.interrupt();
	}
}
