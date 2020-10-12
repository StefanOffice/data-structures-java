package demo;


import mydatastructures.MyGenericHashMap;

public class DemoMyGenericHashMap {
  public static void main(String[] args) {
    // Create a map
    MyGenericHashMap<String, Integer> map = new MyGenericHashMap<>();
    System.out.println("Adding Jasmine, John, Clark, Lucy, Bettie...");
    
    map.put("Jasmine", 25);
    map.put("John", 31);
    map.put("Clark", 52);
    map.put("Lucy", 43);
    map.put("Bettie", 31);
    
    System.out.println("Printing all keys in the map...");
    for (String key : map.keySet()) {
      System.out.print(key + " ");
    }
    System.out.println();
    
    System.out.println("Printing all values in the map...");
    for(Integer value : map.values()){
      System.out.print(value + " ");
    }
    System.out.println();
  
    System.out.println("All employees from the map: " + map);
    System.out.println("Number of entries: " + map.size());

    System.out.println("The age for " + "Jasmine is " +
      map.get("Jasmine"));

    System.out.println("Is Clark in the map? " +
      map.containsKey("Smith"));
    System.out.println("Is anyone of age 33 in the map? " +
      map.containsValue(33));
  
    System.out.println("Removing John...");
    map.remove("John");
    System.out.println("All employees from the map: " + map);
  
    System.out.println("Clearing the map...");
    map.clear();
    System.out.println("Employees in the map after clearing: " + map);
  }
}