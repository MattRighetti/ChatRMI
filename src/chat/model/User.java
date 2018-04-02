package chat.model;

import java.io.Serializable;
import java.rmi.RemoteException;

public class User implements Serializable {
    private final String username;
    private Group group;

    MessageObserver observer;

    public User(String username, Group group, MessageObserver messageObserver) {
        super();
        this.username = username;
        this.group = group;
        this.observer = messageObserver;
    }

    public void observeUser(MessageObserver messageObserver) {
        observer = messageObserver;
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
        observer.onNewMessage(message);
    }

    public void receiveMessage(String operation, String usr) throws RemoteException {
        if (operation.equals("logout")) {
            observer.onLeave(usr);
        } else if (operation.equals("login")) {
            observer.onNewUserJoined(usr);
        }

    }

    @Override
    public String toString() {
        return "@" + username;
    }
}
