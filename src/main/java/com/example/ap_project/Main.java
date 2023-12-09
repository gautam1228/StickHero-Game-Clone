package com.example.ap_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Objects;

public class Main extends Application {
    public static Player currPlayerInstance = Player.getInstance();
    @Override
    public void start(Stage mainStage) throws Exception {

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/StartPage.fxml")));
            Scene scene = new Scene(root);

            String css = Objects.requireNonNull(this.getClass().getResource("Style/startPage.css")).toExternalForm();
            scene.getStylesheets().add(css);

            mainStage.setScene(scene);

            mainStage.setTitle("STICK HERO");
            mainStage.setResizable(false);

            // Setting the icon for the main-mainStage
            Image gameIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/game-icon.png")));
            mainStage.getIcons().add(gameIcon);
            mainStage.initStyle(StageStyle.TRANSPARENT);

            mainStage.show();

        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }
    }
    public static void main(String[] args) {
        Player currPlayer = currPlayerInstance;
        String testClassName = "com.example.ap_project.PlayerTest";
        Result result = JUnitCore.runClasses(PlayerTest.class.getClasses());
        for (Failure failure: result.getFailures()){
            System.out.println(failure.toString());
        }
        if(result.wasSuccessful()) {
            System.out.println("junit tested succesful:-");
            System.out.println("1. singleton design pattern tested");
            System.out.println("2. player class methods functionality tested");
        }
        currPlayer.setAvailableCherries(10);
        launch(args);
    }

}