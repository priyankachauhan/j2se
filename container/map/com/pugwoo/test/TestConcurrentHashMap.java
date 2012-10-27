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
		
		
	}
}
