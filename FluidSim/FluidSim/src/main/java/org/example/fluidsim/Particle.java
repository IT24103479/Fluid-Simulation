package org.example.fluidsim;

public class Particle {
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;

    public Particle(double startX, double startY) {
        this.positionX = startX;
        this.positionY = startY;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    // Getters
    public double getX() { return positionX; }
    public double getY() { return positionY; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }

    // Setters
    public void setX(double x) { this.positionX = x; }
    public void setY(double y) { this.positionY = y; }
    public void setVelocityX(double vx) { this.velocityX = vx; }
    public void setVelocityY(double vy) { this.velocityY = vy; }

    // Update particle position based on velocity
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}