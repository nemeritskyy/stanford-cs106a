package com.shpp.p2p.cs.anemeritskyy.assignment1;

import com.shpp.karel.KarelTheRobot;

/*
Put beepers into cells as chess desk,
starts from the left bottom corner
 */
public class Assignment1Part4 extends KarelTheRobot {

    @Override
    public void run() throws Exception {
        while (notFacingNorth()) {
            putBeepers();
        }
    }

    /*
    Put beepers in the cells
     */
    private void putBeepers() throws Exception {
        while (notFacingNorth()) {
            putBeeper();
            moveToNextCellWithoutBeeper();
        }
    }

    /*
    We take two steps depending on the obstacles
     */
    private void moveToNextCellWithoutBeeper() throws Exception {
        if (frontIsClear()) {
            move();
            if (frontIsClear()) {
                move();
            } else {
                moveToNextRow();
            }
        } else {
            moveToNextRow();
            if (frontIsClear()) {
                move();
            }
        }
    }

    /*
    Move to the next row in the North side
     */
    private void moveToNextRow() throws Exception {
        turnToNorth();
        bothBlocked();
        leftBlocked();
        rightBlocked();
    }

    /*
    Actions if it's blocked on both sides
     */
    private void bothBlocked() throws Exception {
        if (leftIsBlocked()) {
            if (rightIsBlocked()) {
                if (frontIsClear()) {
                    move();
                }
            }
        }
    }

    /*
    Action if left side is blocked
     */
    private void leftBlocked() throws Exception {
        if (leftIsBlocked()) {
            if (frontIsClear()) {
                move();
                turnRight();
            }
        }
    }

    /*
    Action if right side is blocked
     */
    private void rightBlocked() throws Exception {
        if (rightIsBlocked()) {
            if (frontIsClear()) {
                move();
                turnLeft();
            }
        }
    }

    private void turnToNorth() throws Exception {
        while (notFacingNorth()) {
            turnLeft();
        }
    }

    private void turnRight() throws Exception {
        turnLeft();
        turnLeft();
        turnLeft();
    }
}