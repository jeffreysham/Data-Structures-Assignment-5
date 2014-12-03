README-Graph

Data Structures
Assignment 5
Jeffrey Sham JHED: jsham2
Tyler Lee JHED: tlee93
Alwin Hui JHED: ahui5

The Graph class is a class that creates a generic graph with keys and values. In order to
complete this implementation, we utilized a hash map for the adjacency list. This adjacency
list is an array of nodes with each node representing a vertex. Each node also contains 
a pointer that is able to create a linked list of the adjacent vertices. 

Each node/vertex has a variable for data, key, inDegree, and a pointer to an adjacent node.
The data and key are simply Objects in order to make the generic graph work.

In this class there are 2 constructors. The default constructor creates an undirected graph
and the other constructor takes a boolean parameter that corresponds to whether the graph
is directed. 

So, to add a vertex into the graph structure, addNewNode(K key, V data) would be called. 
In this method, it first checks if the node is in the graph already. If it is not, the key 
is hashed, then inserted into the hash map. If there were collisions, it would be 
handled by linear probing. If the load factor (.75) is exceeded, then we rehash the hash
map hash map with double the size. Then all the previous vertices and their linked lists
are added into the new larger hash map (the keys get rehashed as well).

This linked list of adjacent vertices is created by calling the method called 
connectVertices(K keyA, K keyB) where keyA refers to the vertex that is adjacent to keyB's
vertex. This method finds the indices of each of the nodes in the adjacency list. If the
graph is directed, it creates a new vertex with keyB and adds it to the linked list of
keyA. So, keyA is adjacent to keyB, but keyB is not adjacent to keyA. If the graph is 
not directed, it creates new vertices with keyA and keyB and it makes keyA adjacent to keyB
and keyB adjacent to keyA.

One of the main methods of this graph class is topologicalSort(). In order to use this
method, there are 2 helper methods; findInDegrees() and findNodesWithNoIncomingEdges().

For the method findInDegrees(), the adjacency list is looped through and sets all the
indegrees to 0. Then, we loop through the list again, but we look at the linked lists.
We then loop through the linked lists, where we find the corresponding vertex in the 
hash map and increment its inDegree. For the method findNodesWithNoIncomingEdges(), it 
returns a list of vertices with inDegree == 0.

After these two methods are called in topologicalSort(), the algorithm was modeled from
Prof. More's lecture. In short, it utilizes a queue structure and decrements the inDegrees
of the adjacent vertices until a new sorted list is formed with all the vertices. The one
caveat is that this method will not work if there are cycles. In our topologicalSort(), we
add null values to separate levels. In each level, the order does not matter. So, the
sorting is done by level. At the end of the method, a topologically sorted list is returned.