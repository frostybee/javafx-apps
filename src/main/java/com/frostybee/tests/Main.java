package com.frostybee.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Bouncing balls example: Apply forces gravity and wind
 */
public class Main extends Application {

	static Random random = new Random();

	Pane playfield;

	List<Sprite> allSprites = new ArrayList<>();

	AnimationTimer gameLoop;

	Scene scene;

	@Override
	public void start(Stage primaryStage) {

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
		primaryStage.setScene(scene);
		primaryStage.show();

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
				allSprites.forEach(s -> s.applyForce(Settings.FORCE_GRAVITY));
				allSprites.forEach(s -> s.applyForce(Settings.FORCE_WIND));

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

		Pane layer = playfield;

		// random location
		double x = random.nextDouble() * layer.getWidth();
		double y = random.nextDouble() * layer.getHeight();

		// create sprite data
		PVector location = new PVector(x, y);
		PVector velocity = new PVector(0, 0);
		PVector acceleration = new PVector(0, 0);
		
		double mass = random.nextDouble() * 50 + 20; // at least 20 pixels, max
														// 50 pixels

		// create sprite and add to layer
		Sprite sprite = new Sprite(layer, location, velocity, acceleration, mass);

		// register sprite
		allSprites.add(sprite);

	}

	public static void main(String[] args) {
		launch(args);
	}
}