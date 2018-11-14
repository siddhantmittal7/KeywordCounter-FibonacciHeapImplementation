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
- Description: 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | 4.067768 |

- Return value: 
##### removeMax()
- Description: 
- Return value: 
##### joinTrees(Node y, Node x)
- Description: 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | 4.067768 |

- Return value: 
##### consolidate()
- Description: 
- Return value: 

##### increaseKey(Node z, int k)
- Description: 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | 4.067768 |

- Return value: 

##### cut(Node child, Node parent)
- Description: 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | 4.067768 |

- Return value: 

##### cascadingCut(Node z)
- Description: 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | 4.067768 |

- Return value: 

##### printRootList(Node z)
- Description: 
- Parameters: 

| Parameter Name | Significance |
| ------ | ------ |
| Node | 4.067768 |

- Return value: 




