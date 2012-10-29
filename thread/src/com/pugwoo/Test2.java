package com.pugwoo;



public class Test2 extends Thread {

	private Store store;

	public Test2(Store store) {
		this.store = store;
	}

	public static void main(String[] args) throws InterruptedException {
		Store store = new Store(100000000);

		// 经测试，最大5000个线程
		for (int i = 0; i < 5000; i++) {
			new Test2(store).start();
		}

	}

	// 一个线程代表一个客户端，去取一个商品
	@Override
	public void run() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // 休息3秒后开始

		for (int i = 0; i < 10000; i++) {
			@SuppressWarnings("unused")
			int result = store.get();
			// System.out.println(result);
		}

	}

}

class Store {
	// 库存
	private int stock;

	public Store(int stock) {
		this.stock = stock;
	}

	// 获得一个商品，返回剩余的商品数目，返回-1表示没有取到
	public synchronized int get() {
		return stock > 0 ? --stock : -1;
	}

	// 查询剩余商品个数
	public int query() {
		return stock;
	}
}