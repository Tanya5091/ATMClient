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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.ResourceBundle;

public class RechargeStageController implements Initializable {


    public Button nextApproveTransferButton;
    public Button backMenuButton;
    @FXML
    private TextField cardNumField;
    @FXML
    private TextField amountField;
    @FXML
    private Slider periodicityBox;
    @FXML
    private Label errorLabel;
    @FXML
    private Label periodicityLabel;
    private String fst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardNumField.textProperty().addListener((observable, oldValue, newValue) -> checkTransferField(newValue, 'a', 16, true));

        amountField.setOnKeyTyped(event -> {
            event.consume();
            char charTyped = event.getCharacter().charAt(0);
            checkTransferField(amountField.getText(), charTyped, 8, false);
        });

        periodicityBox.valueProperty().addListener((observable, oldValue, newValue) -> periodicityLabel.textProperty().setValue(String.valueOf((int) periodicityBox.getValue())));
    }

    private void checkTransferField(String newValue, char digit, int maxLength, boolean isCardNum) {

        if (isCardNum) {
            if (!newValue.matches("\\d+")) {
                cardNumField.setText(newValue.replaceAll("[^\\d]", ""));
                displayErrorField("Field can be filled only with digits");
            }
            if (newValue.length() > maxLength) {
                cardNumField.setText(newValue.substring(0, maxLength));
            }
        } else if (!Character.isDigit(digit)) {
            if (newValue.length() > 1)
                amountField.setText(newValue.substring(0, newValue.length() - 2));
            amountField.setText("0");
            displayErrorField("Field can be filled only with digits");
        } else if (newValue.length() > maxLength) {
            amountField.setText(newValue.substring(1));
            displayErrorField("Max limit is exceeded.");
            displayErrorField("Max length of input - " + maxLength);
        } else {
            errorLabel.setText(" ");
//0.001 10 1
            double value = newValue.length() > 2 ? Double.parseDouble(newValue.substring(1)) : 0;
            value *= 10;
            value += Character.getNumericValue(digit) * 0.01;
            value = (int) (Math.round(value * 100)) / 100.;
            amountField.setText(value + "");
        }
    }

    private void displayErrorField(String s) {
        errorLabel.setText(s);
    }

    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        changeStage(actionEvent, "accountStage.fxml");
    }


    @FXML
    public void changePeriodicityLabel() {
        double periodicity = periodicityBox.getValue();
        periodicityLabel.setText(periodicity + "");
    }


    @FXML
    public void approveTransferButton(ActionEvent actionEvent) {
        String cardN = cardNumField.getText();
        String amount = amountField.getText();
        double periodicity = periodicityBox.getValue();
        if (cardN.equals("") || amount.equals("")) {
            displayErrorField("Fill both fields!");
        } else if (cardN.length() != 16 || amount.length() <= 0) {
            displayErrorField("Fill card number with 16 digits, amount>=0!");
        } else {
            try {
                boolean corr;
                if (periodicity < 1) {
                    corr = Main.rmiServer.transfer(Main.user.getCardNumber(), cardN, Main.user.getPin(), Main.user.getOp(), Float.parseFloat(amount));
                } else {
                    corr = Main.rmiServer.setAutoTransfer(Main.user.getCardNumber(), cardN, Main.user.getPin(), Main.user.getOp(), Float.parseFloat(amount), new Date((long) (periodicity * 86400)));
                }
                if (corr)
                    changeStage(actionEvent, "successWindow.fxml");
                else
                    changeStage(actionEvent, "FailWindow.fxml");
            } catch (RemoteException e) {
                changeStage(actionEvent, "FailWindow.fxml");
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
}
