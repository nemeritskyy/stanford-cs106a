package com.shpp.p2p.cs.anemeritskyy.assignment8;

import acm.graphics.GPoint;
import acm.graphics.GRect;

/**
 * Box with additional properties
 */
public class Box extends GRect {
    /* Direction for moving */
    private Direction direction = null;
    /* Start coordinates when init this box*/
    private GPoint startCoordinates = null;
    /* Gravity for falls*/
    private double gravity;

    /**
     * Constructor
     *
     * @param coordinates location for box
     * @param boxSize     size of the box
     */
    public Box(GPoint coordinates, double boxSize) {
        super(coordinates.getX(), coordinates.getY(), boxSize, boxSize);
        startCoordinates = coordinates;
        this.gravity = 1;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /**
     * Method for return this object to original condition
     */
    public void defaultCondition() {
        this.setLocation(this.getX(), startCoordinates.getY());
        setGravity(1); // default gravity
        setDirection(null);
    }
}