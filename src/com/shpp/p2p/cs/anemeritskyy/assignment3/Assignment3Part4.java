package com.shpp.p2p.cs.anemeritskyy.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program draw the pyramid using some params as
 * brick height, width and count of bricks in base
 * also pyramid will center on middle of window
 */

public class Assignment3Part4 extends WindowProgram {
    public static final int BRICK_HEIGHT = 25;
    public static final int BRICK_WIDTH = 50;
    public static final int BRICKS_IN_BASE = 13;

    @Override
    public void run() {
        drawPyramid();
    }

    /**
     * Draw the pyramid, using basic param as count of bricks in base
     */
    private void drawPyramid() {
        for (int i = 0; i <= BRICKS_IN_BASE; i++) {
            for (int j = 0; j < BRICKS_IN_BASE - i; j++) {
                drawBrick(i, BRICKS_IN_BASE - i, j);
            }
        }
    }


    /**
     * Draw a brick depending on his position
     *
     * @param rowNumber  - number of roof, count from bottom
     * @param totalInRow - total bricks in current row
     * @param position   - position of current brick in current row
     */
    private void drawBrick(int rowNumber, int totalInRow, int position) {
        GRect brick = new GRect(
                (double) getWidth() / 2 - (double) (BRICK_WIDTH * totalInRow) / 2 + position * BRICK_WIDTH,
                getHeight() - BRICK_HEIGHT * rowNumber - BRICK_HEIGHT,
                BRICK_WIDTH,
                BRICK_HEIGHT
        );
        brick.setFilled(true);
        brick.setColor(Color.ORANGE);
        brick.setFillColor(Color.RED);
        add(brick);
    }
}