package sr.frostybee.gravityballs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;
import javafx.stage.Stage;

/**
 * Bouncing balls example: Apply forces gravity and wind.
 *
 * source: https://gist.github.com/Roland09/4021335c1a0c38f4fdd47602b63885f1
 */
public class GravityBalls extends Stage {

    private static Random random = new Random();
    private Pane animationPane;
    private List<Sprite> balls = new ArrayList<>();
    private AnimationTimer animationLoop;
    private Scene scene;
    private static final Color[] COLORS = new Color[]{RED, YELLOW, GREEN,
        BLUE, PURPLE, CADETBLUE, DARKBLUE, PINK, BLACK};

    public GravityBalls(Stage mainStage) {
        initOwner(mainStage);
        initStageComponents();
    }

    private void initStageComponents() {
        // create containers
        BorderPane root = new BorderPane();
        // playfield for our Sprites
        animationPane = new Pane();
        animationPane.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        animationPane.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        root.setCenter(animationPane);
        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        this.setOnCloseRequest((event) -> {
            // Stop the animation timer upon closing this window. 
            if (animationLoop != null) {
                animationLoop.stop();
            }
        });
        setScene(scene);
        //show();
        // add content
        createBalls();
        // run animation loop
        initAnimationLoop();
    }

    private void createBalls() {
        // add sprites
        for (int i = 0; i < Settings.SPRITE_COUNT; i++) {
            double x = random.nextDouble() * animationPane.getWidth();
            double y = random.nextDouble() * animationPane.getHeight();

            // create sprite data
            PVector location = new PVector(x, y);
            PVector velocity = new PVector(0, 0);
            PVector acceleration = new PVector(0, 0);
            double mass = random.nextDouble() * 50 + 20; // at least 20 pixels, max
            // 50 pixels
            // create sprite and add to layer
            //Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            Color color = COLORS[i % COLORS.length];
            Sprite sprite = new Sprite(animationPane, location, velocity, acceleration, mass, color);
            // register sprite
            balls.add(sprite);
        }
    }

    private void initAnimationLoop() {

        // start game
        animationLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // physics: apply forces
                balls.forEach(sprite -> sprite.applyForce(Settings.FORCE_GRAVITY));
                balls.forEach(sprite -> sprite.applyForce(Settings.FORCE_WIND));
                // move
                balls.forEach(Sprite::move);
                // check boundaries
                balls.forEach(Sprite::checkBounds);
                // update in fx scene
                balls.forEach(Sprite::render);
            }
        };
        animationLoop.start();
    }
}
