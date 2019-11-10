package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuccessWindowController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        changeStage(actionEvent, "accountStage.fxml");
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

}
