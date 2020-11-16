# Description:
WGraph is a package with 2 classes- ex1: the actuall code, and tests: a class of jUnit tests.
ex1 is simulator a weighted graph- with vertices and weighted edges.

# Requirements:
Implemet the attached interfaces with the required time complicity.

# About the classes:
## WGraph_DS: 
### Data Structure:
This class is using a HashMap of Integer as keys and NodeInfo as values.
Also a counter of number of nodes, and a counter of number of changes.

### Functions:

#### getNode:
Returns the node in the graph according to the node's key.
#### hasEdge:
Returns true or false according to whether the two given nodes has an edge between them.
Uses a function called "hasNi".
#### getEdge:
Returns the weight of the edge between the two given nodes.
If there is no edge between the two nodes returns -1.
Uses a function called "hasNi".
#### addNode:
Creating a new node with the given key.
Uses inner class NodeData's constructor.
If the key allready exist in the graph- does nothing.
#### connect:
Connecting two nodes- Using the function "addNi" in the inner class- NodeInfo.
Does not connect a node to itself- if the two given keys are the same.
Does not connect two nodes that are allready connected- Using the function "hasEdge" in the inner class- NodeInfo.
#### getV:
Returns a collection of all the nodes in the graph.
#### getV(int key): 
Returns a collection of all the connected nodes to the given node.
#### removeNode:
Removes a node from the graph and dissconnect all of his neighbors.
Uses the function "removeEdge" of the inner class NodeInfo.
Does not remove a node from the graph if it doesn't exist.
#### removeEdge: 
Removes a weighted edge of the two given nodes.
Uses a function called "hasNi".
Uses the function "removeNode" of the inner class NodeInfo.
#### nodeSize:
Returns the number of nodes in the graph.
#### edgeSize: 
Returns the number of edges in the graph.
#### getMC: 
Returns the number of changes made in the graph.
#### hasNi:
returns true if the two given node's keys has edge between them.
#### equals: 
Implements the Object function.
Compares the given graph with this graph according to list of nodes- their neigbors and the edges connecting them.
If any of them is copied by refernce the function will return false.  

## NodeInfo: 
### Data Structure:
This class is using a HashMap of node_info as keys and Double as values.
Also an int key, String info and Double tag.

### Functions:
#### getKey:
Returns the node's key.
#### getInfo:
Returns the node's info.
#### setInfo:
Sets the node's info.
#### getTag:
Returns the node's tag.
#### setTag:
Sets the node's tag.
#### getNi:
Returns the node's hash map of neighbors.
#### addNi:
If the node doesn't allready contains the key of the given node the function adds it to the node's hash map of neighbors.
And this node to the given node's list of neighbors.
#### removeNode:
If the node doesn't allready contains the key of the given node the function removes it from the node's hash map of neighbors.
And this node from the given node's list of neighbors.
#### equals:
Implements the Object function.
Compares the given node with this node according to the list of neigbors and the edges connecting them.
If any of them is copied by refernce the function will return false.  

## WGraph_Algo: 
### Data Structure:
This class is using a WGraph_DS as a data structure.
### Functions:
#### init:
Like a cunstructor, inits the WGraph_DS with the given graph.
#### getGraph:
Returns the graph on which WGraph_algo is working on.
#### copy:
Returns a deep copy of the geaph using copy constructor in NodeInfo and in WGraph_DS.
#### isConnected:
Returns true if the graph is connected and false otherwise.
Uses the private function bfs.
If the graph contains no vertices returns true.
Ig the graph has onlt one Vertex returns true.

#### bfs:
Implemets the breath first search algorithm.
Uses a queue and the node's tag to mark visited nodes.
Uses the node's info to keep track of the path between node start and node end.
Uses the private function reSet.

#### reSet:
Resetting all of the node's tags to 0 and info to an empty string.

#### shortestPathDist:
Retuans the distance of the shortest path between two given nodes.
Uses the inner class Dijkstra.
Returns 0 if the node start and node end are the same.

#### shortestPath:
Retuans the shortest path between two given nodes.
Uses the inner class Dijkstra.
Returns an empy list if the node start and node end are the same.

#### save:
Saves the graph in the given location.

#### load:
Loads the graph from the given location.

#### equals:
Implements the Object function.
Compares the given WGraph_Algo with this WGraph_Algo according to the number of nodes, number of edges, the list of nodes- their neigbors and the edges connecting them.
Uses equals of the classes: NodeInfo and WGraph_DS.
If any of them is copied by refernce the function will return false.

## Dijkstra: 
### Data Structure:
This class is using a WGraph_DS as a data structure.
Also a String representing the path and two nodes: node start and node end.
### Functions:
#### reSet:
Resetting all of the node's tags to infinity and info to an empty string.
#### dijkstra:
Implemets the dijkstra algorithm.
Uses a priority queue and the node's info to mark visited nodes.
Also uses the node's info to keep track of the path between node start and node end.
Uses the node's tag to keep track of the cumulative weight.
Uses the private function reSet.
#### cut:
Cuts the class' variable String represrnting the path between node start to node end.
#### stringToList:
Converts the class' variable String represrnting the path to a list of nodes.

# AND THAT'S IT :)
