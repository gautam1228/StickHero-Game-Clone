package com.example.ap_project;

import javafx.scene.image.Image;

public class Player {

    private int xCord;
    private int yCord;

    private Image player;

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public Player(int xCord, int yCord, Image image) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.player = image;
    }
}
