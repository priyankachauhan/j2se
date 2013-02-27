package com.pugwoo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 固定大小线程池
 * 2013年2月15日 22:18:15
 */
public class FixedThreadPool {

	public static void main(String[] args) {

		// 创建一个最多同时运行 3 个任务的线程池
		ExecutorService service = Executors.newFixedThreadPool(3);

		// 安排若干个任务运行
		for (int i = 1; i <= 20; i++) {
			service.execute(new RunnableImpl(i));
		}

		// 结束线程池。shutdown() 不会马上结束，而是等所有安排的任务执行完才结束。
		service.shutdown();

		// 如果不想马上结束，又不愿意等太长时间，那么可以调用 awaitTermination() 方法等待一段时间
		try {
			service.awaitTermination(5, TimeUnit.SECONDS);
			System.err.println("等待时间已过");

			// shutdownNow() 将放弃所有正在等待的任务，等当前执行的任务全部完成之后，结束线程池。
			List<Runnable> abandoned = service.shutdownNow();
			System.err.println("被遗弃的任务个数：" + abandoned.size());

		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static class RunnableImpl implements Runnable {

		private int id;

		private RunnableImpl(int id) {
			this.id = id;
		}

		public void run() {
			System.out.println("开始执行" + id);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(id + "执行完毕。");
		}

		@Override
		public String toString() {
			return "线程" + id;
		}
	}
}
