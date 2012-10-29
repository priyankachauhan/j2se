package com.pugwoo;

public class Test implements Runnable {
	
	

	public static void main(String[] args) throws InterruptedException {
		Test test = new Test();
		Thread thread = new Thread(test);
		thread.start();
		synchronized (thread) {
			thread.wait();
		}
		System.out.println("main");
	}

	@Override
	public synchronized void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("thread");
		this.notify();
	}

}
