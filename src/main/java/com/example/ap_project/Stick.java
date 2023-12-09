package com.example.ap_project;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Stick extends Line {

    public Stick() {
        super(150, 413, 150, 413); // Starting with zero length line
        setStroke(Color.web("#2b2b2b"));
        setStrokeWidth(5);
    }

    public void extend(int extension) {
        setStartY(getStartY() - extension); // Increase height in the upward direction
    }

    public void removeFromPane(Pane pane){

        pane.getChildren().remove(this);

    }
}
