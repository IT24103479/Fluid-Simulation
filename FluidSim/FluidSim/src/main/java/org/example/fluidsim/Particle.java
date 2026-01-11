package org.example.fluidsim;

/**
 * Represent a single particle in the fluid simulation.
 * Stores position and velocity
 */

public class Particle {
    public double positionX;
    public double positionY;


    public double velocityX;
    public double velocityY;

    public Particle(double startX, double startY) {
        this.positionX = startX;
        this.positionY = startY;
        this.velocityX = 0;
        this.velocityY = 0;
    }
}
