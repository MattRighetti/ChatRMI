package chat;

import chat.controller.RemoteController;
import chat.view.TextView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();

        RemoteController remoteController = (RemoteController) registry.lookup("chat");

        new TextView(remoteController).run();
    }
}
