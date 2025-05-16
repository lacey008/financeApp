package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FinanceApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Data.loadTransactions();
        FXMLLoader loader = new FXMLLoader(FinanceApp.class.getResource("HomePage.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Finance Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 