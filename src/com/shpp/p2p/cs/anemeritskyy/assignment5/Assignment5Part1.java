package com.shpp.p2p.cs.anemeritskyy.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.List;

/**
 * Counting the number of warehouses
 * <p>
 * Count the number of vowels in the word (taking into account y),
 * without:
 * - vowels preceded by other vowels
 * - letter e if it is at the end of the word
 */
public class Assignment5Part1 extends TextProgram {
    private final List<Character> VOWELS = List.of('a', 'e', 'i', 'o', 'u', 'y');

    /**
     * Start program and wait word from user
     */
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("Syllable count: " + syllablesInWord(word));
            println();
        }
    }

    /**
     * Count syllables in word
     *
     * @param word inputted word from user
     * @return count of syllables in current word
     */
    public int syllablesInWord(String word) {
        boolean isPreviousVowel = false;
        int vowelCounter = 0;
        word = (word).toLowerCase();

        for (int i = 0; i < word.length() - 1; i++) {
            if (isPreviousVowel && VOWELS.contains(word.charAt(i)))
                continue;
            if (VOWELS.contains(word.charAt(i))) {
                isPreviousVowel = true;
                vowelCounter++;
            } else {
                isPreviousVowel = false;
            }
        }

        if (checkLastChars(word, isPreviousVowel))
            vowelCounter++;

        return Math.max(vowelCounter, 1); // min 1 syllable
    }

    /**
     * Check last char with 'e' case
     *
     * @param word            inputted word from user
     * @param isPreviousVowel flag show if previous char was vowel
     * @return result of checking can we count this vowel or not
     */
    private boolean checkLastChars(String word, boolean isPreviousVowel) {
        char lastChar = word.charAt(word.length() - 1);
        return VOWELS.contains(lastChar)
                && lastChar != 'e' && !isPreviousVowel;
    }
}