package com.example;

public class CollisionEvent implements Comparable<CollisionEvent>{
    private final double time;
    private final Ball a, b;
    private final int colCountA, colCountB; // Collision counter

    public CollisionEvent(double time, Ball a, Ball b) {
        this.time = time;
        this.a = a;
        this.b = b;
        if (a != null) {
            this.colCountA = a.colCount();
        }
        else {
            this.colCountA = -1;
        }
        if (b != null) {
            this.colCountB = b.colCount();
        }
        else {
            this.colCountB = -1;
        }

    }

    public Ball getA() {
        return this.a;
    }

    public Ball getB() {
        return this.b;
    }

    public double getTime() {
        return this.time;
    }

    @Override
    public int compareTo(CollisionEvent that) {
        return Double.compare(this.time, that.time);
    }

    // Check to see if ball a or b have collided with another ball / wall since event initialized in PQ
    // If so, the event is no longer valid as the balls are on different trajectories
    public boolean isValid() {
        if (a == null && b == null) {
            return false;
        }

        if (a != null && a.colCount() != colCountA) {
            return false;
        }
        else if (b != null && b.colCount() != colCountB) {
            return false;
        }
        else {
            return true;
        }
    }
}
