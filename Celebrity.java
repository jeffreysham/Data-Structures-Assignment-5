import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to display the capabilities
 * of a graph. This class takes file input with pairs
 * of numbers, which represent people, and output who
 * the celebrity is (the person that everyone knows,
 * but knows no one).
 *
 * @author Jeffrey Sham JHED: jsham2
 * @author Tyler Lee JHED: tlee93
 * @author Alwin Hui JHED: ahui5
 */
public final class Celebrity {

    /** The graph to hold the people.
     */
    public static JHUGraph<Integer, Integer> graph;

    /** The number of people.
     */
    public static int numOfPeople;

    /** Empty private constructor.
     */
    private Celebrity() {

    }

    /** This method finds the celebrity and
     * prints them out. The algorithm takes
     * advantage of the fact that everyone knows
     * the celebrity.
     */
    public static void findCelebrity() {
        boolean found = false;

        int startIndex = 0, endIndex = numOfPeople - 1;

        while (!found) {
            if (graph.isAdjacent(startIndex, endIndex)) {
                //if the person at the start index
                //knows the person at the end index,
                //the person at the start index is not
                //the celebrity
                startIndex++;
            } else {
                //if the person at the start index does not
                //know the person at the end index,
                //the person at the end index is not
                //the celebrity
                endIndex--;
            }

            if (startIndex == endIndex) {
                found = true;
            }
        }
        System.out.println("The celebrity is Person " + startIndex + ".");
    }

    /**
     * This method retrieves the data from the file
     * and inputs the people and who they know into the
     * graph.
     * @param stream the InputStream for the text file
     */
    public static void parseInput(InputStream stream) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(stream));

            String stringToMatch;

            stringToMatch = br.readLine();

            numOfPeople = Integer.parseInt(stringToMatch);

            while ((stringToMatch = br.readLine()) != null) {
                Matcher m =
                        Pattern.compile("\\d+\\s").matcher(stringToMatch + " ");

                MyJHUSet<Integer> set = new MyJHUSet<Integer>();

                while (m.find()) {
                    String tempString = m.group(0).trim();
                    set.add(Integer.parseInt(tempString));
                }

                int tempKey = 0;

                if (set.size() == 2) {
                    tempKey = set.get(0);
                    graph.addNewNode(tempKey, -1);
                    graph.addNewNode(set.get(1), -1);
                    graph.connectVertices(tempKey, set.get(1));
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * This is the main method that runs the program.
     * It takes one argument, the file name of the people
     * and calls the other methods to find the celebrity.
     * @param args the file name
     */
    public static void main(String[] args) {
        String fileName;

        if (args.length != 1) {
            System.out.println("Enter 1 command line argument.");
        } else {
            fileName = args[0];
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            graph = new JHUGraph<Integer, Integer>(true);

            parseInput(inputStream);

            findCelebrity();
        }
    }
}
