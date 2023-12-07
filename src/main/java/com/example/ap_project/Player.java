package com.example.ap_project;

public class Player {
    private int availableCherries;

    // Private constructor to prevent instantiation outside of this class
    private Player() {
        this.availableCherries = 0;
    }

    // Private static instance of the class
    private static Player instance;

    // Public method to get the singleton instance
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
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
