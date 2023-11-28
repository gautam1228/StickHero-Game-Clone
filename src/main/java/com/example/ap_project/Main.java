package com.example.ap_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("FXML/StartPage.fxml"));
            Scene scene = new Scene(root);

            String css = this.getClass().getResource("Style/startPage.css").toExternalForm();
            scene.getStylesheets().add(css);

            mainStage.setScene(scene);
            mainStage.setTitle("STICK HERO");
            mainStage.setResizable(false);

            // Setting the icon for the main-mainStage
            Image gameIcon = new Image(getClass().getResourceAsStream("icons/game-icon.png"));
            mainStage.getIcons().add(gameIcon);

            mainStage.show();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}