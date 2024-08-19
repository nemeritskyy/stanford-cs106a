package com.shpp.p2p.cs.anemeritskyy.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
Find central cell using only Karel language,
If we have odd cells in row, put beeper into central cell,
if we have even cells in row, put to one from central cells
 */
public class Assignment1Part3 extends KarelTheRobot {

    @Override
    public void run() throws Exception {
        if (frontIsClear()) {
            move();
            putBeepersStraightCells();

            // Collect all beepers
            takeOuterBeeper();
        }

        // Put beeper into last position
        putBeeper();
    }

    /*
    Turn around and take last beeper in the row
     */
    private void takeOuterBeeper() throws Exception {
        turnAround();
        while (beepersPresent()) {
            pickBeeper();

            // We already take beeper, go to the next cell
            move();

            goToTheLastBeeper();
            turnAround();

            if (noBeepersPresent()) {
                move();
            }
        }
    }

    private void goToTheLastBeeper() throws Exception {
        while (beepersPresent()) {
            move();
        }
    }

    /*
    Put beepers in the cells in front
     */
    private void putBeepersStraightCells() throws Exception {
        while (frontIsClear()) {
            putBeeper();
            move();
        }

        // Put beeper in the last cell
        putBeeper();
    }

    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }
}