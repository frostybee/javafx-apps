package sr.frostybee.moneybag;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.media.AudioClip;

/**
 *
 * @author frostybee
 */
public final class MoneyBagApp extends Stage {

    private int score = 0;
    private long lastNanoTime = System.nanoTime();
    private AudioClip coinClip;
    private AnimationTimer animation;

    public MoneyBagApp(Stage mainStage) {
        initOwner(mainStage);
        initStageComponents();
    }

    public void initStageComponents() {

        //-- Set up the stage and the scene graph. 
        this.setTitle("Collect the Money Bags!");
        Group root = new Group();
        Scene scene = new Scene(root);
        this.setScene(scene);
        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);
        this.setOnCloseRequest((event) -> {
            // Stop the animation timer upon closing this window. 
            if (animation != null) {
                animation.stop();
            }
        });

        //-- Create and configure the media player.                
        coinClip = new AudioClip(getClass().getResource("/sounds/picked-coin.wav").toExternalForm());

        List<String> input = new ArrayList<>();
        scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased((KeyEvent e) -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font scoreFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(scoreFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Sprite briefcase = new Sprite();
        briefcase.setImage(getClass().getResource("/images/briefcase.png").toExternalForm());
        briefcase.setPosition(200, 0);

        List<Sprite> moneybagList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Sprite moneybag = new Sprite();
            moneybag.setImage(getClass().getResource("/images/moneybag.png").toExternalForm());
            double px = 350 * Math.random() + 50;
            double py = 350 * Math.random() + 50;
            moneybag.setPosition(px, py);
            moneybagList.add(moneybag);
        }

        animation = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // game logic
                briefcase.setVelocity(0, 0);
                if (input.contains("LEFT")) {
                    briefcase.addVelocity(-250, 0);
                }
                if (input.contains("RIGHT")) {
                    briefcase.addVelocity(250, 0);
                }
                if (input.contains("UP")) {
                    briefcase.addVelocity(0, -250);
                }
                if (input.contains("DOWN")) {
                    briefcase.addVelocity(0, 250);
                }

                briefcase.update(elapsedTime);

                // collision detection
                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while (moneybagIter.hasNext()) {
                    Sprite moneybag = moneybagIter.next();
                    if (briefcase.intersects(moneybag)) {
                        moneybagIter.remove();
                        coinClip.play();
                        score++;
                    }
                }

                // render
                gc.clearRect(0, 0, 512, 512);
                briefcase.render(gc);

                for (Sprite moneybag : moneybagList) {
                    moneybag.render(gc);
                }

                String pointsText = "Cash: $" + (100 * score);
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);
            }
        };
        animation.start();
    }
}
