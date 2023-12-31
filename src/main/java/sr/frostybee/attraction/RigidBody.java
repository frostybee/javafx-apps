package sr.frostybee.attraction;

import javafx.geometry.Point2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class RigidBody extends Circle {

    private Point2D location;
    private Point2D velocity;
    private Point2D acceleration;
    private double mass = 1;
    private SimulationSettings settings;
    
    public RigidBody(Point2D location, Point2D velocity, Point2D acceleration, double mass, Color color, SimulationSettings settings) {
        this.settings = settings;
        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.mass = mass;

        // initialize view depending on mass
        setRadius(mass);
        // create view
        setStroke(color);
        setFill(color.deriveColor(2, 2, 1, .7));
    }

    public void applyForce(Point2D force) {
        Point2D f = new Point2D(force.getX(), force.getY());
        f = f.multiply(1 / mass);
        acceleration = acceleration.add(f);
    }

    public void move() {
        // set velocity depending on acceleration
        velocity = velocity.add(acceleration);
        // limit velocity to max speed
        double magnitude = velocity.magnitude();
        if (magnitude > settings.getBodyMaxSpeed()) {
            velocity = velocity.normalize();
            velocity = velocity.multiply(magnitude);
        }
        // change location depending on velocity
        location = location.add(velocity);
        // clear acceleration
        acceleration = new Point2D(0, 0);
    }

    public Point2D attract(RigidBody rigidBody) {
        // force direction
        Point2D force = location.subtract(rigidBody.location);
        double distance = force.magnitude();
        // constrain movement
        distance = constrain(distance, settings.getMinAttractionDistance(), settings.getMaxAttractionDistance());
        force = force.normalize();
        // force magnitude
        double strength = (settings.getGravitationalConstant() * mass * rigidBody.mass) / (distance * distance);
        force = force.multiply(strength);
        return force;
    }

    public static double constrain(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public void display() {
        setCenterX(location.getX());
        setCenterY(location.getY());
    }
}
