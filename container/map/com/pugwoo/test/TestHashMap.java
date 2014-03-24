package com.pugwoo.test;
import java.util.HashMap;

import junit.framework.TestCase;

/**
 * 2012年2月7日 12:43:10
 * 
 * hash算法：就2种，“分桶单链表”或“N次探测法”
 * 
 * HashMap原理：
使用桶(叫table或bucket)式存放数据。Capacity指桶的个数，默认16，必须是2的N次方，使用&操作代替除和取模运算。

当存储的个数>桶的个数*0.75，则把桶扩大一倍，然后把旧的桶移动到新的桶，这个过程很快，
因为所有object的hash值已经算好了，只需要再&一下就知道它在新桶中的位置。

Hashmap中也有一个hash函数，它作用是让hash更加均匀。你能想像插入一堆从0~N为key的数据吗？
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
