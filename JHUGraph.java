import java.util.List;
import java.util.ArrayList;

/**
 * This class creates a graph structure. It
 * utilizes a hash map in order to place nodes.
 * The nodes can be connected by calling the
 * connect vertices method. Otherwise, it can
 * function as a normal hash map.
 *
 * @author Jeffrey Sham JHED: jsham2
 * @author Tyler Lee JHED: tlee93
 * @author Alwin Hui JHED: ahui5
 *
 * @param <K>
 * @param <V>
 */
public class JHUGraph<K, V> {

    /** The rehash multiplying factor.
     */
    private static final int REHASH_MULTIPLYING_FACTOR = 2;

    /** The default initial capacity of the adjacency list.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    /** The default load factor of the adjacency list.
     */
    private static final double DEFAULT_LOAD_FACTOR = .75;

    /** The size of the adjacency list.
     */
    private int size;

    /** The load factor of the adjacency list.
     */
    private double loadFactor;

    /** The initial capacity of the adjacency list.
     */
    private int initialCapacity;

    /** The adjacency list for the nodes.
     */
    private JHUGraphNode[] adjacencyList;

    /** Determines if the graph is directed.
     */
    private boolean directedGraph;

    /** This is the empty constructor that sets
     * up the adjacency list.
     */
    public JHUGraph() {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        this.directedGraph = false;
        this.makeGraphHash(this.initialCapacity);
    }

    /**
     * This is the constructor where the
     * directed nature of the graph is a
     * parameter.
     * @param directed true if the graph is directed
     * false otherwise
     */
    public JHUGraph(boolean directed) {
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
        this.directedGraph = directed;
        this.makeGraphHash(this.initialCapacity);
    }

    /**
     * This method adds a new node into the graph.
     *
     * @param key the key of the node
     * @param data the data for the node
     * @return true if successful, false if node
     * already exists
     */
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

            double tempLoadFactor =
                    (double) this.size / this.adjacencyList.length;

            if (tempLoadFactor >= this.loadFactor) {
                this.rehash();
            }

            return true;

        }

        return false;
    }

    /**
     * This method is used to for rehashing the adjacency list.
     * The nodes are added to the new list while retaining the
     * connections between nodes intact.
     *
     * @param node The node to add to the graph
     * @return true if the node was added, false otherwise
     */
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

            double tempLoadFactor =
                    (double) this.size / this.adjacencyList.length;

            if (tempLoadFactor >= this.loadFactor) {
                this.rehash();
            }

            return true;

        }

        return false;
    }

//    public K removeEdgeFromGraph(Object keyA, Object keyB) {
//        return (K) this.removeEdge(keyA, keyB).key;
//    }

    /**
     * This method removes an edge from NodeA to NodeB.
     * @param keyA NodeA's key
     * @param keyB NodeB's key
     * @return the node that was removed.
     */
    private JHUGraphNode removeEdge(Object keyA, Object keyB) {

        int index = this.findIndex(keyA);

        if (index != -1 && !keyA.equals(keyB)) {
            JHUGraphNode current = this.adjacencyList[index];
            JHUGraphNode previous = current;

            while (!current.key.equals(keyB) && current != null) {
                previous = current;
                current = current.adjacent;
            }

            previous.adjacent = current.adjacent;

            return current;

        }

        return null;
    }

    /**
     * This method connects NodeA to NodeB.
     * If directed, NodeA will be adjacent to NodeB.
     * Otherwise, NodeA will be adjacent to NodeB and
     * NodeB will be adjacent to NodeA.
     *
     * @param keyA NodeA's key
     * @param keyB NodeB's key
     * @return true if vertices are connected successfully,
     * false otherwise
     */
    public boolean connectVertices(Object keyA, Object keyB) {

        if (keyA.equals(keyB)) {
            return false;
        }

        int indexA = this.findIndex(keyA);
        int indexB = this.findIndex(keyB);

        if (indexA >= 0 && indexB >= 0) {

            JHUGraphNode nodeA = this.adjacencyList[indexA];
            JHUGraphNode nodeB = this.adjacencyList[indexB];

            if (!this.directedGraph) {
                JHUGraphNode tempNodeA =
                        new JHUGraphNode(nodeA.data, nodeA.key);
                JHUGraphNode tempNodeB =
                        new JHUGraphNode(nodeB.data, nodeB.key);

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
                JHUGraphNode tempNodeB =
                        new JHUGraphNode(nodeB.data, nodeB.key);
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

    /**
     * This method checks if NodeA is adjacent
     * to NodeB.
     *
     * @param keyA NodeA's key
     * @param keyB NodeB's key
     * @return true if adjacent, false otherwise
     */
    public boolean isAdjacent(K keyA, K keyB) {
        List<K> list = this.getAdjacents(keyA);
        return list.contains(keyB);
    }

    /**
     * This method gets the nodes adjacent to
     * the given node's key.
     * @param key the key of the node
     * @return a list of adjacent nodes' keys
     */
    public List<K> getAdjacents(K key) {
        List<K> list = new ArrayList<K>();
        int index = this.findIndex(key);

        if (index == -1) {
            return list;
        } else {

            JHUGraphNode node = this.adjacencyList[index];

            while (node.adjacent != null) {
                list.add((K) node.adjacent.key);
                node = node.adjacent;
            }

            return list;
        }

    }

    /**
     * This method finds the nodes with no incoming edges.
     * @return list of nodes with no incoming edges
     */
    private List<K> findNodesWithNoIncomingEdges() {

        List<K> list = new ArrayList<K>();
        for (int i = 0; i < this.adjacencyList.length; i++) {
            JHUGraphNode tempNode = this.adjacencyList[i];

            if (tempNode == null) {
                continue;
            } else {
                list.add((K) tempNode.key);
            }
        }

        for (int i = 0; i < this.adjacencyList.length; i++) {
            JHUGraphNode tempNode = this.adjacencyList[i];

            if (tempNode == null) {
                continue;
            } else {
                while (tempNode.adjacent != null) {
                    list.remove(tempNode.adjacent.key);
                    tempNode = tempNode.adjacent;
                }
            }
        }

        return list;
    }

    /**
     * Modeled from Prof. More's slides.
     *
     * This method finds all the in-degrees of the nodes.
     */
    private void findInDegrees() {
        for (int i = 0; i < this.adjacencyList.length; i++) {
            JHUGraphNode tempNode = this.adjacencyList[i];

            if (tempNode == null) {
                continue;
            } else {
                tempNode.indegree = 0;
            }
        }

        for (int i = 0; i < this.adjacencyList.length; i++) {
            JHUGraphNode tempNode = this.adjacencyList[i];

            if (tempNode == null) {
                continue;
            } else {
                List<K> adjacent = this.getAdjacents((K) tempNode.key);
                for (int j = 0; j < adjacent.size(); j++) {
                    JHUGraphNode adjNode =
                            this.adjacencyList[this.findIndex(adjacent.get(j))];
                    adjNode.indegree++;
                }

            }
        }

    }


    //Wikipedia for algorithm
    /**
     * Modeled from Wikipedia.
     *
     * This method performs a topological sort of the
     * nodes in the graph.
     *
     * @return list of the keys of the nodes in
     * topological order with "null" values separating
     * the different levels.
     */
    public List<K> topologicalSort() {
        List<K> list = this.findNodesWithNoIncomingEdges();
        List<K> sortedList = new ArrayList<K>();

        int length = list.size();
        int count = 0;

        while (!list.isEmpty()) {

            K tempKey = list.remove(0);


            if (length == count) {
                length += list.size() + 1;
                sortedList.add(null);
            }

            count++;

            int index = this.findIndex(tempKey);

            JHUGraphNode tempNode = null;

            if (index != -1) {
                tempNode = this.adjacencyList[index];


                sortedList.add(tempKey);

                while (tempNode.adjacent != null) {
                    JHUGraphNode tempDeletedEdgeToNode =
                            this.removeEdge(tempKey, tempNode.adjacent.key);

                    if (this.findNodesWithNoIncomingEdges()
                            .contains(tempDeletedEdgeToNode.key)) {
                        //System.out.println("Size in add: " + list.size());

                        list.add((K) tempDeletedEdgeToNode.key);
                    }
                }
            }

        }

        if (count != this.size) {
            throw new CycleFoundException();
        }

        return sortedList;
    }

    /**
     * Modeled from Wikipedia and Prof. More's slides.
     *
     * This method performs a topological sort of the
     * nodes in the graph.
     *
     * This is more efficient than the other
     * topological sort.
     *
     * @return list of the keys of the nodes in
     * topological order with "null" values separating
     * the different levels.
     */
    public List<K> topologicalSort2() {
        List<K> list = this.findNodesWithNoIncomingEdges();
        List<K> sortedList = new ArrayList<K>();
        this.findInDegrees();

        int length = list.size();
        int count = 0;

        while (!list.isEmpty()) {

            K tempKey = list.remove(0);


            if (length == count) {
                length += list.size() + 1;
                sortedList.add(null);
            }

            count++;

            int index = this.findIndex(tempKey);

            JHUGraphNode tempNode = null;

            if (index != -1) {
                tempNode = this.adjacencyList[index];
                sortedList.add(tempKey);

                List<K> tempList = this.getAdjacents(tempKey);
                for (int i = 0; i < tempList.size(); i++) {
                    JHUGraphNode adjNode =
                            this.adjacencyList[this.findIndex(tempList.get(i))];
                    if (--adjNode.indegree == 0) {
                        list.add((K) adjNode.key);
                    }
                }

            }

        }

        if (count != this.size) {
            throw new CycleFoundException();
        }

        return sortedList;
    }

    /**
     * This method finds a node given a key
     * and returns the node's data.
     * @param key the node to look for
     * @return the associated data
     */
    public V find(Object key) {
        if (this.containNode(key)) {

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

    /**
     * This method checks if the graph contains a node.
     * @param key the node to look for
     * @return true if node is in graph, false otherwise
     */
    public boolean containNode(Object key) {
        int index = this.findIndex(key);
        return !(index == -1);
    }

    /**
     * This method finds the index of a node.
     * @param key the key of the node
     * @return the index of the node, -1 if not found
     */
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

    /**
     * The number of vertices in the graph.
     * @return the number of vertices
     */
    public int numberOfVertices() {
        return this.size;
    }

    /**
     * This method rehashes the adjacencyList when the
     * the load factor is exceeded.
     */
    private void rehash() {
        JHUGraphNode[] tempAdjList = this.adjacencyList;
        this.makeGraphHash(this.adjacencyList.length
                * JHUGraph.REHASH_MULTIPLYING_FACTOR);

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

    /**
     * This method creates the hash table for
     * the adjacencyList.
     * @param s the size of the hash table
     */
    private void makeGraphHash(int s) {
        this.adjacencyList = new JHUGraphNode[s];
    }

    /**
     * This class handles the nodes that will be used
     * in the JHUGraph.
     *
     * @author Jeffrey Sham
     *
     */
    private static final class JHUGraphNode {

        /** The data of the node.
         */
        Object data;

        /** The key of the node.
         */
        Object key;

        /** The adjacent node.
         */
        JHUGraphNode adjacent;

        /** The in-degree of the node.
         * (The number of arrows pointing to
         * this node)
         */
        int indegree;

        /**
         * This is the empty constructor that
         * sets up the empty JHUGraphNode.
         */
        private JHUGraphNode() {
            this.data = null;
            this.key = null;
            this.adjacent = null;
            this.indegree = -1;
        }

        /**
         * This is the constructor that
         * sets up the JHUGraphNode with data
         * and a key.
         * @param d data for the node
         * @param k key for the node
         */
        private JHUGraphNode(Object d, Object k) {
            this.data = d;
            this.key = k;
            this.adjacent = null;
            this.indegree = -1;
        }

    }

}
