package com.example.ap_project;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Platform extends Node {

    private static final int PLATFORM_WIDTH = 155;
    private static final int PLATFORM_HEIGHT = 390;

    private static final Random random = new Random();

    private Rectangle platform;

    public Platform() {
        generatePlatform();
    }

    private void generatePlatform() {

        int minX = 165;
        int maxX = 576;

        // Ensuring that the platform is completely within the screen horizontally
        int x = (int) (random.nextDouble() * (maxX - minX) + minX);

        int y = 411;

        // Ensuring a minimum width of 24
        int minWidth = 24;
        int maxWidth = maxX - x;
        int platformWidth = Math.max(minWidth, (int) (random.nextDouble() * maxWidth));

        platform = new Rectangle(x, y, platformWidth, PLATFORM_HEIGHT);

        setFillColor("#2b2b2b");

    }

    public void addToPane(AnchorPane pane) {
        pane.getChildren().add(platform);
    }

    public void removeFromPane(AnchorPane pane, Platform platformToBeRemoved){
        pane.getChildren().remove(platformToBeRemoved);
    }

    private void setFillColor(String hexColor) {
        Color color = Color.web(hexColor);
        platform.setFill(color);
    }

    public int getX() {
        return (int) platform.getX();
    }

    public int getY() {
        return (int) platform.getY();
    }

    public int getWidth() {
        return (int) platform.getWidth();
    }
}
