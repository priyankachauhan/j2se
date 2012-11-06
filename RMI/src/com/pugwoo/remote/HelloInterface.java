package com.pugwoo.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 远程接口必须扩展接口java.rmi.Remote
 */
public interface HelloInterface extends Remote {
	/**
	 * 远程接口方法必须抛出 java.rmi.RemoteException
	 */
	public String say(String something) throws RemoteException;

	/**
	 * 返回另外一个remote对象，client可以操作这个remote对象
	 */
	public ResultInterface remoteSay(String something) throws RemoteException;
}
