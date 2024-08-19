package com.shpp.p2p.cs.anemeritskyy.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    /**
     * Field for entry name
     */
    private JTextField nameInput;

    /**
     * Storage of names and data for it
     */
    private NameSurferDataBase dataBase;

    /**
     * Canvas for visualisation
     */
    private NameSurferGraph graph;

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        InputListener inputListener = new InputListener();
        dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
        graph = new NameSurferGraph();
        nameInput = new JTextField(NAME_FIELD_SIZE);
        JButton buttonGraph = new JButton("Graph");
        JButton buttonClear = new JButton("Clear");

        addComponent(new JLabel("Name:"));
        addComponent(nameInput);
        addComponent(buttonGraph);
        addComponent(buttonClear);
        this.add(graph);

        nameInput.addKeyListener(inputListener);
        buttonGraph.setActionCommand("Graph");
        buttonClear.setActionCommand("ClearGraph");

        this.addActionListeners();
    }


    /**
     * Add component on a certain direction
     *
     * @param component any component added on canvas
     */
    private void addComponent(Component component) {
        this.add(component, NORTH);
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Graph" -> graphRecord();
            case "Clear" -> clearInput();
            case "ClearGraph" -> clearGraph();
        }
    }

    /**
     * Clear already inputted data and text field
     */
    private void clearGraph() {
        graph.clear();
        clearInput();
    }

    /**
     * Clear text field
     */
    private void clearInput() {
        this.nameInput.setText(null);
    }

    /**
     * Get name without spaces outside
     */
    private String getInputName() {
        return this.nameInput.getText().trim();
    }

    /**
     * Add entered name to entered names map if it exists
     */
    private void graphRecord() {
        NameSurferEntry nameRecord = dataBase.findEntry(getInputName());
        graph.addEntry(nameRecord);
    }

    /**
     * Keyword listeners
     */
    private class InputListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 10 -> graphRecord(); // enter action
                case 32 -> clearInput(); // space action
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
