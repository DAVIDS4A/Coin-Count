package com.example.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class coinCount extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(coinCount.class.getResource("openningpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1027, 700);
        stage.setTitle("My CoinCount");
        stage.setScene(scene);
        stage.show();
    }

}