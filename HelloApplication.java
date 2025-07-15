package com.example.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import src.main.RoleSelection;

import static javafx.application.Application.launch;

public class HelloApplication extends Application {

    public void start(Stage stage) throws Exception {


       new RoleSelection().start(stage);
    }

    public static void main(String[] args) {

        launch(args);
    }




}
