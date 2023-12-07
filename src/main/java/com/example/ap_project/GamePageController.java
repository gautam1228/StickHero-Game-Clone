package com.example.ap_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {
    private boolean spaceKeyPressed = false;
    private boolean spaceKeyReleased = false;
    private Stage stage;
    @FXML
    private AnchorPane GamePage;
    @FXML
    private ImageView playerViewGamePage;
    private Timeline extendTimeline;
    private Stick currentStick;
    private Player currPlayer;
    private double distanceToBeMoved;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currPlayer = Player.getInstance();
        boolean spaceKeyPressed = false;
        Pillar nextPillar = new Pillar();
        currentStick = new Stick();
        nextPillar.addToPane(GamePage);
        GamePage.getChildren().add(currentStick);
        playerViewGamePage.setImage(currPlayer.getCurrentSkin());
    }
    // Method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
        setupEventHandlers();
    }
    // Method to set up event handlers
    private void setupEventHandlers() {
        // Adding event listener for the "Space" key
        if (stage != null) {
            stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE && !spaceKeyPressed) {
                    // Handling "Space" key press
                    startExtendingStick();
                    spaceKeyPressed = true;
                }
            });

            stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                if (event.getCode() == KeyCode.SPACE && !spaceKeyReleased) {
                    // Handling "Space" key release
                    stopExtendingStick();
                    spaceKeyReleased = true;
                }
            });
        }
    }

    private void startExtendingStick() {
        extendTimeline = new Timeline(new KeyFrame(
                Duration.millis(10), // Duration at which the height will be updated
                event -> {
                    currentStick.extend(3);
                }
        ));
        extendTimeline.setCycleCount(Timeline.INDEFINITE); // Running indefinitely until key release
        extendTimeline.play();
    }

    private void stopExtendingStick() {
        if (extendTimeline != null) {
            extendTimeline.stop();
            // Creating a different thread to first calculate the length to which stick was extended and then update the UI.
            new Thread(()->{

                synchronized (this){
                    distanceToBeMoved = 413 + 55 - currentStick.getStartY(); // added the 50 because it's the image width.
                }

                System.out.println("Player has to move by : " + distanceToBeMoved);

                // Updating UI Elements.
                Platform.runLater(()->{
                    Rotate rotate = new Rotate(0, 125,411);
                    currentStick.getTransforms().add(rotate);

                    Translate translate = new Translate();
                    translate.setX(0);
                    translate.setY(0);
                    playerViewGamePage.getTransforms().add(translate);

                    KeyFrame rotateKeyFrame = new KeyFrame(Duration.millis(500), new KeyValue(rotate.angleProperty(), 90));

                    KeyFrame translateKeyFrame = new KeyFrame(Duration.millis(1000), new KeyValue(translate.xProperty(), distanceToBeMoved));

                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().addAll(rotateKeyFrame);
                    timeline.setCycleCount(1);
                    timeline.setOnFinished(event->{
                        timeline.getKeyFrames().clear();
                        timeline.getKeyFrames().add(translateKeyFrame);
                        timeline.play();
                    });
                    timeline.play();

                });
            }).start();

        }
    }

}
