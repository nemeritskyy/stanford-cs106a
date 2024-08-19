package com.shpp.p2p.cs.anemeritskyy.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.util.RandomGenerator;

import java.awt.*;

/**
 * This program created art-caterpillar using basic params
 */
public class Assignment2Part6 extends DrawingUtils {

    public static final int APPLICATION_WIDTH = 600;
    public static final int APPLICATION_HEIGHT = 330;

    // Count of caterpillar parts
    private static final int CATERPILLAR_LENGTH = 8;

    // Size of every part of caterpillar
    private static final int CATERPILLAR_PART_SIZE = 80;

    /**
     * It's a coefficient for shift difference,
     * param recommended from 0.2 to 0.5,
     * P.S. don't torture caterpillar
     */
    private static final double SHIFT_DIFFERENCE = 0.2;

    /**
     * Start position of caterpillar, for better illustration
     * recommended use positive digits, but negative also supported
     */
    private static final double START_OFFSET_X_CATERPILLAR = 20;
    private static final double START_OFFSET_Y_CATERPILLAR = 60;

    private final RandomGenerator GENERATOR = new RandomGenerator();

    @Override
    public void run() {
        drawCaterpillar();
    }

    /**
     * Draw a caterpillar using default final properties as CATERPILLAR_LENGTH, CATERPILLAR_PART_SIZE,
     * SHIFT_DIFFERENCE, and start offset position START_OFFSET_X_CATERPILLAR, START_OFFSET_Y_CATERPILLAR
     */
    private void drawCaterpillar() {
        // Location current using element
        GPoint partOffset = new GPoint(START_OFFSET_X_CATERPILLAR, START_OFFSET_Y_CATERPILLAR);
        for (int i = 0; i < CATERPILLAR_LENGTH; i++) {
            GOval circle = drawCircle(partOffset, CATERPILLAR_PART_SIZE);
            fillAndAddToFrame(circle, getRandomColor());
            fillBorder(circle, getRandomColor());

            //Set location for next part of caterpillar
            setNextLocation(partOffset);
        }
        addSmile(partOffset);
    }

    /**
     * Add smile to the last part (head)
     *
     * @param partOffset - coordinates of smile position
     */
    private void addSmile(GPoint partOffset) {
        GLabel smile = new GLabel("^_^");
        smile.setFont("Verdana-" + CATERPILLAR_PART_SIZE / 3);
        smile.setLocation(
                partOffset.getX() - (double) CATERPILLAR_PART_SIZE / 2,
                partOffset.getY() + (double) CATERPILLAR_PART_SIZE / 2
        );
        add(smile);
    }

    /**
     * Calculate and set next location
     */
    private void setNextLocation(GPoint partOffset) {
        partOffset.setLocation(
                partOffset.getX() + CATERPILLAR_PART_SIZE * (1 - SHIFT_DIFFERENCE),
                (partOffset.getY() + 1 * SHIFT_DIFFERENCE) + getNextOffsetY(partOffset)
        );
    }

    /**
     * Caterpillar tries to take its place in the screen
     * and stay within it regardless of the starting point
     *
     * @param partOffset - current GPoint with coordinates of his part
     * @return double - indicates where to offset the Y-axis
     */
    private double getNextOffsetY(GPoint partOffset) {
        // If position of part is negative than endeavour to positive
        if (partOffset.getY() < CATERPILLAR_PART_SIZE * SHIFT_DIFFERENCE)
            return CATERPILLAR_PART_SIZE * SHIFT_DIFFERENCE;
        // Random next position up or down
        return GENERATOR.nextBoolean() ? -CATERPILLAR_PART_SIZE * SHIFT_DIFFERENCE : CATERPILLAR_PART_SIZE * SHIFT_DIFFERENCE;
    }

    /**
     * Generate random color using RandomGenerator
     *
     * @return Color - random color
     */
    private Color getRandomColor() {
        return GENERATOR.nextColor();
    }
}
