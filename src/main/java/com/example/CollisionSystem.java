package com.example;

import java.util.List;
import java.util.PriorityQueue;

public class CollisionSystem {
    private List<Ball> balls;
    private double sceneWidth;
    private double sceneHeight;
    private PriorityQueue<CollisionEvent> pq;
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
            predict(a, limit);
        }
        nextEvent = pq.poll();
    }

    public void predict(Ball a, double limit) {
        // Ball-Wall Collisions
        double tH = a.timeToHWall(sceneHeight);
        double tV = a.timeToVWall(sceneWidth);

        // Define an event with b = null as horizontal wall collision
        // Define an event with a = null as vertical wall collision
        if (simTime + tH <= limit) {
            pq.add(new CollisionEvent(simTime + tH, a, null));
        }
        if (simTime + tV <= limit) {
            pq.add(new CollisionEvent(simTime + tV, null, a));
        }

        for (Ball b : balls) {
            if (b != a) {
                double dt = a.timeToBall(b);
                if (simTime + dt <= limit) {
                    pq.add(new CollisionEvent(simTime + dt, a, b));
                }
            }
        }
    }

    private void updateAllPos(double dt) {
        for (Ball ball : balls) {
            ball.updatePos(dt);
        }
    }

    public void nextFrame(double timeToNextFrame) {
        if (nextEvent == null || simTime >= limit) {
            return;
        }

        double targetTime = simTime + timeToNextFrame;

        // Process all events that occur between simTime and timeToNextFrame
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
                a.collide(b);
            }
            
            if (a != null) {
                predict(a, limit);
            }
            if (b != null) {
                predict(b, limit);
            }
        }
    }
}
