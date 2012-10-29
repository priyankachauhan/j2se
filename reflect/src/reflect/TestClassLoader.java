package reflect;

//import java.net.URLClassLoader;

public class TestClassLoader {

	public static void main(String[] args) throws ClassNotFoundException {
		
		ClassLoader cl = new ClassLoader() {
		};
		Class<?> clazz = cl.loadClass("java.lang.String");
		System.out.println(clazz.getName());
		
		//URLClassLoader classLoader = new URLClassLoader("");
	}

}
