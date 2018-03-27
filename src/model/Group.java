package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Group implements Serializable {
    private static int groupID = 0;
    private List<Message> messages;
    private Set<User> users;
    private String groupName;

    public Group(String groupName) {
        groupID++;
        this.messages = new LinkedList<>();
        this.users = new HashSet<>();
        this.groupName = "group" + groupID;
    }



}
