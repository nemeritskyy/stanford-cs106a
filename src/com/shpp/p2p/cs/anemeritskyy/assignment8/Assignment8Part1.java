package com.shpp.p2p.cs.anemeritskyy.assignment8;

import acm.graphics.GObject;
import acm.graphics.GPoint;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Vertical squares
 * This programs move boxes with some gravity when user clicked on box
 */
public class Assignment8Part1 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 550;
    public static final int APPLICATION_HEIGHT = 550;
    /* Count of boxes drawing at canvas */
    private static final int BOX_COUNT = 10;
    /* The width and height of each box */
    private static final double BOX_SIZE = 40;
    /* The horizontal spacing between the boxes and bottom spacing from the frame. */
    private static final double BOX_SPACING = 10;
    /* Force for moving box from the bottom */
    private static final double PUSH_FORCE = 5;
    /* Gravity force */
    private static final double GRAVITY = 0.425;
    /* Delay for each loop default 48 fps */
    private static final double DELAY = 1000.0 / 48;
    /* Default color for boxes */
    private static final Color BOX_COLOR = Color.BLACK;
    /* Start coordinates for first box */
    private static final GPoint startPoint = new GPoint();
    /* Collection of elements what we need to move */
    private final Set<Box> movedElements = new HashSet<>();

    /**
     * Start program and wait when user add boxes to move
     */
    @Override
    public void run() {
        startPoint.setLocation(getStartCoordinates());
        initBoxes();
        addMouseListeners();

        while (true) {
            if (movedElements != null && !movedElements.isEmpty())
                moveBoxes(movedElements);
            pause(DELAY);
        }
    }

    /**
     * Move every boxes selected by user
     *
     * @param movedElements collection of boxes
     */
    private void moveBoxes(Set<Box> movedElements) {
        GObject removeElement = null;
        for (Box movedBox : movedElements) {
            switch (movedBox.getDirection()) {
                case UP -> {
                    if (movedBox.getY() > 1) {
                        movedBox.move(0, -movedBox.getY() / PUSH_FORCE);
                    } else { // touch top frame
                        movedBox.setDirection(Direction.DOWN);
                    }
                }
                case DOWN -> {
                    if (movedBox.getY() < startPoint.getY() &&
                            movedBox.getY() + movedBox.getGravity() < startPoint.getY()) { // so as not to fall beyond the lower limit
                        movedBox.move(0, movedBox.getGravity());
                        movedBox.setGravity(movedBox.getGravity() + GRAVITY);
                    } else { // set box to start location and removed it from moved collection
                        movedBox.defaultCondition();
                        removeElement = movedBox;
                    }
                }
            }
        }
        movedElements.remove(removeElement);
    }

    /**
     * Init and draw boxes on canvas
     */
    private void initBoxes() {
        for (int i = 0; i < BOX_COUNT; i++) {
            GPoint boxCoordinate = new GPoint(startPoint.getX() + (BOX_SIZE + BOX_SPACING) * i, startPoint.getY());
            Box box = new Box(boxCoordinate, BOX_SIZE);
            box.setFilled(true);
            box.setColor(BOX_COLOR);
            add(box);
        }
    }

    /**
     * Start point for drawing boxes
     *
     * @return GPoint with starting coordinates
     */
    private GPoint getStartCoordinates() {
        return new GPoint(
                (double) getWidth() / 2 - ((BOX_SIZE + BOX_SPACING) * BOX_COUNT - BOX_SPACING) / 2,
                getHeight() - (BOX_SIZE + BOX_SPACING));
    }

    /**
     * Listen when mouse pressed check if object is not null add it to move collection
     *
     * @param mouseEvent the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Box movedObject = (Box) getElementAt(mouseEvent.getX(), mouseEvent.getY());
        if (movedObject != null) {
            movedObject.setDirection(Direction.UP);
            movedElements.add(movedObject);
        }
    }
}