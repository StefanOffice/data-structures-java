package demo;

import mydatastructures.MyGenericArrayList;

public class DemoMyGenericArrayList {
    public static void main(String[] args) {
        // Create a list
        MyGenericArrayList<String> list = new MyGenericArrayList<>();
        
        // Add elements to the list
        list.add("Strawberry"); // Add Strawberry to the end of the list
        System.out.println("-1- " + list + " " + list.size());
        
        list.trimToSize();
        
        list.add(0, "Peach"); // Add Peach to the beginning of the list
        System.out.println("-2- " + list);
        
        list.add("Cherry"); // Add Cherry to the end of the list
        System.out.println("-3- " + list);
        
        list.add("Apple"); // Add Apple to the end of the list
        System.out.println("-4- " + list);
        
        list.add(1, "Banana"); // Add Banana to the list at index 1
        System.out.println("-5- " + list);
        
        list.add(3, "Kiwi"); // Add Kiwi to the list at index 3
        System.out.println("-6- " + list);
        System.out.printf("Does list contain Kiwi? : %b \nIndex of Kiwi %d%n",
                            list.contains("Kiwi"), list.indexOf("Kiwi"));
        System.out.printf("Does list contain Plum? : %b \nIndex of Plum %d%n",
                            list.contains("Plum"), list.indexOf("Plum"));
        // Remove elements from the list
        
        list.remove("Peach"); //remove specific
        System.out.println("-7- " + list);
    
        list.remove(list.size() - 1); // Remove the last element
        System.out.println("-9- " + list);
        
        list.remove(1); // Remove the element at index 1
        System.out.println("-8- " + list);
        
        
        //Iterator demo
        System.out.print("-10- ");
        for (String s: list)
            System.out.print(s.toUpperCase() + " ");
    }
}