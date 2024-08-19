package com.shpp.p2p.cs.anemeritskyy.assignment3;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Illustration of Collatz hypothesis from task number 2
 * five seconds, no more and no less
 */
public class Assignment3Part6 extends WindowProgram {
    private final static RandomGenerator RANDOM_GENERATOR = RandomGenerator.getInstance();
    private final static long MAX_ANIMATION_TIME_MS = 5_000;
    private final static int MIN_CIRCLE_SIZE = 5;
    private final static int MAX_CIRCLE_SIZE = 15;
    private final static GPoint CIRCLE_COORDINATES = new GPoint(); // used to not always create a new point for object coordinates

    @Override
    public void run() {
        long startTimeMs = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTimeMs < MAX_ANIMATION_TIME_MS) {
            drawCollatzConjecture(RANDOM_GENERATOR.nextInt(1, getWidth() - MAX_CIRCLE_SIZE), startTimeMs);
        }
        addTimeToFrame(startTimeMs);
    }

    /**
     * Use Collatz Conjecture from task 2 to demonstrate how it's looks on frame
     *
     * @param posX        - any natural number
     * @param startTimeMs - time in ms when program was started
     */
    private void drawCollatzConjecture(int posX, long startTimeMs) {
        int posY = posX;
        while (posY > 1) {
            if (System.currentTimeMillis() - startTimeMs > MAX_ANIMATION_TIME_MS)
                break;
            if (posY % 2 == 0) {
                posY /= 2;
            } else {
                posY = posY * 3 + 1;
            }
            CIRCLE_COORDINATES.setLocation(posX, posY);
            drawCircle();
            pause(3); // delay in ms
        }
    }

    /**
     * Draw a circle on frame
     */
    private void drawCircle() {
        int circleSize = RANDOM_GENERATOR.nextInt(MIN_CIRCLE_SIZE, MAX_CIRCLE_SIZE);
        GOval oval = new GOval(CIRCLE_COORDINATES.getX() - (double) circleSize / 2, CIRCLE_COORDINATES.getY(), circleSize, circleSize);
        oval.setFilled(true);
        oval.setColor(RANDOM_GENERATOR.nextColor());
        add(oval);
    }

    /**
     * Draw a label with total time for execution program
     * when time is green color then all conditions are met
     * when time is red color then main condition with execution time is not met
     *
     * @param startTimeMs - time in ms when program was started
     */
    private void addTimeToFrame(long startTimeMs) {
        long animationTimeMs = System.currentTimeMillis() - startTimeMs; // total mills

        GLabel time = new GLabel(animationTimeMs + "ms");
        time.setLocation(20, getHeight() - 20);
        time.setFont("Verdana-50");
        time.setColor(animationTimeMs <= MAX_ANIMATION_TIME_MS + 150 ? Color.green : Color.red);

        GRect timeBackground = new GRect(20, getHeight() - 70, time.getWidth(), time.getHeight());
        timeBackground.setFilled(true);
        timeBackground.setColor(Color.BLACK);

        add(timeBackground);
        add(time);
    }
}