package com.example.ap_project;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private AnchorPane startPage;
    @FXML
    private ImageView soundStatusImage;
    @FXML
    private Button soundButton;
    @FXML
    private Rectangle initialPlatform;

    private Image muted;
    private Image unmuted;
    private boolean Mute;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Mute = false;
        muted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/no-sound-icon.png")));
        unmuted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/sound-icon.png")));
    }
    @FXML
    public void playButtonPressed(ActionEvent event){

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(startPage);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished((ActionEvent eventNew) -> {
            try {
                loadGamePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    // Play-Game-Button
    private void loadGamePage(ActionEvent event) throws IOException {
        FXMLLoader gamePageLoader = new FXMLLoader(getClass().getResource("FXML/GamePage.fxml"));
        Parent root = gamePageLoader.load();

        // Retrieve the current stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Pass the entire Stage to GamePageController
        GamePageController gamePageController = gamePageLoader.getController();
        gamePageController.setStage(stage);

        Scene scene = new Scene(root);
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
    public void infoPage(ActionEvent event) throws IOException {
//        Button closeButton = new Button("Close.");
////        closeButton.setLayoutX();
////        closeButton.setLayoutY();
//        closeButton.setOnAction( e -> {
//            popUpInfoPage.close();
//        });
//
        Stage popUpInfoPage = new Stage();
        //popUpInfoPage.initStyle(StageStyle.UNDECORATED);
        popUpInfoPage.initModality(Modality.APPLICATION_MODAL);
        popUpInfoPage.setTitle("Info Page");

        AnchorPane infoPageLayout = new AnchorPane();

        TextArea termsAndConditionsTextArea = new TextArea();
        termsAndConditionsTextArea.setText(
                "**Game Terms and Conditions**\n" +
                        "\n" +
                        "Welcome to Stick Hero!\n" +
                        "\n" +
                        "By using or accessing Stick Hero, you agree to comply with and be bound by the following terms and conditions of use. If you do not agree to these terms, please do not use the Game.\n" +
                        "\n" +
                        "1. **User Agreement**\n" +
                        "   a. *Fair Play:* Users are expected to play the Game fairly and in accordance with the rules and guidelines provided within the Game.\n" +
                        "   b. *Cheating:* Any form of cheating, hacking, or exploiting the Game's mechanics is strictly prohibited.\n" +
                        "   c. *Respect:* Users are expected to treat other players with respect and refrain from engaging in any form of harassment, discrimination, or offensive behavior.\n" +
                        "\n" +
                        "2. **Gameplay**\n" +
                        "   a. *Account Creation:* Users may be required to create an account to access certain features of the Game. You are responsible for maintaining the confidentiality of your account information.\n" +
                        "   b. *Age Restriction:* The Game is intended for users who are at least 9 years old. If you are under the specified age, please discontinue use of the Game.\n" +
                        "\n" +
                        "3. **User Accounts**\n" +
                        "   a. *Ownership:* All intellectual property rights related to the Game, including but not limited to trademarks, copyrights, and patents, are owned by Ketchapp.\n" +
                        "   b. *User Content:* By submitting any content (e.g., usernames, comments) to the Game, you grant Ketchapp a worldwide, royalty-free, non-exclusive license to use, reproduce, modify, adapt, publish, translate, distribute, and display such content.\n" +
                        "\n" +
                        "4. **Intellectual Property**\n" +
                        "   Ketchapp is not liable for any direct, indirect, incidental, special, or consequential damages resulting from the use or inability to use the Game.\n" +
                        "\n" +
                        "5. **Limitation of Liability**\n" +
                        "   Ketchapp reserves the right to update or modify these terms and conditions at any time. Users are encouraged to review this page periodically for any changes.\n" +
                        "\n" +
                        "6. **Modifications to Terms**\n" +
                        "   These terms and conditions are governed by and construed in accordance with the laws of Govt. of India."
        );

        termsAndConditionsTextArea.setWrapText(true);
        termsAndConditionsTextArea.setEditable(false);

        ScrollPane scrollPane = new ScrollPane(termsAndConditionsTextArea);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        scrollPane.setPrefSize(400, 400);

        infoPageLayout.getChildren().add(scrollPane);

        Button okButton = new Button("AGREE");
        okButton.setOnAction(e -> {
            popUpInfoPage.close();
        });

        infoPageLayout.getChildren().add(okButton);

        AnchorPane.setBottomAnchor(okButton, 10.0);
        AnchorPane.setRightAnchor(okButton, 10.0);

        AnchorPane.setTopAnchor(scrollPane, 10.0);
        AnchorPane.setBottomAnchor(scrollPane, 50.0);
        AnchorPane.setLeftAnchor(scrollPane, 10.0);
        AnchorPane.setRightAnchor(scrollPane, 10.0);

        Scene popupScene = new Scene(infoPageLayout, 400, 600);
        popUpInfoPage.setScene(popupScene);

        popUpInfoPage.show();
    }

}
