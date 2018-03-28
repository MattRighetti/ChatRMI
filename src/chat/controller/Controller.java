package chat.controller;

import chat.model.Database;
import chat.model.Message;
import chat.model.User;
import chat.view.RemoteTextView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private transient final Database database;

    public Controller() throws RemoteException {
        super();
        database = Database.get();
    }

    @Override
    public User login(String username, RemoteTextView remoteTextView) throws RemoteException {
        User user;
        try {
            user = database.loginUser(username);
            if(user != null)
                return user;
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void logout(User user) {
        try {
            database.logoutUser(user);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            database.sendMessage(message);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
