package com.shpp.p2p.cs.anemeritskyy.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.Arrays;

public class NameSurferEntry implements NameSurferConstants {
    /**
     * Name for current entry
     */
    private final String name;

    /**
     * Storage of decades saved in order from START_DECADE
     */
    private final int[] decades;

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        this.name = line.substring(0, line.indexOf(" "));
        String[] parseDecades = line.substring(line.indexOf(" ") + 1).split(" ");
        this.decades = new int[parseDecades.length];
        for (int i = 0; i < parseDecades.length; i++) {
            this.decades[i] = Integer.parseInt(parseDecades[i]);
        }
    }

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        return this.decades[decade];
    }

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    @Override
    public String toString() {
        return "NameSurferEntry{" +
                "name='" + name + '\'' +
                ", decades=" + Arrays.toString(decades) +
                '}';
    }
}

