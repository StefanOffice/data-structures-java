package mydatastructures;

import java.util.Iterator;

public class MyGenericArrayList<E> implements Iterable<E>{
    public static final int INITIAL_CAPACITY = 16;
    
    @SuppressWarnings("unchecked")
    private E[] elements = (E[]) new Object[INITIAL_CAPACITY];
    //a little note on the line above
    /*you might be thinking "ouch arrays and generics don't mix well"
        and you are right, but the point of this class is to provide a implementation
        of an ArrayList from scratch so using a List<E> in that place would defeat
        the demonstrative purpose of this class.
        
        It is also safe to Suppress Warnings because 'elements' is a private property,
        that is used only internally by MyArrayList<E>
        The only way to add elements to it is through add() method
        that takes element of Type E, and the array itself is never exposed to the client
    */
    
    private int size = 0; // Number of elements in the list
    
    /** Default constructor for an empty list */
    public MyGenericArrayList() {}
    
    /** Creates a list from an array of elements of type E */
    public MyGenericArrayList(E[] elements) {
        for (E element : elements)
            add(size, element);
    }
    
    /** Adds a new element to the end of the list */
    //Runtime complexity: O(1) (Amortized), O(n) worst case when capacity needs to be increased
    public void add(E e){
        add(size , e);
    }
    
    /** Adds a new element at the specified index */
    //Runtime complexity: O(n)
    public void add(int index, E e) {
        
        //this is not a duplicate of checkIndex, notice how it's checking index > size (instead of index >=size)
        //index can be equal to size in this case because that means it will go to the end of the list.
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException
                    ("Index: " + index + ", Size: " + size);
        //and this method will make sure that there is enough space for it.
        guaranteeCapacity();
        
        // Make place for new element by shifting the existing elements
        // to the right after the specified index
        for (int i = size - 1; i >= index; i--)
            elements[i + 1] = elements[i];
        
        // Insert the new element
        elements[index] = e;
        
        // Increment size by 1
        size++;
    }
    
    /** Internal helper method that is called when adding a new element
     * to make sure that new item can be inserted
     * Creates a new internal array, with doubled size + 1 */
    //Runtime complexity: O(n)
    private void guaranteeCapacity() {
        if (size >= elements.length) {
            @SuppressWarnings("unchecked") // safe, see elements declaration comment above
            E[] newData = (E[])(new Object[size * 2 + 1]);
           /* +1 is added for the edge case when trimToSize() method is used and the ArrayList contains no elements
            meaning the size would be 0, adding a new element would call this method first
            and if new array was created with capacity of size*2 that would still be 0*2=0
            so the client wouldn't be able to add new elements */
            
            //copy the elements from the old array to the new larger one
            System.arraycopy(elements, 0, newData, 0, size);
            //point the elements reference to the new array
            
            System.out.println("\nFOR DEMONSTRATION: increasing internal capacity."
                    + " \n Old Capacity = " + elements.length
                    + " \n New Capacity = " + newData.length
                    + " \n Size = " + size + "\n");
            
            elements = newData;
        }
    }
    
    /** Removes an element by index
     *  Shifts any subsequent elements leftward
     *  Returns: The element that was removed. */
    //Runtime complexity: O(n)
    public E remove(int index) {
        checkIndex(index);
        
        E e = elements[index];
        
        // Shift elements leftward
        for (int j = index; j < size - 1; j++)
            elements[j] = elements[j + 1];
        
        // This line is optional, as we can just leave the double reference to the last element there
        // since 'size' dictates what we see
        // but it is more memory efficient to set it to null
        elements[size - 1] = null;
        size--;
        return e;
    }
    
    /**Removes the specified element from the list.
     * Returns: the element that was removed */
    //Runtime complexity: O(n)
    public E remove(E e){
        int index = indexOf(e);
        //no need to throw exceptions here, as remove(index) will call checkIndex first
        // which will throw exception if the element is not in the list
        return remove(index);
    }
    
    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException
                    ("Index: " + index + ", Size: " + size);
    }
    
    /** Clears the list */
    public void clear() {
        @SuppressWarnings("unchecked") //safe, see 'elements' declaration comment above
        E[] cleared = (E[])new Object[INITIAL_CAPACITY];
        elements =cleared;
        size = 0;
    }
    
    /** Checks if the list contains specified element.
     * Returns: true if the specified element is in the list. */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++)
            if (e.equals(elements[i])) return true;
        
        return false;
    }
    
    /** Returns: the element at the specified index */
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }
    
    /** Replaces the element at the specified index with the specified element.
     * Returns: the element that was previously on the specified index */
    public E set(int index, E e) {
        checkIndex(index);
        E temp = elements[index];
        elements[index] = e;
        return temp;
    }
    
    /** Finds the index of the first element int the list that matches the specified element.
     *  Returns: Index of the element or -1 if no match was found. */
    public int indexOf(E e) {
        for (int i = 0; i < size; i++)
            if (e.equals(elements[i])) return i;
        
        return -1;
    }
    
    /** Finds the index of the last element in the list that matches the specified element.
     *  Returns: Index of the element or -1 if no match was found. */
    public int lastIndexOf(E e) {
        for (int i = size - 1; i >= 0; i--)
            if (e.equals(elements[i])) return i;
        
        return -1;
    }
    
    /** Returns: Count of elements in the list */
    public int size() {
        return size;
    }
    
    /** Trims the capacity to current size */
    public void trimToSize() {
        if (size != elements.length) {
            @SuppressWarnings("unchecked") // safe, see elements declaration comment above
            E[] newData = (E[])(new Object[size]);
            //make a new smaller array and copy elements(if there are any) into it
            System.arraycopy(elements, 0, newData, 0, size);
            
            System.out.println("\nFOR DEMONSTRATION: decreasing internal capacity."
                    + " \n Old Capacity = " + elements.length
                    + " \n New Capacity = " + newData.length
                    + " \n Size = " + size + "\n");
            
            elements = newData;
        } // If size == capacity, no need to trim
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            sb.append(", ");
        }
        //delete the comma and space after the last element
        //to avoid another if check for each element when appending ", "
        //alternative version is in the LinkedList
        if(sb.length() > 2) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }
    
    private class MyArrayListIterator
            implements Iterator<E> {
        
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        
        @Override
        public E next() {
            return elements[currentIndex++];
        }
    }
    
}