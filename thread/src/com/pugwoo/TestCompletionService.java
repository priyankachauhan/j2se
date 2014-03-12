package com.pugwoo;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletionService适合于批量执行任务，一次性将所有的task添加到CompletionService中，
 * 然后再一个一个get()到运行结束的线程结果，不需要顺序，哪个先执行完哪个先返回
 * 
 * 支持callable的任务
 * 
 * 2014-3-11 下午07:32:16
 */
class Task implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		Thread.sleep(1000); // sleep 1秒
		System.out.println(Thread.currentThread().getName());
		return new Double(Math.random() * 10000).intValue();
	}
}

public class TestCompletionService {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		CompletionService<Integer> completionServcie = new ExecutorCompletionService<Integer>(
				pool);

		// 批量加入
		for (int i = 0; i < 10; i++) {
			completionServcie.submit(new Task());
		}

		// 批量获取，哪个先完成哪个先返回
		for (int i = 0; i < 10; i++) {
			// take 方法等待下一个结果并返回 Future 对象。
			// poll 不等待，有结果就返回一个 Future 对象，否则返回 null。
			//System.out.println(completionServcie.take().get());
			System.out.println(completionServcie.take().get());
		}
		
		pool.shutdown();
	}
}
