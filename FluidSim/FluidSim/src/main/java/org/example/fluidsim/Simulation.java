package org.example.fluidsim;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public List<Particle> particles = new ArrayList<>();
    private double width, height;
    private double gravity = 0.05;
    private double particleRadius = 5;
    private double smoothingRadius = 12;

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
            p.vy += gravity;

            // small random horizontal drift for natural unevenness
            p.vx += (Math.random() - 0.5) * 0.02;

            // update position
            p.x += p.vx;
            p.y += p.vy;

            // clamp velocities
            p.vx = Math.max(Math.min(p.vx, 2), -2);
            p.vy = Math.max(Math.min(p.vy, 2), -2);

            // bottom boundary
            if (p.y > height - particleRadius) {
                p.y = height - particleRadius;
                p.vy *= -0.2;
            }

            // left and right walls
            if (p.x < 0) { p.x = 0; p.vx *= -1; }
            if (p.x > width - particleRadius) { p.x = width - particleRadius; p.vx *= -1; }
        }

        // inter-particle repulsion
        for (int i = 0; i < particles.size(); i++) {
            Particle a = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle b = particles.get(j);
                double dx = b.x - a.x;
                double dy = b.y - a.y;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < smoothingRadius && dist > 0) {
                    double force = (smoothingRadius - dist) * 0.05;
                    double angle = Math.atan2(dy, dx);
                    double fx = Math.cos(angle) * force;
                    double fy = Math.sin(angle) * force;

                    a.vx -= fx;
                    a.vy -= fy;
                    b.vx += fx;
                    b.vy += fy;
                }

                // vertical stacking: prevent overlapping
                if (Math.abs(dx) < particleRadius && dy > 0 && dy < particleRadius) {
                    a.vy -= 0.05;
                    b.vy += 0.05;
                }
            }
        }

        // mouse interaction
        if (mouseX >= 0 && mouseY >= 0) {
            for (Particle p : particles) {
                double dx = p.x - mouseX;
                double dy = p.y - mouseY;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < 50 && dist > 0) {
                    double force = (50 - dist) * mouseForce * 0.1;
                    p.vx += dx / dist * force;
                    p.vy += dy / dist * force;
                }
            }
        }

        // damping for smooth motion
        for (Particle p : particles) {
            p.vx *= 0.98;
            p.vy *= 0.98;
        }
    }
}
