package com.frostybee.gravityballs;

import com.frostybee.common.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Bouncing balls example: Apply forces gravity and wind source:
 * https://gist.github.com/Roland09/4021335c1a0c38f4fdd47602b63885f1
 */
public class GravityBalls extends Stage {

    private static Random random = new Random();
    private Pane playfield;
    private List<Sprite> allSprites = new ArrayList<>();
    private AnimationTimer gameLoop;
    private Scene scene;

    public GravityBalls() {
        initStageComponents();
    }

    private void initStageComponents() {
        Utils.bringToFront(this);
        // create containers
        BorderPane root = new BorderPane();

        // entire game as layers
        StackPane layerPane = new StackPane();

        // playfield for our Sprites
        playfield = new Pane();
        playfield.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        layerPane.getChildren().addAll(playfield);
        root.setCenter(layerPane);
        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        this.setOnCloseRequest((event) -> {
            // Stop the animation timer upon closing this window. 
            if (gameLoop != null) {
                gameLoop.stop();
            }
        });
        setScene(scene);
        show();
        // add content
        prepareGame();
        // run animation loop
        startGame();
    }

    private void prepareGame() {
        // add sprites
        for (int i = 0; i < Settings.SPRITE_COUNT; i++) {
            addSprite();
        }
    }

    private void startGame() {

        // start game
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // physics: apply forces
                allSprites.forEach(sprite -> sprite.applyForce(Settings.FORCE_GRAVITY));
                allSprites.forEach(sprite -> sprite.applyForce(Settings.FORCE_WIND));
                // move
                allSprites.forEach(Sprite::move);
                // check boundaries
                allSprites.forEach(Sprite::checkBounds);
                // update in fx scene
                allSprites.forEach(Sprite::display);
            }
        };
        gameLoop.start();
    }

    private void addSprite() {
        // random location
        double x = random.nextDouble() * playfield.getWidth();
        double y = random.nextDouble() * playfield.getHeight();

        // create sprite data
        PVector location = new PVector(x, y);
        PVector velocity = new PVector(0, 0);
        PVector acceleration = new PVector(0, 0);
        double mass = random.nextDouble() * 50 + 20; // at least 20 pixels, max
        // 50 pixels
        // create sprite and add to layer
        Sprite sprite = new Sprite(playfield, location, velocity, acceleration, mass);
        // register sprite
        allSprites.add(sprite);
    }

}
