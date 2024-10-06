package com.shpp.p2p.cs.anemeritskyy.assignment12;

import acm.graphics.GImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This program find count of silhouette for inputted file using static method main and args[0] with the filename
 * recommend add -Xss512M or above in VM options for increase memory
 */
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
     * Recommended 0.65 for remove artefacts caused by loss of quality during storage
     */
    private final static double ARTEFACT_COEFFICIENT = 0.65;
    /**
     * Saturation of a pixel taken as a silhouette
     */

    /**
     * Color of background in black-white
     */
    private static Color backgroundColorBW;
    public static int TEST_EXPECTED;

    /**
     * This method find count of silhouette for inputted file, and show result in the console
     *
     * @param args array with filename in args[0]
     */
    public static void main(String[] args) {
        String sourceFileName = checkFileName(args);
        List<Integer> allSilhouettes = findSilhouettes(sourceFileName);
        removeGarbage(allSilhouettes);
        TEST_EXPECTED = allSilhouettes.size();
        System.out.println("Count of silhouette(s): " + allSilhouettes.size());
    }

    /**
     * Remove garbage after processing of detection
     * it removes all than less in GARBAGE_COEFFICIENT times
     *
     * @param allSilhouettes list silhouettes with count of pixels, with garbage
     */
    private static void removeGarbage(List<Integer> allSilhouettes) {
        allSilhouettes.sort((o1, o2) -> o2 - o1); // sort from largest to small
        if (!allSilhouettes.isEmpty()) {
            int maxObject = allSilhouettes.getFirst();
            allSilhouettes.removeIf(pixelsInSilhouettes -> pixelsInSilhouettes < maxObject * GARBAGE_COEFFICIENT / 100);
        }
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
        setBackgroundColorBW(sourceImageArray);

        for (int row = 0; row < sourceImageArray.length; ++row) {
            for (int col = 0; col < sourceImageArray[row].length; ++col) {
                if (pixelToBW(sourceImageArray[row][col]) != getBackgroundColorBW()
                        && !visitedPixels[row][col]) {
                    startInitSilhouettes(allSilhouettes, sourceImageArray, visitedPixels, row, col);
                }
            }
        }

        return allSilhouettes;
    }

    /**
     * This method init depth search for anticipated silhouette
     * and add all what were found to list of silhouettes
     *
     * @param allSilhouettes   list of all silhouettes with garbage
     * @param sourceImageArray array with colors
     * @param visitedPixels    array of visited pixels
     * @param row              position in array
     * @param col              position in array
     */
    private static void startInitSilhouettes(List<Integer> allSilhouettes, int[][] sourceImageArray, boolean[][] visitedPixels, int row, int col) {
        AtomicInteger totalPixelsInGraph = new AtomicInteger(1);
        depthFirstSearch(row, col, totalPixelsInGraph, sourceImageArray, visitedPixels);
        allSilhouettes.add(totalPixelsInGraph.get());
    }

    /**
     * Get pixel and check theirs closest pixels
     *
     * @param sourceImageArray        array with colors
     * @param visitedPixels           array of visited pixels
     * @param row                     position in array
     * @param col                     position in array
     * @param totalPixelsInSilhouette count of pixels in current silhouette
     */
    private static void depthFirstSearch(int row, int col, AtomicInteger totalPixelsInSilhouette, int[][] sourceImageArray, boolean[][] visitedPixels) {
        if (!visitedPixels[row][col]) {
            visitedPixels[row][col] = true;
            Color currentPixelBW = pixelToBW(sourceImageArray[row][col]);
            if (currentPixelBW != backgroundColorBW)
                checkClosestPixels(sourceImageArray, visitedPixels, row, col, totalPixelsInSilhouette);
        }
    }

    /**
     * Check if closest pixels are part of current silhouette
     *
     * @param sourceImageArray        array with colors
     * @param visitedPixels           array of visited pixels
     * @param row                     position in array
     * @param col                     position in array
     * @param totalPixelsInSilhouette count of pixels in current silhouette
     */
    private static void checkClosestPixels(int[][] sourceImageArray, boolean[][] visitedPixels, int row, int col, AtomicInteger totalPixelsInSilhouette) {
        int previousRow = Math.max(0, row - 1);
        int nextRow = Math.min(sourceImageArray.length - 1, row + 1);
        int previousCol = Math.max(0, col - 1);
        int nextCol = Math.min(sourceImageArray[0].length - 1, col + 1);

        checkLinkedPixel(row, col, nextRow, col, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // bottom
        checkLinkedPixel(row, col, row, nextCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // next column
        checkLinkedPixel(row, col, previousRow, previousCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // top left diagonal
        checkLinkedPixel(row, col, nextRow, nextCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // right bottom diagonal
    }

    /**
     * Check closest non-visited pixels with same color
     */
    private static void checkLinkedPixel(int previousRow, int previousCol, int nextRow, int nextCol, int[][] sourceImageArray, boolean[][] visitedPixels, AtomicInteger totalPixelsInSilhouette) {
        if (!visitedPixels[nextRow][nextCol] &&
                pixelToBW(sourceImageArray[nextRow][nextCol]) ==
                        pixelToBW(sourceImageArray[previousRow][previousCol])) { // if the same color
            totalPixelsInSilhouette.incrementAndGet();
            depthFirstSearch(nextRow, nextCol, totalPixelsInSilhouette, sourceImageArray, visitedPixels);
        }
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
     * Change pixel to white-black using ARTEFACT_COEFFICIENT for color deep
     *
     * @param pixel current pixel
     * @return color of current pixel
     */
    private static Color pixelToBW(int pixel) {
        //for transparent
        int alpha = (pixel >> 24) & 0xff;
        if (alpha == 0)
            return Color.WHITE;

        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = pixel & 0xff;

        int avg = (red + green + blue) / 3;

        int deep = (int) (255 * ARTEFACT_COEFFICIENT); // color deep
        return avg > deep ? Color.WHITE : Color.BLACK;
    }

    /**
     * Get background color
     *
     * @return background color
     */
    public static Color getBackgroundColorBW() {
        return backgroundColorBW;
    }

    /**
     * Check background of current image and set color for it
     *
     * @param sourceImageArray array of colors for inputted image
     */
    public static void setBackgroundColorBW(int[][] sourceImageArray) {
        backgroundColorBW = pixelToBW(sourceImageArray[0][0]);
    }
}