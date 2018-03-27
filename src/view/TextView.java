package view;

import controller.RemoteController;
import model.Message;
import model.MessageObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class TextView extends UnicastRemoteObject implements MessageObserver, RemoteTextView {

    private final RemoteController remoteController;
    private final Scanner input;

    public TextView(RemoteController remoteController, Scanner input) throws RemoteException {
        this.remoteController = remoteController;
        this.input = input;
    }

    public void displayText(String text) {
        System.out.println(">>>>" + text);
    }

    @Override
    public void onNewMessage(Message message) throws RemoteException {
        
    }
}
