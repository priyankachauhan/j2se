package com.pugwoo.test;

import java.util.Formatter;

public class Printf {

	/**
	 * 格式化输出，和C语言一致
	 * 原理：java.util.Formatter类
	 */
	public static void main(String[] args) {
		
		System.out.printf("id: %d\n", 123);
		// 等价于 System.out.format("id: %d", 123);
		
		/**
		 * 原理：Formatter类
		 * 参数可以是OutputStream、String等，数据format之后将写入到流(或字符串)里面
		 */
		Formatter f = new Formatter(System.out);
		f.format("id: %d\n", 123);
		
	}

}
