package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    private final String username;

    private final transient List<MessageObserver> observers;

    public User(String username, List<MessageObserver> observers) {
        this.username = username;
        this.observers = new LinkedList<>();
    }

    public void observeMessage(MessageObserver observer) {
        observers.add(observer);
    }

    public String getUsername() {
        return username;
    }


}
