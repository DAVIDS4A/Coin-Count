package com.example.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import com.DataBase.*;
import javafx.stage.Stage;


public class walletController implements Initializable{
    @FXML
    private Button btn_income_save;
    @FXML
    private TextField income_name;
    @FXML
    private TextField income_Amount;
    @FXML
    private ComboBox income_category;
    @FXML
    private DatePicker income_Date;

    private Scene scene;
    private Stage stage;

    public void switchToDashBoard(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("dash.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }
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

    Connection connection = null;


    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> choiceList = FXCollections.observableArrayList("Daily","Weekly","Monthly","Yearly","Other");
        income_category.setItems(choiceList);
    }
    @FXML
    private void save(MouseEvent event) {

        connection = DataBaseRead.connect("coin count","root","");
        String Category = income_category.getValue().toString();
        String Date = String.valueOf(income_Date.getValue());
        String Name = income_name.getText();
        String Amount = income_Amount.getText();
        String Values[]={Category,Name,Amount,Date};

        if (Category.isEmpty() || Date.isEmpty() || Name.isEmpty() || Amount.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill Complete The Form");
            alert.showAndWait();

        } else {
            DataBaseInsert.insert(connection,"income",Values);
            clean();

        }

    }

    //Clears the form
    @FXML
    private void clean() {
        income_name.setText(null);
        income_Date.setValue(null);
        income_Amount.setText(null);
        income_category.setValue(null);

    }




}