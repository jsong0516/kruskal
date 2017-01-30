/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

    protected List[] list;
    private int size;
    private int num_bucket;



    /** 
     *  Construct a new empty hash table intended to hold roughly sizeEstimate
     *  entries.  (The precise number of buckets is up to you, but we recommend
     *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
     **/

    public HashTableChained(int sizeEstimate) {
       int nana = (int)(sizeEstimate / 0.7);
       int bucket = findPrime(nana);
       num_bucket = bucket;
       list = new DList[bucket];
       for (int i = 0; i < bucket; i++) {
	   list[i] = new DList();
       }
    }

    public int findPrime(int n){
	if (isPrime(n)) {
            return n;
        } else {
            int primeN = n; 
            while (!(isPrime(primeN))) {
                primeN++;
            }
            return primeN;
        }
    }

    public boolean isPrime(int n){
	for (int i = 2; i * i <= n; i++){
	    if(n % i == 0){
		return false;
	    }
	}
	return true;
    }

    /** 
     *  Construct a new empty hash table with a default size.  Say, a prime in
     *  the neighborhood of 100.
     **/

    public HashTableChained() {
	this.list = new List[101];
    }

    /**
     *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
     *  to a value in the range 0...(size of hash table) - 1.
     *
     *  This function should have package protection (so we can test it), and
     *  should be used by insert, find, and remove.
     **/

    int compFunction(int code) {
	// Replace the following line with your solution.
	int result = code % list.length;
	if (result < 0) {
	    result += list.length;
	}
	return result;
    }
    
    /** 
     *  Returns the number of entries stored in the dictionary.  Entries with
     *  the same key (or even the same key and value) each still count as
     *  a separate entry.
     *  @return number of entries in the dictionary.
     **/

    public int size() {
	// Replace the following line with your solution.
	return size;
    }

    /** 
     *  Tests if the dictionary is empty.
     *
     *  @return true if the dictionary has no entries; false otherwise.
     **/

    public boolean isEmpty() {
	// Replace the following line with your solution.
      if (this.size() == 0) {
          return true;
      }
      return false;
    }

    /**
     *  Create a new Entry object referencing the input key and associated value,
     *  and insert the entry into the dictionary.  Return a reference to the new
     *  entry.  Multiple entries with the same key (or even the same key and
     *  value) can coexist in the dictionary.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the key by which the entry can be retrieved.
     *  @param value an arbitrary object.
     *  @return an entry containing the key and value.
     **/

    public Entry insert(Object key, Object value) {
	// Replace the following line with your solution.
	resize();
	int index = compFunction(key.hashCode());
	Entry result = new Entry();
	result.key = key;
	result.value = value;
	if(list[index] == null) {
	    list[index] = new DList();
	    list[index].insertFront(result);
	} else {
	    list[index].insertFront(result);      
	}
	size++;
	return result;
    }



    public void resize() {
	if((size / num_bucket) < 1.0)
	{
		return;
	}
	try {
		int old_num_bucket = num_bucket;
		List[] temp = list;
		num_bucket *= 2;
		list = new DList[num_bucket];
		makeEmpty(); 
		for(int i = 0; i < old_num_bucket; i++)
		{
			DList chain = (DList)temp[i];
			DListNode node = (DListNode)chain.front();
			
	       		for (int j = 0; j < chain.length(); j++) 
			{
				Entry entry = (Entry)node.item();
				Object key = entry.key();
				Object value = entry.value();
				insert(key, value);
				node = (DListNode)node.next();
	       		}
		}
	}catch (InvalidNodeException e) {
	}
    }

    /** 
     *  Search for an entry with the specified key.  If such an entry is found,
     *  return it; otherwise return null.  If several entries have the specified
     *  key, choose one arbitrarily and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     **/

    public Entry find(Object key) {
	// Replace the following line with your solution.
	try{
	    int index = compFunction(key.hashCode());
	    if (list[index] != null){
		ListNode curr = list[index].front();
		while (curr.isValidNode()){
		    Entry na = (Entry) curr.item();
                  if (na.key.equals(key)) {
                      return na;
                  } else {
                      curr = curr.next();
                  }
		}
	    }
	}catch (InvalidNodeException e){
	    System.err.println(e);
	}
	return null;
    }

    /** 
     *  Remove an entry with the specified key.  If such an entry is found,
     *  remove it from the table and return it; otherwise return null.
     *  If several entries have the specified key, choose one arbitrarily, then
     *  remove and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     */

    public Entry remove(Object key) {
      // Replace the following line with your solution.
      
      resize_downgrade();
      int nanaIndex = compFunction(key.hashCode());
      ListNode node = list[nanaIndex].front();
      try {
      if (!node.isValidNode()) {
          return null;
      } else {
          while (node.isValidNode()) {
        	  Entry na = (Entry) node.item();
              if (na.key.equals(key)) {
                  node.remove();
                  size--;
                  return na;
              } else {
                  node = node.next();
              }
          }
      }
      } catch (InvalidNodeException e) {
      }
      return null;
    }


    public void resize_downgrade() {
	if((size / num_bucket) < 0.5)
	{
		return;
	}
	try {
		int old_num_bucket = num_bucket;
		List[] temp = list;
		num_bucket /= 2;
		list = new DList[num_bucket];
		makeEmpty(); 
		for(int i = 0; i < old_num_bucket; i++)
		{
			DList chain = (DList)temp[i];
			DListNode node = (DListNode)chain.front();
			
	       		for (int j = 0; j < chain.length(); j++) 
			{
				Entry entry = (Entry)node.item();
				Object key = entry.key();
				Object value = entry.value();
				insert(key, value);
				node = (DListNode)node.next();
	       		}
		}
	}catch (InvalidNodeException e) {
	}
    }


    /**
     *  Remove all entries from the dictionary.
     */
    public void makeEmpty() {
	for (int i = 0; i < list.length; i++){
	    list[i] = new DList();
	}
	this.size = 0;
    }
}
