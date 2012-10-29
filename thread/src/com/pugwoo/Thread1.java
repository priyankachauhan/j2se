package com.pugwoo;

/**
 * 该类演示以实现接口的方式创建线程
 * 2010年12月6日 下午08:48:20
 */
public class Thread1 implements Runnable{

	@Override
	public void run() {
		System.out.println("Current Thread: " + Thread.currentThread().getName());
		System.out.println("Thread " + Thread.currentThread().getName() + " will sleep 2 second.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread" + Thread.currentThread().getName() + " is ending.");
	}
	
	/**
	 * 单元测试
	 */
	public static void main(String args[]){
		Thread1 thread1 = new Thread1();
		Thread threada = new Thread(thread1);
		Thread threadb = new Thread(thread1);
		threada.start();
		try {
			Thread.sleep(1000);/*这个Main线程来Sleep*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		threadb.start();
	}

}
