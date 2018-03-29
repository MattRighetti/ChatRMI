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

    public synchronized User getSender(String username) {
        return database.getUserFromDb(username);
    }

    public synchronized void observeUser(MessageObserver messageObserver, User user) {
        database.getUserFromDb(user.getUsername()).observeUser(messageObserver);
    }

    @Override
    public synchronized void chooseGroup(String groupName, String username) {
        database.getOrCreateGroup(groupName);
    }

    @Override
    public synchronized void login(String username, String groupName, RemoteTextView remoteTextView,
                                   MessageObserver messageObserver) throws RemoteException {

        User user;
        user = database.loginUser(username, messageObserver);
        if(user == null)
            throw new RemoteException("User incorrect");
        views.add(remoteTextView);
        user.onLogin(username);
        remoteTextView.ack("Logged in as @" + username);
    }

    @Override
    public synchronized void logout(String username, RemoteTextView remoteTextView) throws RemoteException {
        User user = getSender(username);
        remoteTextView.ack("You disconnected");
        user.onLogout(username);
        database.logoutUser(user);
        views.remove(remoteTextView);
    }

    @Override
    public synchronized Message sendMessage(String message, String username) throws RemoteException {
        User user = getSender(username);
        return user.post(message);
    }
}
