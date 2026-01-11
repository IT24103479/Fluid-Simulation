package org.example.fluidsim;
/**
 * particle initialization
 * updating positions each frame
 * gravity
 * Repulsion between particles
 */

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public List<Particle> particles = new ArrayList<>();
    private double width, height; //size of the simulation area
    private double gravity = 0.05; //pulls particles downward each frame
    private double particleRadius = 5; // visual size and used for collision
    private double smoothingRadius = 12; //fluid pressure(how close particles need to be to push each other apart)

    // Mouse interaction
    public double mouseX = -1;
    public double mouseY = -1;
    public double mouseForce = 0.5;

    public Simulation(double width, double height, int numParticles) {
        this.width = width;
        this.height = height;

        int cols = (int) (width / (particleRadius * 2));
        int rows = numParticles / cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // random horizontal jitter for uneven top
                double x = particleRadius + j * particleRadius * 2 + Math.random() * 2;
                double y = particleRadius + i * particleRadius * 2;
                particles.add(new Particle(x, y));
            }
        }
    }

    public void update() {
        for (Particle p : particles) {
            // gravity
            p.velocityY += gravity;

            // small random horizontal drift for natural unevenness
            p.velocityX += (Math.random() - 0.5) * 0.02;

            // update position
            p.positionX += p.velocityX;
            p.positionY += p.velocityY;

            // clamp velocities
            p.velocityX = Math.max(Math.min(p.velocityX, 2), -2);
            p.velocityY = Math.max(Math.min(p.velocityY, 2), -2);

            // bottom boundary
            if (p.positionY > height - particleRadius) {
                p.positionY = height - particleRadius;
                p.velocityY *= -0.2;
            }

            // left and right walls
            if (p.positionX < 0) { p.positionX = 0; p.velocityX *= -1; }
            if (p.positionX > width - particleRadius) { p.positionX = width - particleRadius; p.velocityX *= -1; }
        }

        // inter-particle repulsion
        for (int i = 0; i < particles.size(); i++) {
            Particle a = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle b = particles.get(j);
                double dx = b.positionX - a.positionX;
                double dy = b.positionY - a.positionY;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < smoothingRadius && dist > 0) {
                    double force = (smoothingRadius - dist) * 0.05;
                    double angle = Math.atan2(dy, dx);
                    double fx = Math.cos(angle) * force;
                    double fy = Math.sin(angle) * force;

                    a.velocityX -= fx;
                    a.velocityY= fy;
                    b.velocityX += fx;
                    b.velocityY += fy;
                }

                // vertical stacking: prevent overlapping
                if (Math.abs(dx) < particleRadius && dy > 0 && dy < particleRadius) {
                    a.velocityY -= 0.05;
                    b.velocityY+= 0.05;
                }
            }
        }

        // mouse interaction
        if (mouseX >= 0 && mouseY >= 0) {
            for (Particle p : particles) {
                double dx = p.positionX - mouseX;
                double dy = p.positionY - mouseY;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < 50 && dist > 0) {
                    double force = (50 - dist) * mouseForce * 0.1;
                    p.velocityX += dx / dist * force;
                    p.velocityY += dy / dist * force;
                }
            }
        }

        // damping for smooth motion
        for (Particle p : particles) {
            p.velocityX *= 0.98;
            p.velocityY *= 0.98;
        }
    }
}
