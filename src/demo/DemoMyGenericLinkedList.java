package demo;

import mydatastructures.MyGenericLinkedList;

public class DemoMyGenericLinkedList {
  
  
  public static void main(String[] args) {
    // Create a list for strings
    String[] elements = {"Dog", "Cat", "Turtle", "Duck", "Horse"};
    
//      public MyGenericLinkedList(E[] elements)
    System.out.println("Creating a list from the array of Strings...");
    MyGenericLinkedList<String> list = new MyGenericLinkedList<>(elements);
    System.out.println("-1-" + list);
    
//      public void addFirst(E e)
    System.out.println("Adding a rabbit to the start of the list...");
    list.addFirst("Rabbit");
    System.out.println("-2-" + list);
    
//      public void addLast(E e)
    System.out.println("Adding a fox to the end of the list...");
    list.addLast("Fox");
    System.out.println("-3-" + list);

//      public void add(E e)
    System.out.println("Adding a cow to the end of the list...");
    list.add("Cow");
    System.out.println("-4-" + list);

//      public void add(int index, E e)
    System.out.println("Adding a sheep to index 3...");
    list.add(3,"Sheep");
    System.out.println("-5-" + list);

//      public E set(int index, E e)
    System.out.println("Replacing the dog with labrador...");
    list.set(list.indexOf("Dog"), "Labrador");
    System.out.println("-6-" + list);

//      public E getFirst()
    System.out.println("First on the list is: " + list.getFirst());

//      public E getLast()
    System.out.println("Last on the list is: " + list.getLast());

//      public E get(int index)
    System.out.println("Fifth on the list is: " + list.get(4));

//      public boolean contains(Object e)
    System.out.println("Is cow on the list: " + list.contains("Cow"));
    System.out.println("Is eagle on the list: " + list.contains("Eagle"));

//      public int indexOf(Object e)
    System.out.println("What's the Turtle's index" + list.indexOf("Turtle"));
    
//      public E removeFirst()
    System.out.println("Removing first element...");
    list.removeFirst();
    System.out.println("-7-" + list);
//      public E removeLast()
    System.out.println("Removing last element...");
    list.removeLast();
    System.out.println("-8-" + list);
//      public E remove(int index)
    System.out.println("Removing element at index 3...");
    list.remove(3);
    System.out.println("-9-" + list);
//      public void clear()
    System.out.println("Clearing the list...");
    list.clear();
    System.out.println("-10-" + list);
//      public int size()
    System.out.println("Size of the list is..." + list.size());
//      public boolean isEmpty()
    System.out.println("Is the list empty? " + list.isEmpty());

  }
}