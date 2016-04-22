/*
 * Copyright 2016 Jordan Carr
 * File: Dictionary.java
 * Class: gutenberg.Dictionary
 */

package gutenberg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

class Dictionary {

    private static final ArrayList<String> wordList = new ArrayList<>(); //Where all valid words are stored for the dictionary
    private static int wordCount = 0; //Variable to keep track of the number of words that meet the validity check

    /**
     * Takes user input as to the input file and dictionary output directory. Checks Validity of words, removes the
     * duplicate entries, sorts the valid word list and, writes the resultant dictionary to "dictionary.out" in the
     * user specified directory.
     * <br><br>
     * If any exceptions are encountered the error is printed to System.out and the program exits with error code "1"
     *
     * @param args There are no args for this program
     */
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Please enter input file: ");
            String inFile = in.nextLine();

            System.out.print("Please enter output directory (the file will be named 'Dictionary.out'): ");
            String outFile = in.nextLine() + "/dictionary.out";
            in.close();

            wordValidity(inFile);
            removeDuplicates();
            Collections.sort(wordList);

            writeFile(wordCount, inFile, outFile);
            System.out.print("Dictionary.out created successfully");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Takes an Array:ist and file location as input and goes through the input file looking for all valid words to
     * add to the ArrayList which stores the words to be included in the dictionary.
     * <br><br>
     * The Criteria for a word being valid is that it is at least 3 characters long and that it begins with a
     * lowercase letter
     *
     * @param fileInput The file to be used as a source of words for the dictionary which is specified by the user
     * @throws java.io.IOException If an IOException is encountered such as the file being unreadable or nonexistant
     * then the method throws the exception which is handled in the main method
     */
    private static void wordValidity(String fileInput) throws java.io.IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader(fileInput));

        String line; //Stores a line from fileInput prior to the validity check
        String word; //Stores a line from fileInput during the validity check

        while ((line = bufferReader.readLine()) != null) {
            Scanner scanner = new Scanner(line);
            while (scanner.hasNext()) {
                word = scanner.next();
                if ((word.length() >= 3) & word.matches("[a-z]*")) {
                    Dictionary.wordList.add(word);
                    wordCount++;
                }
            }
            scanner.close();
        }
        bufferReader.close();
    }

    /**
     * Takes an ArrayList as input which contains the words validated by wordValidity and adds them to a HashSet
     * whereby all duplicate entries are removed and then after clearing the ArrayList, adds the deduplicated list of
     * words to the ArrayList.
     */
    private static void removeDuplicates() {
        HashSet<String> h = new HashSet<>(Dictionary.wordList);
        Dictionary.wordList.clear();
        Dictionary.wordList.addAll(h);
    }

    /**
     * Takes the ArrayList and writes a file to the previously entered output file which contains all the processed
     * words along with the count of words from the input file and how many words are in the fully processed dictionary.
     *
     * @param countInput The count of how many words were in the inputFile
     * @param inputFile  The file to be used as a source of words for the dictionary which is specified by the user
     * @param outputFile The file to be used as an output location for the dictionary which is specified by the user
     * @throws java.io.IOException If an IOException is encountered such as the file being unreadable or nonexistant
     * then the method throws the exception which is handled in the main method
     */
    private static void writeFile(int countInput, String inputFile, String outputFile)
            throws java.io.IOException {
        FileWriter writer = (new FileWriter(outputFile));
        for (String aWordList : Dictionary.wordList) {
            writer.write(aWordList + "\n");
        }
        writer.write("\nThere are " + countInput + " words in " + "\"" + inputFile + "\"" + "\nThere are " +
                             Dictionary.wordList.size() + " words in this dictionary");
        writer.close();
    }
}
