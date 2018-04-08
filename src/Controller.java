import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class Controller extends UnicastRemoteObject implements RemoteController {
    private final transient Database database;
    private final Set<RemoteTextView> views = new HashSet<>();

    public Controller() throws RemoteException {
        super();
        database = Database.get();
        System.out.println("Database retrieved");
    }

    public synchronized User getSender(String username) {
        return database.getUserFromDb(username);
    }

    @Override
    public synchronized void login(String username, String groupName, RemoteTextView remoteTextView,
                                   MessageObserver messageObserver) throws RemoteException {

        User user;
        user = database.login(username, groupName, messageObserver);
        if(user == null)
            throw new RemoteException("User incorrect");
        views.add(remoteTextView);
        user.onLogin(username);
        remoteTextView.ack("Logged in as @" + username);
    }

    @Override
    public synchronized void logout(String username, RemoteTextView remoteTextView) throws RemoteException {
        User user = getSender(username);
        remoteTextView.ack("You disconnected");
        user.onLogout(username);
        database.logoutUser(user);
        views.remove(remoteTextView);
    }

    @Override
    public synchronized void sendMessage(String message, String username) throws RemoteException {
        User user = getSender(username);
        user.post(message);
    }
}
