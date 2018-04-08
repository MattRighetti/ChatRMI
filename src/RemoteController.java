import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    void login(String username, String groupName, RemoteTextView remoteTextView, MessageObserver messageObserver) throws RemoteException;

    void logout(String username, RemoteTextView remoteTextView) throws RemoteException;

    void sendMessage(String message, String username) throws RemoteException;
}
