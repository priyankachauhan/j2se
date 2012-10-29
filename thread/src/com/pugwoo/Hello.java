package com.pugwoo;

/**
 * 2010年12月6日 下午08:48:20
 * @author Administrator
 *
 */
public class Hello implements Runnable{

	public static void main(String args[]){
		Thread thread[] = new Thread[20];
		for(int i=0; i<20; i++){
			thread[i] = new Thread(new Hello());
		}
		for(int i=0; i<20; i++){
			thread[i].start();
		}
		System.out.println(Thread.currentThread().getName());
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		
	}
}
