package com.pugwoo.test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 2012年2月21日
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
