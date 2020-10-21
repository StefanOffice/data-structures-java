package demo;

import mydatastructures.MyGenericTreeAVL;
import mydatastructures.MyGenericTreeBST;

public class DemoMyGenericAVLTree {
  
  public static void main(String[] args) {
    System.out.println("***\nBST\n***");
    testTree(new MyGenericTreeBST<>(new Integer[]{25, 20, 5}));
    System.out.println("***\nAVL\n***");
    testTree(new MyGenericTreeAVL<>(new Integer[]{25, 20, 5}));
  }
  
  public static void testTree(MyGenericTreeBST<Integer> tree) {

    System.out.print("After inserting 25, 20, 5:");
    printTree(tree);

    tree.insert(34);
    tree.insert(50);
    System.out.print("\nAfter inserting 34, 50:");
    printTree(tree);

    tree.insert(30);
    System.out.print("\nAfter inserting 30");
    printTree(tree);

    tree.insert(10);
    System.out.print("\nAfter inserting 10");
    printTree(tree);

    tree.delete(34);
    tree.delete(30);
    tree.delete(50);
    System.out.print("\nAfter removing 34, 30, 50:");
    printTree(tree);

    tree.delete(5);
    System.out.print("\nAfter removing 5:");
    printTree(tree);
    
    System.out.print("\nTraverse the elements in the tree: ");
    for (int e: tree) {
      System.out.print(e + " ");
    }
    System.out.println();
  }

  public static void printTree(MyGenericTreeBST<?> tree) {
    // Traverse tree
    System.out.print("\nInorder (sorted): ");
    tree.printElementsInorder();
    System.out.print("\nPostorder: ");
    tree.printElementsPostorder();
    System.out.print("\nPreorder: ");
    tree.printElementsPreorder();
    System.out.println("\nThe number of nodes is " + tree.getSize());
    System.out.println("The root is " + tree.getTreeRoot().getElement());
    System.out.println();
  }
}
