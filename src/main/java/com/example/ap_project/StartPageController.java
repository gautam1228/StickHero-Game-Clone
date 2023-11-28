package com.example.ap_project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private ImageView soundStatusImage;
    @FXML
    private Button soundButton;
    Image muted ;
    Image unmuted ;
    private boolean Mute;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Mute = false;
        muted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/no-sound-icon.png")));
        unmuted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/sound-icon.png")));
    }

    // Play-Game-Button
    public void playGame(ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/GamePage.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Sound-Changing-Button
    public void soundChange(ActionEvent event) throws IOException {

        if(Mute){
            Mute = false;
            soundStatusImage.setImage(unmuted);
        }
        else{
            Mute = true;
            soundStatusImage.setImage(muted);
        }

    }

    // Info-Page-Button
    public void infoPage(ActionEvent event) throws IOException{

        Stage popUpInfoPage = new Stage();
//        popUpInfoPage.initStyle(StageStyle.UNDECORATED);
        popUpInfoPage.initModality(Modality.APPLICATION_MODAL);
        popUpInfoPage.setTitle("Info Page.");

        AnchorPane infoPageLayout = new AnchorPane();
        Button closeButton = new Button("Close.");
//        closeButton.setLayoutX();
//        closeButton.setLayoutY();
        closeButton.setOnAction( e -> {
            popUpInfoPage.close();
        });

        infoPageLayout.getChildren().add(closeButton);


        Scene popupScene = new Scene(infoPageLayout, 400, 600);

        popUpInfoPage.setScene(popupScene);

        popUpInfoPage.show();

    }

}