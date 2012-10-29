package com.pugwoo.simulate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 异步消息驱动设备
 * 2011-12-28
 */
public class AsynMsgDrivenDevice extends Thread {

	public AsynMsgDrivenDevice() {
	}

	public AsynMsgDrivenDevice(String name) {
		this.deviceName = name;
	}

	// 设备名称
	private String deviceName;

	// 消息队列，线程安全
	private Queue<Object> msgQ = new ConcurrentLinkedQueue<Object>();

	// 设备状态: 新、运行、暂停、停机
	enum State {
		NEW, RUNNING, PAUSE, HALT
	}

	// 设备状态
	private State state = State.NEW;

	// 暂停时间
	private long pauseTime = 0;

	/**
	 * 重写handle以实现对信息的处理
	 */
	protected void handle(Object message) {
	}

	/**
	 * 启动设备
	 */
	@Override
	public synchronized void start() {
		state = State.RUNNING;
		super.start();
	}

	/**
	 * 暂停milliseconds，如果在设备停止过程中再度调用pause，将重新计时
	 */
	public synchronized void pause(long milliseconds) {
		state = State.PAUSE;
		pauseTime = milliseconds;
		notifyAll();
	}

	/**
	 * 停机
	 */
	public synchronized void halt() {
		state = State.HALT;
		notifyAll();
	}

	/**
	 * 将消息加入消息队列
	 */
	public synchronized void putMsg(Object message) {
		if (state == State.NEW) {
			new Exception("State of device '" + deviceName
					+ "' is NEW, call start() first.").printStackTrace();
			return;
		}
		if (state == State.PAUSE) {
			new Exception("State of device '" + deviceName
					+ "' is PAUSE, msg is ignored.").printStackTrace();
			return;
		}
		if (state == State.HALT) {
			new Exception("State of device '" + deviceName
					+ "' is HALT, cannot recv msg.").printStackTrace();
			return;
		}
		// 将消息添加到消息队列中
		msgQ.offer(message);
		notifyAll();
	}

	@Override
	public void run() {
		while (true) {
			// 这里state不可能是NEW
			if (state == State.RUNNING) {
				while (msgQ.size() > 0) {
					// 记得重新判断运行状态
					if (state == State.RUNNING) {
						// handle过程无法中断（缺陷）
						handle(msgQ.poll());
					} else {
						break;
					}
				}
				// 等待消息的到来
				synchronized (this) {
					try {
						if (state == State.RUNNING)
							wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else if (state == State.PAUSE) {
				try {
					long p = pauseTime;
					pauseTime = 0;
					Thread.sleep(p);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 注意这里的逻辑，有可能在暂停过程中修改了状态
				if (pauseTime == 0 && state == State.PAUSE)
					state = State.RUNNING;
			} else if (state == State.HALT) {
				// 跳出循环，结束线程
				break;
			}
		}
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

}
