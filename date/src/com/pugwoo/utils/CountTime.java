package com.pugwoo.utils;

/**
 * 测试Java运行 时间
 * 
 * 2011年4月13日 下午08:11:30
 */
public class CountTime {

	private Long start = 0L;

	private Long end = 0L;

	// 修正调用nanoTime()的时间
	private static Long delta = 0L;

	static {
		long s = System.nanoTime();
		long e = System.nanoTime();
		delta = e - s;
	}

	/**
	 * 记录当前计时状态： false 未开始; true 开始未结束
	 */
	private boolean COUNTING = false;

	/**
	 * 开始计时
	 */
	public void start() {
		if (!COUNTING) {
			COUNTING = true;
			start = System.nanoTime();
		}
	}

	/**
	 * 结束计时
	 */
	public void stop() {
		if (COUNTING) {
			end = System.nanoTime();
			COUNTING = false;
		}
	}

	/**
	 * 停止并获得毫秒数
	 */
	public double getTimeInMs() {
		stop();
		return (end - start - delta) / 1000000.0;
	}

	/**
	 * 停止并获得纳秒数
	 */
	public Long getTimeInNs() {
		stop();
		return end - start - delta;
	}

	/**
	 * 停止并打印
	 */
	public void printInMs() {
		stop();
		System.out.println(getTimeInMs() + "ms.");
	}

	/**
	 * 停止并打印
	 */
	public void printInNs() {
		stop();
		System.out.println(getTimeInNs() + "ns.");
	}
}
