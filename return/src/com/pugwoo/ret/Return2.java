package com.pugwoo.ret;

public class Return2<T1, T2> {

	public T1 t1;
	public T2 t2;

	public Return2() {
	}

	public Return2(T1 t1, T2 t2) {
		this.t1 = t1;
		this.t2 = t2;
	}

	public T1 getT1() {
		return t1;
	}

	public void setT1(T1 t1) {
		this.t1 = t1;
	}

	public T2 getT2() {
		return t2;
	}

	public void setT2(T2 t2) {
		this.t2 = t2;
	}

}
