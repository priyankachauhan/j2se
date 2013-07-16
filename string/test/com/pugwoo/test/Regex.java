package com.pugwoo.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

/**
 * 正则表达式
 * 2011.11.3
 */
public class Regex {

	public static void easyExample() {
		/**
		 * 判断字符串【完全】符合正则表达式，注意：不是存在子串符合
		 * 判断是否所有字符都是数字，空的也算是
		 */
		System.out.println("123".matches("[0-9]*")); // true
		
		/**
		 * 测试行首^行尾$的匹配
		 */
		System.out.println("abc".matches("^abc$")); // true

		/**
		 * string.split(String regex)
		 * 将字符串从“正则表达式匹配的地方”切开
		 */
		String str = "hello world";
		String[] strArr = str.split(" ");
		for (String s : strArr)
			System.out.println(s);
	}
	
	/**
	 * 完整的强大功能的regex功能演示
	 */
	public static void fullExample(){
		// step 1. 构造正则表达式Pattern
		Pattern pattern = Pattern.compile("[0-9]+"); // 获得数字串的regex
		
		String str = "hello123world 456~~789";
		// step 2. 匹配
		Matcher matcher = pattern.matcher(str);
		
		// step 3. 拿出数据group()
		while(matcher.find()){
			System.out.println("match \"" + matcher.group() + "\" at position"
					+ matcher.start() + "-" + (matcher.end() - 1));
		}
	}
	
	// 分组，假如要从一堆杂乱的语句中拿到电话号码形式020-22556699的数据，要求区号和电话号码单独拿
	public static void testGroup() {
		Pattern pattern = Pattern.compile("(\\d+)-(\\d+)");
		String str = "abc020-22558877ffff0768-2548658pp";
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()) {
			System.out.println("match:" + matcher.group(1) + "," + matcher.group(2));
		}
	}
	

	public static void main(String[] args) {
		easyExample();
		System.out.println("-------------------------------------------------");
		fullExample();
		System.out.println("-------------------------------------------------");
		testGroup();
	}

}
