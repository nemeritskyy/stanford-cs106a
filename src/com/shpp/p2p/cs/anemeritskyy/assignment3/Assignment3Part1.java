package com.shpp.p2p.cs.anemeritskyy.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program getting information about aerobic classes during the week
 * and gives a conclusion based on the data
 */

public class Assignment3Part1 extends TextProgram {
    /**
     * Recommended params for cardiovascular health and bloor pressure
     * basic params based on the medic recommendation
     */
    private final static int RECOMMENDED_CARDIOVASCULAR_HEALTH_TIME = 30;
    private final static int RECOMMENDED_BLOOD_PRESSURE_TIME = 40;
    private final static int RECOMMENDED_CARDIOVASCULAR_HEALTH_CLASSES_PER_WEEK = 5;
    private final static int RECOMMENDED_BLOOD_PRESSURE_PER_WEEK = 3;

    @Override
    public void run() {
        getAerobicInfo();
    }

    /**
     * Getting information about aerobic classes during the week
     */
    private void getAerobicInfo() {
        int cardiovascularHealthDays = 0; // more than 30 min, recommended 5 days per week
        int bloodPressureDays = 0; // more than 40 min, recommended 3 days per week

        for (int i = 0; i < 7; ) {
            i++;
            int aerobicTime = readInt("How many minutes did you do on day " + i + "?");

            if (aerobicTime >= RECOMMENDED_BLOOD_PRESSURE_TIME) {
                bloodPressureDays++;
            }

            if (aerobicTime >= RECOMMENDED_CARDIOVASCULAR_HEALTH_TIME) {
                cardiovascularHealthDays++;
            }
        }

        getHealthAdvice(cardiovascularHealthDays, bloodPressureDays);
    }

    /**
     * Print advice report based on user data
     *
     * @param cardiovascularHealthDays - number of days with recommended load
     * @param bloodPressureDays        - number of days with recommended load
     */
    private void getHealthAdvice(int cardiovascularHealthDays, int bloodPressureDays) {
        println("Cardiovascular health:");
        println("\t" + (
                cardiovascularHealthDays >= RECOMMENDED_CARDIOVASCULAR_HEALTH_CLASSES_PER_WEEK ?
                        "Great job! You've done enough exercise for cardiovascular health." :
                        "You needed to train hard for at least " + (RECOMMENDED_CARDIOVASCULAR_HEALTH_CLASSES_PER_WEEK - cardiovascularHealthDays) + " more day(s) a week!"));

        println("Blood pressure:");
        println("\t" + (
                bloodPressureDays >= RECOMMENDED_BLOOD_PRESSURE_PER_WEEK ?
                        "Great job! You've done enough exercise to keep a low blood pressure." :
                        "You needed to train hard for at least " + (RECOMMENDED_BLOOD_PRESSURE_PER_WEEK - bloodPressureDays) + " more day(s) a week!"));
    }
}
