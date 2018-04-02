package chat.view;

import chat.model.Message;
import chat.controller.RemoteController;
import chat.model.MessageObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class TextView extends UnicastRemoteObject implements MessageObserver, RemoteTextView {
    private static final String QUIT_COMMAND = ":q";
    String username; // To avoid using the User model class
    String groupName;

    private final RemoteController remoteController;
    private final Scanner input;

    public TextView(RemoteController remoteController) throws RemoteException {
        this.remoteController = remoteController;
        this.input = new Scanner(System.in);
    }


    public void run() throws RemoteException {
        chooseUsernameGroupPhase(input);
        messagingPhase(input);
        logoutPhase();
    }

    /**
     *
     *
     * PHASES ------------------------------------------------------------------------
     *
     *
     */

    public void chooseUsernameGroupPhase(Scanner input) throws RemoteException {
        do {
            System.out.println("Choose username:");
            username = input.nextLine();
            if (!username.isEmpty()) {
                System.out.println("Choose group:");
                groupName = input.nextLine();
                if (!groupName.isEmpty()) {
                    try {
                        remoteController.login(username, groupName, this, this);
                    } catch (RemoteException e) {
                        System.err.println("Username already in use, choose a different one");
                        username = "";
                        groupName = "";
                    }
                }
            }
        } while (username.isEmpty());
    }

    public void messagingPhase(Scanner input) throws RemoteException {
        String text;
        Message message;
        System.out.println("Welcome!");
        do {
            text = input.nextLine();
            if (!text.equals(QUIT_COMMAND))
                message = remoteController.sendMessage(text, username);
        } while (!text.equals(QUIT_COMMAND));
    }

    public void logoutPhase() throws RemoteException {
        remoteController.logout(username, this);
    }

    /**
     *
     *
     * PHASES ------------------------------------------------------------------------
     *
     *
     */

    @Override
    public void onNewUserJoined(String usernameJoined) throws RemoteException {
        System.out.println(usernameJoined + " joined the group");
    }

    @Override
    public void onLeave(String usernameLeft) throws RemoteException {
        System.out.println(usernameLeft + " left");
    }

    @Override
    public void onNewMessage(Message message) throws RemoteException {
        System.out.print(">>>" + message.getSender().getUsername() + ":");
        System.out.println(message.getMessage());
    }

    @Override
    public void error(String message) throws RemoteException {
        System.err.println(">>>" + message);
    }

    @Override
    public void ack(String message) throws RemoteException {
        System.out.println(">>>" + message);
    }
}
