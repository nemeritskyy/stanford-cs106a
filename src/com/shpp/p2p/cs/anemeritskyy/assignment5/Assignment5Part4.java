package com.shpp.p2p.cs.anemeritskyy.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * CSV parser
 */
public class Assignment5Part4 extends TextProgram {
    /**
     * File path to csv source file
     */
    private static final String CSV_FILE_PATH = "src/com/shpp/p2p/cs/anemeritskyy/assignment5/assets/csv/basic.csv";
    /**
     * For current task we need to use comma as separator for csv, but you can use another separator for extraction
     */
    private static final String SEPARATOR_REGEX = ",";
    /**
     * Index of extracted column
     */
    private static final int COLUMN_INDEX = 1;

    /**
     * Pattern for replacing quotes inside field
     */
    private static final String REPLACEMENT_PATTERN = "&&";

    @Override
    public void run() {
        ArrayList<String> result = extractColumn(CSV_FILE_PATH, COLUMN_INDEX);
        if (result != null) {
            result.forEach(System.out::println);
        } else println("null");
    }

    /**
     * Extract and return fields with index from csv file
     *
     * @param filename    path to csv file
     * @param columnIndex index of extracted column
     * @return extracted fields on index
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> fileLines = readFile(filename);
        ArrayList<String> fileFields = new ArrayList<>();
        ArrayList<String> fieldsOnIndex = new ArrayList<>();
        int fieldInRow = 0;

        if (fileLines == null || fileLines.isEmpty())
            return null;

        for (String line : fileLines) {
            ArrayList<String> fieldsInRow = fieldsIn(line);
            fieldInRow = fieldsInRow.size();
            fileFields.addAll(fieldsInRow);
        }

        if (fileFields.size() % fileLines.size() != 0) {
            println(fileFields);
            println("Wrong file format");
            return null;
        }

        for (int i = columnIndex; i < fileFields.size(); i += fieldInRow) {
            fieldsOnIndex.add(fileFields.get(i));
        }

        return fieldsOnIndex;
    }

    /**
     * Read all lines from csv and add to ArrayList
     *
     * @param filename path to csv source file
     * @return null if csv is empty, or List of lines (rows)
     */
    private ArrayList<String> readFile(String filename) {
        ArrayList<String> fileLines = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileLines;
    }

    /**
     * Extract all fields from line
     *
     * @param line row with fields in csv file
     * @return list of already extracted fields
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> fields = new ArrayList<>();
        while (line.contains(SEPARATOR_REGEX)) {
            line = extractField(fields, line);
            if (line.startsWith(SEPARATOR_REGEX))
                line = line.substring(SEPARATOR_REGEX.length());
        }
        if (!line.isEmpty())
            extractField(fields, line);
        return fields;
    }

    /**
     * Extract first field from current line and add to fields list
     *
     * @param fields list of already extracted fields
     * @param line   remainder of previous extraction
     * @return remainder of current extraction
     */
    private String extractField(ArrayList<String> fields, String line) {
        String patternThreeQuotes = "\"\"\"";
        String patternOneQuote = "\"";
        String patternReplacement = REPLACEMENT_PATTERN + "\"";
        line = checkQuotesInField(line);
        if (line.startsWith(patternReplacement)) {
            return getLine(fields, line, patternReplacement, true);
        } else if (line.startsWith(patternThreeQuotes)) {
            return getLine(fields, line, patternThreeQuotes, true);
        } else if (line.startsWith(patternOneQuote)) {
            return getLine(fields, line, patternOneQuote, false);
        }
        int indexOfSeparator = line.indexOf(SEPARATOR_REGEX);
        int endIndex = indexOfSeparator < 0 ? line.length() : indexOfSeparator; // condition for right checking last field
        fields.add(line.substring(0, endIndex));
        return line.substring(endIndex);
    }

    /**
     * Source line before extraction
     *
     * @param fields    list of already extracted fields
     * @param line      source before extraction
     * @param pattern   for current extraction
     * @param addQuotes condition for added quotes after extraction
     * @return remainder of current extraction
     */
    private static String getLine(ArrayList<String> fields, String line, String pattern, boolean addQuotes) {
        line = line.substring(pattern.length()); // remove quotes marker
        String extractedField = line.substring(0, line.indexOf(pattern)).replaceAll(REPLACEMENT_PATTERN, "\"");
        if (addQuotes) {
            fields.add('"' + extractedField + '"'); // add field value with quotes
        } else {
            fields.add(extractedField); // add field value without quotes
        }
        return line.substring(line.indexOf(pattern) + pattern.length()); // remove quotes marker
    }

    /**
     * Check quotes inside field and replace with pattern
     *
     * @param line source line
     * @return line with replacing quotes with pattern && by default
     */
    private static String checkQuotesInField(String line) {
        while (line.contains("\"\"")) {
            line = line.replace("\"\"", REPLACEMENT_PATTERN);
        }
        return line;
    }
}