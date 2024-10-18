package sr.frostybee.bouncing;

import static java.lang.Math.sqrt;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 *
 * @author frostybee
 */
public class Ball {

    private final DoubleProperty xVelocity; // pixels per second
    private final DoubleProperty yVelocity;
    private final ReadOnlyDoubleWrapper speed;
    private final double mass; // arbitrary units
    private final double radius; // pixels

    private final Circle ball;

    public Ball(double centerX, double centerY, double radius,
            double xVelocity, double yVelocity, double mass) {

        this.ball = new Circle(centerX, centerY, radius);
        this.xVelocity = new SimpleDoubleProperty(this, "xVelocity", xVelocity);
        this.yVelocity = new SimpleDoubleProperty(this, "yVelocity", yVelocity);
        this.speed = new ReadOnlyDoubleWrapper(this, "speed");
        speed.bind(Bindings.createDoubleBinding(() -> {
            final double xVel = getXVelocity();
            final double yVel = getYVelocity();
            return sqrt(xVel * xVel + yVel * yVel);
        }, this.xVelocity, this.yVelocity));
        this.mass = mass;
        this.radius = radius;
        ball.setRadius(radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public final double getXVelocity() {
        return xVelocity.get();
    }

    public final void setXVelocity(double xVelocity) {
        this.xVelocity.set(xVelocity);
    }

    public final DoubleProperty xVelocityProperty() {
        return xVelocity;
    }

    public final double getYVelocity() {
        return yVelocity.get();
    }

    public final void setYVelocity(double yVelocity) {
        this.yVelocity.set(yVelocity);
    }

    public final DoubleProperty yVelocityProperty() {
        return yVelocity;
    }

    public final double getSpeed() {
        return speed.get();
    }

    public final ReadOnlyDoubleProperty speedProperty() {
        return speed.getReadOnlyProperty();
    }

    public final double getCenterX() {
        return ball.getCenterX();
    }

    public final void setCenterX(double centerX) {
        ball.setCenterX(centerX);
    }

    public final DoubleProperty centerXProperty() {
        return ball.centerXProperty();
    }

    public final double getCenterY() {
        return ball.getCenterY();
    }

    public final void setCenterY(double centerY) {
        ball.setCenterY(centerY);
    }

    public final DoubleProperty centerYProperty() {
        return ball.centerYProperty();
    }

    public Shape getView() {
        return ball;
    }
}
