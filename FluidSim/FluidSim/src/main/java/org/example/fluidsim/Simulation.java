package org.example.fluidsim;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final int width;
    private final int height;
    private final List<Particle> particles;

    public Simulation(int width, int height, int numParticles) {
        this.width = width;
        this.height = height;
        this.particles = new ArrayList<>();

        // Initialize particles in random positions
        for (int i = 0; i < numParticles; i++) {
            particles.add(new Particle(Math.random() * width, Math.random() * height));
        }
    }

    // Step simulation: update all particles
    public void step() {
        for (Particle p : particles) {
            p.update();
        }
    }

    // Add velocity to a particle near (x, y)
    public void addVelocity(int x, int y, double vx, double vy) {
        for (Particle p : particles) {
            if (Math.abs(p.getX() - x) < 5 && Math.abs(p.getY() - y) < 5) {
                p.setVelocityX(p.getVelocityX() + vx);
                p.setVelocityY(p.getVelocityY() + vy);
            }
        }
    }

    // Add density (just move particles toward the mouse)
    public void addDensity(int x, int y, double amount) {
        for (Particle p : particles) {
            if (Math.abs(p.getX() - x) < 5 && Math.abs(p.getY() - y) < 5) {
                p.setX(p.getX() + Math.random() * amount - amount / 2);
                p.setY(p.getY() + Math.random() * amount - amount / 2);
            }
        }
    }

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public List<Particle> getParticles() { return particles; }
}