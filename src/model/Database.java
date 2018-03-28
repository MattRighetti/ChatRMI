package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Database {
    private static Database database;
    private final Set<Group> groupsById = new HashSet<>();

    private Database() {

    }

    public synchronized static Database get() {
        if (database == null)
            database = new Database();
        return database;
    }

    //TODO wrong implementation, should not check all groups
    public boolean checkUsernameAlreadyInUse(String username) {
        for (Group group : groupsById) {
            for (User user : group.getUsers()) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }
}
