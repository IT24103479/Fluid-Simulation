package org.example.fluidsim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int N = 400;
    private static final int CELL = 1;

    private final Simulation fluid = new Simulation(N, N, 500); // 500 particles
    private final Renderer renderer = new Renderer(fluid);

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(N * CELL, N * CELL);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Label label = new Label("Density:");
        TextField densityField = new TextField("50");
        densityField.setPrefWidth(80);

        HBox topBar = new HBox(10, label, densityField);

        canvas.setOnMouseDragged(e -> {
            int x = (int) (e.getX() / CELL);
            int y = (int) (e.getY() / CELL);

            double amount = 50;
            try { amount = Double.parseDouble(densityField.getText()); }
            catch (NumberFormatException ex) { }

            fluid.addDensity(x, y, amount);
            fluid.addVelocity(x, y, 5, 5);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                fluid.step();
                renderer.render(gc);
            }
        }.start();

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(canvas);

        stage.setTitle("Fluid Simulation");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}