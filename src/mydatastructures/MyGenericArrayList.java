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
    
    public E remove(int index) {
        checkIndex(index);
        E e = elements[index];
        for (int j = index; j < size - 1; j++)
            elements[j] = elements[j + 1];
        
        elements[size - 1] = null;
        size--;
        return e;
    }
    
    public E remove(E e){
        int index = indexOf(e);
        return remove(index);
    }
    
    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException
                    ("Index: " + index + ", Size: " + size);
    }
    
    public int indexOf(E e) {
        for (int i = 0; i < size; i++)
            if (e.equals(elements[i])) return i;
        
        return -1;
    }
    
    public int lastIndexOf(E e) {
        for (int i = size - 1; i >= 0; i--)
            if (e.equals(elements[i])) return i;
        
        return -1;
    }
    
    public void clear() {
        @SuppressWarnings("unchecked")
        E[] cleared = (E[])new Object[16];
        elements =cleared;
        size = 0;
    }
    
    public boolean contains(E e) {
        for (int i = 0; i < size; i++)
            if (e.equals(elements[i])) return true;
        
        return false;
    }
    
    public E get(int index) {
        checkIndex(index);
        return elements[index];
    }
    
    public E set(int index, E e) {
        checkIndex(index);
        E temp = elements[index];
        elements[index] = e;
        return temp;
    }
    
    public int size() {
        return size;
    }
    
    public void trimToSize() {
        if (size != elements.length) {
            @SuppressWarnings("unchecked")
            E[] newData = (E[])(new Object[size]);
            System.arraycopy(elements, 0, newData, 0, size);
            
            System.out.println("\nFOR DEMONSTRATION: decreasing internal capacity."
                    + " \n Old Capacity = " + elements.length
                    + " \n New Capacity = " + newData.length
                    + " \n Size = " + size + "\n");
            
            elements = newData;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            sb.append(", ");
        }
        
        if(sb.length() > 2) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    
    
}
