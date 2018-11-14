# COP 5536 – Fall 2018
# Programming Project 

- Siddhant Mohan Mittal UFID: 6061-8545 Email: s.mittal@ufl.edu

# Porject Directory Structure

- Main directory contains Project Problem statement pdf, this report pdf, program files, make file and test input files

# Instructions for running the code
- After unziping the mittal_siddhant.zip  
- All the program files are under the zip folder
```sh
$ make
$ java keywordCounter input.txt
```
- Just copy the two input files in this directory and replace input.txt with the new input file name

# Output format
- Output will be printed in output_file.txt. 
-  Output for a query should be comma separated list without any new lines. Once the output for a query is finished you should put a new line to write the output for the next query

# Assumptions
- Input keyword can be any arbitrary string including alphanumeric characters and special  symbols.
- The count/frequency can only be a positive number greater than 0.
- No spaces in the keywords.
- For two keywords to be same, whole word should match. i.e. #youtube and #youtube_music are two different keywords.
- One query has only one integer.
- If there are more than one keyword with similar frequencies, you can print them in any order.
- Query integer 𝑛 ≤ 20. i.e. you need to find at most 20 popular keywords.
- Input file may consist of more than 1 million keywords.




# Classes
- Node: node.java is the class which stores the information about a keyword

|  Type| Name |
| ------ | ------ |
|Node  | left |
 | Node |    right |
|   Node  |   child|
 |  Node  |   parent |
 |  String  |    word|
  | Integer     | key |
  | Boolean     |mark|
 |  Integer   |  degree|

- Fibonacci Heap: Contains fibonacci heap implementation

|  Type| Name |
| ------ | ------ |
|Node  | maxNode |
 | Integer |    numberOfNodes |

- Keyword Counter: Main driver class, containing our main function

# Function prototypes
### Fibonacci Heap

##### insert(Node node)
- Description: This procedure inserts node x into Fibonacci heap H, assuming that the node has already been allocated and that x:key has already been filled in
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | It's the new node getting inserted into the heap |

- Return value: It's void function no return value
##### removeMax()
- Description: Remove max works by first making a root out of each of the maximum node’s children and removing the maximum node from the root list. It then consolidates the root list by linking roots of equal degree until at most one root remains of each degree
- Return value: Return the Max node from the heap
##### joinTrees(Node y, Node x)
- Description:  This procedure unites Fibonacci heaps y and x,
destroying y and x in the process. It simply concatenates the root lists of y and x and then deter-mines the new maximum node. Afterward, the objects representing y and x will never be used again.
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | The node with the smaller key value |
| Node | The node with the bigger key value |

- Return value: Void procedure
##### consolidate()
- Description: In which we reduce the number of trees in the Fibonacci heap, is consolidating the root list of H, which the call CONSOLIDATE.H/ accomplishes. Consolidating the root list consists of repeatedly executing the following steps until every root in the root list has a distinct degree value:
1. Find two roots x and y in the root list with the same degree. Without loss of generality, let x:key > y:key.
2. joinTrees procedure. This procedure increments the attribute x:degree and clears the mark on y.
- Return value: Void function

##### increaseKey(Node z, int k)
- Description: In the following procedure for the operation FIB-HEAP-INCREASE-KEY, we assume as before that removing a node from a linked list does not change any of the structural attributes in the removed node, and we increase the key of the node. If the node.key > parent.key we remove the node and insert it into root list. Then we run procedure cut and cascading cut. We then update max node. 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | The node whoes key is getting updates |
| Integer | The value of the new key of the node which we have to update |

- Return value: Void procedure

##### cut(Node child, Node parent)
- Description:  Removing the node if its key increases more than its root
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | Child node which is being cut from parent node|
| Node | The parent node |

- Return value: Void procedure

##### cascadingCut(Node z)
- Description: The reason for the cascading cut is to keep degreeArray(n) low. It turns out that if you allow any number of nodes to be cut from a tree, then degreeArray(n) can grow to be linear, which makes delete and delete-min take linear time. Intuitively, you want the number of nodes in a tree of order k to be exponential in k. That way, you can have only logarithmically many trees in a consolidated heap. If you can cut an arbitrary number of nodes from a tree, you lose this guarantee. Specifically, you could take a tree of order k, then cut all of its grandchildren. This leaves a tree with k children, each of which are leaves. Consequently, you can create trees of order k with just k + 1 total nodes in them. This means that in the worst case you would need a tree of order n - 1 to hold all the nodes, so degreeArray(n) becomes n - 1 rather than O(log n).
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | Keyword node that is undergoing cascading cut|

- Return value: Void function

##### printRootList(Node z)
- Description: Print the root list of the tree
- Return value: Void procedure
