package mydatastructures;

import java.util.*;

public class MyGenericHashMap<K, V> {
    
    private static final int START_CAP_DEFAULT = 4;
    private static final float LOAD_FACTOR_DEFAULT = 0.75f;
    private static final int MAX_CAP = 1 << 30;
    
    private int capacity;
    private final float loadFactorLimit;
    private int size = 0;
    
    // This map implementation will use 'chaining' to handle key collisions
    // this means that the map is built as an array that will hold a linked list in each slot(bucket)
    // if, after hashing, a key  needs to go to a bucket that contains a key already
    // that new key will just be added to the end of the linked list in that bucket
    ArrayList<LinkedList<MyEntry<K, V>>> data;
    
    /**
     * Default constructor
     */
    public MyGenericHashMap() {
        this(START_CAP_DEFAULT, LOAD_FACTOR_DEFAULT);
    }
    
    /**
     * Constructs a map with default load factor and the desired initial capacity
     */
    public MyGenericHashMap(int initialCapacity) {
        this(initialCapacity, LOAD_FACTOR_DEFAULT);
    }
    
    /**
     * Constructs a map with provided starting capacity and load factor
     */
    public MyGenericHashMap(int initialCapacity, float loadFactorLimit) {
        this.capacity = Math.min(initialCapacity, MAX_CAP);
        //set bounds for load factor between 0.1 and 1
        this.loadFactorLimit = Math.min(Math.max(0.1f, loadFactorLimit),1f);
        
        data = new ArrayList<>();
        //need to create these eagerly to avoid mixing arrays with generic types, ouch
        for (int i = 0; i < capacity; i++) {
            LinkedList<MyEntry<K, V>> currentBucket = new LinkedList<>();
            data.add(currentBucket);
        }
    }
    
    /**
     * Adds an Entry with specified key and value into the map
     * Returns: the old value (if specified key was already in the map)
     * or the value added(if not)
     */
    public V put(K key, V value) {
        //if the key is already in the map, find the entry that contains it
        // and replace the associated value
        if (get(key) != null) {
            int bucketIndex = hash(key);
            LinkedList<MyEntry<K, V>> keyBucket = data.get(bucketIndex);
            for (MyEntry<K, V> entry : keyBucket)
                
                if (entry.getKey().equals(key)) {
                    V oldValue = entry.getValue();
                    entry.value = value;
                    return oldValue;
                }
        }
        
        // Ensure that proper load factor is being maintained
        if (size >= capacity * loadFactorLimit) {
            if (capacity == MAX_CAP)
                throw new RuntimeException("Maximum capacity reached");
            //if the new entry pushes the load factor beyond the set threshold
            // create a new larger array and rehash ALL entries, expensive operation
            rehash();
        }
        
        MyEntry<K, V> newEntry = new MyEntry<>(key, value);
        
        int bucketIndex = hash(key);
        
        // Add the new entry to the linked list in this bucket
        data.get(bucketIndex).add(newEntry);
        size++;
        
        return value;
    }
    
    /**
     * Removes the entry that contains the provided key
     */
    public void remove(K key) {
        int keyBucketIndex = hash(key);
        
        LinkedList<MyEntry<K, V>> keyBucket = data.get(keyBucketIndex);
        //go through the bucket and find the entry that has the key
        for (MyEntry<K, V> currentEntry : keyBucket)
            if (key.equals(currentEntry.getKey())) {
                keyBucket.remove(currentEntry);
                size--;
                break;
            }
    }
    
    /**
     * Removes all data from this map
     */
    public void clear() {
        size = 0;
        for (LinkedList<MyEntry<K, V>> currentBucket : data) {
            currentBucket.clear();
        }
    }
    
    /**
     * Returns: A set of all keys currently in this map
     */
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        
        for (LinkedList<MyEntry<K, V>> currentBucket : data) {
            for (MyEntry<K, V> currentEntry : currentBucket)
                set.add(currentEntry.getKey());
        }
        
        return set;
    }
    
    /**
     * Return: A set of all(unique) values currently in this map
     */
    public Set<V> values() {
        Set<V> set = new HashSet<>();
        for (LinkedList<MyEntry<K, V>> currentBucket : data) {
            for (MyEntry<K, V> currentEntry : currentBucket)
                set.add(currentEntry.getValue());
        }
        
        return set;
    }
    
    /**
     * Returns: A set of all entries currently in this map
     */
    public Set<MyEntry<K, V>> entrySet() {
        
        Set<MyEntry<K, V>> allEntries = new HashSet<>();
        
        for (LinkedList<MyEntry<K, V>> currentBucket : data)
            allEntries.addAll(currentBucket);
        
        return allEntries;
    }
    
    /**
     * Returns: The value that is connected to the provided key
     */
    //Runtime complexity: O(1) - in theory assuming a good hash function, key collision handling and load factor
    // but if that's not the case it can be much slower up to O(n) if all keys are in the same bucket
    public V get(K key) {
        //find out in which bucket the key is
        int bucketIndex = hash(key);
        // go through that bucket to look for the Entry with that key
        LinkedList<MyEntry<K, V>> keyBucket = data.get(bucketIndex);
        for (MyEntry<K, V> currentEntry : keyBucket)
            if (key.equals(currentEntry.getKey()))
                return currentEntry.getValue();
        
        return null;
    }
    
    /**
     * Returns: true is provided key is in this map
     */
    //Runtime complexity: O(1)
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    /**
     * Return: true if provided value is in this map
     */
    //Runtime complexity: O(n)
    public boolean containsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            //go down the array and compare each value to the desired value
            for (LinkedList<MyEntry<K, V>> currentBucket : data) {
                for (MyEntry<K, V> entry : currentBucket)
                    if (entry.getValue().equals(value))
                        return true;
            }
        }
        return false;
    }
    
    /**
     * Basic hashing function
     */
    private int hash(K key) {
        //Focus of this class is to demonstrate how a Map can be implemented, not to demonstrate various hashing functions
        // therefore the most basic one will be sufficient for this example
        return Math.abs(Objects.hashCode(key) % capacity);
    }
    
    /**
     * Rehash the map
     */
    private void rehash() {
        Set<MyEntry<K, V>> temp = entrySet(); // Temporarily save entries
        capacity = capacity << 1; // Double capacity
        //Clear out all the buckets because all keys will be rehashed
        //so the same entry might go in a different bucket now
        for (LinkedList<MyEntry<K, V>> currentBucket : data) {
            currentBucket.clear();
        }
        //add more buckets to match capacity.
        while (data.size() < capacity) {
            LinkedList<MyEntry<K, V>> newBucket = new LinkedList<>();
            data.add(newBucket);
        }
        
        size = 0;//reset the size
        
        for (MyEntry<K, V> entry : temp) {
            //place all the entries back into the map
            put(entry.getKey(), entry.getValue());
        }
    }
    
    /**
     * Returns: The count of entries currently in this map
     */
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        
        for (LinkedList<MyEntry<K, V>> currentBucket : data) {
            for (MyEntry<K, V> currentEntry : currentBucket) {
                sb.append(currentEntry);
            }
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    private class MyEntry<K, V> {
        K key;
        V value;
        
        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
        
        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }
    
}