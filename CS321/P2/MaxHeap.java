/**
 * A MaxHeap structured array of processes and to implement a priority queue 
 *  and Process class. 
 * @author CesarRaymundo
 * 
 */
public class MaxHeap {
	
	private int heapSize;//The size of MaxHeap in this project and index at 0 will as be pointed as "null"
	private Process[] heap; //The array that will keep track of the processes.
	private final int STARTING_CAPACITY = 100;// The starting array size for all the processes
	/**
	 * Constructor for MaxHeap. 
	 * It will set the array size to 100 and heapSize to 0
	 */
	public MaxHeap() {
		heap = new Process[STARTING_CAPACITY];
		heapSize = 0;
	}

	
	/**
	 * Heapify upwards based on a given index.
	 * @param index The index from which to heapify upwards
	 */
	public void maxHeapifyUp(int index) {
		//TODO
		if (index > 0 && index < heapSize && heap[index].compareTo(heap[parent(index)])> 0) {
			Process tmp = heap[index];
			heap[index] = heap[parent(index)];
			heap[parent(index)] = tmp;
			maxHeapifyUp(parent(index));
		}
	}
	/**
	 * Sorts the heap array into a MaxHeap binary tree structure, meaning the left
	 * and right child are always less than the parent. 
	 * @param i; the index to start the MaxHeapify process.
	 */
	public void maxHeapifyDown(int i) {
		int largest = i;
		int right = rightChild(i);
		int left = leftChild(i);
		

		if (left <= heapSize && heap[left].compareTo(heap[largest]) < 0) {
			largest = left;
		}
		if (right <= heapSize && heap[right].compareTo(heap[largest]) < 0) {
			largest = right;
		}
		if (largest != i) {
			Process temp = heap[i];
			heap[i] = heap[largest];
			heap[largest] = temp;
			maxHeapifyDown(largest);
		}
	}

	/**
	 * Sorts the entire heap of Process objects into MaxHeap Structure using
	 * MaxHeapifyDown.
	 */
	public void buildMaxHeap() {
		for(int i = heapSize/2; i >= 1; i--) {
			maxHeapifyDown(i);
		}
	}
	
	/**
	 * Extracts the highest priority Process object in the heap. This object will
	 * always be at index 1.
	 * @return The highest priority process
	 */
	public Process extractMax() {
		if(heapSize < 1) {
			throw new RuntimeException("Heap Underflow");
		}
		Process max = heap[1];
		heap[1] = heap[heapSize];
		heapSize--;
		maxHeapifyDown(1);
		return max;
	}
	
	/**
	 * Inserts a Process object into the heap and sorts it
	 * into the correct index
	 * @param key The Process object to be inserted.
	 */
	public void maxHeapInsert(Process key) {
		heapSize++;
		heap[heapSize] = key;
		heapIncreaseKey(heapSize, key);
	}
	
	/**
	 * A method to help with maxHeapInsert() method.
	 * @param i The index of the Process object 
	 * @param key The Process object being inserted
	 */
	private void heapIncreaseKey(int i, Process key) {
		if(key.compareTo(heap[i]) > 0) {
			throw new RuntimeException("New key must be larger than current key");
		}
		while(i > 1 && heap[parent(i)].compareTo(heap[i]) > 0) {
			Process temp = heap[i];
			heap[i] = heap[parent(i)];
			heap[parent(i)] = temp;
			i = parent(i);
		}
	}
	
	/**
	 * Returns the size of the heap.
	 * @return The size of the heap.
	 */
	public int getHeapSize() {
		return heapSize;
	}
	
	/**
	 * Returns true if the heap is empty, false otherwise.
	 * @return True if the heap is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return heapSize == 0;
	}
	
	/**
	 * Returns heap.
	 * @return heap
	 */
	public Process[] getHeap() {
		return heap;
	}
	
	/**
	 * Returns the index of the left child of the given index.
	 * @param i The current index
	 * @return The index of the left child of the current index
	 */
	private int leftChild(int i) {
		return 2*i;
	}
	
	/**
	 * Returns the index of the right child of the given index.
	 * @param i The current index
	 * @return The index of the right child of the current index
	 */
	private int rightChild(int i) {
		return 2*i+1;
	}
	
	/**
	 * Returns the index of the parent of the given index (meaning the current
	 * index is a left or right child).
	 * @param i The current index
	 * @return The index of the parent of the current index
	 */
	private int parent(int i) {
		return i/2;
	}

}
	