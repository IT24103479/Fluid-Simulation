package org.example.fluidsim;

public class Particle {
    public double x, y;
    public double vx, vy;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = Math.random() * 0.2 - 0.1; // small random horizontal velocity
        this.vy = 0; // start vertical velocity
    }
}
