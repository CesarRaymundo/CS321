/**
 * A queue data structure of Process objects, implemented by using MaxHeap
 * @author CesarRaymundo
 *
 */
public class PQueue {

	private MaxHeap queue;
	
	/**
	 * Constructor to build PriorityQueue from MaxHeap
	 */
	public PQueue() {
		queue = new MaxHeap();
	}
	
	/**
	 * Adds Process to the end of queue, then organizes by priority
	 * @param p The process to be added
	 */
	public void enPQueue(Process p) {
		queue.maxHeapInsert(p);
	}
	
	/**
	 * Gets Process with the most priority in queue.
	 * @return The process with the most priority
	 */
	public Process dePQueue() {
		return queue.extractMax();
	}
	
	/**
	 * To determine if queue is empty or not
	 * @return True if the queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return queue.getHeapSize() == 0;
	}

	/**
	 * Updates the priority levels of all processes in the array
	 * @param timeToIncrement; The time splits processed before increasing the priority level
	 * @param maxLevel; The highest priority level a process can be in the queue
	 */
	public void update(int timeToIncrement, int maxLevel) {
		if(!queue.isEmpty()) {
			for(int i = 1; i <= queue.getHeapSize(); i++) {
				Process p = queue.getHeap()[i];
				p.UpdateTimeNotProcessed();
				
				if(p.getTimeNotProcessed() >= timeToIncrement) {
					p.resetTimeNotProcessed();
					if(p.getPriority() < maxLevel) {
						p.setPriority(p.getPriority() + 1);
						queue.buildMaxHeap();
					}
				}
			}
		}
	}
	
}