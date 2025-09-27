package com.example;

import javafx.animation.AnimationTimer;

import java.util.List;
import java.util.ArrayList;

public class Animation extends AnimationTimer {
    // Some constant definitions
    private static final double NS_IN_S = 1_000_000_000.0;
    private static final double COST_OF_RESTITUTION = 1.0; // Adds energy loss on bounce if < 1.0

    private List<Ball> balls;
    private double sceneWidth;
    private double sceneHeight;
    private long previousTime;

    public Animation(List<Ball> balls, double sceneWidth, double sceneHeight) {
        this.balls = balls;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    @Override
    public void handle(long currentTime) {
        double elapsedTime = (currentTime - previousTime) / NS_IN_S;

        for (Ball ball : balls) {
            ball.updatePos(elapsedTime, sceneWidth, sceneHeight, COST_OF_RESTITUTION);
        }

        previousTime = currentTime;
    }

    @Override
    public void start() {
        previousTime = System.nanoTime();
        super.start();
    }

    @Override
    public void stop() {
        previousTime = -1;
        super.stop();
    }
}
