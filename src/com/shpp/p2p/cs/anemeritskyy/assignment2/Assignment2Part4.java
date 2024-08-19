package com.shpp.p2p.cs.anemeritskyy.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Draw flag and make text label in right bottom corner
 */
public class Assignment2Part4 extends DrawingUtils {

    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 330;
    private static final int FLAG_WIDTH = 270;
    private static final int FLAG_HEIGHT = 220;
    private static final Color COLOR_YELLOW = new Color(255, 215, 0);
    private static final Color COLOR_BLUE = new Color(0, 87, 183);
    private static final int FONT_SIZE = 20;
    private static final String FONT = "Verdana-" + FONT_SIZE;

    /**
     * A method of expecting a description of the characteristics of the flag,
     * namely its description, the arrangement of colours
     * and the colours themselves in any number of colours
     */
    @Override
    public void run() {
        drawFlagWithLabel(
                "Kyiv Oblast",
                true,
                COLOR_BLUE, COLOR_YELLOW, COLOR_BLUE
        );
    }

    /**
     * Draw flag using some methods, separately draw flag and separately draw label
     *
     * @param country  - text for label
     * @param vertical - arrangement of colours
     * @param colors   - some colors
     */
    private void drawFlagWithLabel(String country, boolean vertical, Color... colors) {
        drawFlag(vertical, colors);
        drawDescriptionInRightBottomCorner(country);
    }

    /**
     * Draw label in right bottom corner given the descent
     *
     * @param country - text for label
     */
    private void drawDescriptionInRightBottomCorner(String country) {
        GLabel label = new GLabel("Flag of " + country);
        label.setFont(FONT);
        label.setLocation(
                getWidth() - label.getWidth(),
                getHeight() - label.getDescent());
        fillAndAddToFrame(label, Color.BLACK);
    }

    /**
     * Depending on the position of the colours,
     * draw parts horizontally or vertically
     *
     * @param vertical - arrangement of colours
     * @param colors   - some colors
     */
    private void drawFlag(boolean vertical, Color... colors) {
        for (int i = 0; i < colors.length; i++) {
            if (vertical) {
                drawVerticalPart(i, colors[i], colors.length);
            } else {
                drawHorizontalPart(i, colors[i], colors.length);
            }
        }
    }

    private void drawHorizontalPart(int positionNumber, Color color, int totalFlagColors) {
        GRect flagPart = new GRect(
                (double) getWidth() / 2 - (double) FLAG_WIDTH / 2,
                ((double) getHeight() / 2 - (double) FLAG_HEIGHT / 2) + (double) FLAG_HEIGHT / totalFlagColors * positionNumber,
                FLAG_WIDTH,
                (double) FLAG_HEIGHT / totalFlagColors
        );
        fillAndAddToFrame(flagPart, color);
    }

    private void drawVerticalPart(int positionNumber, Color color, int totalFlagColors) {
        GRect flagPart = new GRect(
                ((double) getWidth() / 2 - (double) FLAG_WIDTH / 2) + (double) FLAG_WIDTH / totalFlagColors * positionNumber,
                (double) getHeight() / 2 - (double) FLAG_HEIGHT / 2,
                (double) FLAG_WIDTH / totalFlagColors,
                FLAG_HEIGHT
        );
        fillAndAddToFrame(flagPart, color);
    }
}
