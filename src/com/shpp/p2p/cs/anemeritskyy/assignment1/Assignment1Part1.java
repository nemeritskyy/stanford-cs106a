package com.shpp.p2p.cs.anemeritskyy.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
Robot who takes newspaper from his threshold and return to base cell
 */
public class Assignment1Part1 extends KarelTheRobot {

    @Override
    public void run() throws Exception {
        moveToNewspaper();
        pickUpNewspaper();
        moveToStartPosition();
    }

    private void moveToStartPosition() throws Exception {
        turnAround();
        goToPrecedent();
        moveKarelToRightCell();
        turnAround();
    }

    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    private void moveToNewspaper() throws Exception {
        moveKarelToRightCell();
        goToPrecedent();
    }

    /*
    Move Karel to right cell from his current position,
    with saving direction
     */
    private void moveKarelToRightCell() throws Exception {
        turnRight();
        move();
        turnLeft();
    }

    private void turnRight() throws Exception {
        turnLeft();
        turnLeft();
        turnLeft();
    }

    /*
    Go to precedent ( beeper or wall )
     */
    private void goToPrecedent() throws Exception {
        for (int i = 0; i < 4; i++) {
            move();
        }
    }

    private void pickUpNewspaper() throws Exception {
        pickBeeper();
    }
}