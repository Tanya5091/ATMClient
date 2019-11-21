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

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ReplenishBalanceStageController implements Initializable {


    public Button backMenuButton;
    public Button approveReplenishmentButton;
    @FXML
    private TextField amountField;
    @FXML
    private Label errorLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        amountField.setOnKeyTyped(event -> {
            event.consume();
            char charTyped = event.getCharacter().charAt(0);
            checkAmountField(amountField.getText(), charTyped);
        });
    }

    private void displayErrorField(String s) {
        errorLabel.setText(s);
    }

    private void checkAmountField(String newValue, char digit) {
        if (!Character.isDigit(digit)) {
            if (newValue.length() > 1)
                amountField.setText(newValue.substring(0, newValue.length() - 2));
            amountField.setText("0");
            displayErrorField("Field can be filled only with digits");
        } else if (newValue.length() > 8) {
            amountField.setText(newValue.substring(1));
            displayErrorField("Max limit is exceeded.");
        } else {
            errorLabel.setText(" ");
            double value;
            if (newValue.length() > 2)
                value = Double.parseDouble(newValue.substring(1));
            else
                value = 0;
            value *= 10;
            value += Character.getNumericValue(digit) * 0.01;
            value = (int) (Math.round(value * 100)) / 100.0;
            amountField.setText(value + "");
        }
    }

    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        changeStage(actionEvent, "accountStage.fxml");
    }

    @FXML
    public void approveReplenishment(ActionEvent actionEvent) {
        try {
            boolean corr = Main.rmiServer.replenishAccount(Main.user.getCardNumber(), Main.user.getPin(), Main.user.getOp(), Float.parseFloat(amountField.getText()));
            if (corr)
                changeStage(actionEvent, "successWindow.fxml");
            else
                changeStage(actionEvent, "FailWindow.fxml");
        } catch (RemoteException e) {
            displayErrorField("Cannot process your request at the moment");
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
}
