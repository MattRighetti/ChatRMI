package chat.view;

import chat.model.Message;
import chat.model.User;
import chat.controller.RemoteController;
import chat.model.MessageObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class TextView extends UnicastRemoteObject implements MessageObserver, RemoteTextView {

    private static final String QUIT_COMMAND = ":q";
    private final RemoteController remoteController;
    private final Scanner input;

    public TextView(RemoteController remoteController) throws RemoteException {
        this.remoteController = remoteController;
        this.input = new Scanner(System.in);
    }

    /**
     * Runna la TextView che:
     * - Chiede lo username e controlla se non è già utilizzato
     * - Continua a inviare messaggi
     * - Quando viene scritto ":q" esce e viene disconnesso
     * @throws RemoteException
     */
    public void run() throws RemoteException {
        String username;
        User user = null;
        do {
            System.out.println("Provide username:");
            username = input.nextLine();
            if (!username.isEmpty())
                System.out.println("Username not empty");
                user = remoteController.login(username, this);
            System.out.println("Created");
            onNewUserJoined();
        } while (username.isEmpty());

        Message message;
        String text;
        do {
            System.out.println("Provide command:");
            text = input.nextLine();
            message = new Message(text, user, user.getGroup());
            if (!text.startsWith(QUIT_COMMAND)) {
                remoteController.sendMessage(message);
                onNewMessage(message);
            }
        } while (text.startsWith(QUIT_COMMAND));

        remoteController.logout(user);
        onLeave();
        System.out.println("Disconnected");
    }

    public void displayText(String text) {
        System.out.println(">>>>" + text);
    }

    @Override
    public void onNewUserJoined() throws RemoteException {
        System.out.println("New user joined");
    }

    @Override
    public void onLeave() throws RemoteException {
        System.out.println("User left");
    }

    @Override
    public void onNewMessage(Message message) throws RemoteException {
        System.out.println(">>>" + message.getSender() + ":");
        System.out.println(message.getMessage());
    }
}
