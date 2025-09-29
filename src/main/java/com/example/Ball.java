package com.example;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Ball extends Circle {
    private int colCount;
    private final double radius;
    private final double mass;
    private double x;
    private double y;
    private double vel_x;
    private double vel_y;

    public Ball(double radius, double x, double y, double vel_x, double vel_y, Color fill_colour) {
        super(x, y, radius);
        this.colCount = 0;
        this.radius = radius;
        this.mass = Math.PI * radius * radius; // Assumes uniform density among balls
        this.x = x;
        this.y = y;
        this.vel_x = vel_x;
        this.vel_y = vel_y;
        setFill(fill_colour);
    }

    public void updatePos(double elapsedTime) {
        x = x + vel_x * elapsedTime;
        y = y + vel_y * elapsedTime;
        setCenterX(x);
        setCenterY(y);
    }

    public double timeToHWall(double sceneHeight) {
        // Bottom wall
        if (vel_y > 0) {
            return (sceneHeight - y - radius) / vel_y;
        }
        // Top wall
        else if (vel_y < 0) {
            return (radius - y) / vel_y;
        }
        // No vertical velocity
        else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public double timeToVWall(double sceneWidth) {
        // Right wall
        if (vel_x > 0) {
            return (sceneWidth - x - radius) / vel_x;
        }
        // Left wall
        else if (vel_x < 0) {
            return (radius - x) / vel_x;
        }
        // No horizontal velocity
        else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public double timeToBall(Ball that) {
        if (this == that) {
            return Double.POSITIVE_INFINITY;
        }

        double EPSILON = 1e-10;
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        double dvx = that.vel_x - this.vel_x;
        double dvy = that.vel_y - this.vel_y;

        /*
        Let relative position dr = (dx, dy)
        Let relative velocity dv = (dvx, dvy)

        Dot product of two vectors denoted A ⋅ B

        Time at which balls will collide follows equation:
        A = dv ⋅ dv
        B = dr ⋅ dv
        C = dr ⋅ dr - sigma^2

        |dr(t)|^2 <= sigma^2
        -> At^2 + 2Bt + C + sigma^2 <= sigma^2
        -> At^2 + 2Bt + C <= 0

        Solve quadratic equation, smallest t where balls collide:
        t = -(B + sqrt(B^2 - A*C))/A
        */

        double B = dx*dvx + dy*dvy;
        // If B > 0, balls are moving away from each other
        if (B > 0) {
            return Double.POSITIVE_INFINITY;
        }

        double A = dvx*dvx + dvy*dvy;
        // If A is close to 0, balls are moving in parallel
        if (Math.abs(A) <= EPSILON) {
            return Double.POSITIVE_INFINITY;
        }

        double sigma = this.radius + that.radius;
        double C = dx*dx + dy*dy - sigma*sigma;
        double radicand = B*B - A*C;

        // If radicand cannot be solved, there exists no time when balls will collide
        if (radicand < 0) {
            return Double.POSITIVE_INFINITY;
        }

        return -(B + Math.sqrt(radicand)) / A;
    }

    public void collideVWall() {
        vel_x = -vel_x;
        colCount++;
    }

    public void collideHWall() {
        vel_y = -vel_y;
        colCount++;
    }

    public void collide(Ball that) {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        double dvx = that.vel_x - this.vel_x;
        double dvy = that.vel_y - this.vel_y;
        double m1 = this.mass;
        double m2 = that.mass;

        double B = dx*dvx + dy*dvy;
        double d = this.radius + that.radius;

        /*
        Impulse magnitude along the line connecting centers:
        Derived from 1D elastic collision along line of centers.
        J = (2 * m1 * m2 / (m1 + m2)) * (relative velocity along line) / distance
        This ensures momentum and kinetic energy are conserved along the collision axis.
        */

        double J = (2.0 * m1 * m2) / (m1 + m2) * (B / d);
        double Jx = J * dx / d;
        double Jy = J * dy / d;

        // Update Velocities
        this.vel_x += Jx / m1;
        this.vel_y += Jy / m1;
        that.vel_x -= Jx / m2;
        that.vel_y -= Jy / m2;

        // Increment collision counter for both balls
        this.colCount++;
        that.colCount++;
    }

    public int colCount() {
        return this.colCount;
    }
}
