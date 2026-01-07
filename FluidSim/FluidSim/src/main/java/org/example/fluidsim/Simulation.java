package org.example.fluidsim;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    public List<Particle> particles = new ArrayList<>();
    private double width, height;
    private double gravity = 0.05;

    private double particleRadius = 5;
    private double smoothingRadius = 12;

    public Simulation(double width, double height, int numParticles) {
        this.width = width;
        this.height = height;

        // spawn particles in top half of canvas
        for (int i = 0; i < numParticles; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height / 2;
            particles.add(new Particle(x, y));
        }
    }

    public void update() {
        for (Particle p : particles) {
            // apply gravity
            p.vy += gravity;

            // update position
            p.x += p.vx;
            p.y += p.vy;

            // clamp velocities
            p.vx = Math.max(Math.min(p.vx, 2), -2);
            p.vy = Math.max(Math.min(p.vy, 2), -2);

            // bottom boundary
            if (p.y > height - particleRadius) {
                p.y = height - particleRadius;
                p.vy *= -0.3; // bounce damping
            }

            // left wall
            if (p.x < 0) {
                p.x = 0;
                p.vx *= -1;
            }

            // right wall
            if (p.x > width - particleRadius) {
                p.x = width - particleRadius;
                p.vx *= -1;
            }
        }

        // inter-particle repulsion
        double repulsionStrength = 0.3;
        for (int i = 0; i < particles.size(); i++) {
            Particle a = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle b = particles.get(j);
                double dx = b.x - a.x;
                double dy = b.y - a.y;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < smoothingRadius && dist > 0) {
                    double force = (smoothingRadius - dist) * repulsionStrength;
                    a.vx -= dx / dist * force;
                    a.vy -= dy / dist * force;
                    b.vx += dx / dist * force;
                    b.vy += dy / dist * force;
                }
            }
        }
    }
}
