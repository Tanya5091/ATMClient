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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class BalanceStageController implements Initializable {

    public Button backButton;
    @FXML
    private Label balanceLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            double val;
            val = Main.rmiServer.getBalance(Main.user.getCardNumber(), Main.user.getPin(), Main.user.getOp());
            balanceLabel.setText(Double.toString(val));
        } catch (RemoteException e) {

            balanceLabel.setText("Cannot reach server at the moment");
        }
    }

    private void changeStage(ActionEvent actionEvent) {
        Parent tableViewParent;
        try {

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
    public void backToLogForm(ActionEvent event) {
        changeStage(event);
    }
}
