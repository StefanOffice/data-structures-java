package mydatastructures;

import java.util.LinkedList;

//A Queue is a FIFO(First In First Out) data structure where elements are added to one end
// and removed from the other in order in which they were added.
// This is a very basic implementation of most used methods
  // Alternatively the Node can be made as an inner class here
  // but there is already an full implementation of a linked list in this project
  // so to keep it simple composition was used in this case,
  // if you are interested in the inner workings of a linked list check out MyGenericLinkedList class.
public class MyGenericQueue<E> {
  private LinkedList<E> list = new LinkedList<E>();
  
  /** Adds an element to the end of the queue */
  // Runtime Complexity: O(1)
  public void enqueue(E e) {
    list.addLast(e);
  }

  /** Removes the next in line element from the head of the queue
   * Returns: the element that was removed */
  // Runtime Complexity: O(1)
  public E dequeue() {
    return list.removeFirst();
  }
  
  /** Returns: the item at the head of the queue which is next in line to be removed,
   * but doesn't remove it.*/
  // Runtime Complexity: O(1)
  public E peek(){
    return list.get(0);
  }
  
  public int getSize() {
    return list.size();
  }

  @Override
  public String toString() {
    return "Queue: " + list.toString();
  }
}
