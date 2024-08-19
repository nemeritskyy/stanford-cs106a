package com.shpp.p2p.cs.anemeritskyy.assignment2;

import acm.graphics.GOval;

import java.awt.*;

/* Program draw two paws on window frame */
public class Assignment2Part3 extends DrawingUtils {

    /* Constants controlling the relative positions of the
     * three toes to the upper-left corner of the paw-print.
     */
    private static final double FIRST_TOE_OFFSET_X = 0;
    private static final double FIRST_TOE_OFFSET_Y = 20;
    private static final double SECOND_TOE_OFFSET_X = 30;
    private static final double SECOND_TOE_OFFSET_Y = 0;
    private static final double THIRD_TOE_OFFSET_X = 60;
    private static final double THIRD_TOE_OFFSET_Y = 20;

    /* The position of the heel relative to the upper-left
     * corner of the paw-print.
     */
    private static final double HEEL_OFFSET_X = 20;
    private static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    private static final double TOE_WIDTH = 20;
    private static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    private static final double HEEL_WIDTH = 40;
    private static final double HEEL_HEIGHT = 60;

    public static final int APPLICATION_WIDTH = 270;
    public static final int APPLICATION_HEIGHT = 220;

    public void run() {
        drawPawPrint(20, 20);
        drawPawPrint(180, 70);
    }

    /**
     * Draws a Paw-print
     *
     * @param x - the x coordinate of position this object
     * @param y - the y coordinate of position this object
     */
    private void drawPawPrint(double x, double y) {
        drawToes(x, y);
        drawHeel(x, y);
    }

    /**
     * Condition: getting offset coordinates for drawing heel
     * Result: draw heel using our offsets positions
     *
     * @param offsetX - the x coordinate of position this object
     * @param offsetY - the y coordinate of position this object
     */
    private void drawHeel(double offsetX, double offsetY) {
        drawOval(offsetX + HEEL_OFFSET_X, offsetY + HEEL_OFFSET_Y,
                HEEL_WIDTH, HEEL_HEIGHT);
    }

    /**
     * Condition: getting offset coordinates for drawing toes
     * Result: draw toes using our offsets positions for different toes
     *
     * @param offsetX - the x coordinate of position this object
     * @param offsetY - the y coordinate of position this object
     */
    private void drawToes(double offsetX, double offsetY) {
        drawToe(offsetX + FIRST_TOE_OFFSET_X, offsetY + FIRST_TOE_OFFSET_Y);
        drawToe(offsetX + SECOND_TOE_OFFSET_X, offsetY + SECOND_TOE_OFFSET_Y);
        drawToe(offsetX + THIRD_TOE_OFFSET_X, offsetY + THIRD_TOE_OFFSET_Y);
    }

    /**
     * Condition: getting offset coordinates for drawing toes
     * Result: draw toe using our util drawOval with default toes sizes
     *
     * @param offsetX - the x coordinate of position this object
     * @param offsetY - the y coordinate of position this object
     */
    private void drawToe(double offsetX, double offsetY) {
        drawOval(offsetX, offsetY, TOE_WIDTH, TOE_HEIGHT);
    }

    /**
     * Condition: getting offset positions and oval size
     * Result: added filled by default black color oval object to our window frame
     *
     * @param offsetX    - the x coordinate of position this object
     * @param offsetY    - the y coordinate of position this object
     * @param ovalWidth  - width of the created Oval
     * @param ovalHeight - height of the created Oval
     */
    private void drawOval(double offsetX, double offsetY, double ovalWidth, double ovalHeight) {
        GOval toe = new GOval(offsetX, offsetY, ovalWidth, ovalHeight);
        fillAndAddToFrame(toe, Color.BLACK);
    }
}
