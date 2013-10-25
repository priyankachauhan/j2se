package com.pugwoo.ret;

public class Test {

	public static Return2<Integer, String> testMultiReturn() {
		return new Return2<Integer, String>(1, "hi");
	}

	public static void main(String[] args) {
		Return2<Integer, String> ret = testMultiReturn();
		System.out.println(ret.t1 + "," + ret.t2);
	}
}
