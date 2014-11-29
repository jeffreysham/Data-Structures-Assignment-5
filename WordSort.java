import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordSort {

    private static JHUGraph<String, Integer> graph;

    private static List<String> createdDictionary;

    private static JHUGraph<String, Integer> sortedGraph;

    private static boolean sortedComplete = false;

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
                        && moveOnToNext == false) {
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

    private static List<String> sortInputList(List<String> inputList) {
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
                        && moveOnToNext == false) {
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
                }

                prevToCompare = str;
            }
        }

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

    public static void main(String[] args) {
        System.out.println("Running the WordSort program...\n");
        System.out
                .println("===============================================================\n");

        if (args.length != 3) {
            System.out.println("Enter 3 command line arguments.");
        } else {
            String wordDictionary = args[0];
            String unsortedFile = args[1];
            String sortedFile = args[2];

            System.out.println("Processing the file \n\t" + wordDictionary
                    + "...\n");

            List<String> dictionary = new ArrayList<String>();

            graph = new JHUGraph<String, Integer>(true);

            Scanner scanDictionary;

            try {
                scanDictionary = new Scanner(new FileReader(wordDictionary));

                try {
                    String word;

                    while (scanDictionary.hasNext()) {
                        word = scanDictionary.next();
                        dictionary.add(word);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (scanDictionary != null) {
                        scanDictionary.close();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            createDictionaryGraph(dictionary);

            System.out
                    .println("File processing of dictionary complete. Now processing the given unsorted file \n\t"
                            + unsortedFile + "...\n");

            List<String> unsortedList = new ArrayList<String>();

            Scanner scanList;

            try {
                scanList = new Scanner(new FileReader(unsortedFile));

                try {
                    String word;

                    while (scanList.hasNext()) {
                        word = scanList.next();
                        unsortedList.add(word);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (scanList != null) {
                        scanList.close();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sortedGraph = new JHUGraph<String, Integer>(true);

            System.out
                    .println("File processing of unsorted file complete. Now sorting the unsorted file...\n");

            List<String> sortedList = sortInputList(unsortedList);

            BufferedWriter bw = null;

            try {

                File outputFile = new File(sortedFile);

                // if file doesn't exists, then create it
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }

                FileWriter fw = new FileWriter(outputFile.getAbsoluteFile());
                bw = new BufferedWriter(fw);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                for (int i = 0; i < sortedList.size(); i = i + 2) {
                    bw.write(sortedList.get(i) + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out
                    .println("===============================================================\n");

            System.out.println("Using the given dictionary \n\t"
                    + wordDictionary
                    + ", \nwe have sorted the following unsorted file \n\t"
                    + unsortedFile + "\ninto the following sorted file \n\t"
                    + sortedFile + "\n");
        }
    }
}
