package com.shpp.p2p.cs.anemeritskyy.assignment6.sg;

import acm.graphics.GImage;

import java.awt.*;

public class SteganographyLogic {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int[][] sourceArray = source.getPixelArray();
        boolean[][] secretImage = new boolean[sourceArray.length][sourceArray[0].length];
        for (int i = 0; i < sourceArray.length; i++) {
            for (int j = 0; j < sourceArray[0].length; j++) {
                if (new Color(sourceArray[i][j]).getRed() % 2 != 0) {
                    secretImage[i][j] = true;
                }
            }
        }
        return secretImage;
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int[][] sourceArray = source.getPixelArray();
        int[][] steganographyArray = new int[sourceArray.length][sourceArray[0].length];
        for (int i = 0; i < sourceArray.length; i++) {
            for (int j = 0; j < sourceArray[0].length; j++) {
                steganographyArray[i][j] = modifyColor(sourceArray[i][j], message[i][j]);
            }
        }
        return new GImage(steganographyArray);
    }

    /**
     * Modify default color from image depending on it, it is encoded or not
     *
     * @param sourceColor color before processing
     * @param isBlack     flag when pixel is encrypted
     * @return if the secret pixel is black, represented by true, then you must make the value of the red channel odd
     * if the secret pixel is white, represented by false , then you must make the red channel value even.
     */
    private static int modifyColor(int sourceColor, boolean isBlack) {
        Color currentColor = new Color(sourceColor);
        int modifiedRed = currentColor.getRed();

        if (isBlack) {
            if (modifiedRed % 2 == 0) {
                modifiedRed++;
            }
        } else {
            if (modifiedRed % 2 != 0) {
                modifiedRed--;
            }
        }

        if (modifiedRed < 0 || modifiedRed > 255) {
            modifiedRed = currentColor.getRed();
        }
        return new Color(modifiedRed, currentColor.getGreen(), currentColor.getBlue()).getRGB();
    }
}