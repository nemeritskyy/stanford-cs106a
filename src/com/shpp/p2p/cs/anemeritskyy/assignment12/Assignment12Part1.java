package com.shpp.p2p.cs.anemeritskyy.assignment12;

import acm.graphics.GImage;
import acm.graphics.GPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment12Part1 {
    /**
     * Folder with assets
     */
    private final static String ASSETS_PATH = "assets/";
    private final static String DEFAULT_IMG = "test.jpg";
    /**
     * All items that are PARAM times smaller than the largest object will be uncounted.
     */
    private final static int GARBAGE_COEFFICIENT = 10;
    /**
     * Recommended 0.6 for remove artefacts caused by loss of quality during storage
     */
    private final static double ARTEFACT_COEFFICIENT = 0.6;
    /**
     * If Silhouette contain 1 shade this param may be true
     * if Silhouette is color may be false
     */
    public static boolean isSilhouettePlain = true;
    private static int colorDeep = 255;
    public static int TEST_EXPECTED = 0;

    /**
     * This method find count of silhouette for inputted file, and show result in the console
     * @param args array with filename in args[0]
     */
    public static void main(String[] args) {
        String sourceFileName = checkFileName(args);
        List<Integer> allSilhouettes = findSilhouettes(sourceFileName);
        removeGarbage(allSilhouettes);
        System.out.println("Count of silhouette(s): " + allSilhouettes.size());
        TEST_EXPECTED = allSilhouettes.size();
    }

    /**
     * Remove garbage after processing of detection
     * it removes all than less in GARBAGE_COEFFICIENT times
     *
     * @param allSilhouettes list silhouettes with count of pixels, with garbage
     */
    private static void removeGarbage(List<Integer> allSilhouettes) {
        allSilhouettes.sort((o1, o2) -> o2 - o1); // sort from largest to small
        int maxObject = allSilhouettes.getFirst();
        allSilhouettes.removeIf(pixelsInSilhouettes -> pixelsInSilhouettes < maxObject * GARBAGE_COEFFICIENT / 100);
    }

    /**
     * Find all anticipated silhouettes
     *
     * @param filename with extension
     * @return list silhouettes with count of pixels, with garbage
     */
    private static List<Integer> findSilhouettes(String filename) {
        List<Integer> allSilhouettes = new ArrayList<>();
        GImage sourceImage = new GImage(ASSETS_PATH + filename);
        int[][] sourceImageArray = sourceImage.getPixelArray();
        boolean[][] visitedPixels = new boolean[sourceImageArray.length][sourceImageArray[0].length];
        int background = getBackground(sourceImageArray);
        setColorDeep();

        for (int row = 0; row < sourceImageArray.length; ++row) {
            for (int col = 0; col < sourceImageArray[row].length; ++col) {
                if (sourceImageArray[row][col] != background
                        && !visitedPixels[row][col]) {
                    startInitSilhouettes(allSilhouettes, sourceImageArray, visitedPixels, row, col, background);
                }
            }
        }

        return allSilhouettes;
    }

    /**
     * This method init depth search for anticipated silhouette
     *
     * @param allSilhouettes list of all silhouettes with garbage
     * @param sourceImageArray array with colors
     * @param visitedPixels array of visited pixels
     * @param row position in array
     * @param col position in array
     * @param background color
     */
    private static void startInitSilhouettes(List<Integer> allSilhouettes, int[][] sourceImageArray, boolean[][] visitedPixels, int row, int col, int background) {
        Stack<GPoint> stack = new Stack<>();
        AtomicInteger totalPixelsInGraph = new AtomicInteger(1);
        stack.add(new GPoint(col, row));
        depthFirstSearch(stack, totalPixelsInGraph, sourceImageArray, visitedPixels, background);
        allSilhouettes.add(totalPixelsInGraph.get());
    }

    /**
     * Get pixel and check theirs closest pixels
     *
     * @param stack anticipated pixels of silhouette
     * @param totalPixelsInSilhouette counter for current silhouette
     * @param sourceImageArray array with colors
     * @param visitedPixels array of visited pixels
     * @param background color
     */
    private static void depthFirstSearch(Stack<GPoint> stack, AtomicInteger totalPixelsInSilhouette, int[][] sourceImageArray, boolean[][] visitedPixels, int background) {
        while (!stack.isEmpty()) {
            GPoint checkPixel = stack.pop();
            int row = (int) checkPixel.getY();
            int col = (int) checkPixel.getX();

            if (!visitedPixels[row][col]) {
                visitedPixels[row][col] = true;
                totalPixelsInSilhouette.incrementAndGet();
                checkClosestPixels(stack, sourceImageArray, visitedPixels, background, row, col);
            }
        }
    }

    /**
     *
     * @param stack anticipated pixels of silhouette
     * @param sourceImageArray array with colors
     * @param visitedPixels array of visited pixels
     * @param background color
     * @param row position in array
     * @param col position in array
     */
    private static void checkClosestPixels(Stack<GPoint> stack, int[][] sourceImageArray, boolean[][] visitedPixels, int background, int row, int col) {
        int previousRow = Math.max(0, row - 1);
        int nextRow = Math.min(sourceImageArray.length - 1, row + 1);
        int previousCol = Math.max(0, col - 1);
        int nextCol = Math.min(sourceImageArray[0].length - 1, col + 1);

        //vertical && Horizontal pixels
        checkLinkedPixel(stack, previousRow, col, sourceImageArray, visitedPixels, background);
        checkLinkedPixel(stack, nextRow, col, sourceImageArray, visitedPixels, background);
        checkLinkedPixel(stack, row, previousCol, sourceImageArray, visitedPixels, background);
        checkLinkedPixel(stack, row, nextCol, sourceImageArray, visitedPixels, background);

        //diagonals pixels
        checkLinkedPixel(stack, previousRow, previousCol, sourceImageArray, visitedPixels, background);
        checkLinkedPixel(stack, previousRow, nextCol, sourceImageArray, visitedPixels, background);
        checkLinkedPixel(stack, nextRow, previousCol, sourceImageArray, visitedPixels, background);
        checkLinkedPixel(stack, nextRow, nextCol, sourceImageArray, visitedPixels, background);
    }

    /**
     * Check if closest linked pixel is not background and not artifact, add it to stack
     */
    private static void checkLinkedPixel(Stack<GPoint> stack, int row, int col, int[][] sourceImageArray, boolean[][] visitedPixels, int background) {
        if (!visitedPixels[row][col] && sourceImageArray[row][col] != background) {
            Color color = new Color(sourceImageArray[row][col]);
            if (color.getRed() < getColorDeep() && color.getGreen() < getColorDeep() && color.getBlue() < getColorDeep())
                stack.push(new GPoint(col, row));
        }
    }

    /**
     * We assume that silhouette can't start at the zero point
     *
     * @param sourceImageArray array with colors for each pixels
     * @return color
     */
    private static int getBackground(int[][] sourceImageArray) {
        return sourceImageArray[0][0];
    }

    /**
     * Check if file is image, else return default filename
     */
    private static String checkFileName(String[] args) {
        if (args.length > 0) {
            String firstParam = args[0];
            if (firstParam.matches("(\\S+(\\.(?i)(jpe?g|png|gif|bmp))$)")) {
                return firstParam;
            }
        }
        return DEFAULT_IMG;
    }

    /**
     * Change deep param depends on silhouette simple with one tone or not
     * if simple remove more artifacts
     */
    private static void setColorDeep() {
        if (isSilhouettePlain()) {
            setColorDeep((int) (255 * ARTEFACT_COEFFICIENT));
        } else setColorDeep(255);
    }

    private static int getColorDeep() {
        return colorDeep;
    }

    public static void setColorDeep(int deep) {
        colorDeep = deep;
    }

    private static boolean isSilhouettePlain() {
        return isSilhouettePlain;
    }
}