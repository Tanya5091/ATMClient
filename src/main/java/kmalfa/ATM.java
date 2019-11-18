package kmalfa;

import kmalfa.utils.PinCodeAnalyzer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ATM {
    private static final String SERVER_ADDRESS = "13.81.213.191", REGISTRY_NAME = "bankServer";
    private static final int SERVER_PORT = 1099;
    private static ReceiveMessageInterface rmiServer;
    private static boolean isServerOK = true;

    static public void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(SERVER_ADDRESS, SERVER_PORT);
            rmiServer = (ReceiveMessageInterface) registry.lookup(REGISTRY_NAME);
            int pin;
            String cardNumber;
            User session;
            cardNumber = "1123581321345589";
            System.out.println("Please enter your PIN: ");
            int op = PinCodeAnalyzer.generateOp();

            pin = PinCodeAnalyzer.generatePin(Integer.parseInt("1123"), op);

//            String cardHolderName = rmiServer.verifyPIN(cardN, pin, op);
            String cardHolderName = rmiServer.verifyPIN(cardNumber, pin, op);
            System.out.println(cardHolderName);
            session = new User(cardHolderName, cardNumber, pin, op);
            System.out.println(rmiServer.getBalance(session.getCardNumber(), session.getPin(), session.getOp()));

        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
        }


    }
}
