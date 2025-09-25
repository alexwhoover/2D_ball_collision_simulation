package com.example;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball extends Circle {
    private double radius;
    private double x;
    private double y;
    private double vel_x;
    private double vel_y;
    private double min_vel_threshold = 10;

    public Ball(double radius, double x, double y, double vel_x, double vel_y, Color fill_colour) {
        super(x, y, radius);
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.vel_x = vel_x;
        this.vel_y = vel_y;
        setFill(fill_colour);
    }

    public void updatePos(double elapsedTime, double sceneWidth, double sceneHeight, double g, double COR) {
        x = x + vel_x * elapsedTime;

        vel_y = vel_y + g * elapsedTime;
        y = y + vel_y * elapsedTime + 1.0 / 2.0 * g * (elapsedTime * elapsedTime);

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
        if (y + radius >= sceneHeight && Math.abs(vel_y) >= min_vel_threshold) {
            vel_y *= -COR;
            y = sceneHeight - radius;
        }
        else if (y + radius >= sceneHeight) {
            vel_y = 0;
        }
    }
}
