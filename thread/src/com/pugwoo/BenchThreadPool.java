package com.pugwoo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用于测试指定固定线程池且队列限长的threadPool的性能
 * 
 * 空跑以测试threadPool本身的性能
 * 进程池并非越大越大，例如1kw的任务，在进程池大小为100，队列长度为1000时，只需要25.7s就完成
 * 而线程池大小为1000，队列长度为10000时，需要110s才能完成
 */
public class BenchThreadPool {

	public static void main(String[] args) {
		
		int fixedThreadPoolSize = 100;
		int queueSize = 1000;
		int threadNum = 10000000;
		
		CountTime countTime = new CountTime();
		countTime.start();
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(fixedThreadPoolSize, fixedThreadPoolSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		

		for (int i = 0; i < threadNum; i++) {
			final int fi = i;
			executor.execute(new Runnable() {
				@Override
				public void run() {
//					System.out.println(fi);
				}
			});
		}
		
		executor.shutdown();
		
		countTime.printInMs();
	}

}
