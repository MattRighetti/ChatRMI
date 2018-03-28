import controller.RemoteController;
import view.TextView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();

        RemoteController remoteController = (RemoteController) registry.lookup("controller");

        new TextView(remoteController).run();
    }
}
