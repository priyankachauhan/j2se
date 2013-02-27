package com.pugwoo.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2012年2月22日
 */

interface Store {
	// 取一件商品，返回剩余个数，如果返回负值表示取不到
	public int get();
}

class NotSyncStore implements Store {
	private int stock;

	public NotSyncStore(int stock) {
		this.stock = stock;
		System.out.println(this.getClass().getSimpleName());
	}

	public int get() {
		// return --stock;
		return stock > 0 ? --stock : -1;
	}
}

class SyncStore implements Store {
	private int stock;

	public SyncStore(int stock) {
		this.stock = stock;
		System.out.println(this.getClass().getSimpleName());
	}

	public synchronized int get() {
		// return --stock;
		return stock > 0 ? --stock : -1;
	}
}

@SuppressWarnings("serial")
class ReentrantStore extends ReentrantLock implements Store {
	private int stock;

	public ReentrantStore(int stock) {
		this.stock = stock;
		System.out.println(this.getClass().getSimpleName());
	}

	public int get() {
		// return --stock;
		int result;
		lock(); // 加锁
		result = stock > 0 ? --stock : -1;
		unlock(); // 解锁
		return result;
	}
}

class AtomicStore implements Store {
	private AtomicInteger stock;

	public AtomicStore(int stock) {
		this.stock = new AtomicInteger();
		this.stock.set(stock);
		System.out.println(this.getClass().getSimpleName());
	}

	public int get() {
		return stock.decrementAndGet();
	}
}

class Customer extends Thread {
	private Store store;
	private int num;
	private CountDownLatch countDownLatch;

	// 商店及购买数量
	public Customer(Store store, int num, CountDownLatch countDownLatch) {
		this.store = store;
		this.num = num;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		try {
			// 先睡一秒后再进行，这样能更好地模拟并发
			// 否则没同步时很难出现错误的情况
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < num; i++) {
			store.get();
			// System.out.println(store.get());
		}
		countDownLatch.countDown();
	}
}

class Test {
	private Customer customers[];
	private CountDownLatch countDownLatch; // 用于让main线程等待所有线程结束

	public Test(Store store, int clientNum, int buyNum) {
		customers = new Customer[clientNum];
		countDownLatch = new CountDownLatch(clientNum);
		for (int i = 0; i < customers.length; i++) {
			customers[i] = new Customer(store, buyNum, countDownLatch);
		}
	}

	public void test() throws InterruptedException {
		for (Customer customer : customers) {
			customer.start();
		}
		countDownLatch.await();
	}
}

public class TestSynchronized {

	public static void main(String[] args) throws InterruptedException {
		// Test test = new Test(new NotSyncStore(10000000), 1000, 1000);
		// Test test = new Test(new SyncStore(10000000), 1000, 1000);
		// Test test = new Test(new ReentrantStore(10000000), 1000, 1000);
		Test test = new Test(new AtomicStore(10000000), 1000, 1000);
		CountTime countTime = new CountTime();
		countTime.start();
		test.test();
		double ms = countTime.getTimeInMs();
		// 结果要减去1秒的等待时间
		System.out.println(ms - 1000 + "ms");
	}

}
