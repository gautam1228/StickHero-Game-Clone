package com.example.ap_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void playGame(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("FXML/GamePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
