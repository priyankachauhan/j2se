package com.pugwoo.test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 2010年12月6日 下午08:46:06
 */
public class Client {

	public static void main(String args[]){
		
		//获取一个业务接口的实现对象
		HelloWorld hw = new HelloWorldImpl();
		
		//获取一个InvocationHandler实现，此处是HelloWorldHandler对象
		InvocationHandler handler = new HelloWorldHandler(hw);
		
		//创建动态代理对象
		HelloWorld proxy = (HelloWorld) Proxy.newProxyInstance(
				hw.getClass().getClassLoader(),
				hw.getClass().getInterfaces(),
				handler);
		
		//通过动态代理对象调用sayHelloWorld()方法
		proxy.sayHello();
		
	}
}
