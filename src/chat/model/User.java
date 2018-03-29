package chat.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    private final String username;
    private Group group;

    List<MessageObserver> observers = new LinkedList<>();

    public User(String username, Group group) {
        super();
        this.username = username;
        this.group = group;
    }

    public void observeUser(MessageObserver messageObserver) {
        observers.add(messageObserver);
    }

    public Group getGroup() {
        return group;
    }

    public String getUsername() {
        return username;
    }

    public void forEachFunction(String operation, String username) throws RemoteException {
        getGroup().getUsers().stream().filter(x -> !x.equals(this)).forEach(x -> {
            try {
                x.receiveMessage(operation, username);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public void forEachFunction(Message message) throws RemoteException {
        getGroup().getUsers().stream().filter(x -> !x.equals(this)).forEach(x -> {
            try {
                x.receiveMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public void onLogin(String username) throws RemoteException {
        forEachFunction("login", username);
    }

    public void onLogout(String username) throws RemoteException {
        forEachFunction("logout", username);
    }

    public Message post(String text) throws RemoteException {
        Message message = new Message(text, this);

        forEachFunction(message);

        return message;
    }

    public void receiveMessage(Message message) throws RemoteException {
        for (MessageObserver messageObserver : observers) {
            messageObserver.onNewMessage(message);
        }
    }

    public void receiveMessage(String operation, String usr) throws RemoteException {
        if (operation.equals("logout")) {
            for (MessageObserver messageObserver : observers) {
                messageObserver.onLeave(usr);
            }
        } else if (operation.equals("login")) {
            for (MessageObserver messageObserver : observers) {
                messageObserver.onNewUserJoined(usr);
            }
        }

    }

    @Override
    public String toString() {
        return "@" + username;
    }
}
