package com.pugwoo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class MyTimerTask extends TimerTask {
	
	private final static byte[] TYPES = new byte[]{
		        0x01,0x02,0x03,0x04,0x05
		    };

	
	private final static ConcurrentHashMap<Byte, ConcurrentHashMap<Long, Integer>> queues =
		        new ConcurrentHashMap<Byte, ConcurrentHashMap<Long, Integer>>(){{
		            for(byte type : TYPES)
		                put(type, new ConcurrentHashMap<Long, Integer>());
		        }};


	@Override
	public void run() {
		System.out.println("MyTimerTask running");
		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Timer timer = new Timer();
		/**
		 * 1秒钟后只运行一次
		 */
		//timer.schedule(new MyTimerTask(), 1 * 1000);

		/**
		 * 每秒执行一次MyTimerTask，必须等待它执行完毕后再执行下一次
		 * 这个方法更注重保持间隔时间的稳定。
		 */
		timer.schedule(new MyTimerTask(), 0, 1 * 1000);

		// 此外还有scheduleAtFixedRate，这个方法更注重保持执行频率的稳定。
		
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
	}
}
