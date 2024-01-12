import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * An implementation of Prim's Algorithm for 
 * MST.  
 * 
 * @author rosaleaj
 *
 */
public class Prims {
    /**
     * List of words for the Prim's algorithm. Words can be loaded
     * by calling the load method that lives in this class
     */
    private static ArrayList<Node> wordsList;
    
    /**.
     * Load the strings into an ArrayList from the file path. Called
     * load to fit style
     * 
     * @param file Path to the file from where the information is
     * 
     * @return an ArrayList of Strings loaded from the data file
     * 
     * @throws IOException load throws exceptions on errors
     */
    private static ArrayList<Node> load(String file) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(file));
        // get data from each line of file
        while (scanner.hasNextLine()) {
            // grab each line to next line in file
            String inputLine = scanner.nextLine();
            Scanner line = new Scanner(inputLine);
            String word = line.next();
            Node node = new Node(word);
            line.close();
        }
        scanner.close();
        
        // assert that the words list is not empty
        assert (wordsList != null);
        
        // return the list of words
        return wordsList;
    }
    
    /**.
     * Get the distance or edge weights between word 1 and word 2 using the 
     * following formula:
     * EdgeCost = 1 / (1 + Distance) 
     * Distance is calculated in the getDistance function found in
     * this class
     * 
     * @param word1 is a string and is the word being compared to word2
     * 
     * @param word2 is also a string and is the word being compared to words1
     * 
     * @return double that is the edge cost between the two vertices.
     */
    public static double getEdgeCost(String word1, String word2) {
        // calculate the edge cost using the formula: 
        // EdgeCost = 1 / (1 + Distance)
        return 1 / (1 + getDistance(word1, word2));
    }
    
    /**.
     * Calculates Distance
     * 
     * @param word1 is a string and is the word being compared to word2
     * 
     * @param word2 is also a string and is the word being compared to words1
     * 
     * @return int that is the number of 2-letter substrings that occur
     */
    public static double getDistance(String word1, String word2) {
        // define distance to be used later
        int distance = 0;
        // first loop traverses the first word 2 letters at a time
        for (int index1 = 0; index1 < word1.length() - 2; index1++) {
            // second loop traverses the second word 2 letters at a time
            for (int index2 = 0; index2 < word1.length() - 2; index2++) {
                // word1Letters are the two letters from word1
                String word1Letters = word1.substring(index1, index1 + 2);
                // word2Letters are the two letters from word2
                String word2Letters = word2.substring(index2, index2 + 2);
                // checks if the given pair of words match, if so increment 
                // distance by 1
                if (word1Letters.equalsIgnoreCase(word2Letters)) {
                    distance++;
                }
            }
        }
        return distance;
    }
    
    /**.
     * Implement prims algorithm
     * 
     * @return miniumum spanning tree
     */
    public static ArrayList<Node> prim() {
        // foreach (v belongs to V) a[v] <- infinity
        // Initialize an empty priority queue Q
        
        PriorityQueue<Node> tempEdgeWeights = new PriorityQueue<Node>();
        for (Node node: wordsList) {
            tempEdgeWeights.add(node);
        }
        
        ArrayList<Node> minSpanTree = new ArrayList<Node>();
        
        // whilE (Q is not empty)
        while (!tempEdgeWeights.isEmpty()) {
            // u <- delete min element from Q
            Node u = tempEdgeWeights.remove();
            minSpanTree.add(u);
            // foreach (edge e = (u, v) incident to u)
            for (Node v: tempEdgeWeights) {
                double distance = getEdgeCost(u.value, v.value);
                // if ((v not belongs to S) and (ce < a[v]))
                if (!minSpanTree.contains(v) && distance < v.priority) {
                    // decrease priority a[v] to ce
                    v.priority = distance;
                    v.parent = u;
                }
            }
        }
        return minSpanTree; 
    }
    
    /**.
     * Print out the necessary information for graphiz
     * 
     * @param minSpanTree is an ArrayList filled with Nodes
     */
    public static void print(ArrayList<Node> minSpanTree) {
        System.out.println("diagraph");
        System.out.println("subgraph cluster_0 {");
        for (Node node: minSpanTree) {
            System.out.println(" \"" + node.value + "\" -> \"");
            System.out.print(node.parent + "\" [label=\"");
            System.out.print(node.priority + "\"]\n");
        }
        System.out.println("}");
    }

    /**.
     * The main method uses a text file, name specified by the command-line
     * arguments, to create a MST
     * 
     * @param args The command-line arguments 
     * @throws IOException throws an exception
     */
    public static void main(String[] args) throws IOException {
        // call load to initialize wordsList to the words in the file
        wordsList = load(args[0]);
        
        // initialize k to the second command-line argument
        int k = Integer.parseInt(args[1]);
        
        ArrayList<Node> mst = prim();
        print(mst);
    }

}
