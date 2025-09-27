package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        int num_balls = 10;
        double min_vel = -200;
        double max_vel = 200;
        double min_radius = 10;
        double max_radius = 50;

        ArrayList<Ball> balls = new ArrayList<>();
        Pane root = new Pane();
        Scene scene = new Scene(root, 1200, 800);
        Random rand = new Random();

        // Generate random balls
        for (int i = 0; i < num_balls; i++) {
            Ball ball = genRandBall(min_radius, max_radius, min_vel, max_vel, scene.getWidth(), scene.getHeight(), rand);
            balls.add(ball);
        }

        showBalls(balls, root);

        primaryStage.setTitle("Bouncing Ball Animation");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new Animation(balls, scene.getWidth(), scene.getHeight());
        timer.start();
    }

    private Ball genRandBall(double min_radius, double max_radius, double min_vel, double max_vel, double sceneWidth, double sceneHeight, Random rand) {
        double radius = randNumber(min_radius, max_radius, rand);
        double x = randNumber(2 * radius, sceneWidth - 2 * radius, rand);
        double y = randNumber(2 * radius, sceneHeight - 2 * radius, rand);
        double vel_x = randNumber(min_vel, max_vel, rand);
        double vel_y = randNumber(min_vel, max_vel, rand);
        Color color = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

        return new Ball(radius, x, y, vel_x, vel_y, color);
    }

    private double randNumber(double min, double max, Random rand) {
        return min + rand.nextDouble() * (max - min);
    }

    public void showBalls(List<Ball> balls, Pane root) {
        for (Ball ball : balls) {
            root.getChildren().add(ball);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}