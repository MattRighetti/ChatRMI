package chat.controller;

import chat.model.Message;
import chat.model.MessageObserver;
import chat.model.User;
import chat.view.RemoteTextView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote {
    User login(String username, RemoteTextView remoteTextView, MessageObserver messageObserver) throws RemoteException;

    void logout(User user) throws RemoteException;

    Message sendMessage(String message, User sender) throws RemoteException;

    void observeUser(MessageObserver messageObserver, User user) throws RemoteException;
}
