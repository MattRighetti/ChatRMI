package chat.controller;

import chat.model.Message;
import chat.model.User;
import chat.view.RemoteTextView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    User login(String username, RemoteTextView remoteTextView) throws RemoteException;
    void logout(User user) throws RemoteException;
    void sendMessage(Message message) throws RemoteException;
}
