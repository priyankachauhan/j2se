package com.pugwoo;

/**
 * 2014-3-11 上午09:47:06
 */
public class Test implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		Test test = new Test();
		Thread thread = new Thread(test);
		thread.start();
		synchronized (thread) {
			thread.wait(); // ? 这里有点疑问，wait()会让main thread等待
// 【即使是某个进程调用了另外一个进程对象的wait()，实际上也是当前进程阻塞，事实上，进程并不管wait()那个对象是不是Thread对象，只要是Object子类就行】
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
