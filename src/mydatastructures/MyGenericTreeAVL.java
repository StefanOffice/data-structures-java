package mydatastructures;

import java.util.ArrayList;
import java.util.Comparator;

public class MyGenericTreeAVL<E extends Comparable<E>> extends MyGenericTreeBST<E> {
    
    /**
     * Creates an AVL tree, default comparator is the natural order comparator
     */
    public MyGenericTreeAVL() {
        super();//this line is not required
        // as super constructor gets called before this class constructor anyway, but putting it here to clarity
    }
    
    /**
     * Creates an AVL tree with the provided comparator
     */
    public MyGenericTreeAVL(Comparator<E> comparator) {
        super(comparator);
    }
    
    /**
     * Creates a binary search tree and adds the elements from the provided array
     */
    public MyGenericTreeAVL(E[] elements) {
        super(elements);
    }
    
    /**
     * Override createNewNode to create an AVLTreeNode
     */
    @Override
    protected MyAVLNode<E> createNewNode(E e) {
        return new MyAVLNode<>(e);
    }
    
    /**
     * Adds a new element to the AVL tree and performs balancing if necessary
     * Returns: true if operation was executed successfully
     */
    @Override
    public boolean insert(E element) {
        boolean successful = super.insert(element);
        //if adding the node was successful
        // balance nodes from the new node to the root
        if (successful)
            balanceCurrentToRoot(element);
        
        
        return successful;
    }
    
    /**
     * Deletes an element from the AVL tree, and performs balancing if necessary
     * Returns: -true if specified element is removed from the tree.
     * -false if specified element is not in the tree
     */
    @Override
    public boolean delete(E element) {
        //if the root is null there are no elements in the tree, therefore nothing to delete
        if (treeRoot == null)
            return false;
        
        //this part will be the same as in the BST
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
                break;
        }
        
        if (currentNode == null)
            return false;
        
        // the only difference is to the BST implementation is the addition of balancing the Path
        // look at MyGenericTreeBST insert method(in this project) for code explanation
        if (currentNode.left == null) {
            if (parentNode == null) {
                treeRoot = currentNode.right;
            } else {
                if (comparator.compare(element, parentNode.element) < 0)
                    parentNode.left = currentNode.right;
                else
                    parentNode.right = currentNode.right;
                
                // Balance the tree if removing the element has made it imbalanced
                balanceCurrentToRoot(parentNode.element);
            }
        } else {
            // Find the rightmost node (because that node will have the largest value of that whole subtree)
            // in the LEFT subtree of the node to be deleted and also the parent of the left subtree rightmost node
            MyTreeNode<E> parentNodeOfRightMostNode = currentNode;
            MyTreeNode<E> rightMostNode = currentNode.left;
            
            while (rightMostNode.right != null) {
                parentNodeOfRightMostNode = rightMostNode;
                rightMostNode = rightMostNode.right;
            }
            
            // We will not delete the current node,
            // instead we can replace the data in current node by the data in right most node...
            currentNode.element = rightMostNode.element;
            
            // ...and then delete the rightmost node
            if (parentNodeOfRightMostNode.right == rightMostNode)
                parentNodeOfRightMostNode.right = rightMostNode.left;
            else
                // Edge case if parentNodeOfRightMostNode is the current node
                parentNodeOfRightMostNode.left = rightMostNode.left;
            
            // Balance the tree if removing the element has made it imbalanced
            balanceCurrentToRoot(parentNodeOfRightMostNode.element);
        }
        
        size--;
        return true;
    }
    
    /**
     * Balance the nodes starting at the specified node and going up to the root
     * Internal method called when adding new elements or removing the existing ones.
     */
    private void balanceCurrentToRoot(E element) {
        ArrayList<MyTreeNode<E>> pathFromRoot = getPathFromRootToCurrent(element);
        for (int i = pathFromRoot.size() - 1; i >= 0; i--) {
            MyAVLNode<E> currentNode = (MyAVLNode<E>) (pathFromRoot.get(i));
            updateHeight(currentNode);
    
            MyAVLNode<E> parentOfCurrent;
            if (currentNode == treeRoot)
                parentOfCurrent = null;
            else
                parentOfCurrent = (MyAVLNode<E>) (pathFromRoot.get(i - 1));
            
            
            balance(currentNode, parentOfCurrent);
        }
    }
    
    /**
     * Returns: All nodes on the path,
     * starting at the root node, up to and including the specified element
     */
    //this returns MyTreeNode list instead of MyAVLNode
    //because the treeRoot, left node and right node properties are inherited from BST
    // so their reference type is MyTreeNode
    //even though its actual type is MyAVLNode (it's pointing at a MyAVLNode object instance)
    //created by the overwritten createNewNode method
    private ArrayList<MyTreeNode<E>> getPathFromRootToCurrent(E element) {
        ArrayList<MyTreeNode<E>> thePath = new ArrayList<>();
        MyTreeNode<E> currentNode = treeRoot;
        
        while (currentNode != null) {
            thePath.add(currentNode);
            if (comparator.compare(element, currentNode.element) < 0)
                currentNode = currentNode.left;
            else if (comparator.compare(element, currentNode.element) > 0)
                currentNode = currentNode.right;
            else
                break;
        }
        
        return thePath;
    }
    
    private void balance(MyTreeNode<E> currentNode, MyTreeNode<E> parentOfCurrentNode) {
        
        
        if (isLeftHeavy(currentNode)) { //if the imbalance is caused by the left subtree
            
            //if the condition below is true it means the imbalance is LR otherwise it's LL
            //so first the we first need to to left rotation on the left child
            if (getBalanceFactor((MyAVLNode<E>) currentNode.left) < 0)
                currentNode.left = rotateLeft(currentNode.left);
            
            //right rotation gets performed in any case
            if(parentOfCurrentNode == null)
                rotateRight(currentNode);
            else if(parentOfCurrentNode.right == currentNode)
                parentOfCurrentNode.right = rotateRight(currentNode);
            else if(parentOfCurrentNode.left == currentNode)
                parentOfCurrentNode.left = rotateRight(currentNode);
            
        } else if (isRightHeavy(currentNode)) { //if the imbalance is caused by the right subtree
    
            //if the condition below is true it means the imbalance is RL otherwise it's RR
            //so first the we first need to to left rotation on the left child
            if (getBalanceFactor((MyAVLNode<E>) currentNode.right) > 0)
                currentNode.right = rotateRight(currentNode.right);
            
            //left rotation gets performed in any case
            if(parentOfCurrentNode == null)
                rotateLeft(currentNode);
            else if(parentOfCurrentNode.right == currentNode)
                parentOfCurrentNode.right = rotateLeft(currentNode);
            else if(parentOfCurrentNode.left == currentNode)
                parentOfCurrentNode.left = rotateLeft(currentNode);
        }
        
    }
    
    /**
     * performs a left rotation on the specified node(currentNode).
     * This means that newRoot(current's right child) takes currentNode's place
     * newRoots left child becomes the current's new right child
     * and the currentNode becomes newRoot's new left child
     */
    private MyTreeNode<E> rotateLeft(MyTreeNode<E> currentNode) {
        MyTreeNode<E> newRoot = currentNode.right;
        
        currentNode.right = newRoot.left;
        newRoot.left = currentNode;
        
        updateHeight((MyAVLNode<E>) currentNode);
        updateHeight((MyAVLNode<E>) newRoot);
    
        if(treeRoot == currentNode)
            treeRoot = newRoot;
        
        return newRoot;
    }
    
    /**
     * performs a right rotation on the specified node(currentNode).
     * This means that newRoot(current's left child) takes currentNode's place
     * newRoots right child becomes the current's new left child
     * and the currentNode becomes newRoot's new right child
     */
    private MyTreeNode<E> rotateRight(MyTreeNode<E> currentNode) {
        MyTreeNode<E> newRoot = currentNode.left;
        
        currentNode.left = newRoot.right;
        newRoot.right = currentNode;
        
        updateHeight((MyAVLNode<E>) currentNode);
        updateHeight((MyAVLNode<E>) newRoot);
        
        if(treeRoot == currentNode)
            treeRoot = newRoot;
        
        return newRoot;
    }
    
    private boolean isLeftHeavy(MyTreeNode<E> node) {
        return getBalanceFactor((MyAVLNode<E>) node) > 1;
    }
    
    private boolean isRightHeavy(MyTreeNode<E> node) {
        return getBalanceFactor((MyAVLNode<E>) node) < -1;
    }
    
    
    /**
     * Updates the height of a specified node
     */
    private void updateHeight(MyAVLNode<E> currentNode) {
        if (currentNode.left == null && currentNode.right == null) // node is a leaf
            currentNode.height = 0;
        else if (currentNode.left == null) // node has only the right subtree
            currentNode.height = 1 + ((MyAVLNode<E>) (currentNode.right)).height;
        else if (currentNode.right == null) // node has only the left subtree
            currentNode.height = 1 + ((MyAVLNode<E>) (currentNode.left)).height;
        else
            currentNode.height = 1 +
                    Math.max(((MyAVLNode<E>) (currentNode.left)).height,
                            ((MyAVLNode<E>) (currentNode.right)).height);
    }
    
    /**
     * Returns: Specified node's balance factor
     */
    private int getBalanceFactor(MyAVLNode<E> currentNode) {
        if (currentNode.left == null) // has only right subtree
            return -currentNode.height;
        else if (currentNode.right == null) // has only left subtree
            return +currentNode.height;
        else
            return ((MyAVLNode<E>) currentNode.left).height
                    - ((MyAVLNode<E>) currentNode.right).height;
    }
    
    /**
     * The thing i wanted to display with this class inheriting
     * BST and MyAVLNode inheriting MyTreeNode is how sometimes
     * it can just clutter the code without much benefit,
     * all of the casts that need to be performed just to avoid
     * creating a small number of properties and a method or two
     * is not entirely worth it, but what it does provide is extensibility
     * whether that is something that is required depends on many development decisions
     *
     */
    protected static class MyAVLNode<E> extends MyTreeNode<E> {
        protected int height = 0; // New data field
        
        public MyAVLNode(E o) {
            super(o);
        }
    }
}
