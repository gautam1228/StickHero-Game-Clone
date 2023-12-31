package com.example.ap_project;

import javafx.scene.media.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

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
    @FXML
    private ImageView playerView;
    private Image muted;
    private Image unmuted;
    public static boolean Mute;
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer backgroundSong;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ImageView backgroundImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Background-Images/background-1.jpg"))));
        backgroundImage.setFitHeight(800);
        backgroundImage.setFitWidth(700);
        startPage.getChildren().add(backgroundImage);
        backgroundImage.toBack();

        String backgroundSoundFile = Objects.requireNonNull(getClass().getResource("Sounds/bg_song.mp3")).toString();
        Media bg_music = new Media(backgroundSoundFile);
        backgroundSong = new MediaPlayer(bg_music);
        backgroundSong.play();
        backgroundSong.setOnEndOfMedia(()->{
            backgroundSong.seek(Duration.ZERO);
            backgroundSong.play();
        });

        Mute = false;
        muted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/no-sound-icon.png")));
        unmuted = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/sound-icon.png")));
        playerView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Skins/Skin-Default.jpg"))));

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
                String clickSoundFile = Objects.requireNonNull(getClass().getResource("Sounds/click.wav")).toString();
                Media md1 = new Media(clickSoundFile);
                clickSoundPlayer = new MediaPlayer(md1);
                loadGamePage(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    // Play-Game-Button
    private void loadGamePage(ActionEvent event) throws IOException {

        if(!Mute) {
            clickSoundPlayer.play();
        }
        backgroundSong.stop();
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
            backgroundSong.seek(Duration.ZERO);
            backgroundSong.play();
            Mute = false;
            soundStatusImage.setImage(unmuted);
        }
        else{
            Mute = true;
            backgroundSong.stop();
            soundStatusImage.setImage(muted);
        }

    }
public void howToPlay(ActionEvent event) throws IOException {
    Stage popUpInfoPage = new Stage();
    //popUpInfoPage.initStyle(StageStyle.UNDECORATED);
    popUpInfoPage.initModality(Modality.APPLICATION_MODAL);
    popUpInfoPage.setTitle("Info Page");

    AnchorPane infoPageLayout = new AnchorPane();

    TextArea termsAndConditionsTextArea = new TextArea();
    termsAndConditionsTextArea.setText(
            "How to Play:\n" +
                    "\n"+
                    "Start the Game:\n" +
                    "\n" +
                    "Press the space bar to extend the stick and start the game.\n" +
                    "\n" +
                    "Extend the Stick:\n" +
                    "\n" +
                    "Tap the space bar to extend the stick. The longer you hold, the longer the stick becomes.\n" +
                    "\n" +
                    "Land on Platforms:\n" +
                    "\n" +
                    "Time your stick extension to land on platforms and keep the character moving forward.\n" +
                    "\n" +
                    "Beat Your High Score:\n" +
                    "\n" +
                    "Challenge yourself to beat your own high score with each attempt.\n" +
                    "\n" +
                    "Avoid Falling into the Void:\n" +
                    "\n" +
                    "Be cautious! If you fail to land on a platform, the character will fall into the void, and the game will end.\n"
    );

    termsAndConditionsTextArea.setWrapText(true);
    termsAndConditionsTextArea.setEditable(false);

    ScrollPane scrollPane = new ScrollPane(termsAndConditionsTextArea);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);

    scrollPane.setPrefSize(400, 400);

    infoPageLayout.getChildren().add(scrollPane);

    Button okButton = new Button("OK");
    okButton.setOnAction(e -> popUpInfoPage.close());
    okButton.setStyle("-fx-font-size: 14px; -fx-min-width: 60px; -fx-min-height: 30px;");


    infoPageLayout.getChildren().add(okButton);

    AnchorPane.setBottomAnchor(okButton, 10.0);
    AnchorPane.setRightAnchor(okButton, 10.0);

    AnchorPane.setTopAnchor(scrollPane, 10.0);
    AnchorPane.setBottomAnchor(scrollPane, 50.0);
    AnchorPane.setLeftAnchor(scrollPane, 10.0);
    AnchorPane.setRightAnchor(scrollPane, 10.0);

    Scene popupScene = new Scene(infoPageLayout, 400, 400);
    popUpInfoPage.setScene(popupScene);

    popUpInfoPage.show();
}
    // Info-Page-Button
    public void infoPage(ActionEvent event) throws IOException {

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
    private Set<Integer> unlockedSkins = new HashSet<>();
    public void skinsPage(ActionEvent event) throws IOException {
        Stage skinPageStage = new Stage();
        skinPageStage.initModality(Modality.APPLICATION_MODAL);
        skinPageStage.setTitle("Choose Skin");

        AnchorPane skinPageLayout = new AnchorPane();



        Label titleLabel = new Label("Choose Skin");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);
        skinPageLayout.getChildren().add(titleLabel);

        HBox dressesBox = new HBox(10);
        dressesBox.setPrefHeight(10);

        for (int i = 1; i <= 6; i++) {

            Image unlockedSkinImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Locked-Skins/sk" + i + ".jpg")));

            double width = 500;
            double proportionalHeight = width / unlockedSkinImage.getWidth() * unlockedSkinImage.getHeight();

            ImageView skinImage = new ImageView(unlockedSkinImage);
            skinImage.setFitWidth(width);
            skinImage.setFitHeight(proportionalHeight);
            skinImage.setPreserveRatio(true);

            Button unlockButton = new Button("Unlock");
            unlockButton.setStyle("-fx-font-size: 25px;");

            Label priceLabel = new Label("Price: 10 cherries");

            StackPane imageContainer = new StackPane();
            imageContainer.getChildren().add(skinImage);

            VBox skinsBox = new VBox(5);
            skinsBox.getChildren().addAll(imageContainer, unlockButton, priceLabel);
            skinsBox.setAlignment(Pos.CENTER);

            dressesBox.getChildren().add(skinsBox);

            if (unlockedSkins.contains(i)) {
                unlockButton.setText("Equip");
                int finalI1 = i;
                unlockButton.setOnAction(equipEvent -> {
                    //System.out.println("Skin :" + finalI + " bought.");
                    playerView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Skins/Skin-" + finalI1 + ".jpg"))));
                    Main.currPlayerInstance.setCurrentSkin(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Skins/Skin-" + finalI1 + ".jpg"))));
                    skinPageStage.close();
                });
            } else {
                int finalI = i;
                unlockButton.setOnAction(e -> {
                    int userCherries = Player.getInstance().getAvailableCherries();
                    if (userCherries >= 10) {
                        Player.getInstance().setAvailableCherries(userCherries - 10);
                        unlockButton.setText("Equip");
                        unlockButton.setOnAction(equipEvent -> {
                            System.out.println("Skin :" + finalI + " bought.");
                            playerView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Skins/Skin-" + finalI + ".jpg"))));
                            Main.currPlayerInstance.setCurrentSkin(new Image(Objects.requireNonNull(getClass().getResourceAsStream("Skins/Skin-" + finalI + ".jpg"))));
                            skinPageStage.close();
                        });
                        unlockedSkins.add(finalI);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Not enough cherries!", ButtonType.OK, ButtonType.CANCEL);
                        ButtonType cherriesPageButton = new ButtonType("Buy Cherries");
                        alert.getButtonTypes().add(cherriesPageButton);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                alert.close();
                            } else if (response == cherriesPageButton) {
                                alert.close();
                                try {
                                    cherriesPage(null);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        }

        skinPageLayout.getChildren().add(dressesBox);
        ScrollPane scrollPane = new ScrollPane(dressesBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPane, 10.0);
        AnchorPane.setBottomAnchor(scrollPane, 10.0);
        AnchorPane.setLeftAnchor(scrollPane, 10.0);
        AnchorPane.setRightAnchor(scrollPane, 10.0);
        skinPageLayout.getChildren().add(scrollPane);
        Scene dressPageScene = new Scene(skinPageLayout, 600, 450);
        skinPageStage.setScene(dressPageScene);

        skinPageStage.show();

    }
    public void cherriesPage(ActionEvent event) throws IOException {
        Stage cherriesPageStage = new Stage();
        cherriesPageStage.initModality(Modality.APPLICATION_MODAL);
        cherriesPageStage.setTitle("Buy Cherries");

        AnchorPane cherriesPageLayout = new AnchorPane();
        Label titleLabel = new Label("Buy Cherries");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);
        cherriesPageLayout.getChildren().add(titleLabel);
        HBox cherriesBox = new HBox(25);
        cherriesBox.setPrefHeight(200);

        Label addedCherriesLabel = new Label("Added Cherries: 0");
        AnchorPane.setBottomAnchor(addedCherriesLabel, 10.0);
        cherriesPageLayout.getChildren().add(addedCherriesLabel);

        for (int i = 1; i <= 5; i++) {

            Image cherryImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Icons/cherries.png")));

            ImageView cherryImageView = new ImageView(cherryImage);
            cherryImageView.setFitWidth(120);
            cherryImageView.setFitHeight(120);
            cherryImageView.setPreserveRatio(true);

            int cherryValue = i * 5;
            Button buyButton = new Button("Buy\n" + cherryValue + " Cherries");
            buyButton.setStyle("-fx-font-size: 15px;");

            VBox cherryBoxVBox = new VBox(15);
            cherryBoxVBox.getChildren().addAll(cherryImageView, buyButton);
            cherryBoxVBox.setAlignment(Pos.CENTER);

            cherriesBox.getChildren().add(cherryBoxVBox);
            addedCherriesLabel.setText("Available Cherries: " + Player.getInstance().getAvailableCherries());
            buyButton.setOnAction(e -> {
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to buy cherries?", ButtonType.YES, ButtonType.NO);
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        Player.getInstance().addAvailableCherries(cherryValue);
                        addedCherriesLabel.setText("Available Cherries: " + Player.getInstance().getAvailableCherries());
                        System.out.println("Cherries bought!");
                    }
                });
            });
        }
        ScrollPane cherriesScrollPane = new ScrollPane(cherriesBox);
        cherriesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cherriesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane.setTopAnchor(cherriesScrollPane, 50.0);
        AnchorPane.setBottomAnchor(cherriesScrollPane, 70.0);
        AnchorPane.setLeftAnchor(cherriesScrollPane, 10.0);
        AnchorPane.setRightAnchor(cherriesScrollPane, 10.0);
        cherriesPageLayout.getChildren().add(cherriesScrollPane);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> cherriesPageStage.close());
        AnchorPane.setBottomAnchor(backButton, 10.0);
        AnchorPane.setRightAnchor(backButton, 10.0);
        backButton.setPrefSize(80, 40);
        cherriesPageLayout.getChildren().add(backButton);


        Scene cherriesPageScene = new Scene(cherriesPageLayout, 600, 350);
        cherriesPageStage.setScene(cherriesPageScene);

        cherriesPageStage.show();
    }

}
