package com.pugwoo;

import com.caucho.hessian.server.HessianServlet;

@SuppressWarnings("serial")
public class HessianMathService extends HessianServlet implements MathService {

	public int add(int a, int b) {
		return a + b;
	}
}