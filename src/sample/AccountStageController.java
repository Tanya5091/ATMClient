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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountStageController implements Initializable {



    @FXML
    ToggleGroup menuGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
    public void backToLogForm(ActionEvent event){
            Main.user.destroySession();
            changeStage(event, "loginStage.fxml");
    }

    @FXML
    public void nextStage(ActionEvent event){
        RadioButton selectedRadioButton = (RadioButton) menuGroup.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();

        if(toogleGroupValue.equals("Check your balance"))
            changeStage(event, "BalanceStage.fxml");
        else if(toogleGroupValue.equals("Transfer to card"))
            changeStage(event, "RechargeStage.fxml");
        else if(toogleGroupValue.equals("Replenish balance"))
            changeStage(event, "ReplenishBalanceStage.fxml");
        else if(toogleGroupValue.equals("Change PIN"))
            changeStage(event, "ChangePINStage.fxml");
         else
            changeStage(event, "WithdrawStage.fxml");
    }

}
