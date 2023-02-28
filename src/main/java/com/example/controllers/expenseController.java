package com.example.controllers;

import javafx.collections.FXCollections;
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


public class expenseController implements Initializable{
    @FXML
    private Button btn_expense_save;
    @FXML
    private ComboBox expense_category;
    @FXML
    private TextField expense_name;
    @FXML
    private TextField expense_Amount;
    @FXML
    private DatePicker expense_Date;

    private Scene scene;
    private Stage stage;

    public void switchToDashBoard(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("dash.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDashWallet(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("wallet.fxml"));
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
        expense_category.setItems(choiceList);
    }

    //Save method to insert data into the database if all cells of the form are filled
    @FXML
    private void save(MouseEvent event) {

        connection = DataBaseRead.connect("coin count","root","");
        String Category = expense_category.getValue().toString();
        String Date = String.valueOf(expense_Date.getValue());
        String Name = expense_name.getText();
        String Amount = Integer.toString(-Integer.parseInt(expense_Amount.getText()));
        String Values[]={Category,Name,Amount,Date};

        if (Category.isEmpty() || Date.isEmpty() || Name.isEmpty() || Amount.isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setContentText("Please Fill Complete The Form");
            alert1.showAndWait();

        } else {
            DataBaseInsert.insert(connection,"income",Values);
            clean();

        }

    }

    //Clears the form
    @FXML
    private void clean() {
        expense_name.setText(null);
        expense_Date.setValue(null);
        expense_Amount.setText(null);
        expense_category.setValue(null);

    }




}