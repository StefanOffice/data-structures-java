package mydatastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

//A binary Heap is a binary tree that satisfies the shape property and heap property
// shape property - must be a complete tree(each of it's levels is full, except the last level may or may not be full)
// BUT last level is always filled from left to right
// heap property - (each node is greater than or equal to any of it's children)
public class MyGenericHeap<E extends Comparable<E>> implements Iterable<E> {
    //heap can also be implemented using a regular array
    // but since ArrayList is already fully implemented in this project it will be used to somewhat simplify the code
    // see MyGenericArrayList for more information.
    private ArrayList<E> list = new ArrayList<>();
    private Comparator<? super E> c;
    
    /** Creates a heap using natural order for comparison by default */
    public MyGenericHeap() {
        this.c = Comparator.naturalOrder();
        //this.c = (e1, e2) -> e1.compareTo(e2); //this is essentially what the line of code above creates
    }
    
    /** Creates a heap with a custom comparator */
    public MyGenericHeap(Comparator<E> c) {
        this.c = c;
    }
    
    /** Creates a heap with elements from the provided array */
    public MyGenericHeap(E[] starterElements) {
        this(); // runs the default constructor to initialize the comparator
        for (int i = 0; i < starterElements.length; i++)
            add(starterElements[i]);
    }
    
    //this is where things get interesting
    //in order to maintain the shape and heap property
    //the heap needs to be rebuilt(rebalanced) when elements are added and removed
    
    /** Adds a new element into the heap. */
    public void add(E element) {
        //element is first added to the end of the heap(as one of the leaves)...
        list.add(element);
        
        //...then it's index is taken...
        int newElementIndex = list.size() - 1;
        
        
        while (newElementIndex > 0) {
            //...in order to compare it with parent nodes...
            int parentIndex = (newElementIndex - 1) / 2;
            //...and swap the added element with its parent if it's greater than its parent
            if (c.compare(list.get(newElementIndex), list.get(parentIndex)) > 0) {
                swapElements(newElementIndex, parentIndex);
                newElementIndex = parentIndex;
            } else
                break; // the tree is a heap now
        }
    }
    
    /** Performs basic swapping of elements at the specified index positions
     * used only internally by the class itself */
    private void swapElements(int firstIndex, int secondIndex) {
        E temp = list.get(firstIndex);
        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, temp);
    }
    
    /** Removes the root from the heap (which is also the largest element)
     * Returns: the element that was removed */
    public E remove() {
        if (list.size() == 0)
            return null;
        
        E removedObject = list.get(0);
        //Put the rightmost leaf from the last level as the starting root
        list.set(0, list.get(list.size() - 1));
        //and then remove the duplicate
        list.remove(list.size() - 1);
        
        
        int currentIndex = 0;
        while (currentIndex < list.size()) {
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;
            
            // Find the 'larger' child
            
            //if a node doesn't have a left child that means that it also has no right child
            //since children are added left to right in a heap, which makes it a leaf
            if (leftChildIndex >= list.size())
                break; // if it's a leaf then it's surely balanced as there is nothing left to compare it to.
            
            //assume leftChild is larger
            int maxChildIndex = leftChildIndex;
            //check if right child exists before comparing in order to avoid an exception
            if (rightChildIndex < list.size()) {
                //if the right child is larger update the maxChildIndex
                if (c.compare(list.get(maxChildIndex), list.get(rightChildIndex)) < 0)
                    maxChildIndex = rightChildIndex;
            }
            
            // Swap if the current node is less than the maximum
            //if the current node is smaller than its larger child swap them
            if (c.compare(list.get(currentIndex), list.get(maxChildIndex)) < 0) {
                swapElements(currentIndex, maxChildIndex);
                //then update swapped parent's index and check it against its new children
                //this while loop will repeat until one of the conditions is met:
                // - the inspected node is larger than both of its children
                // - it becomes a leaf node again
                currentIndex = maxChildIndex;
            } else
                break; // The tree is a heap
        }
        return removedObject;
    }
    
    public int getSize() {
        return list.size();
    }
    
    public boolean isEmpty() {
        return list.size() == 0;
    }
    
    @Override
    public String toString() {
        return list.toString();
    }
    
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
