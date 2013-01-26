package com.pugwoo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 2013年1月26日 09:00:39
 * 来自：http://blog.csdn.net/tanjiazhang/article/details/5139095
 */
public class Test {
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {
		// hello.java.txt存放源代码,把源代码存放到C盘
		FileReader fileRd = new FileReader("hello.java.txt");
		FileWriter fileWr = new FileWriter("C:/Hello.java");
		BufferedReader br = new BufferedReader(fileRd);
		String line = null;
		while ((line = br.readLine()) != null) {
			fileWr.write(line + "\n");
		}
		fileWr.close();

		// 编译源代码,class文件和hello.java同目录
		// 如果依赖于其它jar包，可以加上"-cp" "**.jar"等参数
		String[] complieArgs = new String[] { "C:/Hello.java" };
		com.sun.tools.javac.Main.compile(complieArgs);

		// 加载class
		URLClassLoader loader = new URLClassLoader(new URL[] { new File("C:/")
				.toURI().toURL() });
		Class<?> scriptClass = loader.loadClass("Hello");

		Object obj = scriptClass.newInstance();
		obj.getClass().getDeclaredMethod("main", String[].class).invoke(obj,
				(Object) args);
	}
}
