package com.shpp.p2p.cs.anemeritskyy.assignment6.tm;

import java.util.ArrayList;
import java.util.List;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        List<double[]> soundsList = new ArrayList<>();

        for (int i = 0; i < toneMatrix.length; i++) {
            if (toneMatrix[i][column]) {
                soundsList.add(samples[i]);
            }
        }

        if (!soundsList.isEmpty())
            result = unionSoundWave(soundsList);

        return result;
    }

    /**
     * Union all sounds and normalize in the diapason from -1 to 1
     *
     * @param soundsList list of selected sounds
     * @return normalized wave
     */
    private static double[] unionSoundWave(List<double[]> soundsList) {
        double maxRange = 0;
        double[] sumOfWaves = new double[soundsList.get(0).length];

        for (int i = 0; i < soundsList.get(0).length; i++) {
            for (int j = 0; j < soundsList.size(); j++) {
                sumOfWaves[i] += soundsList.get(j)[i];
                if (j == soundsList.size() - 1 && Math.abs(sumOfWaves[i]) > maxRange) // at last sound operation check max range
                    maxRange = Math.abs(sumOfWaves[i]);
            }
        }

        return normalizeWave(sumOfWaves, maxRange);
    }

    /**
     * Normalize wave in the diapason from -1 to 1
     *
     * @param sumOfWaves sum of all sounds
     * @param maxRange   wave upper or lower border for coefficient calculation
     * @return normal wave
     */
    private static double[] normalizeWave(double[] sumOfWaves, double maxRange) {
        double normalizeCoefficient = 1 / maxRange;
        for (int i = 0; i < sumOfWaves.length; i++) {
            sumOfWaves[i] *= normalizeCoefficient;
        }
        return sumOfWaves;
    }
}
