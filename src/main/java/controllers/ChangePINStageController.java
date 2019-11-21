package controllers;

import javafx.beans.value.ChangeListener;
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
import kmalfa.utils.PinCodeAnalyzer;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChangePINStageController implements Initializable {

    public Button nextApproveChangePINButton;
    public Button backToMenuButton;
    @FXML
    private TextField newPINField;
    @FXML
    private TextField oldPINField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ChangeListener keyFieldListenerOld = (ChangeListener<String>) (observable, oldValue, newValue) -> checkPINField(newValue, true);


        ChangeListener keyFieldListenerNew = (ChangeListener<String>) (observable, oldValue, newValue) -> checkPINField(newValue, false);
        oldPINField.textProperty().addListener(keyFieldListenerOld);
        newPINField.textProperty().addListener(keyFieldListenerNew);
    }

    public void backToMenu(ActionEvent actionEvent) {

        changeStage(actionEvent, "accountStage.fxml");
    }

    public void changePIN(ActionEvent actionEvent) {

        String oldPIN = oldPINField.getText();
        String newPIN = newPINField.getText();
        if (oldPIN.equals("") || newPIN.equals("")) {
            displayErrorField("Fill both fields!");
        } else if (newPIN.length() != 4 || oldPIN.length() != 4) {
            displayErrorField("Fill fields with 4 digits!");
        } else {

            int pin = PinCodeAnalyzer.generatePin(Integer.parseInt(newPIN), Main.user.getOp());
            int oldPin = PinCodeAnalyzer.generatePin(Integer.parseInt(oldPIN), Main.user.getOp());
            if (Main.user.getPin() != oldPin) {
                displayErrorField("Old password is wrong");
            } else
                try {
                    if (Main.rmiServer.changePIN(Main.user.getCardNumber(), Main.user.getPin(), pin, Main.user.getOp())) {
                        Main.user.setPin(pin);
                        changeStage(actionEvent, "successWindow.fxml");
                    } else
                        changeStage(actionEvent, "FailWindow.fxml");
                } catch (RemoteException e) {
                    System.err.println(e.getMessage());
                }


        }
    }

    private void changeStage(ActionEvent actionEvent, String path) {
        Parent tableViewParent;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource(path));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void checkPINField(String newValue, boolean isOldField) {

        if (!newValue.matches("\\d+")) {
            if (isOldField)
                oldPINField.setText(newValue.replaceAll("[^\\d]", ""));
            else
                newPINField.setText(newValue.replaceAll("[^\\d]", ""));
            displayErrorField("Field can be filled only with digits");
        } else if (newValue.length() > 4) {
            if (isOldField)
                oldPINField.setText(newValue.substring(0, 4));
            else
                newPINField.setText(newValue.substring(0, 4));

            displayErrorField("Max length of input - " + 4);
        } else
            errorLabel.setText(" ");
    }

    private void displayErrorField(String s) {
        errorLabel.setText(s);
    }


}
