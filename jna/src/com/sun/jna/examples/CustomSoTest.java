package com.sun.jna.examples;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author nickxie 2012-11-8 上午10:22:31
 */
public class CustomSoTest {

	/**
	 * 目前先在linux下跑:
	 * 
	 * 首先要把so加载到系统的LD中：
	 * export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/path/to/so
	 * 
	 * 然后执行java -cp .:jna.jar com/sun/jna/examples/CustomSoTest
	 */
	public interface HelloLibrary extends Library {
		HelloLibrary INSTANCE = (HelloLibrary) Native.loadLibrary("helloworld",
				HelloLibrary.class);

		void sayhello();

		int add(int a, int b);
	}

	public static void main(String[] args) {

		HelloLibrary.INSTANCE.sayhello();
		
		int a = 1, b = 2;
		System.out.println(HelloLibrary.INSTANCE.add(a, b));
		
	}
}
