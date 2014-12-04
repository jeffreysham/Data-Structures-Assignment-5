import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * WordSort.java
 * HW5 file for CS 226, Fall 2014.
 *
 * Code completed by:
 * @author Jeffrey Sham JHED: jsham2
 * @author Tyler Lee JHED: tlee93
 * @author Alwin Hui JHED: ahui5
 */

/**
 * This class is used to display the capabilities of a directed graph. This
 * class takes in a file input with a sorted list, which represents a
 * dictionary, and another file input with a list, which represents an unsorted
 * list of words, and outputs a file output with a list which sorts the unsorted
 * list based on the given dictionary "alphabetization".
 *
 */
public final class WordSort {

    /**
     * The graph to hold each letter in order. Second field, data, is arbitrary.
     */
    private static JHUGraph<String, Integer> graph;

    /** The list to hold the dictionary terms. */
    private static List<String> createdDictionary;

    /**
     * The graph to hold each of the input words in order. Second field, data,
     * is arbitrary.
     */
    private static JHUGraph<String, Integer> sortedGraph;

    /**
     * The boolean to decide if the sorting is finished. This is used to ensure
     * it does not loop back and repeatedly sort the given list.
     */
    private static boolean sortedComplete = false;

    /** The number of arguments that the main method should take in. */
    private static final int NUMBER_OF_ARGUMENTS = 3;

    /** Empty private constructor. */
    private WordSort() {

    }

    /**
     * This method creates the dictionary directed graph based on an input list
     * of words.
     *
     * @param dictionary
     *            is the list of Strings of words inputted.
     */
    private static void createDictionaryGraph(List<String> dictionary) {
        String prevToCompare = "";

        boolean moveOnToNext;

        for (String str : dictionary) {
            moveOnToNext = false;

            if (prevToCompare.equals("")) {
                prevToCompare = str;
            } else {
                int prevLength = prevToCompare.length();
                int currLength = str.length();
                int counter = 0;

                while (counter < prevLength && counter < currLength
                        && !(moveOnToNext)) {
                    String prevChar = prevToCompare.substring(counter,
                            counter + 1);
                    String strChar = str.substring(counter, counter + 1);

                    if (!(prevChar.equals(strChar))) {
                        graph.addNewNode(prevChar, -1);
                        graph.addNewNode(strChar, -1);
                        graph.connectVertices(prevChar, strChar);

                        /**
                         * word processed up to first difference, now moving on
                         * to next word.
                         */
                        moveOnToNext = true;
                    }

                    counter++;
                }

                prevToCompare = str;
            }
        }

        createdDictionary = graph.topologicalSort();
    }

    /**
     * This method takes each word in the input list and connects each word with
     * its adjacent word in the list, with the word which comes "alphabetically"
     * before pointing towards the one that comes after.
     *
     * @param inputList
     *            is the input list of words.
     */
    private static void connectVertices(List<String> inputList) {
        String prevToCompare = "";

        boolean moveOnToNext;

        for (String str : inputList) {
            moveOnToNext = false;

            if (prevToCompare.equals("")) {
                prevToCompare = str;
            } else {
                int prevLength = prevToCompare.length();
                int currLength = str.length();
                int counter = 0;

                while (counter < prevLength && counter < currLength
                        && !(moveOnToNext)) {
                    String prevChar = prevToCompare.substring(counter,
                            counter + 1);
                    String strChar = str.substring(counter, counter + 1);

                    if (!(prevChar.equals(strChar))) {

                        sortedGraph.addNewNode(prevToCompare, -1);
                        sortedGraph.addNewNode(str, -1);

                        if (getIndex(createdDictionary, prevChar) < getIndex(
                                createdDictionary, strChar)) {
                            sortedGraph.connectVertices(prevToCompare, str);
                        } else {
                            sortedGraph.connectVertices(str, prevToCompare);
                        }

                        /**
                         * word processed up to first difference, now moving on
                         * to next word.
                         */
                        moveOnToNext = true;
                    }

                    counter++;

                    handleDifferentWordLengths(counter, prevLength,
                            currLength, str, moveOnToNext, prevToCompare);

                }

                prevToCompare = str;
            }
        }
    }

    /**
     * This method handles the case when the
     * unsorted file has words that have different
     * lengths.
     *
     * @param counter the current index
     * @param prevLength the previous word's length
     * @param currLength the current word's length
     * @param str the current word's key
     * @param moveOnToNext boolean for if the word has been processed
     * @param prevToCompare the previous word's key
     */
    public static void handleDifferentWordLengths(int counter,
            int prevLength, int currLength,
            String str, boolean moveOnToNext, String prevToCompare) {
        if (counter == prevLength && !(moveOnToNext)) {
            sortedGraph.addNewNode(prevToCompare, -1);
            sortedGraph.addNewNode(str, -1);

            sortedGraph.connectVertices(prevToCompare, str);
        } else if (counter == currLength && !(moveOnToNext)) {
            sortedGraph.addNewNode(prevToCompare, -1);
            sortedGraph.addNewNode(str, -1);

            sortedGraph.connectVertices(str, prevToCompare);
        }
    }



    /**
     * This method sorts the given input list based on the current dictionary.
     *
     * @param inputList
     *            is the inputted unsorted list to be sorted.
     * @return a list of strings which is already sorted, with null values
     *         separating each word. Also returns null if the dictionary is not
     *         properly initialized already.
     */
    private static List<String> sortInputList(List<String> inputList) {

        connectVertices(inputList);

        List<String> subList = sortedGraph.topologicalSort();

        while (subList.size() > 1) {

            int indexOfNull = getIndex(subList, "NULL");

            while ((indexOfNull != 1) && !(sortedComplete)) {
                if (indexOfNull != -1) {
                    sortInputList(subList.subList(0, indexOfNull));
                } else {
                    sortedComplete = true;
                    sortInputList(subList);

                }
                indexOfNull = getIndex(subList, "NULL");
            }

            subList = subList.subList(2, subList.size());
        }

        return sortedGraph.topologicalSort();
    }

    /**
     * This method gets the index of a particular string based on the input
     * list.
     *
     * @param listToCheck
     *            is the list in which to search for the given string.
     * @param toCheck
     *            is the string to search for.
     * @return the index at which the string is found.
     */
    private static int getIndex(List<String> listToCheck, String toCheck) {
        for (int i = 0; i < listToCheck.size(); i++) {
            if ((listToCheck.get(i) == null) && (toCheck.equals("NULL"))) {
                return i;
            } else if ((listToCheck.get(i) != null)
                    && (listToCheck.get(i)).equals(toCheck)) {
                return i;
            }

        }
        return -1;
    }

    /**
     * This method will create the dictionary and then sort the given unsorted
     * list.
     *
     * @param unsortName
     *            is the unsorted file name
     * @param dict
     *            is the list of characters in the dictionary in order
     * @param unsort
     *            is the unsorted list of words
     * @return a list of strings in sorted order
     */
    private static List<String> createAndSort(String unsortName,
            List<String> dict, List<String> unsort) {
        createDictionaryGraph(dict);

        System.out.print("File processing of dictionary complete. ");
        System.out.println("Now processing the given unsorted file");
        System.out.println("\t" + unsortName + "...\n");

        System.out.print("File processing of unsorted file complete. ");
        System.out.println("Now sorting the unsorted file...\n");

        if (createdDictionary == null) {
            System.out.println("Error: Dictionary not initialized yet.");
            return null;
        } else {
            return sortInputList(unsort);
        }

    }

    /**
     * This is the main method that runs the program. It takes in three
     * arguments, which represent the file name where the dictionary is stored,
     * the file name where the unsorted list is stored, and the file name where
     * the sorted list is stored, respectively. It then calls the other methods
     * to sort the unsorted list based on the given dictionary and outputs it to
     * the unsorted list file.
     *
     * @param args
     *            are the three file names
     */
    public static void main(String[] args) {
        System.out.println("Running the WordSort program...\n");
        System.out.print("=====================");
        System.out.print("=====================");
        System.out.print("=====================");
        System.out.println("=====================\n");

        if (args.length != NUMBER_OF_ARGUMENTS) {
            System.out.println("Enter 3 command line arguments.");
        } else {
            String wordDictionary = args[0];
            String unsortedFile = args[1];
            String sortedFile = args[2];

            System.out.println("Processing the file \n\t" + wordDictionary
                    + "...\n");

            List<String> dictionary = new ArrayList<String>();
            List<String> unsortedList = new ArrayList<String>();

            graph = new JHUGraph<String, Integer>(true);
            sortedGraph = new JHUGraph<String, Integer>(true);

            Scanner scanDictionary, scanList;

            try {
                scanDictionary = new Scanner(new FileReader(wordDictionary));

                String word;

                while (scanDictionary.hasNext()) {
                    word = scanDictionary.next();
                    dictionary.add(word);
                }

                scanDictionary.close();

                scanList = new Scanner(new FileReader(unsortedFile));

                while (scanList.hasNext()) {
                    word = scanList.next();
                    unsortedList.add(word);
                }

                scanList.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            List<String> sortedList = createAndSort(unsortedFile, dictionary,
                    unsortedList);

            BufferedWriter bw = null;

            try {
                File outputFile = new File(sortedFile);

                // if file doesn't exists, then create it
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }

                FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
                bw = new BufferedWriter(fw);

                for (int i = 0; i < sortedList.size(); i = i + 2) {
                    bw.write(sortedList.get(i));
                    bw.newLine();
                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.print("=====================");
            System.out.print("=====================");
            System.out.print("=====================");
            System.out.println("=====================\n");

            System.out.println("Using the given dictionary \n\t"
                    + wordDictionary
                    + ", \nwe have sorted the following unsorted file \n\t"
                    + unsortedFile + "\ninto the following sorted file \n\t"
                    + sortedFile + "\n");
        }
    }
}
