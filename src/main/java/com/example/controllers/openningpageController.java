package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class openningpageController implements Initializable {

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //Switch to OpenningPage from SignUp
    public void switchToSignUp(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("SignUp.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }

    //Switch to OpenningPage from Login
    public void switchToLogin(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("login.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
        stage.setScene(scene);
        stage.show();
    }
}
