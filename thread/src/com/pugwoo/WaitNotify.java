package com.pugwoo;

import java.util.LinkedList;
import java.util.List;

/**
 * 2014-3-10 下午03:36:36
 * wait和notify是多线程之间的事情，
 * 肯定是某个进程主动调用某个obj.wait()进入【阻塞态】，
 * 【即使是某个进程调用了另外一个进程对象的wait()，实际上也是当前进程阻塞，事实上，进程并不管wait()那个对象是不是Thread对象，只要是Object子类就行】
 * 
 * 然后另外一个进程调用这个obj.notify()方法使得这个阻塞的进程被唤醒
 * 
 * 被调用wait()的那个obj必须包含在synchronized(obj)中
 * 
 * 下面这个例子演示：生产和消费者模型
 */
class Queue{
	private List<Object> queue = new LinkedList<Object>();
	
	public synchronized Object get() { // 必须加上synchronized
		while(queue.isEmpty()) {
			try {
				System.out.println("queue is empty, wait");
				this.wait(); // 使得当queue为空时，调用get()的线程进入阻塞态
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		System.out.println("from queue get one obj");
		return queue.remove(0);
	}
	
	public synchronized void put(Object obj) { // 必须加上synchronized
		System.out.println("add queue one obj");
		queue.add(obj);
		System.out.println("notify all");
		this.notifyAll(); // 唤醒所有等待在queue对象上的线程
	}
}

public class WaitNotify {

	public static void main(String[] args) throws InterruptedException {

		final Queue queue = new Queue();
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				queue.get(); // 这个会被阻塞住
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				queue.put("abc");
			}
		});
		
		thread1.start();
		
		Thread.sleep(3000); // 等待3秒钟
		
		thread2.start();
		
	}

}
