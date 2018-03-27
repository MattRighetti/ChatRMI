package controller;

import view.RemoteTextView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    void login(String username, RemoteTextView remoteTextView) throws RemoteException;
    void logout(String username) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
}
