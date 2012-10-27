package com.pugwoo.test;

import java.util.Scanner;

/**
 * 2011.11.4
 */
public class TestScanner {

	public static void main(String[] args) {
		// scanner的构造函数参数可以是string或输入流
		//Scanner stdin = new Scanner(System.in);
		Scanner stdin = new Scanner("nick\n24\n");
		System.out.println("What's your name?");
		String name = stdin.nextLine();
		System.out.println("your name is " + name + "\nhow old are you?");
		Integer age = stdin.nextInt();
		System.out.println("your age is " + age);
		
		/**
		 *  还有一种用法是使用Scanner的定界符
		 */
		System.out.println("---------------------------------------");
		Scanner scanner = new Scanner("12, 42, 78, 99, 42");
		// 表示间隔，其中\\s*表示任意的空白
		scanner.useDelimiter("\\s*,\\s*");
		while(scanner.hasNextInt())
			System.out.println(scanner.nextInt());
		
		/**
		 * 还有一种是复杂的时候可用的：正则表达式扫描
		 * 给定输入字符串，给定正则表达式，如果输入符合表达式，则获得该输入
		 * 然后继续下一组的匹配
		 * [效率未知]
		 */
	}
}
