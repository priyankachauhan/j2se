package com.pugwoo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于测试固定线程池的性能
 */
public class BenchThreadPool {

	public static void main(String[] args) {
		
		// 测试结果：当线程数达到千万级别时，由于一次全部加入到pool
		// 会出现各种错误，包括java.lang.OutOfMemoryError
		// 所以要控制加入的速度，可以参考TCP的窗口
		int fixedThreadPoolSize = 1000;
		int queueSize = 10000;
		int threadNum = 10000000;
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(fixedThreadPoolSize, fixedThreadPoolSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		

		for (int i = 0; i < threadNum; i++) {
			final int fi = i;
			executor.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(fi);
				}
			});
		}
		
		executor.shutdown();
	}

}
