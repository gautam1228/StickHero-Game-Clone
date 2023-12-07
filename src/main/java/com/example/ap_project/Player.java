package com.example.ap_project;

import javafx.scene.image.Image;

import java.util.Objects;

public class Player {
    private int availableCherries;
    private Image currentSkin = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Skins/Skin-Default.jpg")));

    // Private constructor to prevent instantiation outside of this class
    private Player() {
        this.availableCherries = 0;
    }

    // Private static instance of the class
    private static Player instance = null;

    // Public method to get the singleton instance
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    // Getters and setters for currentSkin.
    public Image getCurrentSkin() {
        return currentSkin;
    }

    public void setCurrentSkin(Image currentSkin) {
        this.currentSkin = currentSkin;
    }

    // Getter and setter for availableCherries
    public int getAvailableCherries() {
        return availableCherries;
    }
    public void setAvailableCherries(int availableCherries) {
        this.availableCherries = availableCherries;
    }
    public void addAvailableCherries(int availableCherries) {
        this.availableCherries += availableCherries;
    }
}
