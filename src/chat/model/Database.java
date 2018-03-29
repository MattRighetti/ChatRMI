package chat.model;

import java.rmi.RemoteException;
import java.util.*;

public class Database {
    private static Database database;
    private final  Group defaultGroup = new Group();
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

    public Group getDefaultGroup() {
        return defaultGroup;
    }

    public User getUserFromDb(String username) {
        for (User user : loggedUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new IllegalArgumentException();
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

    /**
     * Checks if the username is already used by other Clients
     * if not -> adds the new User to the HashSet<User>
     *
     * @param username
     * @throws RemoteException
     */
    public User loginUser(String username, MessageObserver messageObserver) throws RemoteException {
        if (checkUsernameAlreadyInUse(username)) {
            throw new RemoteException("Username already in use:" + username);
        }

        User user = new User(username, getDefaultGroup());
        user.observeUser(messageObserver);
        getDefaultGroup().getUsers().add(user);
        loggedUsers.add(user);
        return user;
    }

    public void logoutUser(User user) throws RemoteException {
        if (!loggedUsers.contains(user)) {
            throw new RemoteException("User not in Set" + user);
        }

        loggedUsers.remove(user);
    }
}
