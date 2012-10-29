package com.pugwoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class TestURL {

	/**
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		URL url = new URL("http://www.baidu.com");
//		InputStream in = url.openStream();
//        int c; 
//        while ((c = in.read()) != -1) 
//                System.out.print((char)c); 
//        in.close(); 
		
		InputStream nin = new URL("http://www.baidu.com").openStream();
		InputStreamReader ireader = new InputStreamReader(nin, "gb2312");
		BufferedReader breader = new BufferedReader(ireader);
		String str;
		do {
			str = breader.readLine();
			System.out.println(str);
		} while (str != null);
	}

}
