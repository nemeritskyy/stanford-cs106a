package com.shpp.p2p.cs.anemeritskyy.assignment2;

import acm.graphics.GPoint;
import acm.graphics.GRect;

import java.awt.*;

/**
 * Drawing optical illusion
 * Matrix from black boxes separated by streets
 */
public class Assignment2Part5 extends DrawingUtils {

    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 350;
    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    @Override
    public void run() {
        drawIllusion();
    }

    /**
     * Draw illusion using params: count of columns and rows
     */
    private void drawIllusion() {
        GPoint illusionStartOffset = getIllusionStartPosition();
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                drawBox(i, j, illusionStartOffset);
            }
        }
    }

    /**
     * Draw box using params
     *
     * @param rowPosition         - position number in row
     * @param colPosition         - position number of column
     * @param illusionStartOffset - position of first box
     */
    private void drawBox(int rowPosition, int colPosition, GPoint illusionStartOffset) {
        GRect box = new GRect(
                illusionStartOffset.getX() + (BOX_SIZE + BOX_SPACING) * colPosition,
                illusionStartOffset.getY() + (BOX_SIZE + BOX_SPACING) * rowPosition,
                BOX_SIZE,
                BOX_SIZE
        );
        fillAndAddToFrame(box, Color.BLACK);
    }

    /**
     * Calculate position of the first box based on variables
     *
     * @return GPoint - coordinates for the first box
     */
    private GPoint getIllusionStartPosition() {
        double illusionWidth = NUM_COLS * BOX_SIZE + NUM_COLS * (BOX_SPACING - 1);
        double illusionHeight = NUM_ROWS * BOX_SIZE + NUM_ROWS * (BOX_SPACING - 1);
        double illusionStartOffsetX = (double) getWidth() / 2 - illusionWidth / 2;
        double illusionStartOffsetY = (double) getHeight() / 2 - illusionHeight / 2;
        return new GPoint(illusionStartOffsetX, illusionStartOffsetY);
    }
}
