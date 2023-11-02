/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.frostybee.gravityballs;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Sprite extends Region {

	PVector location;
	PVector velocity;
	PVector acceleration;

	double mass;

	double maxSpeed = Settings.SPRITE_MAX_SPEED;

	Node view;

	// view dimensions
	double width = 30;
	double height = width;
	double centerX = width / 2.0;
	double centerY = height / 2.0;
	double radius = width / 2.0;

	Pane layer = null;

	public Sprite(Pane layer, PVector location, PVector velocity, PVector acceleration, double mass) {

		this.layer = layer;

		this.location = location;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;

		// initialize view depending on mass
		width = mass;
		height = width;
		centerX = width / 2.0;
		centerY = height / 2.0;
		radius = width / 2.0;

		// create view
		Circle circle = new Circle(radius);
		circle.setCenterX(radius);
		circle.setCenterY(radius);

		circle.setStroke(Color.BLUE);
		circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));

		this.view = circle;

		// add view to this node
		getChildren().add(view);

		// add this node to layer
		layer.getChildren().add(this);

	}

	public void applyForce(PVector force) {

		// Making a copy of the PVector before using it!
		PVector f = PVector.div(force, mass);
		acceleration.add(f);
	}

	public void move() {

		// set velocity depending on acceleration
		velocity.add(acceleration);

		// limit velocity to max speed
		velocity.limit(maxSpeed);

		// change location depending on velocity
		location.add(velocity);

		// clear acceleration
		acceleration.mult(0);
	}

	/**
	 * Ensure sprite can't go outside bounds
	 */
	public void checkBounds() {

		if (location.x > layer.getWidth() - radius) {
			location.x = layer.getWidth() - radius;
			velocity.x *= -1;
		} else if (location.x < 0 + radius) {
			velocity.x *= -1;
			location.x = 0 + radius;
		}

		// reverse direction to bounce off floor
		if (location.y > layer.getHeight() - radius) {
			velocity.y *= -1;
			location.y = layer.getHeight() - radius;
		}

	}

	/**
	 * Update node position
	 */
	public void display() {
		relocate(location.x - centerX, location.y - centerY);
	}
}