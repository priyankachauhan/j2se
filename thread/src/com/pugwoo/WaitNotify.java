package com.pugwoo;

public class WaitNotify {

	public static void main(String[] args) throws InterruptedException {

		WaitNotify waitNotify = new WaitNotify();
		synchronized (waitNotify) {
			waitNotify.wait();
		}
	}
	
	

}
