package chat.model;

import java.rmi.RemoteException;
import java.util.*;

public class Database {
    private static Database database;
    private final Set<Group> groupsById = new HashSet<>(); // DUMMY
    private final Set<User> loggedUsers = new HashSet<>();

    // SINGLETON
    private Database() {

    }

    // SINGLETON
    public synchronized static Database get() {
        if (database == null)
            database = new Database();
        return database;
    }

    public User getUserFromDb(String username) {
        for (User user : loggedUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new IllegalArgumentException();
    }

    public Group getGroup(String groupName) {
        for (Group group : groupsById) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }

        Group group = new Group(groupName);
        groupsById.add(group);
        return group;
    }

    /**
     * Checks if the username is already used by other Clients
     *
     * @param username
     * @return
     */
    public boolean checkUsernameAlreadyInUse(String username) {
        return loggedUsers.stream().map(User::getUsername).anyMatch(x -> x.equals(username));
    }

    public User login(String username, String groupName, MessageObserver messageObserver) throws RemoteException {
        if (checkUsernameAlreadyInUse(username)) {
            throw new RemoteException("Username already in use: " + username);
        }

        Group group = getGroup(groupName);
        User user = new User(username, group, messageObserver);
        loggedUsers.add(user);
        group.getUsers().add(user);
        return user;
    }

    public void logoutUser(User user) throws RemoteException {
        if (!loggedUsers.contains(user)) {
            throw new RemoteException("User not in Set" + user);
        }

        loggedUsers.remove(user);
    }
}
