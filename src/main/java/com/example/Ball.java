package com.example;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball extends Circle {
    private final double radius;
    private double x;
    private double y;
    private double vel_x;
    private double vel_y;

    public Ball(double radius, double x, double y, double vel_x, double vel_y, Color fill_colour) {
        super(x, y, radius);
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.vel_x = vel_x;
        this.vel_y = vel_y;
        setFill(fill_colour);
    }

    public void updatePos(double elapsedTime, double sceneWidth, double sceneHeight) {
        // displacement = vel * time, for zero acceleration
        x = x + vel_x * elapsedTime;
        y = y + vel_y * elapsedTime;
        setCenterX(x);
        setCenterY(y);

        checkWallCollision(sceneWidth, sceneHeight);


    }

    public void checkWallCollision(double sceneWidth, double sceneHeight) {
        // Left Wall
        if (x - radius <= 0) {
            vel_x *= -1;
            x = radius;
            setCenterX(x);
        }

        // Right Wall
        if (x + radius >= sceneWidth) {
            vel_x *= -1;
            x = sceneWidth - radius;
            setCenterX(x);
        }

        // Top Wall
        if (y - radius <= 0) {
            vel_y *= -1;
            y = radius;
            setCenterY(y);
        }

        // Bottom Wall
        if (y + radius >= sceneHeight) {
            vel_y *= -1;
            y = sceneHeight - radius;
            setCenterY(y);
        }
    }
}
