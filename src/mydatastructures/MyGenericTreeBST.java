package mydatastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class MyGenericTreeBST<E extends Comparable<E>> implements Iterable<E> {
    
    protected MyTreeNode<E> treeRoot;
    protected int size = 0;
    protected Comparator<E> comparator;
    
    /**
     * Creates a binary search tree, default comparator is the natural order comparator
     */
    public MyGenericTreeBST() {
        this.comparator = Comparator.naturalOrder();
    }
    
    /**
     * Creates a binary search tree with the provided comparator
     */
    public MyGenericTreeBST(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    
    /**
     * Creates a binary search tree and adds the elements from the provided array
     */
    public MyGenericTreeBST(E[] elements) {
        this();
        for (E element : elements)
            add(element);
    }
    
    //using this method to make the class extensible,
    //since AVL tree will extend this class and AVLNode will extend TreeNode
    protected MyTreeNode<E> createNewNode(E e) {
        return new MyTreeNode<>(e);
    }
    
    /**
     * Inserts a new element into the binary tree
     * Returns: true if operation was executed successfully
     */
    public boolean insert(E element) {
        if (treeRoot == null) {
            //if this is the first node in the tree assign it as the root
            treeRoot = createNewNode(element);
        } else {
            // Otherwise find its parent
            MyTreeNode<E> parent = null;
            MyTreeNode<E> current = treeRoot;
            while (current != null) {
                parent = current;
                
                if (comparator.compare(element, current.element) < 0)
                    current = current.left;
                else if (comparator.compare(element, current.element) > 0)
                    current = current.right;
                else
                    return false; // Duplicate node not inserted
            }
            // Create the new node and attach it to the parent node
            if (comparator.compare(element, parent.element) < 0)
                parent.left = createNewNode(element);
            else
                parent.right = createNewNode(element);
        }
        
        size++;
        return true;
    }
    
    /**
     * same as insert, provided for convenience
     */
    public boolean add(E element) {
        return insert(element);
    }
    
    /**
     * Deletes an element from the binary tree.
     * Returns: -true if specified element is removed from the tree.
     *          -false if specified element is not in the tree
     */
    public boolean delete(E element) {
        // Find the nodeToBeDeleted, and its parent
        // parent is set to null at start for the edge case that root is being removed
        MyTreeNode<E> parentNode = null;
        MyTreeNode<E> currentNode = treeRoot;
        while (currentNode != null) {
            if (comparator.compare(element, currentNode.element) < 0) {
                parentNode = currentNode;
                currentNode = currentNode.left;
            } else if (comparator.compare(element, currentNode.element) > 0) {
                parentNode = currentNode;
                currentNode = currentNode.right;
            } else
                break; // element was found, currentNode is pointing at it
        }
        
        if (currentNode == null)
            return false; // Specified element is not in the tree
        
        // Case 1: currentNode doesn't have a left child
        if (currentNode.left == null) {
            // Connect the deleted node's parent node with the right child of the deleted node
            if (parentNode == null) {
                //for the edge case when root is being deleted
                treeRoot = currentNode.right;
            } else {
                //depending whether the deleted node was a left child or a right child
                // just put the deleted node's right child in its place
                if (comparator.compare(element, parentNode.element) < 0)
                    parentNode.left = currentNode.right;
                else
                    parentNode.right = currentNode.right;
            }
        } else {
            // Case 2: The current node has a left child
            // Find the rightmost node (because that node will have the largest value of that whole subtree)
            // in the LEFT subtree of the node to be deleted and also the parent of the left subtree rightmost node
            MyTreeNode<E> parentNodeOfRightMostNode = currentNode;
            MyTreeNode<E> rightMostNode = currentNode.left;
            
            while (rightMostNode.right != null) {
                parentNodeOfRightMostNode = rightMostNode;
                rightMostNode = rightMostNode.right;
                //Keep going right until it's impossible to go further
            }
            
            // We will not delete the current node,
            // instead we can replace the data in current node by the data in right most node...
            currentNode.element = rightMostNode.element;
            
            // ...and then delete the rightmost node
            if (parentNodeOfRightMostNode.right == rightMostNode)
                //because the rightmost node obviously does not have a right child
                //take its left child and connect it to rightmost node's parent
                parentNodeOfRightMostNode.right = rightMostNode.left;
            else
                // Edge case if parentNodeOfRightMostNode is the current node
                parentNodeOfRightMostNode.left = rightMostNode.left;
        }
        
        size--;
        return true;
    }
    
    /**
     * Removes all data from this tree
     */
    public void clear() {
        treeRoot = null;
        size = 0;
    }
    
    /**
     * Returns: the current root of this tree
     */
    public MyTreeNode<E> getTreeRoot() {
        return treeRoot;
    }
    
    /**
     * Returns: -true if a matching element is found in the tree
     */
    //Runtime complexity O(log N) (binary search)
    public boolean search(E element) {
        MyTreeNode<E> current = treeRoot;
        
        while (current != null) {
            if (comparator.compare(element, current.element) < 0)
                current = current.left;
            else if (comparator.compare(element, current.element) > 0)
                current = current.right;
            else // element was found and current is pointing to the node that contains it
                return true;
        }
        
        return false;
    }
    
    /**
     * Prints elements in the pattern of ROOT > LEFT > RIGHT
     */
    public void printElementsPreorder() {
        printElementsPreorder(treeRoot);
    }
    
    // recursion method, not exposed to the client
    protected void printElementsPreorder(MyTreeNode<E> root) {
        if (root == null)
            return;
        System.out.print(root.element + " ");
        printElementsPreorder(root.left);
        printElementsPreorder(root.right);
    }
    
    /**
     * Prints elements in the pattern of LEFT > ROOT > RIGHT
     */
    public void printElementsInorder() {
        printElementsInorder(treeRoot);
    }
    
    // recursion method, not exposed to the client
    protected void printElementsInorder(MyTreeNode<E> root) {
        if (root == null)
            return;
        printElementsInorder(root.left);
        System.out.print(root.element + " ");
        printElementsInorder(root.right);
    }
    
    /**
     * Prints elements in the pattern of LEFT > ROOT > RIGHT
     */
    public void printElementsPostorder() {
        printElementsPostorder(treeRoot);
    }
    
    // recursion method, not exposed to the client
    protected void printElementsPostorder(MyTreeNode<E> root) {
        if (root == null)
            return;
        printElementsPostorder(root.left);
        printElementsPostorder(root.right);
        System.out.print(root.element + " ");
    }
    
    
    public int getSize() {
        return size;
    }
    
    @Override
    /** Obtain an iterator. Use inorder. */
    public Iterator<E> iterator() {
        return new InorderIterator();
    }
    
    // Custom iterator inner class
    private class InorderIterator implements Iterator<E> {
        // Store the elements in a list
        private final ArrayList<E> elements = new ArrayList<>();
        
        private int currentNodeIndex = 0; // Point to the current element in list
        
        public InorderIterator() {
            storeElementsInorder();
        }
        
        /**
         * Store elements to the list to prepare for iteration in the pattern of LEFTSUBTREE > ROOT > RIGHTSUBTREE
         */
        private void storeElementsInorder() {
            storeElementsInorder(treeRoot);
        }
        
        //helper method for the method above
        private void storeElementsInorder(MyTreeNode<E> treeRoot) {
            if (treeRoot == null)
                return;
            storeElementsInorder(treeRoot.left);
            elements.add(treeRoot.element);
            storeElementsInorder(treeRoot.right);
        }
        
        @Override
        public boolean hasNext() {
            return currentNodeIndex < elements.size();
        }
        
        @Override
        public E next() {
            return elements.get(currentNodeIndex++);
        }
    }
    
    /**
     * It's safe to declare this Class as static because
     * it does not use any of the outer class properties
     */
    //getters and setters are omitted for briefness and readability of code
    // since all of these data structure implementations purpose is demonstrative
    public static class MyTreeNode<E> {
        
        protected E element;
        protected MyTreeNode<E> left;
        protected MyTreeNode<E> right;
        
        public MyTreeNode(E e) {
            element = e;
        }
        
        public E getElement() {
            return element;
        }
        
    }
}
