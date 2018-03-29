package chat;

import chat.controller.Controller;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        Controller controller = new Controller();
        System.out.println("Controller exported...");

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("chat", controller);
    }
}
