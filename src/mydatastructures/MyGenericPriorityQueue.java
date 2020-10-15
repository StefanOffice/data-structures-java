package mydatastructures;

import java.util.Comparator;

// This class presents a simple implementation of a Priority Queue
// Which delegates all of it's calls to the already implemented heap
// See MyGenericHeap for more details on how it all works.
public class MyGenericPriorityQueue<E extends Comparable<E>> {
  private final MyGenericHeap<E> heap;
  
  public MyGenericPriorityQueue() {
    heap = new MyGenericHeap<E>();
  }
  
  public MyGenericPriorityQueue(Comparator<E> c) {
    heap = new MyGenericHeap<>(c);
  }
  
  public void enqueue(E newObject) {
    heap.add(newObject);
  }

  public E dequeue() {
    return heap.remove();
  }

  public int getSize() {
    return heap.getSize();
  }
}
