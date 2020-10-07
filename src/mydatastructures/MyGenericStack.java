package mydatastructures;

import java.util.ArrayList;
import java.util.EmptyStackException;

//Stack is a FIFO(First In First Out) data structure implemented using ArrayList internally in this example
//Java Generic Types are also used to enable storing elements of any type
public class MyGenericStack<E> {
    private final ArrayList<E> elements = new ArrayList<>();
    
    /** Returns the number of elements in the stack. */
    public int getSize() {
        return elements.size();
    }
    
    /** Adds an element to the top of the stack. */
    //Runtime complexity: O(1)
    public void push(E o) {
        elements.add(o);
    }
    
    /** Removes an element from the top of the stack.
     * Returns: the element that was removed. */
    //Runtime complexity: O(1) (since elements are only removed from the end of the ArrayList)
    public E pop() {
        if(isEmpty())
            throw new EmptyStackException();
        
        E o = elements.get(getSize() - 1);
        elements.remove(getSize() - 1);
        return o;
    }
    
    /** Returns the last stored element. */
    //Runtime complexity: O(1)
    public E peek() {
        if(isEmpty())
            throw new EmptyStackException();
        
        return elements.get(getSize() - 1);
    }
    
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    @Override
    public String toString() {
        return "stack: " + elements.toString();
    }
}
