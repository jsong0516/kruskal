package graphalg;
/* ListSorts.java */

import queue.*;

public class ListSorts {

  private final static int SORTSIZE = 100;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    // Replace the following line with your solution.
		 LinkedQueue result = new LinkedQueue();
		 try {
			 while (!q.isEmpty()) {
				 LinkedQueue singleQ = new LinkedQueue();
				 Comparable item = (Comparable) q.dequeue();
				 singleQ.enqueue(item);
				 result.enqueue(singleQ);
			 }
		 } catch (QueueEmptyException e) {
			 	System.err.println(e);
		 }
		 return result;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
    // Replace the following line with your solution.
	  LinkedQueue result = new LinkedQueue();

	  if(q1.isEmpty() && q2.isEmpty()) {
		  return result;
		  
	  } else {
		  try {
			  while (!(q1.isEmpty() && q2.isEmpty())) {
				  if (q1.isEmpty()) {
					  while (!q2.isEmpty()) {
						 result.enqueue(q2.dequeue()); 
					  }
				  } else if (q2.isEmpty()) {
					  while(!q1.isEmpty()) {
						 result.enqueue(q1.dequeue());
					  }
				  } else {
					  Comparable item1 = (Comparable) q1.front();
					  Comparable item2 = (Comparable) q2.front();
					  if(item1.compareTo(item2) <= 0) {
						  //enqueue q1's element
						  result.enqueue(q1.dequeue());
					  } else {
						  //enqueue q2's element
						  result.enqueue(q2.dequeue());
					  }
				  }
			  }
		  } catch (QueueEmptyException e) {
	  			System.err.println(e);
		  }
	  }
	  return result;
			
  }


  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    // Your solution here.
	  try{
		  while(!qIn.isEmpty()) {
			  Comparable item = (Comparable) qIn.dequeue();
			  if (pivot.compareTo(item) > 0) {
				  qSmall.enqueue(item);
			  } else if (pivot.compareTo(item) < 0) {
				  qLarge.enqueue(item);
			  } else {
				  qEquals.enqueue(item);
			  }
		  }
	  } catch (QueueEmptyException e){	  	
	        System.err.println(e);
	  }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    // Your solution here.
	  LinkedQueue result = makeQueueOfQueues(q);
	  //after the above line the q is empty
	  try{
		  while(result.size() > 1) {
			  LinkedQueue q1 = (LinkedQueue) result.dequeue();
			  LinkedQueue q2 = (LinkedQueue) result.dequeue();
			  LinkedQueue q3 = mergeSortedQueues(q1, q2);
			  result.enqueue(q3);  
		  }
		  q.append((LinkedQueue) result.dequeue());
	  } catch (QueueEmptyException e){	  	
	        System.err.println(e);
	  }
  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
	  if (q.isEmpty()){
          return;
      }
	  LinkedQueue qSmall = new LinkedQueue();
      LinkedQueue qEquals = new LinkedQueue();
      LinkedQueue qLarge = new LinkedQueue();
      partition(q, (Comparable)q.nth((int)((q.size() / 2) + 1)), qSmall, qEquals, qLarge);
      //recursive call
      quickSort(qSmall);
      quickSort(qLarge);
      // after sorted small, equal, large we append them in order
      q.append(qSmall);
      q.append(qEquals);
      q.append(qLarge);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

  }
}
