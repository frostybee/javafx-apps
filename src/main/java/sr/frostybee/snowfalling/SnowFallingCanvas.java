package sr.frostybee.snowfalling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * Snow falling animation on a canvas.
 */
public class SnowFallingCanvas extends Application {

    Random random = new Random();
    Pane root = new Pane();
    int width = 1000;
    int height = 1000;
    int numberOfSnowFlakes = 1000;
    Canvas canvas = new Canvas(width, height);
    List<SnowFlake> snowflakes = new ArrayList<>();
    private double angle = Math.random();
    AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) {
        ImageView background = new ImageView(new Image(getClass().getResource("/images/montreal.jpg").toString()));
        root.getChildren().add(background);
        root.setBackground(Background.fill(Color.BLACK));
        Button btnStart = new Button();
        btnStart.setOnAction((event) -> {
            createSnowFlakes();
            timer.start();
        });
        root.getChildren().addAll(canvas, btnStart);
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        // Create and initialize the snowflakes.

        // Create the animation loop.
        //TODO: stop the animation timer upon closing the main window.
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                if (now - lastUpdate >= 20000000) {
                    lastUpdate = now;
                    //update();                                                            
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    draw(gc);
                }
            }
        };
        Scene scene = new Scene(root, 950, 834);
        primaryStage.setTitle("SnowFalling Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSnowFlakes() {
        for (int i = 0; i < numberOfSnowFlakes; i++) {
            double radius = AppHelper.getRandomDouble(2.25, 8);
            double sway = AppHelper.getRandomDouble(-0.3, 0.3);
            double drop = AppHelper.getRandomDouble(-0.5, 0.5);
            double x = AppHelper.getRandomDouble(0, canvas.getWidth());
            double y = AppHelper.getRandomDouble(0, canvas.getHeight());
            // Generate a random white'ish color... 
            Color color = Color.rgb(255, 255, 255, random.nextDouble());
            snowflakes.add(new SnowFlake(x, y, radius, sway, drop, color));
        }
    }

    public void draw(GraphicsContext gc) {
        angle = this.angle + 0.005;
        snowflakes.forEach((flake) -> {
            // Update our flake's next x/y.
            flake.update(canvas.getWidth(), canvas.getHeight(), angle);
            flake.draw(gc);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
