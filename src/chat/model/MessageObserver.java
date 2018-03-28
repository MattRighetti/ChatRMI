package chat.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageObserver extends Remote {
    void onNewMessage(Message message) throws RemoteException;
    void onNewUserJoined() throws RemoteException;
    void onLeave() throws RemoteException;
}
