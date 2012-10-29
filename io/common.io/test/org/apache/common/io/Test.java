package test.org.apache.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.IOUtils;

public class Test {

	/**
	 * 几个小例子 2011.11.7
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/**
		 * 查看文件夹的剩余空间
		 */
		long freeSpace = FileSystemUtils.freeSpaceKb("C:/");
		System.out.println(freeSpace + " KB");
		
		/**
		 * 拉取某个网页
		 */
		InputStream in = new URL( "http://www.google.com/" ).openStream();
		try {
		   System.out.println( IOUtils.toString( in ) );
		} finally {
		   IOUtils.closeQuietly(in);
		}
	}

}
