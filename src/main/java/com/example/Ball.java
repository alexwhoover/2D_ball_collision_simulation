package com.example;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball extends Circle {
    private double radius;
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

    public void updatePos(double elapsedTime, double sceneWidth, double sceneHeight, double COR) {
        x = x + vel_x * elapsedTime;
        y = y + vel_y * elapsedTime;

        checkWallCollision(COR, sceneWidth, sceneHeight);
        setCenterX(x);
        setCenterY(y);
    }

    public void checkWallCollision(double COR, double sceneWidth, double sceneHeight) {
        // Left Wall
        if (x - radius <= 0) {
            vel_x *= -COR;
            x = radius;
        }

        // Right Wall
        if (x + radius >= sceneWidth) {
            vel_x *= -COR;
            x = sceneWidth - radius;
        }

        // Top Wall
        if (y - radius <= 0) {
            vel_y *= -COR;
            y = radius;
        }

        // Bottom Wall
        if (y + radius >= sceneHeight) {
            vel_y *= -COR;
            y = sceneHeight - radius;
        }
    }
}
