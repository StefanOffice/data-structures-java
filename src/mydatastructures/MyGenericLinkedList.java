package mydatastructures;

import java.util.Iterator;

public class MyGenericLinkedList<E> implements Iterable<E>{
    
    private Node<E> head;
    private Node<E> tail;
    private int size = 0; // Count of elements currently in the list
    
    /** Default empty constructor */
    public MyGenericLinkedList() {
    }
    
    /** Creates a list from an array of elements of type E
     * such that first element in the array is the head, and last is tail*/
    public MyGenericLinkedList(E[] elements) {
        for (int i = 0; i < elements.length; i++)
            addLast(elements[i]);
    }
    
    /** Adds an element to the start of the linked list
     * this node becomes the new Head */
    //Runtime complexity: O(1)
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e); // Create a node to wrap the new element
        newNode.next = head; // link the new node with the head
        head = newNode; // head points to the new node
        size++;
        
        // if the list was empty before new node was added
        // new node becomes head AND tail, since it's the only one in the list.
        if (tail == null)
            tail = head;
    }
    
    /** Adds an element to the end of the list
     * added node becomes the new Tail*/
    //Runtime complexity: O(1)
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e); // Create a node to wrap the new element
        
        // if the list was empty before new node was added
        // new node becomes tail AND head, since it's the only one in the list.
        if (tail == null)
            head = tail = newNode;
        else{
            tail.next = newNode; // Connect the newNode to the tail
            tail = newNode; // switch tail to point to new last node
        }
        size++;
    }
    
    /** Same as addLast(E e).
     * Adds an element to the end of the list
     * added node becomes the new Tail*/
    // Runtime complexity O(1)
    public void add(E e){
        addLast(e);
    }
    
    /** Adds a new element at the passed in index.
     * if index > size, element will instead be added to the end of the list*/
    // Runtime complexity: O(n)
    public void add(int index, E e) {
        if(index < 0)
            throw new IndexOutOfBoundsException("Indexing starts at 0. Can't add element to index " + index);
        else if(index == 0)
            addFirst(e);
        else if (index >= size)
            addLast(e);
        else {
            //start at the head
            Node<E> current = head;
            //and travel down the list to the node just before specified index
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            
            Node<E> newNode = new Node<>(e);
            //save the reference to the current element at that index
            //if we don't do this, we will lose the rest of the list after changing the current.next reference below
            Node<E> temp = current.next;
            //overwrite the link to point to the new node
            current.next = newNode;
            //reconnect the saved node to the new node
            newNode.next = temp;
            size++;
        }
    }
    
    /** Replaces the element at the specified index, with the provided element.
     * Returns: Element that was previously on that position
     * Throws: IndexOutOfBoundsException if an element doesn't exist at the specified index. */
    // Runtime complexity O(n)
    public E set(int index, E e) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        
        //start at the head
        Node<E> current = head;
        //and travel down the list to the node just before specified index
        for (int i = 1; i < index; i++) {
            current = current.next;
        }
        
        //most of the process is going to be same as in add method above, look there for explanation
        Node<E> newNode = new Node<>(e);
        Node<E> temp = current.next;
        current.next = newNode;
        //reconnect the node AFTER the saved node to the new node,
        // since we replaced the saved node with the new node
        newNode.next = temp.next;
        
        if(head.equals(temp))
            head = newNode;
        if(tail.equals(temp))
            tail = newNode;
        
        return temp.element;
    }
    
    /** Returns: the first element from the list */
    // Runtime complexity O(n)
    public E getFirst() {
        if (size == 0)
            return null;
        else
            return head.element;
    }
    
    /** Returns: the last element from the list */
    // Runtime complexity O(n)
    public E getLast() {
        if (size == 0)
            return null;
        else
            return tail.element;
    }
    
    /** Returns: the element at the provided index
     * Throws: exception if invalid index is passed*/
    // Runtime complexity O(n)
    public E get(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        //start at the head
        Node<E> current = head;
        //and travel down the list to the node just before specified index
        for (int i = 1; i <= index; i++) {
            current = current.next;
        }
        return current.element;
    }
    
    /** Checks if there is an element in the list matching the specified element
     * Return: true if matching element is found */
    // Runtime complexity O(n)
    public boolean contains(Object e) {
        return indexOf(e) != -1;
    }
    
    /** Returns: the index of the first matching element or -1 if no match was found. */
    // Runtime complexity O(n)
    public int indexOf(Object e) {
        //start at the head
        Node<E> current = head;
        //and check each element to see if it matches
        for (int i = 0; i < size; i++) {
            if(current.element.equals(e))
                return i;
            current = current.next;
        }
        return -1;
    }
    
    /** Returns: the index of the last matching element or -1 if no match was found. */
    // Runtime complexity O(n)
    public int lastIndexOf(E e) {
        //if this was a doubly linked list we would start at tail and work backwards
        //but since this is a singly linked list we need to check each element
        if(isEmpty())
            return -1;
        
        int index = -1;
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if(current.element.equals(e))
                index = i;
            current = current.next;
        }
        
        return index;
    }
    
    /** Removes the first element from the list.
     *  Returns: the element that was removed */
    //Runtime Complexity: O(1)
    public E removeFirst() {
        if (size == 0)
            return null;
        
        else {
            E temp = head.element;
            head = head.next;
            size--;
            //reset the tail as well if removed item was the only one in the list
            //since tail was pointing to it as well
            if (head == null)
                tail = null;
            
            return temp;
        }
    }
    
    /** Removes the last element from the list.
     *  Returns: the element that was removed */
    //Runtime Complexity: O(n)
    public E removeLast() {
        if (size == 0)
            return null;
        
        else if (size == 1) {
            E temp = head.element;
            head = tail = null;
            size = 0;
            return temp;
        } else {
            //to remove the last element we need to find the one before it
            //which requires traveling down the whole list starting from the head
            Node<E> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            //once we find the node before the last we can make it the new tail
            E temp = tail.element;
            tail = current;
            tail.next = null;
            size--;
            return temp;
        }
    }
    
    /** Removes the element at the specified position from the list.
     *  Returns: the element that was removed. */
    //Runtime Complexity: O(n)
    public E remove(int index) {
        if (index < 0 || index >= size)
            return null;
        else if (index == 0)
            return removeFirst();
        else if (index == size - 1)
            return removeLast();
        
        else {
            Node<E> current = head;
            //tavel down the list to the node just before specified index
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> nodeToRemove = current.next;
            //set the current node to reference the node after the node that is being removed
            //this will leave the nodeToRemove without a reference and ready for GC
            current.next = nodeToRemove.next;
            size--;
            
            return nodeToRemove.element;
        }
    }
    
    /** Clears out the list, removing all elements */
    public void clear() {
        //no need to actually remove anything
        //we can just reset the head and tail refs and Garbage Collection will do the rest
        head = tail = null;
        size = 0;
    }
    
    /** Returns: Count of elements currently in the list */
    public int size() {
        return size;
    }
    
    public boolean isEmpty(){
        return head == null;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            
            if (current != null)
                result.append(", ");
        }
        result.append("]");
        return result.toString();
    }
    
    @Override
    public Iterator<E> iterator() {
        return new MyLinkedListIterator();
    }
    
    private class MyLinkedListIterator
            implements Iterator<E> {
        
        private Node<E> currentNode = head;
        
        @Override
        public boolean hasNext() {
            return (currentNode != null);
        }
        
        @Override
        public E next() {
            E e = currentNode.element;
            currentNode = currentNode.next;
            return e;
        }
    }
    
    /** the core of any linked list is the node system
     * where each node knows only about the existence of the node that comes after it*/
    private static class Node<E> {
        E element;
        
        Node<E> next;
        
        public Node(E element) {
            this.element = element;
        }
        
        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    '}';
        }
    }
}

