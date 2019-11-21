package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountStageController implements Initializable {

    public Button backButton;
    public Button nextButton;
    public RadioButton autoTransfersButton;
    public RadioButton rechargeButton;
    public RadioButton withdrawButton;
    public RadioButton refillCard;
    public RadioButton changePIN;
    public RadioButton checkBalanceButton;
    @FXML
    ToggleGroup menuGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
    public void backToLogForm(ActionEvent event) {
        Main.user.destroySession();
        changeStage(event, "loginStage.fxml");
    }

    @FXML
    public void nextStage(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) menuGroup.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();

        switch (toogleGroupValue) {
            case "Check your balance":
                changeStage(event, "BalanceStage.fxml");
                break;
            case "Autotransfers":
                changeStage(event, "AutotransfersStage.fxml");
                break;
            case "Transfer to card":
                changeStage(event, "RechargeStage.fxml");
                break;
            case "Replenish balance":
                changeStage(event, "ReplenishBalanceStage.fxml");
                break;
            case "Change PIN":
                changeStage(event, "ChangePINStage.fxml");
                break;
            default:
                changeStage(event, "WithdrawStage.fxml");
                break;
        }
    }

}
