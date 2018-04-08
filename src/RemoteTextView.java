import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteTextView extends Remote {
    void error(String message) throws RemoteException;

    void ack(String message) throws RemoteException;
}
