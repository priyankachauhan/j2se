package com.pugwoo;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * BiMap的key是唯一的，value也是唯一的
 */
public class BiMapTest {

	private static final BiMap<Integer, String> NUMBER_TO_NAME_BIMAP = HashBiMap
			.create();

	public static void main(String[] args) {
		NUMBER_TO_NAME_BIMAP.put(1, "Hydrogen");
		NUMBER_TO_NAME_BIMAP.put(2, "Helium");
		NUMBER_TO_NAME_BIMAP.put(3, "Lithium");
		
		System.out.println(NUMBER_TO_NAME_BIMAP);
	}
}
