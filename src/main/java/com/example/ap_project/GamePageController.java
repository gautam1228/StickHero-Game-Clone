package com.example.ap_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
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
    Timeline extendTimeline;
    private Stick currentStick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean spaceKeyPressed = false;
        Platform nextPlatform = new Platform();
        currentStick = new Stick();
        nextPlatform.addToPane(GamePage);
        GamePage.getChildren().add(currentStick);
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
            // Handle any other logic you need when stopping the extension
            Rotate rotate = new Rotate(0, 125,411);
            currentStick.getTransforms().add(rotate);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(500), new KeyValue(rotate.angleProperty(), 90))
            );
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

}
