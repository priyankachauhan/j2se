package com.pugwoo;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianClientTest {
	public static void main(String[] args) {
		String url = "http://localhost:8080/Hessian/hessian/math";
		HessianProxyFactory factory = new HessianProxyFactory();
		MathService math = null;
		try {
			math = (MathService) factory.create(MathService.class, url);
		} catch (MalformedURLException e) {
			System.out.println("occur exception: " + e);
		}
		System.out.println("3 + 2 = " + math.add(3, 2));
	}
}