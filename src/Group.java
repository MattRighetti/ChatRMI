import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Group implements Serializable {
    private static int groupID = 0;
    private String groupName;
    private int uniqueGroupID;
    private List<Message> messages;
    private Set<User> users;

    public Group(String groupName) {
        super();
        this.uniqueGroupID = groupID;
        this.messages = new LinkedList<>();
        this.users = new HashSet<>();
        this.groupName = groupName;
        groupID++;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getGroupName() {
        return groupName;
    }

    public Set<User> getUsers() {
        return users;
    }


}
