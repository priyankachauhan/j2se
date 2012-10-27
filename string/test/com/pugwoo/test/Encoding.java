package com.pugwoo.test;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 演示string的编码 2012年10月26日 22:40:29
 * 
 * String的encode方式是由jvm属性file.encoding指定的，可以在启动java时设定，如-Dfile.encoding=GBK
 */
public class Encoding {

	public static String changeEncoding(String str, String encoding)
			throws UnsupportedEncodingException {
		if (str != null) {
			// 无论使用何种encoding，对于计算机而已，它存储的就是字节数组
			byte[] bs = str.getBytes();
			// 指定bs的编码，并生成java默认编码方式的字符串
			return new String(bs, encoding);
		}
		return null;
	}

	public static String changeEncoding(String str, String oldEncoding,
			String newEncoding) throws UnsupportedEncodingException {
		if (str != null) {
			// 注意这个方法是把str转换成oldEncoding编码方式的byte[]
			// 而不是把oldEncoding编码方式的str转换成byte[]
			// str是按照系统的encoding方式存放的
			byte[] bs = str.getBytes(oldEncoding);
			return new String(bs, newEncoding);
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 获得java的默认编码方式
		Properties sysProp = new Properties(System.getProperties());
		System.out.println("file.encoding: "
				+ sysProp.getProperty("file.encoding"));
		System.out.println("user.language: "
				+ sysProp.getProperty("user.language"));

		String str = "你好"; // 这里的str默认使用UTF-8编码，这和eclipse选择的编码方式一致

		// 没有乱码，说明str的默认编码方式是UTF-8
		System.out.println(changeEncoding(str, "UTF-8"));

		// 有乱码
		System.out.println(changeEncoding(str, "GBK"));

		// 有乱码
		System.out.println(changeEncoding(str, "UTF-8", "GBK"));

		// 没有乱码
		System.out.println(changeEncoding(str, "GBK", "GBK"));

	}

}
