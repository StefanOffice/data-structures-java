package mydatastructures;

import java.util.Iterator;

public class MyGenericLinkedList<E> implements Iterable<E>{
    
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;
    
    public MyGenericLinkedList() {
    }
    
    public MyGenericLinkedList(E[] elements) {
        for (int i = 0; i < elements.length; i++)
            addLast(elements[i]);
    }
    
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.next = head;
        head = newNode;
        size++;
        
        if (tail == null)
            tail = head;
    }
    
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        
        if (tail == null)
            head = tail = newNode;
        else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    public void add(E e){
        addLast(e);
    }
    
    
    public void add(int index, E e) {
        if(index < 0)
            throw new IndexOutOfBoundsException("Indexing starts at 0. Can't add element to index " + index);
        else if(index == 0)
            addFirst(e);
        else if (index >= size)
            addLast(e);
        else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            
            Node<E> newNode = new Node<>(e);
            Node<E> temp = current.next;
            current.next = newNode;
            newNode.next = temp;
            size++;
        }
    }
    
    public E set(int index, E e) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        
        Node<E> current = head;
        for (int i = 1; i < index; i++) {
            current = current.next;
        }
        
        Node<E> newNode = new Node<>(e);
        Node<E> temp = current.next;
        current.next = newNode;
        newNode.next = temp.next;
        
        if(head.equals(temp))
            head = newNode;
        if(tail.equals(temp))
            tail = newNode;
        
        return temp.element;
    }
    
    public E getFirst() {
        if (size == 0)
            return null;
        else
            return head.element;
    }
    
    public E getLast() {
        if (size == 0)
            return null;
        else
            return tail.element;
    }
    
    public E get(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<E> current = head;
        for (int i = 1; i <= index; i++) {
            current = current.next;
        }
        return current.element;
    }
    
    public boolean contains(Object e) {
        return indexOf(e) != -1;
    }
    
    public int indexOf(Object e) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if(current.element.equals(e))
                return i;
            current = current.next;
        }
        return -1;
    }
    
    public int lastIndexOf(E e) {
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
    
    public E removeFirst() {
        if (size == 0)
            return null;
        
        else {
            E temp = head.element;
            head = head.next;
            size--;
            if (head == null)
                tail = null;
            
            return temp;
        }
    }
    
    public E removeLast() {
        if (size == 0)
            return null;
        
        else if (size == 1) {
            E temp = head.element;
            head = tail = null;
            size = 0;
            return temp;
        } else {
            Node<E> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            E temp = tail.element;
            tail = current;
            tail.next = null;
            size--;
            return temp;
        }
    }
    
    public E remove(int index) {
        if (index < 0 || index >= size)
            return null;
        else if (index == 0)
            return removeFirst();
        else if (index == size - 1)
            return removeLast();
        
        else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> nodeToRemove = current.next;
            current.next = nodeToRemove.next;
            size--;
            
            return nodeToRemove.element;
        }
    }
    
    public void clear() {
        head = tail = null;
        size = 0;
    }
    
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

