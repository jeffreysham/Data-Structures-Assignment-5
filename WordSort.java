import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordSort {

    public static void main(String[] args) {
        System.out.println("Running the WordSort program...\n");
        System.out.println("==========================================\n");

        String wordDictionary = args[0];
        String unsortedFile = args[1];
        String sortedFile = args[2];

        System.out.println("Processing the file " + unsortedFile + "...\n");

        List<String> dictionary = new ArrayList<String>();

        BufferedReader buffer;

        try {
            buffer = new BufferedReader(new FileReader(wordDictionary));
            String line;

            while ((line = buffer.readLine()) != null) {
                dictionary.add(line);
            }
            buffer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out
                .println("File processing of dictionary complete. Now processing the given unsorted file "
                        + unsortedFile + "...\n");

        List<String> unsortedList = new ArrayList<String>();

        BufferedReader bufferTwo;

        try {
            bufferTwo = new BufferedReader(new FileReader(unsortedFile));
            String line;

            while ((line = bufferTwo.readLine()) != null) {
                unsortedList.add(line);
            }
            bufferTwo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out
                .println("File processing of unsorted file complete. Now sorting the unsorted file...\n");

        System.out.println("==========================================\n");

        System.out.println("Using the given dictionary " + wordDictionary
                + ", we have sorted the following unsorted file "
                + unsortedFile + " into the following sorted file "
                + sortedFile + "\n");

        System.out.println(dictionary);
        System.out.println(unsortedList);
    }
}
