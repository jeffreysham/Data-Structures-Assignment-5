README-WordSort

Data Structures
Assignment 5
Jeffrey Sham JHED: jsham2
Tyler Lee JHED: tlee93
Alwin Hui JHED: ahui5

See README-Graph for more information about the graph structure.

For the WordSort class, we used a graph structure to solve the problem. We chose this structure because it enabled us to easily sort words and letters based on a given dictionary. Specifically, we used a directed graph structure in order to determine which characters come before which characters in this foreign language. In order to use this graph structure, we added a node each time that we encountered a new character while parsing the input from the dictionary file. Then, we connected the letter that came "alphabetically" first to the letter that came "alphabetically" next. This would successfully create a graph structure where each letter was in order based on the given dictionary.

In order to sort the given unsorted word list, we would loop through the list and compare each word to its immediately adjacent neighbors. After doing so, each of these words would be on different levels. In the best case scenario, the words would all be in order already, each on a different level. However, it was entirely possible that each word would be alternating with the previous word and hence it would now be on two levels. The method call would be recursive and then sort each level on its own and concatenate the two lists together to form a cohesive list of each in order. Thus, it would take at most order log N to sort the entire list. In a sense, this acted like a bucket sort in that the words were first sorted into different levels and then sorted in each bucket before combining them all together.
