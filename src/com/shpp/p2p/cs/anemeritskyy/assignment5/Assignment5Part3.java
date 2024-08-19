package com.shpp.p2p.cs.anemeritskyy.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Game in road
 * <p>
 * In this exercise, you need to write a program that asks the user for a string
 * of three letters and then outputs the words that can be made up of those letters.
 */
public class Assignment5Part3 extends TextProgram {
    /**
     * Path to dictionary source file
     */
    private final static String DICTIONARY_FILE = "src/com/shpp/p2p/cs/anemeritskyy/assignment5/assets/en-dictionary.txt";
    /**
     * Count of chars for comparison
     */
    private final static int CHAR_COUNT = 3;

    /**
     * Start method, requests the initial line from user
     */
    @Override
    public void run() {
        List<String> wordList;
        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            wordList = getWordList(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            String inputWord = readLine("Write line: ").toLowerCase();
            inputWord = getOnlyChars(inputWord);
            if (inputWord.length() == CHAR_COUNT) {
                List<String> findWords = findWords(wordList, inputWord);
                if (!findWords.isEmpty()) {
                    println(findWords);
                } else {
                    println("There are no matching words");
                }
            } else {
                println("Write another word who contains only " + CHAR_COUNT + " chars");
            }
        }
    }


    /**
     * Extract only symbols a-z
     *
     * @param inputWord user input line
     * @return only chars a-z
     */
    private String getOnlyChars(String inputWord) {
        return inputWord.replaceAll("[^a-z]", "");
    }

    /**
     * Create list with all words from dictionary
     *
     * @param reader buffer reader of dictionary
     * @return all words from file in array
     * @throws IOException input-output error while reading the line
     */
    private List<String> getWordList(BufferedReader reader) throws IOException {
        List<String> wordList = new ArrayList<>();
        String word;
        while ((word = reader.readLine()) != null)
            wordList.add(word);
        return wordList;
    }

    /**
     * Check all words with input chars
     *
     * @param wordList  array of words from dictionary
     * @param inputWord chars from input data
     * @return list of comparing words
     */
    private List<String> findWords(List<String> wordList, String inputWord) {
        List<String> words = new ArrayList<>();
        for (String word : wordList) {
            if (checkCurrentWord(word, inputWord))
                words.add(word.toUpperCase());
        }
        return words;
    }

    /**
     * Check word from dictionary with inputted chars
     *
     * @param word      from dictionary
     * @param inputWord chars from user
     * @return result if the symbols and their order match
     */
    private boolean checkCurrentWord(String word, String inputWord) {
        int coincidence = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.length() >= inputWord.length() &&
                    word.charAt(i) == inputWord.charAt(coincidence))
                coincidence++;
            if (coincidence == CHAR_COUNT) return true;
        }
        return false;
    }
}