package com.shpp.p2p.cs.anemeritskyy.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    /**
     * Save names entered by user
     */
    private final List<NameSurferEntry> enteredNames = new ArrayList<>();

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        removeAll();
        enteredNames.clear();
        update();
    }

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        if (entry != null && !enteredNames.contains(entry))
            enteredNames.add(entry);
        update();
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        double gridSeparate = (double) getWidth() / NDECADES;
        drawGrid(gridSeparate);
        drawVisualisation(gridSeparate);
    }

    /**
     * Draw visualisation links for every entered name
     *
     * @param gridSeparate separate between columns
     */
    private void drawVisualisation(double gridSeparate) {
        for (int i = 0; i < enteredNames.size(); i++) {
            drawNameLinks(enteredNames.get(i), gridSeparate, getColorOnIndex(i));
        }
    }

    /**
     * Draw visualisation for every decade, links and labels
     *
     * @param nameSurferEntry object for some name with decade data
     * @param gridSeparate    separate between columns
     * @param color           for current visualisation
     */
    private void drawNameLinks(NameSurferEntry nameSurferEntry, double gridSeparate, Color color) {
        int entryPoint = 0;
        int finishPoint = 0;
        for (int i = 0; i < NDECADES - 1; i++) {
            entryPoint = rankPosition(nameSurferEntry.getRank(i));
            finishPoint = rankPosition(nameSurferEntry.getRank(i + 1));
            GLine decadeLine = new GLine(gridSeparate * i, entryPoint, gridSeparate * (i + 1), finishPoint);
            decadeLine.setColor(color);
            drawRankLabel(nameSurferEntry, i, (int) Math.ceil(gridSeparate * i), entryPoint, color);
            add(decadeLine);
        }
        drawRankLabel(nameSurferEntry, NDECADES - 1, (int) Math.ceil(gridSeparate * (NDECADES - 1)), finishPoint, color); // for last label
    }

    /**
     * Method for drawing label for selected decade
     *
     * @param nameSurferEntry object for some name with decade data
     * @param decade          index of selected decade
     * @param offsetX         x position
     * @param offsetY         y position
     * @param color           for current visualisation
     */
    private void drawRankLabel(NameSurferEntry nameSurferEntry, int decade, int offsetX, int offsetY, Color color) {
        GLabel label = new GLabel(
                String.format("%s %s", nameSurferEntry.getName(), getLabelRank(nameSurferEntry, decade)),
                offsetX,
                offsetY
        );
        label.setColor(color);
        add(label);
    }

    /**
     * For 0 results return star, for each other value
     *
     * @param nameSurferEntry object for some name with decade data
     * @param decade          index of selected decade
     * @return rank for label
     */
    private String getLabelRank(NameSurferEntry nameSurferEntry, int decade) {
        return nameSurferEntry.getRank(decade) == 0 ? "*" : "" + nameSurferEntry.getRank(decade);
    }

    /**
     * Calculate y position from the size of the window for current rank
     *
     * @param rank in database for some user
     * @return y offset position for current rank
     */
    private int rankPosition(int rank) {
        double coefficient = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK;
        if (rank == 0) {
            return getHeight() - GRAPH_MARGIN_SIZE;
        } else return GRAPH_MARGIN_SIZE + (int) (coefficient * rank);
    }

    /**
     * Check color for current index
     *
     * @param index of record in visualisation entries
     * @return color for current index
     */
    private Color getColorOnIndex(int index) {
        return NAMES_COLORS[index % NAMES_COLORS.length];
    }

    /**
     * Draw grid on canvas
     *
     * @param gridSeparate separate between columns
     */
    private void drawGrid(double gridSeparate) {
        for (int i = 0; i < NDECADES; i++) {
            drawColumn(gridSeparate, i);
        }
        drawRowLine();
    }

    /**
     * Draw top and bottom lines on grid
     */
    private void drawRowLine() {
        GLine rowLine1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        GLine rowLine2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        add(rowLine1);
        add(rowLine2);
    }

    /**
     * Draw line for every decade
     *
     * @param gridSeparate separate between columns
     * @param columnIndex  index of column(decade)
     */
    private void drawColumn(double gridSeparate, int columnIndex) {
        double xOffset = gridSeparate * columnIndex;
        GLine line = new GLine(xOffset, 0, xOffset, getHeight());
        GLabel decade = new GLabel("" + (START_DECADE + columnIndex * 10), xOffset, getHeight());
        decade.setFont(new Font("Arial", Font.PLAIN, (int) Math.min(GRAPH_MARGIN_SIZE, gridSeparate / 3)));
        add(line);
        add(decade);
    }


    /* Implementation of the ComponentListener interface */

    /**
     * Events for every resize action
     *
     * @param e the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent e) {
        removeAll();
        update();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
