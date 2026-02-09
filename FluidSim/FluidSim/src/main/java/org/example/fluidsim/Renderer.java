package org.example.fluidsim;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
    private final Simulation simulation;

    public Renderer(Simulation simulation) {
        this.simulation = simulation;
    }

    public void render(GraphicsContext gc) {
        // Clear background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, simulation.getWidth(), simulation.getHeight());

        // Draw particles
        gc.setFill(Color.CYAN);
        for (Particle p : simulation.getParticles()) {
            gc.fillOval(p.getX(), p.getY(), 3, 3);
        }
    }
}