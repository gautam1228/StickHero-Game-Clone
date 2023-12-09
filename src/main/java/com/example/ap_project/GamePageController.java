package com.example.ap_project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {
    private boolean spaceKeyPressed = false;
    private boolean spaceKeyReleased = false;
    private Stage stage;
    @FXML
    private Label scoreLabel;
    @FXML
    private AnchorPane GamePage;
    @FXML
    private ImageView playerViewGamePage;
    @FXML
    private Pillar currentPillar;
    private Timeline extendTimeline;
    private Stick currentStick;
    private Player currPlayer;
    private double distanceToBeMovedByPlayer;
    private boolean playerMoving;
    private boolean playerInverted;
    private Pillar nextPillar;
    private boolean gameOver;
    private  int Score;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currPlayer = Player.getInstance();

        boolean spaceKeyPressed = false;

        currentPillar = (Pillar) Pillar.generateNormalPillar();
        nextPillar = (Pillar) Pillar.generateRandomPillar();

        currentStick = new Stick();

        GamePage.getChildren().addAll(currentPillar, currentStick, nextPillar);
        playerViewGamePage.setImage(currPlayer.getCurrentSkin());

        playerMoving = false;
        playerInverted = false;
        gameOver = false;

        Score = 0;

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
                    System.out.println("Extending stick...");
                    startExtendingStick();
                    spaceKeyPressed = true;
                }
            });

            stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                if (event.getCode() == KeyCode.SPACE && !spaceKeyReleased) {
                    // Handling "Space" key release
                    System.out.println("Stopped extending.....");
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

    private boolean playerIsBetween(double i_x, double f_x){

        double curr_i_x = playerViewGamePage.getBoundsInParent().getMinX() + 15;
        double curr_e_x = curr_i_x + 46 - 15;
        return curr_i_x >= i_x && curr_e_x <= f_x ;

    }

    private void stopExtendingStick() {
        if (extendTimeline != null) {
            extendTimeline.stop();
            movePlayer();
        }
    }

    private void movePlayer(){

        distanceToBeMovedByPlayer = 413 + 55 + 25 - currentStick.getStartY();
//            new Thread(()->{
//                // Updating UI Elements.
//                Platform.runLater(()->{
//
//                });
//            }).start();
        Rotate rotate = new Rotate(0, 150,411);
        currentStick.getTransforms().add(rotate);

        Translate translate = new Translate();
        translate.setX(0);
        translate.setY(0);
        playerViewGamePage.getTransforms().add(translate);

        KeyFrame rotateKeyFrame = new KeyFrame(Duration.millis(500), new KeyValue(rotate.angleProperty(), 90));

        KeyFrame translateKeyFrame = new KeyFrame(Duration.millis(1500), new KeyValue(translate.xProperty(), distanceToBeMovedByPlayer));

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(rotateKeyFrame);
        timeline.setCycleCount(1);
        timeline.setOnFinished(event->{
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(translateKeyFrame);
            timeline.play();
            playerMoving = true;
            playerViewGamePage.setFocusTraversable(true);
            System.out.println("Can Invert now....");
            playerViewGamePage.setOnKeyPressed(spaceKeyPressedAgain->{
                if(spaceKeyPressedAgain.getCode() == KeyCode.SPACE && playerMoving && !playerInverted && playerIsBetween(currentPillar.getX() + currentPillar.getWidth(), nextPillar.getX())){
                    playerInverted = true;
                    playerViewGamePage.setTranslateY(playerViewGamePage.getTranslateY() + playerViewGamePage.getBoundsInParent().getHeight());
                    playerViewGamePage.setScaleY(playerViewGamePage.getScaleY()*(-1));

                }
                else if(spaceKeyPressedAgain.getCode() == KeyCode.SPACE && playerMoving && playerInverted && playerIsBetween(currentPillar.getX() + currentPillar.getWidth(), nextPillar.getX())){
                    playerInverted = false;
                    playerViewGamePage.setTranslateY(playerViewGamePage.getTranslateY() - playerViewGamePage.getBoundsInParent().getHeight());
                    playerViewGamePage.setScaleY(playerViewGamePage.getScaleY()*(-1));
                }
            });
            timeline.setOnFinished(stoppedMoving->{
                playerMoving = false;
                System.out.println("Can't invert now...");
                playerViewGamePage.setFocusTraversable(false);
                checkPlayer();
            });
        });
        timeline.play();
    }

    private boolean playerLanded(){

        double x_i_player = playerViewGamePage.getBoundsInParent().getMinX() + 10;
        return x_i_player >= nextPillar.getBoundsInParent().getMinX() && x_i_player <= nextPillar.getBoundsInParent().getMinX() + nextPillar.getBoundsInParent().getWidth();
    }

    private void checkPlayer(){
        double x_i_nextPillar = nextPillar.getBoundsInParent().getMinX();
        double x_e_nextPillar = x_i_nextPillar + nextPillar.getWidth();

        if(playerLanded() && !playerInverted){
            // Continued logic
            Score++;
            scoreLabel.setText("Score : " + Score);
            gameOver = false;
            Pillar nextPillarToCome = (Pillar) Pillar.generateNextPlatform(165, 550);
            GamePage.getChildren().add(nextPillarToCome);

            TranslateTransition translateNextPillar = new TranslateTransition(Duration.seconds(1),nextPillar);
            translateNextPillar.setByX(-600);

            TranslateTransition translateNextPillarToCome = new TranslateTransition(Duration.seconds(1), nextPillarToCome);
            translateNextPillarToCome.setByX(-600);

            TranslateTransition translatePlayer = new TranslateTransition(Duration.seconds(1), playerViewGamePage);
            double x_to_move_player = playerViewGamePage.getBoundsInParent().getMinX() + playerViewGamePage.getBoundsInParent().getWidth() - 101;
            translatePlayer.setByX(-x_to_move_player);

            TranslateTransition translateStick = new TranslateTransition(Duration.seconds(1), currentStick);
            translateStick.setByX(-x_to_move_player);

            translateNextPillar.play();
            translateNextPillarToCome.play();
            translatePlayer.play();
            translateStick.play();

            translateStick.setOnFinished(e->{
                GamePage.getChildren().remove(currentStick);
                currentStick = new Stick();
                GamePage.getChildren().add(currentStick);
            });

            nextPillar = nextPillarToCome;

            spaceKeyPressed = false;
            spaceKeyReleased = false;

        }
        else {
            gameOver = true;

            TranslateTransition translatePlayer = new TranslateTransition(Duration.millis(500), playerViewGamePage);
            translatePlayer.setByY(411);
            translatePlayer.play();
            translatePlayer.setOnFinished(e->{
                Stage gameOverPage = new Stage();
                gameOverPage.initModality(Modality.APPLICATION_MODAL);
                gameOverPage.setTitle("Game Over Page");

                int finalScore = Score;
                currPlayer.CHeckHighScore(finalScore);
                Label messageLabel = new Label("Game Over");
                messageLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
                messageLabel.setAlignment(Pos.CENTER);

                Label scoreLabel = new Label("Your Score: " + finalScore);
                scoreLabel.setStyle("-fx-font-size: 16;");
                scoreLabel.setAlignment(Pos.CENTER);

                Label highScoreLabel = new Label("High Score: " + currPlayer.getHighScore());
                highScoreLabel.setStyle("-fx-font-size: 16;");
                highScoreLabel.setAlignment(Pos.CENTER);

                Button closeButton = new Button("Close");
                closeButton.setOnAction(event -> gameOverPage.close());

                VBox gameOverLayout = new VBox(10);
                gameOverLayout.getChildren().addAll(messageLabel, scoreLabel,highScoreLabel, closeButton);
                gameOverLayout.setAlignment(Pos.CENTER);

                Scene gameOverScene = new Scene(gameOverLayout, 300, 150);
                gameOverPage.setScene(gameOverScene);
                gameOverPage.show();


            });

        }
    }

}
