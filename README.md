# Data Structures (custom implementation demonstration)

This project serves as an example of my implementation of the most commonly used data structures for demonstration of their inner workings, runtime complexity of certain operations associated with them and explanation of core concepts. I have posted this project for anyone that is interested in learning (and for myself to occasionally refresh my knowledge). 

**These implementations are not production ready**, i have posted them only for study purposes.
Check out the implementation of each of the below mentioned data structures [here](https://github.com/StefanOffice/data-structures-java/tree/main/src/mydatastructures)

## Topics covered

+ Array
+ Array List
+ Linked List
+ Stack (& min/max stack)
+ Queue(& priority queue)
+ Hash Map
+ Hash Set
+ Heap
+ Binary Search Tree
+ AVL Tree

## Foreword

All of the implementations are using Generics, if you are not familiar with the concept of Generics in java, take a look at [this link](https://www.baeldung.com/java-generics) before trying to understand the code used in this project.

### Array

*An array is a data structure, which can store a fixed-size collection of elements of the same data type. An array is used to store a collection of data, but it is often more useful to think of an array as a collection of variables of the same type.* 

Arrays however have some limitations:

1) Size of the array is fixed: Upper limit on the number of elements has to be defined upon declaration.
2) The allocated memory is equal to the upper limit irrespective of the usage. (which means some memory could potentially be wasted) 
3) If we want to keep the elements of the collection ordered, and remove a value or insert a value in its correct position, then, it may be necessary to shift many elements to complete these operations (half of the elements of the array (on average), very inefficient).

To overcome these limitations other data structures can be used.

### [Array List](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericArrayList.java)


_An ArrayList represents a dynamic array (re-sizable array), that will grow in size ( double size + 1 in this implementation) to accommodate new elements. I have also included a method(trimToSize()) to shrink the ArrayList capacity to only accommodate current number of elements._

- Elements are stored internally using a regular array. Just like arrays, elements can be retrieved by their index.

- Java ArrayList is an ordered collection. It maintains the insertion order of the elements.

- Primitive types (like ```int``` ```char``` ```float```...) can not be used directly. To store these values in an ArrayList, their boxed version must be used(```Integer``` ```Character``` ```Float```...) 

### [Linked List](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericLinkedList.java)

_A linked list is a linear collection of elements where each element is stored in an object called **node**. The order of elements is not given by their physical placement in memory. Instead, each node contains a reference to the next node. It is a data structure consisting of a collection of nodes which together represent a sequence. A linked list is represented by the **head** (a reference to the first node of the linked list)  If the linked list is empty, then the value of the head is NULL._

It has dynamic size and to insert a new element all that needs to be done is to change the previous node's reference to point to the new node, and the new node's reference to point to the node that the previous node was pointing to. It's a similar process for deleting an element.(See more in the example implementation)

**Limitations:** 

1) Elements have to be accessed sequentially starting from the first node, since random access is not supported. Meaning that binary search can't be used with linked lists efficiently with its default implementation.
2) Extra memory space for a reference is required for each of the list's elements.

**Structure:**  
Each node in a list consists of at least two parts: 

1) Data 
2) Reference to the next node
  
In Java, LinkedList can be represented as a class and a Node as a separate class (in this case as an inner class). The LinkedList class contains a reference of Node class type.
 
### [Stack](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericStack.java) (& [min/max stack](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericMinStack.java))
_Stack represents a collection of items that are processed in a LIFO(Last-In-First-Out) fashion. Meaning that the most recently added item is at the top of the stack and it is the first in line to be removed(popped)_

Removing an element or adding a new element to the top of the stack is completed in O(1) (constant time), which makes it very efficient.

My implementation uses an ArrayList internally to store the elements.

Min(or Max) Stack represents a stack implementation where in addition to the top element a min(or max) element can also be retrieved. This is achieved by using two stacks to store the elements internally(see more details in the implementation)

### [Queue](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericQueue.java)(& [priority queue](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericPriorityQueue.java))
_A Queue represents a collection of items that are processed in a FIFO(First-In-First-Out) fashion. (think of it as a waiting list, or a line in a supermarket(first customers that come to the cashier, are the first to be served) ). Element are added to the end (tail) and are removed from the start (head)._ 

My implementation uses a LinkedList internally to store the elements. 

A priority queue is the same concept except elements can jump ahead, to the head of the queue based on their priority, so this structure no longer follows the FIFO concept. Priority is based on the Comparator that can be passed to the constructor(or natural order by default).

### [Hash Map](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericHashMap.java)
_A Map can be viewed as a sort of a dictionary, where you can retrieve a value using a key. It stores the data in (Key, Value) pairs. One object is used as a key (index) to another object (value). Keys must be unique. If a duplicate key is used, map will not store 2 of the same keys but it will replace the element of the corresponding key._

Important concept with maps is key collision.
You can read more about collision [here](https://en.wikipedia.org/wiki/Hash_collision).  
This map implementation will use 'chaining' to handle key collisions.  
Chaining means that the map is built as an array that will hold a linked list in each slot(bucket).  
If, after hashing, a key  needs to go to a bucket that contains some key(s) already, that new key will just be added to the end of the linked list in that bucket.


### [Hash Set](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericHashSet.java)
_HashSet is a very useful data structure that allows storage of unique items and access of them in constant time (on average).   
Duplicate values can not be stored.  
Since the objects are inserted using hash code, they are not stored in an ordered fashion and can be returned in any random order._

Hashing is a very broad topic and there are various ways of creating efficient hash methods. It is not the focus of this project so a very simple hashing method will be used for both the map and the set implementations
``` java
private int hash(E element) {
  return Math.abs(Objects.hashCode(element) % capacity);
}
```
### Heap
_A Binary Heap is a specialized tree based data structure that satisfies the shape property and heap property_

+ **shape property** - must be a complete tree (each of it's levels is full, except the last level may or may not be full - but the last level is always filled from left to right)
+ **heap property** - each node is greater than or equal to any of it's children

This project provides an example implementation using an ArrayList to store the elements 


### [Binary Search Tree](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericTreeBST.java)

Tree is a data structure that stores elements in a hierarchy.  
Elements are stored in nodes.  
Lines that connect them are called edges.  
Node at the start is called root node.  
Bottom nodes with no children are called leaf nodes.

_Binary Search Tree is a node-based, hierachical, binary tree data structure which is either empty or consists of a **root** and two distinct binary trees called the **left subtree** and the **right subtree**. It must also satisfy these properties:_
+ _The left subtree of a given root contains only nodes with keys lesser than the root node’s key._
+ _The right subtree of a given root contains only nodes with keys greater than the root node’s key._
+ _The left and right subtree each must also be a binary search tree._

This makes it possible to find the required value in O(log n) (_logarithmic time_)  
Each comparison eliminates half of the remaining items (_binary search_ - more about this in the beneficial-algorithms project)	

### [AVL Tree](https://github.com/StefanOffice/data-structures-java/blob/main/src/mydatastructures/MyGenericTreeAVL.java)
**A word about balancing**  
Most operations on binary trees run in O(log n) time (ONLY if the tree is balanced)  
A node is balanced when the difference of height of each subtree is less than or equal to one  
```height(left) - height(right) <=1```  
A tree is balanced when all nodes in the tree are balanced  
The more that the tree is unbalanced the more that all operations go towards O(n) runtime.

The solution to this problem are the self-balancing trees such as AVL trees, red-black trees, b-trees...

_AVL tree is a self-balancing binary search tree. It was the first such data structure to be invented.   In an AVL tree, the heights of the two child subtrees of any node differ by at most one (balanced)  
If at any time the heights differ by more than one, re-balancing will be performed to maintain the balanced state.  
This means that look-up, insertion, and deletion are guaranteed to take O(log n) time in both the average and worst cases. (n is the number of nodes in the tree prior to the operation).  
Insertions and deletions may require the tree to be re-balanced by one or more rotations._

Fun fact AVL tree is named after **A**delson-**V**elsky and **L**andis the inventors of this data structure. 

## Thank you!

Thanks for checking out my implementations of common data structures, I hope that this project will shine a light on some of the concepts associated with data structures and their usage. This is by no means a comprehensive guide, but more of an introduction. Good luck on your learning journey! :wink:

**All the best,  
Stefan Stefanovic**
