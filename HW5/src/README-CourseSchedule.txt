README- CourseSchedule

Data Structures
Assignment 5
Jeffrey Sham JHED: jsham2
Tyler Lee JHED: tlee93
Alwin Hui JHED: ahui5

See README-Graph for more information about the graph structure.

For the CourseSchedule class we used a graph structure in order to solve the problem. 
We take advantage of a directed graph in which the prerequisite of a course 
are adjacent to said course.  

We use a BufferedReader and regex to parse the input file.
The parse method uses String.split(regex) to capture each course in a line.
The course at index 0 would be the new course.
Each subsequent course would be a prerequisite.
If the prerequisite has yet to be inserted into the graph, then we add it.
Then we connect the prerequisite to the course using connectVerticies(Object keyA, Object keyB).
KeyA would now be adjacent to keyB.
After the input has all been parsed, we have a complete graph.
Call topologicalSort() which outputs a list of courses separated by null values.
We go through this list denoting each null as a new semester and printing as we go.
At the end print the total (minimum) number of semesters required to take all of the courses.