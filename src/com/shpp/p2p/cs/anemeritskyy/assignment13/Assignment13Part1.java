package com.shpp.p2p.cs.anemeritskyy.assignment13;

import acm.graphics.GImage;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This program find count of silhouette for inputted file using static method main and args[0] with the filename
 * using breadth search
 */
public class Assignment13Part1 {
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
     *
     * @param allSilhouettes   list of all silhouettes with garbage
     * @param sourceImageArray array with colors
     * @param visitedPixels    array of visited pixels
     * @param row              position in array
     * @param col              position in array
     */
    private static void startInitSilhouettes(List<Integer> allSilhouettes, int[][] sourceImageArray, boolean[][] visitedPixels, int row, int col) {
        Queue<Point> queue = new ArrayDeque<>();
        AtomicInteger totalPixelsInGraph = new AtomicInteger(1);
        queue.add(new Point(col, row));
        breadthFirstSearch(queue, totalPixelsInGraph, sourceImageArray, visitedPixels);
        allSilhouettes.add(totalPixelsInGraph.get());
    }

    /**
     * Key method for starting breath search
     *
     * @param queue                   pixels what's this silhouette contains and need to check
     * @param totalPixelsInSilhouette count of pixels in current silhouette
     * @param sourceImageArray        array with colors
     * @param visitedPixels           array of visited pixels
     */
    private static void breadthFirstSearch(Queue<Point> queue, AtomicInteger totalPixelsInSilhouette, int[][] sourceImageArray, boolean[][] visitedPixels) {
        while (!queue.isEmpty()) {
            Point checkPixel = queue.poll();
            int row = (int) checkPixel.getY();
            int col = (int) checkPixel.getX();

            if (!visitedPixels[row][col]) {
                visitedPixels[row][col] = true;
                checkClosestPixels(queue, sourceImageArray, visitedPixels, row, col, totalPixelsInSilhouette);
            }
        }
    }

    /**
     * Check if closest pixels are part of current silhouette
     *
     * @param queue                   pixels what's this silhouette contains and need to check
     * @param sourceImageArray        array with colors
     * @param visitedPixels           array of visited pixels
     * @param row                     position in array
     * @param col                     position in array
     * @param totalPixelsInSilhouette count of pixels in current silhouette
     */
    private static void checkClosestPixels(Queue<Point> queue, int[][] sourceImageArray, boolean[][] visitedPixels, int row, int col, AtomicInteger totalPixelsInSilhouette) {
        int previousRow = Math.max(0, row - 1);
        int nextRow = Math.min(sourceImageArray.length - 1, row + 1);
        int previousCol = Math.max(0, col - 1);
        int nextCol = Math.min(sourceImageArray[0].length - 1, col + 1);

        checkLinkedPixel(queue, row, col, nextRow, col, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // bottom
        checkLinkedPixel(queue, row, col, previousRow, col, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // top
        checkLinkedPixel(queue, row, col, row, previousCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // left
        checkLinkedPixel(queue, row, col, row, nextCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // right
        checkLinkedPixel(queue, row, col, previousRow, previousCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // top left diagonal
        checkLinkedPixel(queue, row, col, previousRow, nextCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // top right diagonal
        checkLinkedPixel(queue, row, col, nextRow, previousCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // bottom left diagonal
        checkLinkedPixel(queue, row, col, nextRow, nextCol, sourceImageArray, visitedPixels, totalPixelsInSilhouette); // // bottom right diagonal
    }

    /**
     * Check if closest linked pixel is not background and not artifact, add it to queue
     *
     * @param queue                   pixels what's this silhouette contains and need to check
     * @param previousRow             position in array for previous pixel
     * @param previousCol             position in array for previous pixel
     * @param nextRow                 position in array for next pixel
     * @param nextCol                 position in array for next pixel
     * @param sourceImageArray        array with colors
     * @param visitedPixels           array of visited pixels
     * @param totalPixelsInSilhouette count of pixels in current silhouette
     */
    private static void checkLinkedPixel(Queue<Point> queue, int previousRow, int previousCol, int nextRow, int nextCol, int[][] sourceImageArray, boolean[][] visitedPixels, AtomicInteger totalPixelsInSilhouette) {
        if (!visitedPixels[nextRow][nextCol] &&
                pixelToBW(sourceImageArray[nextRow][nextCol]) ==
                        pixelToBW(sourceImageArray[previousRow][previousCol])) { // if the same color
            totalPixelsInSilhouette.incrementAndGet();
            queue.offer(new Point(nextCol, nextRow));
        }
    }

    /**
     * Check if file is image, else return default filename
     */
    private static String checkFileName(String[] args) {
        if (args != null && args.length > 0) {
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