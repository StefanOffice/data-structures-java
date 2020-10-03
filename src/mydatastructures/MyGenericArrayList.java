package mydatastructures;

//An ArrayList represents a variable size list data structures
// elements can be added and removed
// ArrayList overcomes the limit of static arrays which have fixed capacity that needs to be specified at instantiation
// As elements are added to the ArrayList its capacity automatically grows
//      Example below is my implementation of it
//      Complex class name is used to avoid clashes with any existing implementations in java libraries
public class MyGenericArrayList<E> {
    
    @SuppressWarnings("unchecked")
    private E[] elements = (E[]) new Object[16];
    private int size = 0;
    
    public MyGenericArrayList() {}
    public MyGenericArrayList(E[] elements) {
        for (E element : elements)
            add(size, element);
    }
    
    public void add(E e){
        add(size , e);
    }
    
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException
                    ("Index: " + index + ", Size: " + size);
        
        guaranteeCapacity();
        for (int i = size - 1; i >= index; i--)
            elements[i + 1] = elements[i];
        
        elements[index] = e;
        size++;
    }
    
    private void guaranteeCapacity() {
        if (size >= elements.length) {
            @SuppressWarnings("unchecked")
            E[] newData = (E[]) (new Object[size * 2 + 1]);
            System.arraycopy(elements, 0, newData, 0, size);
            System.out.println("\nFOR DEMONSTRATION: increasing internal capacity."
                    + " \n Old Capacity = " + elements.length
                    + " \n New Capacity = " + newData.length
                    + " \n Size = " + size + "\n");
            elements = newData;
        }
    }
    
}
