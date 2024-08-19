package com.shpp.p2p.cs.anemeritskyy.assignment2;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class DrawingUtils extends WindowProgram {
    /**
     * Filled object to selected color
     * added object to main frame
     *
     * @param obj   - object who's implemented GFillable
     * @param color - color for filled current object
     */
    public void fillAndAddToFrame(GObject obj, Color color) {
        if (obj instanceof GRect) {
            ((GRect) obj).setFilled(true);
            obj.setColor(color);
        }
        if (obj instanceof GOval) {
            ((GOval) obj).setFilled(true);
            ((GOval) obj).setFillColor(color);
        }
        if (obj instanceof GLabel) {
            obj.setColor(color);
        }
        add(obj);
    }

    /**
     * Filled border of object to selected color
     *
     * @param obj   - object who's implemented GFillable
     * @param color - color for filled border of current object
     */
    public void fillBorder(GObject obj, Color color) {
        if (obj instanceof GOval) {
            if (!((GOval) obj).isFilled())
                ((GOval) obj).setFilled(true);
            obj.setColor(color);
        }
    }

    /**
     * Create object of circle using params
     *
     * @param coordinates    - using GPoint for getting coordinates
     * @param circleDiameter - diameter of the created circle
     * @return - new object GOval as circle
     */
    public GOval drawCircle(GPoint coordinates, double circleDiameter) {
        return new GOval(coordinates.getX(), coordinates.getY(), circleDiameter, circleDiameter);
    }
}