package controllers;

import additional.TransferData;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static javafx.scene.text.TextAlignment.CENTER;

public class AutotransfersStageController implements Initializable {

    @FXML
    Label errorLabel;
    @FXML
    Pane paneID;
    @FXML
    Button backMenuButton;
    @FXML
    private TableView<TransferData> table;
    @FXML
    private ObservableList<TransferData> transferData = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        String trans;
        try {
            trans = Main.rmiServer.getAutoTransfers(Main.user.getCardNumber(), Main.user.getPin(), Main.user.getOp());
            //get array of cards?
            //get info
            if (trans.isEmpty()) {
                Label inf = new Label("You have no autotransfers.");
                inf.setFont(new Font("System", 16));
                paneID.getChildren().add(inf);
                inf.layoutXProperty().bind(paneID.widthProperty().subtract(inf.widthProperty()).divide(2));
                inf.layoutYProperty().bind(paneID.heightProperty().subtract(inf.heightProperty()).divide(2));
                inf.setTextAlignment(CENTER);

                backMenuButton.layoutXProperty().bind(paneID.widthProperty().subtract(backMenuButton.widthProperty()).divide(2));
                backMenuButton.layoutYProperty().bind(paneID.heightProperty().subtract(backMenuButton.heightProperty().multiply(2)));

            } else {
                initCardData(trans.split("}"));


                table = new TableView<>();
                TableColumn<TransferData, String> cardNumCol = new TableColumn<>("Card number to transfer");
                TableColumn<TransferData, String> periodicityCol = new TableColumn<>("Periodicity");
                TableColumn<TransferData, Double> amountCol = new TableColumn<>("Amount");
                table.getColumns().addAll(cardNumCol, periodicityCol, amountCol);

                cardNumCol.setCellValueFactory(new PropertyValueFactory<>("CardNum"));
                periodicityCol.setCellValueFactory(new PropertyValueFactory<>("Periodicity"));
                amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));


                table.setItems(transferData);
                table.setFixedCellSize(25);
                table.setPrefWidth(340);
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                cardNumCol.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width
                periodicityCol.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 30% width
                amountCol.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

                table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.03)));
                table.minHeightProperty().bind(table.prefHeightProperty());
                table.maxHeightProperty().bind(table.prefHeightProperty());

                paneID.getChildren().add(table);
                table.relocate(25, 75);

                Button deleteButton = new Button("Delete");
                deleteButton.setLayoutX(235);
                deleteButton.setLayoutY(288);
                deleteButton.setPrefHeight(40);
                deleteButton.setPrefWidth(137);
                deleteButton.setOnAction(this::deleteTransfer);
                paneID.getChildren().add(deleteButton);
            }
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }

    private void initCardData(String[] st) {
        for (String s : st) {
            String[] tokens = s.split("'");
            if (tokens.length == 6)
                transferData.add(new TransferData(tokens[1], Long.toString(Long.parseLong(tokens[5]) / 86400), Double.parseDouble(tokens[3])));

        }
    }

    @FXML
    public void backToMenu(ActionEvent actionEvent) {
        changeStage(actionEvent, "accountStage.fxml");
    }

    @FXML
    private void deleteTransfer(ActionEvent actionEvent) {
        //delete
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        if (selectedCells.isEmpty()) {
            displayErrorField("Choose transfer to delete");
        } else {
            TablePosition tablePosition = (TablePosition) selectedCells.get(0);
            Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
            try {
                boolean deleted = Main.rmiServer.removeAutoTransfer(Main.user.getCardNumber(), Main.user.getPin(), Main.user.getOp(), tablePosition.getRow());

                System.out.println("Selected Value" + val);
                if (deleted)
                    changeStage(actionEvent, "successWindow.fxml");
                else
                    changeStage(actionEvent, "FailWindow.fxml");
            } catch (RemoteException e) {
                changeStage(actionEvent, "FailWindow.fxml");
            }
        }
    }

    private void displayErrorField(String s) {
        errorLabel.setText(s);
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
