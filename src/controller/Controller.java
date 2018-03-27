package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller extends UnicastRemoteObject implements RemoteController {
    public Controller() throws RemoteException {
        super();
        //TODO
    }
}
