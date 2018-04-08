import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        System.setProperty("java.security.policy", "stupid.policy");
        System.setSecurityManager(new SecurityManager());

        Controller controller = new Controller();
        System.out.println("Controller exported...");

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("chat", controller);
    }
}
