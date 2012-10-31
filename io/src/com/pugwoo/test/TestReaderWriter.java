package com.pugwoo.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author nickxie 2012-10-31 下午03:49:27
 */
public class TestReaderWriter extends TestCase {

	public void testFileReader() throws IOException {
		// 可以直接从文件获得FileReader对象
		FileReader fileReader = new FileReader("readme.txt");
		// 读取一个字符，16字节
		int ch = fileReader.read();
		System.out.println(ch);
	}

	public void testBufferedReader() throws IOException {
		// BufferedReader可以从Reader的子类获得，如FileReader或InputStreamReader
		FileReader fileReader = new FileReader("readme.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string = bufferedReader.readLine();
		System.out.println(string);
	}

	public static void main(String[] args) {

	}

}
