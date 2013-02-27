package com.pugwoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 2012年11月10日 下午04:24:20
 */
public class TestURL {

	public static void main(String[] args) throws IOException {
		//		URL url = new URL("http://www.baidu.com");
		//		InputStream in = url.openStream();
		//      int c; 
		//      while ((c = in.read()) != -1) 
		//          System.out.print((char)c); 
		//      in.close(); 

		InputStream nin = new URL("http://www.baidu.com").openStream();
		InputStreamReader ireader = new InputStreamReader(nin, "gb2312");
		BufferedReader breader = new BufferedReader(ireader);
		String line = null;
		while ((line = breader.readLine()) != null) {
			System.out.println(line);
		}
		nin.close();
	}

}
