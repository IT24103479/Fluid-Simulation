package org.example.fluidsim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Main extends Application {
    private final double width = 800;
    private final double height = 600;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(width, height);

        Simulation simulation = new Simulation(width, height, 1000); // 300 particles
        Renderer renderer = new Renderer(canvas, simulation);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                simulation.update();   // physics
                renderer.draw();       // draw
            }
        }.start();

        StackPane root = new StackPane(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle("Fluid Simulation");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
