package chat.model;

import java.rmi.RemoteException;
import java.util.*;

public class Database {
    private static Database database;
    private final Set<Group> groupsById = new HashSet<>();
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
    public User loginUser(String username) throws RemoteException {
        if (checkUsernameAlreadyInUse(username)) {
            throw new RemoteException("Username already in use:" + username);
        }

        User user = new User(username);
        loggedUsers.add(user);
        return user;
    }

    public void logoutUser(User user) throws RemoteException {
        if (!loggedUsers.contains(user)) {
            throw new RemoteException("User not in Set" + user);
        }

        loggedUsers.remove(user);
    }

    public void sendMessage(Message message) throws RemoteException {
        if (message.group.getUsers().contains(message.getSender())) {
            message.group.getMessages().add(message);
        }
        System.err.println("Error not in group");
    }
}
