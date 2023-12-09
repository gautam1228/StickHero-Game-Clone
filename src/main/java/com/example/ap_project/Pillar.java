package com.example.ap_project;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Pillar extends Rectangle {

    private static final int PLATFORM_WIDTH = 155;
    private static final int PLATFORM_HEIGHT = 390;

    private static final Random random = new Random();

    private static Rectangle platform;

    public Pillar(int x, int y , int width, int height) {

        super(x, y, width, height);

    }

    public static Rectangle generateNormalPillar(){

        platform = new Pillar(0 , 410, 155, 390);

        setFillColor("#2b2b2b");

        return platform;

    }

    public static Rectangle generateRandomPillar() {

        int minX = 165;
        int maxX = 576;

        // Ensuring that the platform is completely within the screen horizontally
        int x = (int) (random.nextDouble() * (maxX - minX) + minX);

        int y = 411;

        // Ensuring a minimum width of 24
        int minWidth = 24;
        int maxWidth = maxX - x;
        int platformWidth = Math.max(minWidth, (int) (random.nextDouble() * maxWidth));

        platform = new Pillar(x, y, platformWidth, PLATFORM_HEIGHT);

        setFillColor("#2b2b2b");

        return platform;

    }

    public static Rectangle generateNextPlatform(int min_x, int max_X) {

        int minX = 600 + min_x;
        int maxX = 600 + max_X;

        // Ensuring that the next platform will be in the screen completely horizontally
        int x = (int) ((random.nextDouble() * (maxX - minX)) + minX);

        int y = 411;

        // Ensuring a minimum width of 24
        int minWidth = 24;
        int maxWidth = maxX - x;
        int platformWidth = Math.max(minWidth, (int) (random.nextDouble() * maxWidth));

        platform = new Rectangle(x, y, platformWidth, PLATFORM_HEIGHT);

        setFillColor("#2b2b2b");

        return platform;

    }

    public void addToPane(AnchorPane pane) {
        pane.getChildren().add(platform);
    }

    public void removeFromPane(AnchorPane pane, Pillar pillarToBeRemoved){
        pane.getChildren().remove(pillarToBeRemoved);
    }

    private static void setFillColor(String hexColor) {
        Color color = Color.web(hexColor);
        platform.setFill(color);
    }

}
