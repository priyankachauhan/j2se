package com.pugwoo.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列，其实就是生产者消费者模型了
 * 
 * 	抛出异常	    特殊值	            阻塞	        超时
插入	add(e)	    offer(e)  put(e)	offer(e,time, unit)
移除	remove()	poll()	  take()	poll(time,unit)
检查	element()	peek()	   不可用	       不可用

 * 2014-3-17 下午05:55:50
 */
public class TestBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		
		// 最多放5个String,【不能为null】
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
		
		queue.add("test1"); // 非阻塞，当满时抛出java.lang.IllegalStateException: Queue full
		queue.add("test2");
		queue.add("test3");
		queue.put("test4"); // 当queue满时阻塞
		
		System.out.println(queue.take()); // 当queue空时阻塞
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.remove()); // 非阻塞，当queue为空时抛出java.util.NoSuchElementException
		
	}
}
