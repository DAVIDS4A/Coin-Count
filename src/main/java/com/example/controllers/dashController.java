package com.example.controllers;

import java.sql.Connection;
import com.DataBase.DataBaseRead;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.util.Optional;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.lang.String;
import com.DataBase.DatabaseDisplay;



public class dashController implements Initializable{

    @FXML
    private TableView<dataModel> income_table;

    @FXML
    private TableColumn<dataModel, String> Id;
    @FXML
    private TableColumn<dataModel, String> Category ;
    @FXML
    private TableColumn<dataModel, String> Name;
    @FXML
    private TableColumn<dataModel, String> Amount;
    @FXML
    private TableColumn<dataModel, String> Date;

    @FXML
    private TableColumn<dataModel, String> Delete;
    @FXML
    private Label Balance;

    //private Scene scene;
    private Stage stage;

    ObservableList<dataModel> list;
    //ObservableList<dataModel> dataList;

    int index = -1;

    Connection conn;
    String query = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    dataModel transaction=null;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));



        //add cell for Delete button
        Callback<TableColumn<dataModel, String>, TableCell<dataModel, String>> cellFoctory = (TableColumn<dataModel, String> param) -> {
            // make cell containing button
            final TableCell<dataModel, String> cell = new TableCell<dataModel, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteBtn = new Button("Delete");

                        deleteBtn.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                                        + "-fx-background-color:  #E08580"
                        );

                        deleteBtn.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                int a= aLert();
                                if(a==1){
                                    transaction= income_table.getSelectionModel().getSelectedItem();
                                    query = "Delete from income where Id="+transaction.getId();
                                    conn = DataBaseRead.connect("coin count","root","");
                                    pst = conn.prepareStatement(query);
                                    pst.execute();
                                    RefreshTable();
                                }

                            } catch (SQLException ex) {
                                Logger.getLogger(dashController.class.getName()).log(Level.SEVERE, null, ex);
                            }





                        });


                        HBox managebtn = new HBox(deleteBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteBtn, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        Delete.setCellFactory(cellFoctory);

        RefreshTable();
    }

    //Refresh Table of Recent Transactions
    public void RefreshTable(){
        list = DatabaseDisplay.getDatausers();
        income_table.setItems(list);
        Balance.setText(DatabaseDisplay.getBalance());
    }
    //Switch to Walllet(Add-Income) from DashBoard
    public void switchToDashWallet(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("wallet.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to Add-Expense from Dashboard
    public void switchToDashExpense(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("addexpense.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDashStatistics(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("statistics.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }
    public int aLert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this data?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return 1;
        }else{
            return 0;
        }
    }

}
