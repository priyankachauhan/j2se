package com.pugwoo.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class ResultImpl extends UnicastRemoteObject implements ResultInterface {

	public ResultImpl() throws RemoteException {
	}

	private String msg;

	@Override
	public String getMsg() throws RemoteException {
		return msg;
	}

	@Override
	public void setMsg(String msg) throws RemoteException {
		this.msg = msg;
	}

}
