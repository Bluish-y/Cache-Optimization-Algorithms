import java.io.*;
import java.util.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

class DynamicArray 
{   
    public int array[];   
    public int count;   
    public int sizeofarray;   
    //creating a constructor of the class that initializes the values  
    public DynamicArray()   
    {   
        array = new int[1];   
        count = 0;   
        sizeofarray = 1;   
    }   
    //creating a function that appends an element at the end of the array  
    public void addElement(int a)   
    {   
        //compares if the number of elements is equal to the size of the array or not  
        if (count == sizeofarray)   
        {   
            //invoking the growSize() method that creates an array of double size      
            growSize();   
        }   
        //appens an element at the end of the array   
        array[count] = a;   
        count++;   
    }   
    //function that creates an array of double size  
    public void growSize()   
    {   
        //declares a temp[] array      
        int temp[] = null;   
        if (count == sizeofarray)   
        {   
            //initialize a double size array of array  
            temp = new int[sizeofarray * 2];    
            for (int i = 0; i < sizeofarray; i++)   
            {   
                //copies all the elements of the old array  
                temp[i] = array[i];   
            }     
        }   
        array = temp;   
        sizeofarray= sizeofarray * 2;   
    }   

}   

public class LRUCache1 {
 
    Set<Integer> cache;
    int capacity;
    int miss=0,hit=0;
    int[] stackDist;
 
    public LRUCache1(int capacity)
    {
        this.cache = new LinkedHashSet<Integer>(capacity);
        this.capacity = capacity;
        this.stackDist = new int[capacity+1];
        Arrays.fill(this.stackDist, 0);
    }
 
    // This function returns false if key is not
    // present in cache. Else it moves the key to
    // front by first removing it and then adding
    // it, and returns true.
    public boolean get(int key)
    {
        if (!cache.contains(key)){
            miss++;
            // System.out.println(key + " " + stackDist[stackDist.length-1]);
            stackDist[stackDist.length-1] += 1;
            return false;
        }
        hit++;
        stackDist[indexOf(key)]+=1;
        cache.remove(key);
        cache.add(key);
        return true;
    }
 
    /* Refers key x with in the LRU cache */
    public void refer(int key)
    {       
        if (get(key) == false)
           put(key);
    }
 
    // displays contents of cache in Reverse Order
    public void display()
    {
      LinkedList<Integer> list = new LinkedList<>(cache);
       
      // The descendingIterator() method of java.util.LinkedList
      // class is used to return an iterator over the elements
      // in this LinkedList in reverse sequential order
      Iterator<Integer> itr = list.descendingIterator();
       
      while (itr.hasNext())
            System.out.print(itr.next() + " ");
    }
     
    public void put(int key)
    {
         
      if (cache.size() == capacity) {
            int firstKey = cache.iterator().next();
            cache.remove(firstKey);
        }
 
        cache.add(key);
    }

    static HashMap<String, Integer> createHashMap(LinkedList<String> arr)
    {
        // Creates an empty HashMap
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
 
        // Traverse through the given array
        int c = 0;
        for (String arrElement : arr) {
 
 
            // If this is first occurrence of element
            // Insert the element
            if (hmap.get(arrElement) == null) {
                hmap.put(arrElement, ++c);
            }
 
            // If elements already exists in hash map
            // Increment the count of element by 1
        }
 
        // Print HashMap
        return hmap;
    }

    static int[] implement(LinkedList<String> traces, HashMap<String,Integer> traceMap, int cap){
        LRUCache1 ca = new LRUCache1(cap);
        int i = 0;
        for(String trace : traces){
            i = traceMap.get(trace);
            ca.refer(i);
        }
        float missRatio = (float)ca.miss/((float)ca.miss+(float)ca.hit);
        System.out.println("Miss Ratio: " + missRatio + "; Capacity: " + cap);
        return ca.stackDist;
        // ca.display();
    }

    public int indexOf(int element)
    {
  
        // If element not present in the LinkedHashSet it
        // returns -1
        int index = -1;
  
        // get an iterator
        Iterator<Integer> iterator = this.cache.iterator();
  
        int currentIndex = 0;
        while (iterator.hasNext()) {
  
            // If element present in the LinkedHashSet
            if (iterator.next().equals(element)) {
                index = currentIndex;
                break;
            }
  
            currentIndex++;
        }
  
        // Return index of the element
        return index;
    }
     
    public static void main(String[] args) throws IOException, CsvException
    {
        
        Scanner sc = new Scanner(new File("D:\\Courses\\3-2\\InformalCache\\Traces\\DRAMSim2\\traces\\aoe_02_short.trc"));  
        sc.useDelimiter(",");   //sets the delimiter pattern  
        LinkedList<String> traces = new LinkedList<String>();
        while (sc.hasNext())  //returns a boolean value  
        {  
        String str = sc.nextLine();
        String[] arrOfStr = str.split(" ", 2);
        traces.add(arrOfStr[0]);
        // System.out.print(arrOfStr[0]);  //find and returns the next complete token from this scanner  
        }  
        sc.close(); 

        ListIterator<String> it = traces.listIterator();
        while(it.hasNext()){
            it.set(it.next().substring(3,8));
            // System.out.println(trace);
        }
        // for(String trace: traces){
        //     System.out.println(trace);
        // }
        HashMap<String,Integer> traceMap = createHashMap(traces);

        int[] stackDist = implement(traces, traceMap, 129);

        for(int i = 0; i < stackDist.length; i++){
            System.out.print(stackDist[i] + " ");
        }

        int[] sums = new int[stackDist.length];
        Arrays.fill(sums,0);
        for(int j = 1; j<stackDist.length; j ++){
            sums[j] += stackDist[j]+sums[j-1];
        }
        for(int i = 0; i<sums.length-1; i++){
            int sum = sums[i];
            System.out.println("Miss Ratio:" + (1-((float)sum/(float)sums[sums.length-1])) + "; Capacity: " + i);
        }

        // int j = 200;
        // while(j>0){
        //     implement(traces, traceMap, j);
        //     j = j - 10;
        // }
    }
}