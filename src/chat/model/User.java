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

    public Message post(String text) throws RemoteException {
        Message message = new Message(text, this);

        for (User user : group.getUsers()) {
            user.receiveMessage(message);
        }

        return message;
    }

    public void receiveMessage(Message message) throws RemoteException {
        for (MessageObserver messageObserver : observers) {
            messageObserver.onNewMessage(message);
        }
    }

    @Override
    public String toString() {
        return "@" + username;
    }
}
