package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import kmalfa.*;
import kmalfa.utils.PinCodeAnalyzer;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChangePINStageController implements Initializable {

    @FXML
    private TextField newPINField;
    @FXML
    private TextField oldPINField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ChangeListener keyFieldListenerOld =   new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                checkPINField(newValue, 4, true);
            }
        };


        ChangeListener keyFieldListenerNew =   new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                checkPINField(newValue, 4, false);
            }
        };
        oldPINField.textProperty().addListener(keyFieldListenerOld);
        newPINField.textProperty().addListener(keyFieldListenerNew);
    }

    public void backToMenu(ActionEvent actionEvent) {

        changeStage(actionEvent, "accountStage.fxml");
    }

    public void changePIN(ActionEvent actionEvent) {

        String oldPIN = oldPINField.getText();
        String newPIN = newPINField.getText();
        if(oldPIN.equals("")|| newPIN.equals("") ){
            displayErrorField("Fill both fields!");
        }
        else if( newPIN.length()!=4 || oldPIN.length()!=4){
            displayErrorField("Fill fields with 4 digits!");
        }
        else {

            int pin = PinCodeAnalyzer.generatePin(Integer.parseInt(newPIN), Main.user.getOp());
//            if (Main.user.getPin() != pin) {
//                displayErrorField("Password doesn`t match");
//            }
            int oldPin = PinCodeAnalyzer.generatePin(Integer.parseInt(oldPIN), Main.user.getOp());
//
            if (Main.user.getPin()!=oldPin)
            {
                displayErrorField("Old password is wrong");
            }
            else
                try {
                    if (Main.rmiServer.changePIN(Main.user.getCardNumber(), Main.user.getPin(), pin, Main.user.getOp())) {
                        Main.user.setPin(pin);
                        changeStage(actionEvent,"successWindow.fxml" );
                    }

                    else
                        changeStage(actionEvent,"FailWindow.fxml" );
                } catch (RemoteException e) {
                    e.printStackTrace();
                }




        }
    }

    private void changeStage(ActionEvent actionEvent, String path) {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass().getResource(path));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void checkPINField(String newValue, int maxLength, boolean isOldField) {

        if (!newValue.matches("\\d+")) {
            if(isOldField)
                oldPINField.setText(newValue.replaceAll("[^\\d]", ""));
            else
                newPINField.setText(newValue.replaceAll("[^\\d]", ""));
            displayErrorField("Field can be filled only with digits");
        }
        else if(newValue.length()>maxLength ) {
            if(isOldField)
                oldPINField.setText(newValue.substring(0, maxLength));
            else
                newPINField.setText(newValue.substring(0, maxLength));

            displayErrorField("Max length of input - "+maxLength);
        }
        else
            errorLabel.setText(" ");
    }

    private void displayErrorField(String s) {
        errorLabel.setText(s);
    }


}
