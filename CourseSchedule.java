import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to display the capabilities
 * of a directed graph. This class takes file input with courses
 * and their prereqs, and output a course schedule
 * with the minimum number of terms.
 *
 * @author Jeffrey Sham JHED: jsham2
 * @author Tyler Lee JHED: tlee93
 * @author Alwin Hui JHED: ahui5
 */
public final class CourseSchedule {

    /** The graph to hold the courses and prereqs.
     *  Second field, data, is arbitrary.
     */
    public static JHUGraph<String, Integer> graph;

    /** Empty private constructor.
     */
    private CourseSchedule() {

    }

    /** This method runs topological sort on the graph.
     *  Then it prints the courses in order of semester eligible.
     */
    public static void printSchedule() {

        int count = 1;


        List<String> sortedList = new ArrayList<String>();
        sortedList = graph.topologicalSort();

        if (sortedList.size() > 0) {
            System.out.println("Semester 1:");
        }

        for (int traverse = 0; traverse < sortedList.size(); traverse++) {
            if (sortedList.get(traverse) == null) {
                count++;
                System.out.println("Semester " + count + ":");
            } else {
                System.out.println(sortedList.get(traverse));
            }
        }

        System.out.println("Total number of semesters required: " + count);
    }

    /**
     * This method retrieves the data from the file
     * and the prereqs point to the course for which they are required.
     * @param stream the InputStream for the text file
     */
    public static void parseInput(InputStream stream) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(stream));

            String stringToMatch;
            while ((stringToMatch = br.readLine()) != null) {
                String regex = "(\\W)";
                String[] courses = stringToMatch.split(regex);

                graph.addNewNode(courses[0], courses.length - 1);

                for (int i = 1; i < courses.length; i++) {
                    graph.addNewNode(courses[i], courses.length - i);
                    graph.connectVertices(courses[i], courses[0]);
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * This is the main method that runs the program.
     * It takes one argument, the file name of the people
     * and calls the other methods to find optimal schedule.
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

            graph = new JHUGraph<String, Integer>(true);

            parseInput(inputStream);

            printSchedule();
        }
    }
}
