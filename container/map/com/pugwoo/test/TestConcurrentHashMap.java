package com.pugwoo.test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 2012年2月21日
 * 
 * ConcurrentHashMap是线程安全的HashMap，最主要的优化原理是【部分加锁】，这个map分成多个segments。
 * 不是采用synchronized，而是用ReentrantLock
 * 
 * 当线程数>10时，ConcurrentHashMap就比使用synchronized的HashMap快了，线程数越多则快越多。
 * 
 * ConcurrentHashMap原理分析（有深度）
 * http://geeklu.com/2010/07/concurrenthashmap/
 * http://yinwufeng.iteye.com/blog/767084
 * 
 * 关于JSR133问题：http://snake1987.iteye.com/blog/987263
 */
public class TestConcurrentHashMap {

	public static void main(String[] args) {
		ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
		map.put(1, 1);
		System.out.println(map.get(1));
		
		map.putIfAbsent(1, 3); // 如果key不存在，则插入，原子操作
		System.out.println(map.get(1)); // 还是1，因为都在main线程，先后有关系
	}
}
