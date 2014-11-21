import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Celebrity {

    public static JHUGraph<Integer, Integer> graph;
    public static int numOfPeople;

    public static void findCelebrity() {
        boolean found = false;

        int startIndex = 0, endIndex = numOfPeople - 1;

        while(!found) {
            if (graph.isAdjacent(startIndex, endIndex)) {
                startIndex++;
            } else {
                endIndex--;
            }

            if(startIndex == endIndex) {
                found = true;
            }
        }
        System.out.println("The celebrity is Person " + startIndex + ".");
    }

    public static void parseInput(InputStream stream) {
        try{
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(stream));

            String stringToMatch;

            stringToMatch = br.readLine();

            numOfPeople = Integer.parseInt(stringToMatch);

            while((stringToMatch = br.readLine()) != null){
                Matcher m = Pattern.compile("\\d+\\s").matcher(stringToMatch + " ");

                boolean matched = false;
                MyJHUSet<Integer> set = new MyJHUSet<Integer>();

                while (m.find()) {
                    matched = true;

                    String tempString = m.group(0).trim();
                    set.add(Integer.parseInt(tempString));
                }

                int tempKey = set.get(0);

                graph.addNewNode(tempKey, -1);

                for (int i = set.size() - 1; i > 0; i--) {
                    graph.addNewNode(set.get(i), -1);
                    graph.connectVertices(tempKey, set.get(i));
                }

                if (!matched) {
                    System.out.println("NOT matched!");
                }
            }
        } catch(IOException io){
            io.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName;

        if (args.length != 1) {
            System.out.println("Enter 1 command line argument.");
        } else {
           fileName = args[0];
           InputStream inputStream = null;
           try {
               inputStream = new FileInputStream(fileName);
           } catch(FileNotFoundException e) {
               e.printStackTrace();
           }

           graph = new JHUGraph<Integer, Integer>(true);

           parseInput(inputStream);

           findCelebrity();
        }
    }
}
