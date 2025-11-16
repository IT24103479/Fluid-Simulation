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

        //clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.CYAN);
        for (Particle p : simulation.particles) {
            gc.fillOval(p.x, p.y, 5, 5); // 5x5 pixel circles

        }
    }

}
