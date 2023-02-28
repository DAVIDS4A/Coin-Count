package com.example.controllers;

import com.DataBase.DataBaseUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static java.sql.DriverManager.println;
public class loginController implements Initializable {
    @FXML
    private Label errorlabel;
    @FXML
    private TextField usernamefield;
    @FXML
    private PasswordField passwordfield;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private void onlogin(ActionEvent actionEvent) throws IOException{
        if (passwordfield.getText().isEmpty() ) { errorlabel.setText("please fill password ");
            return ;
        }
        if (usernamefield.getText().isEmpty() ) { errorlabel.setText("please fill username ");
            return ;
        }
        int status = DataBaseUsers.checkLogin(usernamefield.getText().trim().toLowerCase(),passwordfield.getText());
        switch(status){
            case 0:{ errorlabel.setText("Succeeded");
                FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("dash.fxml"));
                stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene=new Scene(fxmlLoader.load(), 1027, 700);
                stage.setScene(scene);
                stage.show();
            }break;
            case -1: errorlabel.setText("Connection error");break;
            case 1:  errorlabel.setText("Wrong username or password");break;
        }

    }
}
