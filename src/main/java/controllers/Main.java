package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kmalfa.ReceiveMessageInterface;
import kmalfa.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {

    private static final String SERVER_ADDRESS = "13.81.213.191", REGISTRY_NAME = "bankServer";
    private static final int SERVER_PORT = 1099;
    public static ReceiveMessageInterface rmiServer;
    public static User user;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(SERVER_ADDRESS, SERVER_PORT);
            rmiServer = (ReceiveMessageInterface) registry.lookup(REGISTRY_NAME);
        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getMessage());
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginStage.fxml"));

        primaryStage.setTitle("KmAlfa");
        Scene loginScene = new Scene(root);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}
