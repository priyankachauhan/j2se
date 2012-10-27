package com.pugwoo.test;
import java.util.HashMap;

import junit.framework.TestCase;

/**
 * 2012年2月7日 12:43:10
 * 
 * hash算法：就2种，“分桶单链表”或“N次探测法”
 */
public class TestHashMap extends TestCase {

	public static void main(String[] args) {
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		
		for(Integer i = 0; i< 1000000; i++) {
			hashMap.put(i, i);
		}
		//hashMap.put("name", "nick");
		System.out.println(hashMap.size());
		
		// 整理内存后打印内存大小，单位B
		System.gc();
		System.out.println(Runtime.getRuntime().totalMemory());
		//System.out.println(hashMap.get("name"));
	}
}
