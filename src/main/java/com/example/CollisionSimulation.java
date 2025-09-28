package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollisionSimulation extends Application {
    @Override
    public void start(Stage primaryStage) {
        int num_balls = 100;
        double min_vel = 100.0;
        double max_vel = 300.0;
        double min_radius = 10.0;
        double max_radius = 50.0;

        ArrayList<Ball> balls = new ArrayList<>();
        Pane root = new Pane();
        Scene scene = new Scene(root, 1200, 800);
        Random rand = new Random();

        // Generate random balls
        for (int i = 0; i < num_balls; i++) {
            Ball ball;

            // Keep trying to generate a new random ball until it does not overlap with any other balls
            do {
                ball = genRandBall(min_radius, max_radius, min_vel, max_vel, scene.getWidth(), scene.getHeight(), rand);
            } while (overlaps(ball, balls));

            balls.add(ball);
        }

        showShapes(balls, root);

        primaryStage.setTitle("Bouncing Ball Animation");
        primaryStage.setScene(scene);
        primaryStage.show();

        CollisionSystem sys = new CollisionSystem(balls, scene.getWidth(), scene.getHeight());
        SimulationTimer timer = new SimulationTimer(sys);
        timer.start();
    }

    private Ball genRandBall(double min_radius, double max_radius, double min_vel, double max_vel, double sceneWidth, double sceneHeight, Random rand) {
        double radius = randNumber(min_radius, max_radius, rand);
        double x = randNumber(2 * radius, sceneWidth - 2 * radius, rand);
        double y = randNumber(2 * radius, sceneHeight - 2 * radius, rand);
        double vel_x = randNumber(min_vel, max_vel, rand) * (rand.nextBoolean() ? 1: -1);
        double vel_y = randNumber(min_vel, max_vel, rand) * (rand.nextBoolean() ? 1: -1);
        Color color = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

        return new Ball(radius, x, y, vel_x, vel_y, color);
    }

    private double randNumber(double min, double max, Random rand) {
        return min + rand.nextDouble() * (max - min);
    }

    // Given a list of shapes, show them on a pane
    private void showShapes(List<? extends Shape> objects, Pane pane) {
        for (Shape obj : objects) {
            pane.getChildren().add(obj);
        }
    }

    // Helper function to determine if randomly generated ball overlaps any other already generated balls
    private boolean overlaps(Ball a, List<Ball> balls) {
        for (Ball b : balls) {
            double dx = a.getCenterX() - b.getCenterX();
            double dy = a.getCenterY() - b.getCenterY();
            double drSquared = dx * dx + dy * dy;
            double dSquared = Math.pow(a.getRadius() + b.getRadius(), 2.0);
            if (drSquared < dSquared) {
                return true;
            }
        }
        return false;
    }

    private static class SimulationTimer extends AnimationTimer {
        private long prevTime = -1;
        private final CollisionSystem sys;

        SimulationTimer(CollisionSystem sys) {
            this.sys = sys;
        }

        @Override
        public void handle(long currentTime) {
            if (prevTime < 0) {
                prevTime = currentTime;
                return;
            }
            double dt = (currentTime - prevTime) / 1e9; // seconds
            sys.nextFrame(dt);
            sys.initialize(10.0);
            prevTime = currentTime;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}