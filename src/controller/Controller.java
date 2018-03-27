package controller;

import model.Database;
import view.RemoteTextView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller extends UnicastRemoteObject implements RemoteController {
    public Controller() throws RemoteException {
        super();
        Database database = Database.get();
    }

    @Override
    public void login(String username, RemoteTextView remoteTextView) throws RemoteException {

    }

    @Override
    public void logout(String username) throws RemoteException {

    }

    @Override
    public void sendMessage(String message) throws RemoteException {

    }
}
