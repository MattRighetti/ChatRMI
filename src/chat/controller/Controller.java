package chat.controller;

import chat.model.Database;
import chat.model.Message;
import chat.model.MessageObserver;
import chat.model.User;
import chat.view.RemoteTextView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private final transient Database database;
    private final Set<RemoteTextView> views = new HashSet<>();

    public Controller() throws RemoteException {
        super();
        database = Database.get();
        System.out.println("Database retrieved");
    }

    public synchronized User getUserSender(String username) {
        return database.getUserFromDb(username);
    }

    public synchronized void observeUser(MessageObserver messageObserver, User user) {
        database.getUserFromDb(user.getUsername()).observeUser(messageObserver);
    }

    @Override
    public synchronized User login(String username, RemoteTextView remoteTextView, MessageObserver messageObserver) throws RemoteException {
        User user;
        user = database.loginUser(username, messageObserver);
        if(user == null)
            throw new RemoteException("User incorrect");
        views.add(remoteTextView);
        remoteTextView.ack("Logged in as @" + username);
        return user;
    }

    @Override
    public synchronized void logout(User user) throws RemoteException {
        database.logoutUser(user);
    }

    @Override
    public synchronized Message sendMessage(String message, User sender) throws RemoteException {
        User user = getUserSender(sender.getUsername());
        return user.post(message);
    }
}
