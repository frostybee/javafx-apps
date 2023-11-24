package sr.frostybee.attraction;

import javafx.geometry.Point2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Mover extends Circle {

	Point2D location;
	Point2D velocity;
	Point2D acceleration;

	double mass;

	double maxSpeed = Settings.MOVER_MAX_SPEED;

	public Mover(Point2D location, Point2D velocity, Point2D acceleration, double mass) {

		this.location = location;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;

		// initialize view depending on mass
		setRadius( mass);

		// create view
		setStroke(Color.BLUE);
		setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));

	}

	public void applyForce(Point2D force) {

		Point2D f = new Point2D( force.getX(), force.getY());
		f = f.multiply(1/mass);
		
		acceleration = acceleration.add(f);
	}

	public void move() {

		// set velocity depending on acceleration
		velocity = velocity.add(acceleration);

		// limit velocity to max speed
		double mag = velocity.magnitude();
		if( mag > Settings.MOVER_MAX_SPEED) {
			velocity = velocity.normalize();
			velocity = velocity.multiply(mag);
		}

		// change location depending on velocity
		location = location.add(velocity);

		// clear acceleration
		acceleration = new Point2D(0,0);
	}

	public Point2D attract(Mover m) {

		// force direction
		Point2D force = location.subtract(m.location);
		double distance = force.magnitude();
		
		// constrain movement
		distance = constrain(distance, Settings.ATTRACTION_DISTANCE_MIN, Settings.ATTRACTION_DISTANCE_MAX);
		
		force = force.normalize();

		// force magnitude
		double strength = (Settings.GRAVITATIONAL_CONSTANT * mass * m.mass) / (distance * distance);
		force = force.multiply(strength);

		return force;
	}

	public static double constrain(double value, double min, double max) {

		if (value < min)
			return min;

		if (value > max)
			return max;

		return value;
	}
	
	public void display() {
		setCenterX( location.getX());
		setCenterY( location.getY());
	}	
}
