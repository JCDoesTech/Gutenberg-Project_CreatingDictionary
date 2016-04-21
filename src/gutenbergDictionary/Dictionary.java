/*
 * Copyright 2016 Jordan Carr
 * File Name: Dictionary.java
 * Class Name: GutenburgDictionary
 * Description: To take an e-book from gutenburg.ca and, after removing the preamble and credits so that just the
 * text itself remains, create a dictionary of words from the book that are at least three letters long and begins
 * with a lowercase letter.
 */

package gutenbergDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

class GutenburgDictionary {

    private static int wordCount = 0; //Variable to keep track of the number of words that meet the validity check
    private static ArrayList<String> wordList = new ArrayList<>(); //Where all valid words are stored

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Please enter input file: ");
            String inFile = in.nextLine(); //Takes user input from System.in as to where the input is located

            System.out.print("Please enter output directory (the file will be named 'Dictionary.out'): ");
            String outFile = in.nextLine() + "/dictionary.out"; /*Takes input of output directory and appends
                                                                 *dictionary.out as it's filename*/
            in.close();

            wordValidity(wordList, inFile); /*Determines the validity of the words from "file" and if valid adds them
                                             *to "wordList"*/
            removeDuplicates(wordList); //Removes duplicates from wordList
            Collections.sort(wordList); //Sorts wordList alphabetically

            writeFile(wordList, wordCount, inFile, outFile); /*Writes dictionary to file along with count or raw and
                                                              *processed words*/
            System.out.print("Dictionary.out created successfully");

        } catch (Exception e) { //Catches any exceptions and print the error
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void wordValidity(ArrayList<String> wordListInput, String fileInput) throws java.io.IOException {
        BufferedReader bufferReader = new BufferedReader(new FileReader(fileInput));

        String line; //Where each line of text is stored prior to each word's validity check
        String word; //Where each word is store during the validity check for inclusion to the dictionary

        while ((line = bufferReader.readLine()) != null) {/*While there is another line in the document continue
                                                           *looping and set "line" equal to the next line of the file*/
            Scanner scanner = new Scanner(line);
            while (scanner.hasNext()) { //While the line of text store in "line" has another portion continue the loop
                word = scanner.next(); //Stores the word for validity check
                if ((word.length() >= 3) & word.matches("[a-z]*")) { /*Determines if the word meets the at least 3
                                                                      *characters in length and beginning with a
                                                                      *lower case letter requirements*/
                    wordListInput.add(word); //The word is valid so add it to the ArrayList "wordListInput"
                    wordCount++;
                }
            }
            scanner.close();
        }
        bufferReader.close();
    }

    private static void removeDuplicates(ArrayList<String> input) {
        HashSet<String> h = new HashSet<>(input); /*Adds all of wordList to the HashSet resulting in a single instance
                                                   *of each word in the HashSet*/
        input.clear(); //Clears all contents from wordList
        input.addAll(h); //Adds all of the data from HashSet h to wordList which has had all duplicate entries removed
    }

    private static void writeFile(ArrayList<String> wordListInput, int countInput, String inputFile, String outputFile)
            throws java.io.IOException {
        FileWriter writer = (new FileWriter(outputFile));
        for (String aWordList : wordListInput) {
            writer.write(aWordList + "\n"); //writes each word from wordList on a new line
        }
        writer.write("\nThere are " + countInput + " words in " + "\"" + inputFile + "\"" + "\nThere are " +
                             wordListInput.size() + " words in this dictionary");
        writer.close();
    }
}