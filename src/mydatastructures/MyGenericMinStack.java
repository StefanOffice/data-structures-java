package mydatastructures;

// We need two stacks to implement a min stack.
// One stack holds the values, the other stack
// (called minStack) holds the minimums.
public class MyGenericMinStack<E extends Comparable<E>>{
  //delegating most calls to MyGenericStack, exceptions are handled there
  private final MyGenericStack<E> mainStack = new MyGenericStack<>();
  private final MyGenericStack<E> minStack = new MyGenericStack<>();

  /** Adds the element to the top of the stack */
  public void push(E element) {
    mainStack.push(element);

    if (minStack.isEmpty())
      minStack.push(element);
    //check if the element is smaller than the current smallest element, if so add it to min stack
    //if this is set to compare with > 0 we would get a MaxStack
    else if (element.compareTo(minStack.peek()) < 0)
      minStack.push(element);
  }

  /** Removes the last added element from the stack
   * Returns: the removed element */
  public E pop() {
    E top = mainStack.pop();

    if (minStack.peek().equals(top))
      minStack.pop();
    
    return top;
  }

  /** Returns the smallest element in the stack, but does not remove it */
  public E min() {
    return minStack.peek();
  }
  
  /** Returns the last added element from the stack, but does not remove it */
  public E peek(){
    return mainStack.peek();
  }
  
  public boolean isEmpty(){
    //we only need to check if main stack is empty since
    //elements get popped from the minStack as well when they are popped from the mainStack
    return mainStack.isEmpty();
  }
}
