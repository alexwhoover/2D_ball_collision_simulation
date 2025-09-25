package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a ball
        Ball ball = new Ball(20, 150, 200, 200, -400, Color.BLUE);

        Pane root = new Pane(ball);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Bouncing Ball Animation");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new Animation(ball, scene.getWidth(), scene.getHeight());
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}