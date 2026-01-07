package org.example.fluidsim;

public class Particle {
    public double x, y;
    public double vx, vy;

    public Particle(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0; // start horizontal velocity small for natural drift
        this.vy = 0;
    }
}
