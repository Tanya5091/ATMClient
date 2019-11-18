package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kmalfa.User;
import kmalfa.utils.PinCodeAnalyzer;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Button logButton;
    @FXML
    private TextField cardNumField;
    @FXML
    private TextField pinField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardNumField.textProperty().addListener((observable, oldValue, newValue) -> checkLogField(newValue, 16, true));
        pinField.textProperty().addListener((observable, oldValue, newValue) -> checkLogField(newValue, 4, false));
    }

    public void logAction(ActionEvent actionEvent) {

        String cardN = cardNumField.getText();
        String pinN = pinField.getText();
        if (cardN.equals("") || pinN.equals("")) {
            displayErrorField("Fill both fields!");
        } else if (cardN.length() != 16 || pinN.length() != 4) {
            displayErrorField("Fill card number with 16 digits, PIN - 4!");
        } else {
            try {

                int op = PinCodeAnalyzer.generateOp();
                int pin = PinCodeAnalyzer.generatePin(Integer.parseInt(pinN), op);
                String cardHolderName = Main.rmiServer.verifyPIN(cardN, pin, op);
                if (!cardHolderName.isEmpty()) {
                    Main.user = new User(cardHolderName, cardN, pin, op);
                    changeStage(actionEvent);
                } else {
                    displayErrorField("User doesn`t exist");
                }
            } catch (RemoteException e) {
                displayErrorField("Cannot connect to server");
            }
        }
    }

    private void changeStage(ActionEvent actionEvent) {
        Parent tableViewParent;
        try {

//            Path first = Paths.get("C:/Users/Julia/Desktop/workspace/MOOP_KmAlfa/src/controllers/MainController.java"); Path second = Paths.get("C:/Users/Julia/Desktop/workspace/MOOP_KmAlfa/src/controllers/accountStage.fxml");
//            System.out.println(first.relativize(second));
//            System.out.println(second.relativize(first));

            tableViewParent = FXMLLoader.load(getClass().getResource("accountStage.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            //This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkLogField(String newValue, int maxLength, boolean isCardNum) {

        if (!newValue.matches("\\d+")) {
            if (isCardNum)
                cardNumField.setText(newValue.replaceAll("[^\\d]", ""));
            else
                pinField.setText(newValue.replaceAll("[^\\d]", ""));
            displayErrorField("Field can be filled only with digits");
        } else if (newValue.length() > maxLength) {
            if (isCardNum)
                cardNumField.setText(newValue.substring(0, maxLength));
            else
                pinField.setText(newValue.substring(0, maxLength));
            displayErrorField("Max length of input - " + maxLength);
        } else
            errorLabel.setText(" ");
    }

    private void displayErrorField(String s) {
        errorLabel.setText(s);
    }


}
