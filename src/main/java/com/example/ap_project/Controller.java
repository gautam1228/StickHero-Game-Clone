package com.example.ap_project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static int Score = 0;
//    private static Player currPlayer = new Player(xCord, YCord, Image);
    @FXML
    // All the UI-Components here.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //method to be implemented
    }

    @FXML
    void start(KeyEvent keyEvent){
        // game start and restart.
    }

    @FXML
    void movePlayer(KeyEvent event){

    }

    @FXML
    void stretchStick(KeyEvent event){

    }

    @FXML
    void releaseStick(KeyEvent event){

    }

    // method which checks whether the stick has landed correctly on the platform or not.
    private void stickLanded(){

    }

    @FXML
    void generatePlatform(){

    }


}