package com.shpp.p2p.cs.anemeritskyy.assignment2;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Draw an optical illusion using four circles and one rectangle
 *
 * @author Andrii Nemeritskyy
 */
public class Assignment2Part2 extends DrawingUtils {

    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;
    public static final int CIRCLE_RADIUS = 50;
    public static final int CIRCLE_DIAMETER = CIRCLE_RADIUS * 2;

    /**
     * Draw four circles in corners of window frame,
     * after that overlay draw a rectangle based on the centres of circles
     */
    @Override
    public void run() {
        drawCircles();
        drawRectangle();
    }

    /**
     * Condition: frame with circle in every corner
     * Result: draw rectangle based on central coordinates of every circle
     */
    private void drawRectangle() {
        GRect rectangle = new GRect(CIRCLE_RADIUS, CIRCLE_RADIUS,
                getWidth() - CIRCLE_DIAMETER, getHeight() - CIRCLE_DIAMETER);
        fillAndAddToFrame(rectangle, Color.WHITE);
    }

    /**
     * Condition: frame without circles
     * Result: frame with circle in every corner
     */
    private void drawCircles() {
        drawCircle(0, 0);
        drawCircle(0, getHeight() - CIRCLE_DIAMETER);
        drawCircle(getWidth() - CIRCLE_DIAMETER, 0);
        drawCircle(getWidth() - CIRCLE_DIAMETER, getHeight() - CIRCLE_DIAMETER);
    }

    /**
     * Draw filled circle by default black color,
     * positioning using coordinates,
     * added to main frame
     *
     * @param positionX - the x coordinate of position this object
     * @param positionY - the y coordinate of position this object
     */
    private void drawCircle(double positionX, double positionY) {
        GOval circle = super.drawCircle(new GPoint(positionX, positionY), CIRCLE_DIAMETER);
        fillAndAddToFrame(circle, Color.BLACK);
    }
}