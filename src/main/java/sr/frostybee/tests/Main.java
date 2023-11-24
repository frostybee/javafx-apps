package sr.frostybee.tests;

import java.time.Duration;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author frostybee
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final int size = 400;
        final Rectangle[] rectangles = new Rectangle[size];
        final long[] delays = new long[size];
        final double[] angles = new double[size];
        final long duration = Duration.ofSeconds(3).toNanos();
        final Random random = new Random();

        for (int i = 0; i < size; i++) {
            rectangles[i] = new Rectangle(5, 5, Color.hsb(random.nextInt(360), 1, 1));
            delays[i] = (long) (Math.random() * duration);
            angles[i] = 2 * Math.PI * random.nextDouble();
        }
        stage.setScene(new Scene(new Pane(rectangles), 500, 500, Color.BLACK));
        stage.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> System.exit(0));
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                final double width = 0.5 * stage.getWidth();
                final double height = 0.5 * stage.getHeight();
                final double radius = Math.sqrt(2) * Math.max(width, height);

                for (int i = 0; i < size; i++) {
                    Rectangle r = rectangles[i];
                    double angle = angles[i];
                    long t = (now - delays[i]) % duration;
                    double d = t * radius / duration;

                    r.setOpacity((duration - t) / (double) duration);
                    r.setTranslateX(Math.cos(angle) * d + width);
                    r.setTranslateY(Math.sin(angle) * d + height);
                }
            }
        }.start();
    }

}
