import java.util.List;
import java.util.ArrayList;
public class JHUGraph<K, V> {

    /** The rehash multiplying factor.
     */
    private static final int REHASH_MULTIPLYING_FACTOR = 2;

    /** The default initial capacity of the hash map.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    /** The default load factor of the hash map.
     */
    private static final double DEFAULT_LOAD_FACTOR = .5;

    /** The size of the hash map.
     */
    private int size;

    /** The load factor of the hash map.
     */
    private double loadFactor;

    /** The initial capacity of the hash map.
     */
    private int initialCapacity;

    private JHUGraphNode[] adjacencyList;

    private boolean directedGraph;

    public JHUGraph() {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        this.directedGraph = false;
        this.makeGraphHash(initialCapacity);
    }

    public JHUGraph(boolean directed) {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        this.directedGraph = directed;
        this.makeGraphHash(initialCapacity);
    }

    public boolean addNewNode(K key, V data) {

        int indexOfItem = this.findIndex(key);

        if (indexOfItem == -1) {
            int hashValue = key.hashCode() % this.adjacencyList.length;

            if (hashValue < 0) {
                hashValue *= -1;
            }

            boolean inserted = false;

            int index = hashValue;

            while (!inserted) {
                JHUGraphNode tempNode = this.adjacencyList[index];

                if (tempNode == null) {
                    JHUGraphNode node = new JHUGraphNode(data, key);
                    this.adjacencyList[index] = node;
                    this.size++;
                    inserted = true;
                } else {
                    index = (index + 1) % this.adjacencyList.length;
                }

            }

            double tempLoadFactor = (double) this.size / this.adjacencyList.length;

            if (tempLoadFactor >= this.loadFactor) {
                this.rehash();
            }

            return true;

        }

        return false;
    }

    private boolean addOldNode(JHUGraphNode node) {

        int indexOfItem = this.findIndex(node.key);

        if (indexOfItem == -1) {
            int hashValue = node.key.hashCode() % this.adjacencyList.length;

            if (hashValue < 0) {
                hashValue *= -1;
            }

            boolean inserted = false;

            int index = hashValue;

            while (!inserted) {
                JHUGraphNode tempNode = this.adjacencyList[index];

                if (tempNode == null) {
                    this.adjacencyList[index] = node;
                    this.size++;
                    inserted = true;
                } else {
                    index = (index + 1) % this.adjacencyList.length;
                }

            }

            double tempLoadFactor = (double) this.size / this.adjacencyList.length;

            if (tempLoadFactor >= this.loadFactor) {
                this.rehash();
            }

            return true;

        }

        return false;
    }

    public boolean connectVertices(Object keyA, Object keyB) {

        if (keyA.equals(keyB)) {
            return false;
        }

        int indexA = this.findIndex(keyA);
        int indexB = this.findIndex(keyB);

        if (indexA >= 0 && indexB >= 0) {

            JHUGraphNode nodeA = this.adjacencyList[indexA];
            JHUGraphNode nodeB = this.adjacencyList[indexB];

            if (!directedGraph) {
                JHUGraphNode tempNodeA = new JHUGraphNode(nodeA.data, nodeA.key);
                JHUGraphNode tempNodeB = new JHUGraphNode(nodeB.data, nodeB.key);

                JHUGraphNode counterA = nodeA;
                JHUGraphNode counterB = nodeB;

                while (counterA.adjacent != null) {
                    counterA = counterA.adjacent;
                }

                counterA.adjacent = tempNodeB;

                while (counterB.adjacent != null) {
                    counterB = counterB.adjacent;
                }

                counterB.adjacent = tempNodeA;
            } else {
                JHUGraphNode tempNodeB = new JHUGraphNode(nodeB.data, nodeB.key);
                JHUGraphNode counterA = nodeA;

                while (counterA.adjacent != null) {
                    counterA = counterA.adjacent;
                }

                counterA.adjacent = tempNodeB;
            }

            return true;

        }

        return false;
    }

    public List<K> getAdjacents(K key) {
        List<K> list = new ArrayList<K>();
        int index = this.findIndex(key);

        if (index == -1) {
            return list;
        } else {

            JHUGraphNode node = this.adjacencyList[index];

            while(node.adjacent != null) {
                list.add((K) node.adjacent.key);
                node = node.adjacent;
            }

            return list;
        }

    }

    public V find(Object key) {
        if(this.containNode(key)) {

            int value = 0;
            if (key != null) {
                value = key.hashCode() % this.adjacencyList.length;

                if (value < 0) {
                    value *= -1;
                }
            }

            int index = value;
            V data = null;

            do {
                JHUGraphNode tempNode = this.adjacencyList[index];

                if (tempNode == null) {
                    data = null;
                    break;
                } else if (key.equals(tempNode.key)) {
                    data = (V) tempNode.data;
                    break;
                }

                index = (index + 1) % this.adjacencyList.length;

            } while (index != value);

            return data;
        }

        return null;
    }

    public boolean containNode(Object key) {
        int index = this.findIndex(key);
        return !(index == -1);
    }

    private int findIndex(Object key) {
        int value = 0;
        if (key != null) {
            value = key.hashCode() % this.adjacencyList.length;

            if (value < 0) {
                value *= -1;
            }

        }

        int index = value;

        do {
            JHUGraphNode tempNode = this.adjacencyList[index];

            if (tempNode == null) {
                return -1;
            } else if (key.equals(tempNode.key)) {
                return index;
            }

            index = (index + 1) % this.adjacencyList.length;

        } while (index != value);

        return -1;
    }

    public int numberOfVertices() {
        return this.size;
    }

    private void rehash() {
        JHUGraphNode[] tempAdjList = this.adjacencyList;
        this.makeGraphHash(this.adjacencyList.length * JHUGraph.REHASH_MULTIPLYING_FACTOR);

        this.size = 0;

        for (int i = 0; i < tempAdjList.length; i++) {
            JHUGraphNode tempNode = tempAdjList[i];

            if (tempNode == null) {
                continue;
            } else {
                this.addOldNode(tempNode);
            }
        }

    }

    private void makeGraphHash(int s) {
        this.adjacencyList = new JHUGraphNode[s];
    }

    private static final class JHUGraphNode {
        Object data;
        Object key;
        JHUGraphNode adjacent;

        private JHUGraphNode() {
            this.data = null;
            this.key = null;
            this.adjacent = null;
        }

        private JHUGraphNode(Object d, Object k) {
            this.data = d;
            this.key = k;
            this.adjacent = null;
        }

    }

    public static void main(String[] args) {

        JHUGraph<String, Integer> graph = new JHUGraph<String, Integer>(true);

        graph.addNewNode("A", 0);
        graph.addNewNode("B", 0);
        graph.addNewNode("C", 0);
        graph.addNewNode("D", 0);
        graph.addNewNode("E", 0);
        graph.addNewNode("F", 0);
        graph.addNewNode("G", 0);
        graph.addNewNode("H", 0);
        graph.addNewNode("I", 0);
        graph.addNewNode("J", 0);
        graph.addNewNode("K", 0);
        graph.addNewNode("L", 0);
        graph.addNewNode("M", 0);

        graph.connectVertices("A", "B");

        graph.connectVertices("B", "C");
        graph.connectVertices("B", "G");

        graph.connectVertices("G", "D");

        graph.connectVertices("E", "I");

        graph.connectVertices("C", "E");
        graph.connectVertices("C", "H");
        graph.connectVertices("C", "L");

        graph.connectVertices("F", "M");
        graph.connectVertices("F", "J");
        graph.connectVertices("F", "E");

        graph.connectVertices("D", "C");
        graph.connectVertices("D", "F");

        graph.connectVertices("H", "F");

        graph.connectVertices("K", "H");

        graph.connectVertices("I", "M");
        graph.connectVertices("I", "J");

        graph.connectVertices("L", "K");
        graph.connectVertices("L", "M");

        graph.connectVertices("M", "J");

        System.out.println("A :" + graph.getAdjacents("A"));
        System.out.println("B :" + graph.getAdjacents("B"));
        System.out.println("C :" + graph.getAdjacents("C"));
        System.out.println("D :" + graph.getAdjacents("D"));
        System.out.println("E :" + graph.getAdjacents("E"));
        System.out.println("F :" + graph.getAdjacents("F"));
        System.out.println("G :" + graph.getAdjacents("G"));
        System.out.println("H :" + graph.getAdjacents("H"));
        System.out.println("I :" + graph.getAdjacents("I"));
        System.out.println("J :" + graph.getAdjacents("J"));
        System.out.println("K :" + graph.getAdjacents("K"));
        System.out.println("L :" + graph.getAdjacents("L"));
        System.out.println("M :" + graph.getAdjacents("M"));

    }


}
