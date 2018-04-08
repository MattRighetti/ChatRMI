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

    /**
     * Returns User in the HashSet by its username
     * @param username
     * @return
     */
    public User getUserFromDb(String username) {
        for (User user : loggedUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Checks if the group is present in the HashSet,
     * otherwise it creates one and returns it after adding it to the HashSet
     * @param groupName
     * @return
     */
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

    /**
     * Retrieves the group by groupName
     * Creates a new User with its variables
     * Adds user to the LoggedUsers HashSet
     * Adds user to the Group HashSet
     * @param username
     * @param groupName
     * @param messageObserver
     * @return
     * @throws RemoteException
     */
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

    /**
     * Retrieves the Group by User's group and removes the user from the Group's HashSet
     * Removes user from the loggedUsers HashSet
     * @param user
     * @throws RemoteException
     */
    public void logoutUser(User user) throws RemoteException {
        if (!loggedUsers.contains(user)) {
            throw new RemoteException("User not in Set" + user);
        }

        getGroup(user.getGroup().getGroupName()).getUsers().remove(user);
        loggedUsers.remove(user);
    }
}
