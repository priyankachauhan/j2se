package com.pugwoo.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 扩展了UnicastRemoteObject类，并实现远程接口 HelloInterface
 */
@SuppressWarnings("serial")
public class HelloImpl extends UnicastRemoteObject implements HelloInterface {

	/**
	 * 必须定义构造方法，即使是默认构造方法，也必须把它明确地写出来，因为它必须抛出出RemoteException异常
	 */
	public HelloImpl() throws RemoteException {
	}

	/**
	 * 远程接口方法的实现
	 */
	public String say(String something) throws RemoteException {
		System.out.println("Called by HelloClient:" + something);
		return something;
	}

	@Override
	public ResultInterface remoteSay(String something) throws RemoteException {
		ResultInterface r = new ResultImpl();
		r.setMsg(something);
		return r;
	}

}
