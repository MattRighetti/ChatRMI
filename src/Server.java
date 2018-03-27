import controller.Controller;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Controller controller = new Controller();
        System.out.println("Something");

        Registry registry = LocateRegistry.getRegistry();
        registry.bind("controller", controller);
    }
}
