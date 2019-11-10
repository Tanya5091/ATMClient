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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
//    @FXML
//    private Button logButton;
//    @FXML
//    private TextField cardNumField;
//    @FXML
//    private TextField pinField;
//    @FXML
//    private Label errorLabel;
//
//    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        cardNumField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable,
//                                String oldValue, String newValue) {
//
//                checkLogField(newValue, 16, true);
//            }
//        });
//        pinField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable,
//                                String oldValue, String newValue) {
//
//                checkLogField(newValue, 4, false);
//            }
//        });
    }
//
//    @FXML
//    public void logAction(ActionEvent actionEvent) {
//        System.out.println("Ad!");
//        String cardN = cardNumField.getText();
//        String pinN = pinField.getText();
//        if(cardN.equals("")|| pinN.equals("") ){
//            displayErrorField("Fill both fields!");
//        }
//        else if( cardN.length()!=16 || pinN.length()!=4){
//            displayErrorField("Fill card number with 16 digits, PIN - 4!");
//        }
//        else{
////            checkPinCard();
////            if(false)
////                displayErrorField("Wrong card number or PIN");
////            else
//            changeStage(actionEvent,"..\\accountStage.fxml" );
//
//        }
//
//    }
//
//    private void changeStage(ActionEvent actionEvent, String path) {
//        Parent tableViewParent = null;
//        try {
//
////            Path first = Paths.get("C:/Users/Julia/Desktop/workspace/MOOP_KmAlfa/src/sample/MainController.java"); Path second = Paths.get("C:/Users/Julia/Desktop/workspace/MOOP_KmAlfa/src/sample/accountStage.fxml");
////            System.out.println(first.relativize(second));
////            System.out.println(second.relativize(first));
//
//            tableViewParent = FXMLLoader.load(getClass().getResource(path));
//            Scene tableViewScene = new Scene(tableViewParent);
//            //This line gets the Stage information
//            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//            window.setScene(tableViewScene);
//            window.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    public void checkLogField(String newValue, int maxLength, boolean isCardNum) {
//
//        if (!newValue.matches("\\d+")) {
//            if(isCardNum)
//                cardNumField.setText(newValue.replaceAll("[^\\d]", ""));
//            else
//                pinField.setText(newValue.replaceAll("[^\\d]", ""));
//            displayErrorField("Field can be filled only with digits");
//        }
//        else if(newValue.length()>maxLength) {
//            if(isCardNum)
//                cardNumField.setText(newValue.substring(0, maxLength));
//            else
//                pinField.setText(newValue.substring(0, maxLength));
//            displayErrorField("Max length of input - "+maxLength);
//        }
//        else
//            errorLabel.setText(" ");
//    }
//
//    private void displayErrorField(String s) {
//        errorLabel.setText(s);
//    }
//
//    @FXML
//    public void backToLogForm(ActionEvent event){
//        changeStage(event, "loginStage.fxml");
//    }
//
//    @FXML
//    public void nextStage(ActionEvent event){
//        changeStage(event, "loginStage.fxml");
//    }

}

