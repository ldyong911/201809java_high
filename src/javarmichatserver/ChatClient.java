package javarmichatserver;

import java.lang.String;

import java.rmi.Remote;

import java.rmi.RemoteException;

public interface ChatClient extends Remote {

	public void printMessage(String msg) throws RemoteException;

}