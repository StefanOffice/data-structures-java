package mydatastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class MyGenericHashSet<E> implements Iterable<E> {
    
    private static final int START_CAP_DEFAULT = 4;
    private static final float LOAD_FACTOR_DEFAULT = 0.75f;
    private static final int MAX_CAP = 1 << 30;
    
    private int capacity;
    private final float loadFactorLimit;
    private int size = 0;
    
    private ArrayList<LinkedList<E>> data;
    
    /**
     * Default constructor
     */
    public MyGenericHashSet() {
        this(START_CAP_DEFAULT, LOAD_FACTOR_DEFAULT);
    }
    
    /**
     * Constructs a set with default load factor and the desired initial capacity
     */
    public MyGenericHashSet(int initialCapacity) {
        this(initialCapacity, LOAD_FACTOR_DEFAULT);
    }
    
    /**
     * Constructs a set with provided starting capacity and load factor
     */
    public MyGenericHashSet(int initialCapacity, float loadFactorLimit) {
        this.capacity = Math.min(initialCapacity, MAX_CAP);
        //set bounds for load factor between 0.1 and 1
        this.loadFactorLimit = Math.min(Math.max(0.1f, loadFactorLimit), 1f);
        
        data = new ArrayList<>();
        //need to create these eagerly to avoid mixing arrays with generic types, ouch
        for (int i = 0; i < capacity; i++) {
            LinkedList<E> currentBucket = new LinkedList<>();
            data.add(currentBucket);
        }
    }
    
    /**
     * Adds an element to the set
     * Returns: false if element was already in the set
     */
    public boolean add(E e) {
        
        if (contains(e))
            return false; // Element is already in the set
        
        if (size + 1 > capacity * loadFactorLimit) {
            if (capacity == MAX_CAP)
                throw new RuntimeException("Maximum capacity reached");
            else
                rehash();
        }
        
        //figure out where should the new element go,
        // based on the hash function
        int bucketIndex = hash(e);
        data.get(bucketIndex).add(e);
        size++;
        
        return true;
    }
    
    /**
     * Removes the specified element from the set
     * Returns: false if element was not in the set to begin with
     */
    public boolean remove(E e) {
        if (!contains(e))
            return false;
        
        int bucketIndex = hash(e);
        data.get(bucketIndex).remove(e);
        size--;
        
        return true;
    }
    
    /**
     * Removes all data from this set
     */
    public void clear() {
        size = 0;
        for (LinkedList<E> currentBucket : data) {
            currentBucket.clear();
        }
    }
    
    
    /**
     * Returns: true if set contains the specified element
     */
    public boolean contains(E e) {
        int bucketIndex = hash(e);
        return data.get(bucketIndex).contains(e);
    }
    
    
    /**
     * Hash function
     */
    private int hash(E element) {
        //Focus of this class is to demonstrate how a Map can be implemented, not to demonstrate various hashing functions
        // therefore the most basic one will be sufficient for this example
        return Math.abs(Objects.hashCode(element) % capacity);
    }
    
    /**
     * Rehash the set
     */
    private void rehash() {
        ArrayList<E> temp = getListOfElements(); // Temporarily save entries
        capacity = capacity << 1; // Double capacity
        //Clear out all the buckets because all keys will be rehashed
        //so the same entry might go in a different bucket now
        for (LinkedList<E> currentBucket : data) {
            currentBucket.clear();
        }
        //add more buckets to match capacity.
        while (data.size() < capacity) {
            LinkedList<E> newBucket = new LinkedList<>();
            data.add(newBucket);
        }
        
        size = 0;//reset the size
        
        for (E element : temp) {
            add(element); // Add from the old table to the new table
        }
    }
    
    /**
     * Copy elements in the hash set to an array list
     */
    private ArrayList<E> getListOfElements() {
        ArrayList<E> list = new ArrayList<>();
        for (LinkedList<E> currentBucket : data) {
            list.addAll(currentBucket);
        }
        return list;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public String toString() {
        ArrayList<E> list = getListOfElements();
        StringBuilder sb = new StringBuilder("[");
        
        
        for (E e : list) {
            sb.append(e).append(", ");
        }
        
        if (list.size() != 0) {
            sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    /** Returns: An iterator for traversing through all elements in the set */
    public Iterator<E> iterator() {
        return new MyGenericHashSetIterator(this);
    }
    
    private class MyGenericHashSetIterator implements Iterator<E> {
        // Store the elements in a list
        private final ArrayList<E> elements;
        private int currentElement = 0;
        private MyGenericHashSet<E> set;
        
        /**
         * Creates a list of elements from the current set.
         */
        public MyGenericHashSetIterator(MyGenericHashSet<E> set) {
            this.set = set;
            elements = getListOfElements();
        }
        
        @Override
        public boolean hasNext() {
            return currentElement < elements.size();
        }
        
        @Override
        public E next() {
            return elements.get(currentElement++);
        }
    }
}

