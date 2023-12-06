package com.example.ap_project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.Random;

public class Stick extends Line {

    public Stick() {
        super(125, 409, 125, 409); // Starting with zero length line
        setStroke(Color.web("#2b2b2b"));
        setStrokeWidth(5);
    }

    public void extend(int extension) {
        setStartY(getStartY() - extension); // Increase height in the upward direction
    }
}
