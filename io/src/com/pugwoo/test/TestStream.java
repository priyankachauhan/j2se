package com.pugwoo.test;

import java.io.FileInputStream;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * 2012年10月31日 15:38:31
 */
public class TestStream extends TestCase{

	public void testFileInputStream() throws IOException
	{
		// readme.txt是在项目的根目录下
		FileInputStream fileInputStream = new FileInputStream("readme.txt");
		byte[] b = new byte[1024];
		fileInputStream.read(b);
		System.out.println(b[0]); // 打印出ascii码的数字
	}
	
	public static void main(String[] args) {

	}

}
