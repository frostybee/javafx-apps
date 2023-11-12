package com.frostybee.tests;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author frostybee
 */
public class MainFX extends Application {

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    private static final double SPEED = 100; // pixels/second
    private static final double PLAYER_RADIUS = 10;
    private AnchorPane pane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        pane = new AnchorPane();
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        double playerX = pane.getWidth() / 2;
        double playerY = pane.getHeight() / 2;
        Circle player = new Circle(playerX, playerY, PLAYER_RADIUS);
        pane.getChildren().add(player);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::press);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::release);

        AnimationTimer timer = new AnimationTimer() {

            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
                double elapsedSeconds = (now - lastUpdate) / 1_000_000_000.0;
                int deltaX = 0;
                int deltaY = 0;
                if (leftPressed) {
                    deltaX -= 1;
                }
                if (rightPressed) {
                    deltaX += 1;
                }
                if (upPressed) {
                    deltaY -= 1;
                }
                if (downPressed) {
                    deltaY += 1;
                }

                Point2D translationVector = new Point2D(deltaX, deltaY)
                        .normalize()
                        .multiply(SPEED * elapsedSeconds);

                player.setCenterX(clampX(player.getCenterX() + translationVector.getX()));
                player.setCenterY(clampY(player.getCenterY() + translationVector.getY()));

                lastUpdate = now;
            }
        };
        timer.start();

    }

    private double clampX(double value) {
        return clamp(value, PLAYER_RADIUS, pane.getWidth() - PLAYER_RADIUS);
    }

    private double clampY(double value) {
        return clamp(value, PLAYER_RADIUS, pane.getHeight() - PLAYER_RADIUS);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void press(KeyEvent event) {
        handle(event.getCode(), true);
    }

    private void release(KeyEvent event) {
        handle(event.getCode(), false);
    }

    private void handle(KeyCode key, boolean press) {
        switch (key) {
            case UP:
                upPressed = press;
                break;
            case DOWN:
                downPressed = press;
                break;
            case LEFT:
                leftPressed = press;
                break;
            case RIGHT:
                rightPressed = press;
                break;
            default: ;
        }
    }
}
