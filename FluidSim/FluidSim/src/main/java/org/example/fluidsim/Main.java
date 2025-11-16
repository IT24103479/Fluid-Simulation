package org.example.fluidsim;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("FluidSim");

        Canvas canvas = new Canvas(800, 600);
        javafx.scene.Group root = new javafx.scene.Group(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Create simulation and renderer
        Simulation simulation = new Simulation();
        Renderer renderer = new Renderer(canvas, simulation);

        // Animation loop
        new javafx.animation.AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime > 0) {
                    double dt = (now - lastTime) / 1_000_000_000.0; // convert ns to seconds
                    simulation.update(dt);
                }
                lastTime = now;

                renderer.draw();
            }
        }.start();
    }


    public static void main(String[] args) {
        launch();
    }
}
