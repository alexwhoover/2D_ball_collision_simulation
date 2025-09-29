package com.example;

import java.util.List;
import java.util.PriorityQueue;

public class CollisionSystem {
    private final List<Ball> balls;
    private final double sceneWidth;
    private final double sceneHeight;
    private final PriorityQueue<CollisionEvent> pq;
    private double simTime;
    private double limit;
    private CollisionEvent nextEvent;

    public CollisionSystem(List<Ball> balls, double sceneWidth, double sceneHeight) {
        this.balls = balls;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.pq = new PriorityQueue<>();
        this.simTime = 0.0;
    }

    public void initialize(double limit) {
        this.limit = limit;
        pq.clear();
        simTime = 0.0;
        for (Ball a : balls) {
            predict(a);
        }
        nextEvent = pq.poll();
    }

    public void predict(Ball a) {
        // Ball-Wall Collisions
        double tH = a.timeToHWall(sceneHeight);
        double tV = a.timeToVWall(sceneWidth);

        // Define an event with a != null and b == null as horizontal wall collision
        // Define an event with a == null and b != null as vertical wall collision
        addEvent(simTime + tH, a, null);
        addEvent(simTime + tV, null, a);

        // Ball-Ball Collisions
        for (Ball b : balls) {
            double dt = a.timeToBall(b);
            addEvent(simTime + dt, a, b);
        }
    }

    private void updateAllPos(double dt) {
        for (Ball a : balls) {
            a.updatePos(dt);
        }
    }

    private void addEvent(double t, Ball a, Ball b) {
        if (a != b && t <= limit) {
            pq.add(new CollisionEvent(t, a, b));
        }
    }

    public void nextFrame(double timeToNextFrame) {
        if (nextEvent == null || simTime >= limit) {
            return;
        }

        double targetTime = simTime + timeToNextFrame;

        // Process all events that occur between simTime and targetTime
        // Necessary because there may be multiple events which occur between animation frames
        while (nextEvent != null && nextEvent.getTime() <= targetTime) {
            double timeToEvent = nextEvent.getTime() - simTime;
            updateAllPos(timeToEvent);
            simTime += timeToEvent;
            processCollisionEvent();
            nextEvent = pq.poll();
        }

        // Move remaining time after last collision
        double remainingTime = targetTime - simTime;
        if (remainingTime > 0) {
            updateAllPos(remainingTime);
            simTime = targetTime;
        }
    }
    
    public void processCollisionEvent() {
        if (nextEvent.isValid()) {
            Ball a = nextEvent.getA();
            Ball b = nextEvent.getB();

            // Ball - Wall Collisions
            if (a != null && b == null) {
                a.collideHWall();
            } 
            else if (a == null && b != null) {
                b.collideVWall();
            }
            // Ball - Ball Collision
            else {
                assert a != null; // Always fine because of isValid()
                a.collide(b);
            }

            // Re-predict ball collisions involving a and b, as they are now on different trajectories
            if (a != null) {
                predict(a);
            }
            if (b != null) {
                predict(b);
            }
        }
    }

    public boolean hasReachedLimit() {
        return simTime >= limit;
    }
}
