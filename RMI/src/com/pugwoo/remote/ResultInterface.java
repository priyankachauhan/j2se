package com.pugwoo.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ResultInterface extends Remote {

	public String getMsg() throws RemoteException;

	public void setMsg(String msg) throws RemoteException;

}
