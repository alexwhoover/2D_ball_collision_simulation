package com.example;

import javafx.animation.AnimationTimer;

public class Animation extends AnimationTimer {
    private final Ball ball;
    private final double sceneWidth;
    private final double sceneHeight;
    private long previousTime;
    private static final double NS_IN_S = 1_000_000_000.0;
    private final double GRAVITY = 2000;
    private final double COST_OF_RESTITUTION = 0.9;

    public Animation(Ball ball, double sceneWidth, double sceneHeight) {
        this.ball = ball;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    @Override
    public void handle(long currentTime) {
        double elapsedTime = (currentTime - previousTime) / NS_IN_S;
        ball.updatePos(elapsedTime, sceneWidth, sceneHeight, GRAVITY, COST_OF_RESTITUTION);
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
