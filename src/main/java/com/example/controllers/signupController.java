package com.example.controllers;

import com.DataBase.*;
import com.DataBase.DataBaseInsert;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class signupController implements Initializable {

    @FXML
    private TextField username_up;
    @FXML
    private TextField email_up;

    @FXML
    private TextField password_up;

    @FXML
    private Label errorlb;

    private Stage stage;

    Connection connection = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    public void add_users (MouseEvent event) throws IOException {
        connection = DataBaseReadUsers.connect("coin count","root","");
        String username = username_up.getText();
        String email = email_up.getText();
        String password = password_up.getText();
        String Values[]={username,email,password};

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setContentText("Please Fill Complete The Form");
            alert1.showAndWait();

        } else {
            DataBaseSignUp.insert(connection,"users",Values);
            clean();
            FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("login.fxml"));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    private void clean() {
        username_up.setText(null);
        email_up.setText(null);
        password_up.setText(null);

    }

    }
