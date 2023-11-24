package sr.frostybee.bouncing;

import sr.frostybee.common.AppHelpers;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static javafx.scene.paint.Color.*;
import java.util.ListIterator;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

/**
 * @source: https://gist.github.com/james-d/8327842
 * @author frostybee
 */
public class RandomBouncingBalls extends Stage {

    private final ObservableList<Ball> balls;
    private static final int NUM_BALLS = 400;
    private static final double MIN_RADIUS = 5;
    private static final double MAX_RADIUS = 15;
    private static final double MIN_SPEED = 50;
    private static final double MAX_SPEED = 150;
    private static final Color[] COLORS = new Color[]{RED, YELLOW, GREEN,
        BROWN, BLUE, PINK, BLACK};

    private final FrameStats frameStats;
    private AnimationTimer animation;

    public RandomBouncingBalls() {
        balls = FXCollections.observableArrayList();
        frameStats = new FrameStats();
        initStageComponents();
    }

    private void initStageComponents() {
        AppHelpers.bringToFront(this);
        final Pane ballContainer = new Pane();
        constrainBallsOnResize(ballContainer);
        this.setAlwaysOnTop(true);
        ballContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                balls.clear();
                createBalls(NUM_BALLS, MIN_RADIUS, MAX_RADIUS, MIN_SPEED, MAX_SPEED, ballContainer.getWidth() / 2, ballContainer.getHeight() / 2);
            }
        });

        balls.addListener((Change<? extends Ball> change) -> {
            while (change.next()) {
                for (Ball b : change.getAddedSubList()) {
                    ballContainer.getChildren().add(b.getView());
                }
                for (Ball b : change.getRemoved()) {
                    ballContainer.getChildren().remove(b.getView());
                }
            }
        });

        createBalls(NUM_BALLS, MIN_RADIUS, MAX_RADIUS, MIN_SPEED, MAX_SPEED, 400, 300);

        final BorderPane root = new BorderPane();
        final Label stats = new Label();
        stats.textProperty().bind(frameStats.textProperty());

        root.setCenter(ballContainer);
        root.setBottom(stats);

        final Scene scene = new Scene(root, 800, 600);
        this.setScene(scene);
        this.setOnCloseRequest((event) -> {
            // Stop the animation timer upon closing this window. 
            if (animation != null) {
                animation.stop();
            }
        });
        startAnimation(ballContainer);
    }

    private void startAnimation(final Pane ballContainer) {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        animation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    long elapsedTime = timestamp - lastUpdateTime.get();
                    checkCollisions(ballContainer.getWidth(), ballContainer.getHeight());
                    updateWorld(elapsedTime);
                    frameStats.addFrame(elapsedTime);
                }
                lastUpdateTime.set(timestamp);
            }
        };
        animation.start();
    }

    private void updateWorld(long elapsedTime) {
        double elapsedSeconds = elapsedTime / 1_000_000_000.0;
        for (Ball b : balls) {
            b.setCenterX(b.getCenterX() + elapsedSeconds * b.getXVelocity());
            b.setCenterY(b.getCenterY() + elapsedSeconds * b.getYVelocity());
        }
    }

    private void checkCollisions(double maxX, double maxY) {
        for (ListIterator<Ball> slowIt = balls.listIterator(); slowIt.hasNext();) {
            Ball b1 = slowIt.next();
            // check wall collisions:
            double xVel = b1.getXVelocity();
            double yVel = b1.getYVelocity();
            if ((b1.getCenterX() - b1.getRadius() <= 0 && xVel < 0)
                    || (b1.getCenterX() + b1.getRadius() >= maxX && xVel > 0)) {
                b1.setXVelocity(-xVel);
            }
            if ((b1.getCenterY() - b1.getRadius() <= 0 && yVel < 0)
                    || (b1.getCenterY() + b1.getRadius() >= maxY && yVel > 0)) {
                b1.setYVelocity(-yVel);
            }
            for (ListIterator<Ball> fastIt = balls.listIterator(slowIt.nextIndex()); fastIt.hasNext();) {
                Ball b2 = fastIt.next();
                // performance hack: both colliding(...) and bounce(...) need deltaX and deltaY, so compute them once here:
                final double deltaX = b2.getCenterX() - b1.getCenterX();
                final double deltaY = b2.getCenterY() - b1.getCenterY();
                if (colliding(b1, b2, deltaX, deltaY)) {
                    bounce(b1, b2, deltaX, deltaY);
                }
            }
        }
    }

    public boolean colliding(final Ball b1, final Ball b2, final double deltaX, final double deltaY) {
        // square of distance between balls is s^2 = (x2-x1)^2 + (y2-y1)^2
        // balls are "overlapping" if s^2 < (r1 + r2)^2
        // We also check that distance is decreasing, i.e.
        // d/dt(s^2) < 0:
        // 2(x2-x1)(x2'-x1') + 2(y2-y1)(y2'-y1') < 0

        final double radiusSum = b1.getRadius() + b2.getRadius();
        if (deltaX * deltaX + deltaY * deltaY <= radiusSum * radiusSum) {
            if (deltaX * (b2.getXVelocity() - b1.getXVelocity())
                    + deltaY * (b2.getYVelocity() - b1.getYVelocity()) < 0) {
                return true;
            }
        }
        return false;
    }

    private void bounce(final Ball b1, final Ball b2, final double deltaX, final double deltaY) {
        final double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
        final double unitContactX = deltaX / distance;
        final double unitContactY = deltaY / distance;

        final double xVelocity1 = b1.getXVelocity();
        final double yVelocity1 = b1.getYVelocity();
        final double xVelocity2 = b2.getXVelocity();
        final double yVelocity2 = b2.getYVelocity();

        final double u1 = xVelocity1 * unitContactX + yVelocity1 * unitContactY; // velocity of ball 1 parallel to contact vector
        final double u2 = xVelocity2 * unitContactX + yVelocity2 * unitContactY; // same for ball 2

        final double massSum = b1.getMass() + b2.getMass();
        final double massDiff = b1.getMass() - b2.getMass();

        final double v1 = (2 * b2.getMass() * u2 + u1 * massDiff) / massSum; // These equations are derived for one-dimensional collision by
        final double v2 = (2 * b1.getMass() * u1 - u2 * massDiff) / massSum; // solving equations for conservation of momentum and conservation of energy

        final double u1PerpX = xVelocity1 - u1 * unitContactX; // Components of ball 1 velocity in direction perpendicular
        final double u1PerpY = yVelocity1 - u1 * unitContactY; // to contact vector. This doesn't change with collision
        final double u2PerpX = xVelocity2 - u2 * unitContactX; // Same for ball 2....
        final double u2PerpY = yVelocity2 - u2 * unitContactY;

        b1.setXVelocity(v1 * unitContactX + u1PerpX);
        b1.setYVelocity(v1 * unitContactY + u1PerpY);
        b2.setXVelocity(v2 * unitContactX + u2PerpX);
        b2.setYVelocity(v2 * unitContactY + u2PerpY);

    }

    private void createBalls(int numBalls, double minRadius, double maxRadius, double minSpeed, double maxSpeed, double initialX, double initialY) {
        final Random rng = new Random();
        for (int i = 0; i < numBalls; i++) {
            double radius = minRadius + (maxRadius - minRadius) * rng.nextDouble();
            double mass = Math.pow((radius / 40), 3);

            final double speed = minSpeed + (maxSpeed - minSpeed) * rng.nextDouble();
            final double angle = 2 * PI * rng.nextDouble();
            Ball ball = new Ball(initialX, initialY, radius, speed * cos(angle),
                    speed * sin(angle), mass);
            ball.getView().setFill(COLORS[i % COLORS.length]);
            balls.add(ball);
        }
    }

    private void constrainBallsOnResize(final Pane ballContainer) {
        ballContainer.widthProperty().addListener(new ChangeListenerImpl());

        ballContainer.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.doubleValue() < oldValue.doubleValue()) {
                for (Ball b : balls) {
                    double max = newValue.doubleValue() - b.getRadius();
                    if (b.getCenterY() > max) {
                        b.setCenterY(max);
                    }
                }
            }
        });
    }

    private class ChangeListenerImpl implements ChangeListener<Number> {

        public ChangeListenerImpl() {
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue) {
            if (newValue.doubleValue() < oldValue.doubleValue()) {
                for (Ball b : balls) {
                    double max = newValue.doubleValue() - b.getRadius();
                    if (b.getCenterX() > max) {
                        b.setCenterX(max);
                    }
                }
            }
        }
    }
}
