package chat.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    private final String username;
    private Group group;

    private final transient List<MessageObserver> observers;

    public User(String username) {
        super();
        this.username = username;
        this.observers = new LinkedList<>();
    }

    public Group getGroup() {
        return group;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "@" + username;
    }
}
