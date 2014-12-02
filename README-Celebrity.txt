README-Celebrity

Data Structures
Assignment 5
Jeffrey Sham JHED: jsham2
Tyler Lee JHED: tlee93
Alwin Hui JHED: ahui5

See README-Graph for more information about the graph structure.

For the Celebrity class, we used a graph structure in order to solve the problem. We
chose this structure because it enabled us to easily know if one person knew another
person. So, we used a directed graph structure because while everyone knows the celebrity,
the celebrity knows no one. In order to use the graph structure, we added a node every 
time we encountered a new number/person when parsing the input from the file. Then, 
we connected the first number of the pair of numbers to the second number of the pair.
This succcessfully created a graph structure where the numbers that know other numbers
are adjacent to them. 

In order to find who the celebrity was, we had a method called isAdjacent(K keyA, K keyB)
that took in 2 arguments and returned a boolean, true if the nodes were adjacent, false
otherwise. The graph was made with generics with a key K and a value V, but only the key
was necessary for the implementation of the Celebrity class. For the algorithm of finding
the celebrity, we took advantage of the fact that everyone knows the celebrity. So, since
each person is represented with numbers, we started at 0 and the number of people - 1. This
ensured that we would not encounter an index out of bounds problem. So, if the smaller
index knew (isAdjacent to) the larger index, then we knew that the smaller index was not
the celebrity. This is due to the fact that the celebrity knew no one at the party. We
then would increment the value of the smaller index by 1. If the smaller index did not know
the larger index, then we knew that the larger index was not the celebrity. This is because
everyone knew the celebrity. We then would decrement the larger index by 1. After this
if-else statement, we would repeat the process until the smaller index was equal to the
larger index. With this algorithm, we asked N-1 questions to determine the celebrity.