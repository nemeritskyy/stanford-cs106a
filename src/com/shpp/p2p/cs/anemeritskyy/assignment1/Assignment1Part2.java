package com.shpp.p2p.cs.anemeritskyy.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
Put beepers into clear cells opposite the columns
 */
public class Assignment1Part2 extends KarelTheRobot {

    @Override
    public void run() throws Exception {
        while (frontIsClear()) {

            columnChecker();
            // If we have another column move to start
            moveToStartOfRightColumn();
        }

        // Check last column
        columnChecker();
    }

    private void moveToStartOfRightColumn() throws Exception {
        turnAround();
        moveToColumnBottom();
        if (leftIsClear()) {
            moveToNextColumn();
        }
    }

    private void moveToColumnBottom() throws Exception {
        while (frontIsClear()) {
            move();
        }
    }

    /*
    Go straight and check beepers,
    if beeper exist skip this cell,
    if cell is clear, put beeper
     */
    private void columnChecker() throws Exception {
        // Turn to start checker position
        turnLeft();

        while (frontIsClear()) {
            checkBeeper();
            move();
        }

        // Check last cell
        checkBeeper();
    }

    private void moveToNextColumn() throws Exception {
        turnLeft();
        for (int i = 0; i < 4; i++) {
            move();
        }
    }

    private void checkBeeper() throws Exception {
        if (noBeepersPresent()) {
            putBeeper();
        }
    }

    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }
}
