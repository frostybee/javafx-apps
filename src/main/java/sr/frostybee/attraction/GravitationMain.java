package sr.frostybee.attraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GravitationMain extends Stage {

    static Random random = new Random();

    Pane playfield;

    List<Mover> allMovers = new ArrayList<>();

    AnimationTimer gameLoop;

    Scene scene;

    public GravitationMain() {

        // create containers
        BorderPane root = new BorderPane();
        // playfield for our sprites
        playfield = new Pane();
        //TODO: replace with actual screen dimensions .
        playfield.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        // entire game as layers
        Pane layerPane = new Pane();
        layerPane.getChildren().addAll(playfield);
        root.setCenter(layerPane);
        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        setScene(scene);        

        // add content
        prepareGame();

        // run animation loop
        startGame();

    }

    private void prepareGame() {

        // add sprites
        for (int i = 0; i < Settings.MOVER_COUNT; i++) {
            addMovers();
        }

    }

    private void startGame() {

        // start game
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // force: attraction
                for (Mover m1 : allMovers) {
                    for (Mover m2 : allMovers) {

                        if (m1 == m2) {
                            continue;
                        }

                        // calculate attraction
                        Point2D force = m1.attract(m2);

                        // apply attraction
                        m2.applyForce(force);

                    }
                }
                // move
                allMovers.forEach(Mover::move);
                // update in fx scene
                allMovers.forEach(Mover::display);
            }
        };
        gameLoop.start();
    }

    private void addMovers() {

        // random location
        double x = random.nextDouble() * playfield.getWidth();
        double y = random.nextDouble() * playfield.getHeight();

        // create sprite data
        Point2D location = new Point2D(x, y);
        Point2D velocity = new Point2D(0, 0);
        Point2D acceleration = new Point2D(0, 0);
        double mass = random.nextDouble() * 10 + 10;

        // create sprite and add to layer
        Mover mover = new Mover(location, velocity, acceleration, mass);

        // register sprite
        allMovers.add(mover);

        // add this node to layer
        playfield.getChildren().add(mover);
    }
    
}
