package chat.model;

import java.io.Serializable;

public class Message implements Serializable {
    final String message;
    final User user;
    final Group group;

    public Message(String message, User user) {
        super();
        this.message = message;
        this.user = user;
        this.group = user.getGroup();
    }

    public User getSender() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return user.getUsername() + "@" + group.getGroupName() + ":" + message;
    }
}
