package com.pugwoo.test;

import javax.xml.bind.DatatypeConverter;

/**
 * 2014-3-14 2014-3-14 15:38:05
 * 
 * 在线base64编码解码：http://www.baidu.com/#wd=base64
 */
public class TestBase64 {

	/**
	 * http://stackoverflow.com/questions/14413169/which-library-provide-base64-encoding-decoding
	 */
	public static void main(String[] args) {
		byte[] message = "hello world 你好".getBytes(); // 文件是什么编码，这里拿出来的byte就是这样的
		
		String encoded = DatatypeConverter.printBase64Binary(message); // base64看到的只是8位字节，不关心编码
		byte[] decoded = DatatypeConverter.parseBase64Binary(encoded);

		System.out.println(encoded);
		System.out.println(new String(decoded));
	}

}
