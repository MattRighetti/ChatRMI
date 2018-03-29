package chat.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageObserver extends Remote {
    void onNewMessage(Message message) throws RemoteException;
    void onNewUserJoined(String username) throws RemoteException;
    void onLeave(String username) throws RemoteException;
}
