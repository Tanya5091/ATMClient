package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class BalanceStageController implements Initializable {

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
        changeStage(event, "accountStage.fxml");
    }
}
