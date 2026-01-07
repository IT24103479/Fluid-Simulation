package org.example.fluidsim;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
    private final Canvas canvas;
    private final Simulation simulation;

    public Renderer(Canvas canvas, Simulation simulation) {
        this.canvas = canvas;
        this.simulation = simulation;
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double particleRadius = 5;
        double smoothingRadius = 12;

        for (Particle p : simulation.particles) {
            // draw smoothing radius as soft blue blur
            gc.setFill(Color.CYAN.deriveColor(0, 1, 1, 0.1));
            gc.fillOval(p.x - smoothingRadius, p.y - smoothingRadius,
                    smoothingRadius * 2, smoothingRadius * 2);

            // draw particle
            gc.setFill(Color.CYAN);
            gc.fillOval(p.x - particleRadius / 2, p.y - particleRadius / 2,
                    particleRadius, particleRadius);
        }
    }
}
