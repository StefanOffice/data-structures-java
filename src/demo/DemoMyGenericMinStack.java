package demo;


import mydatastructures.MyGenericMinStack;

public class DemoMyGenericMinStack {
    
    public static void main(String[] args) {
        
        MyGenericMinStack<Integer> stack = new MyGenericMinStack<>();
        stack.push(5);
        stack.push(3);
        stack.push(20);
        stack.push(1);
        stack.push(22);
        System.out.println("Minimum element in the stack: " + stack.min());
        System.out.println("Element at the top of the stack: " + stack.peek());
        System.out.println();
        
        while(!stack.isEmpty()){
            System.out.println("Popping top element from the stack: removed element " + stack.pop());
            System.out.println(!stack.isEmpty() ? "Minimum element in the stack after removing top element: " + stack.min() : "Stack is empty");
            System.out.println();
        }
    }
    
}
