package com.shpp.p2p.cs.anemeritskyy.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NameSurferDataBase implements NameSurferConstants {

    /**
     * Storage for every name with entry for it
     */
    private final Map<String, NameSurferEntry> namesDB = new HashMap<>();

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            fillInstance(reader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fill namesDB-map from reader
     *
     * @param reader buffered reader for data file
     */
    private void fillInstance(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String name = line.substring(0, line.indexOf(" ")).toLowerCase();
            NameSurferEntry nameDetails = new NameSurferEntry(line);
            namesDB.put(name, nameDetails);
        }
    }

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        return namesDB.get(name.toLowerCase());
    }
}

