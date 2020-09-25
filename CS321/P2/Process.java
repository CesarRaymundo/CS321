/**
 * Description: This class defines the characteristics of a CPU Process
 * @author CesarRaymundo
 *
 */
public class Process implements Comparable<Process>{

	private final int ARRIVAL_TIME; //The time the process was made
	private int priorityLevel; //The given priority level of the process
	private int timeRemaining; //The time remaining until the process is completed
	private int timeNotProcessed; //The time the process has not been through the CPU
	
	
	/**
	 * Constructor to create a process for the CPU.
	 * @param currentTime The time the process is made
	 * @param maxProcessTime The time required to finish the process
	 * @param maxLevel The priority level
	 */
	public Process(int currentTime, int requiredProcessTime, int maxLevel) {
		this.ARRIVAL_TIME = currentTime;
		this.priorityLevel = maxLevel;
		this.timeRemaining = requiredProcessTime;
		this.timeNotProcessed = 0;
	}
	
	/**
	 * Returns the priority of the process.
	 * @return The priority
	 */
	public int getPriority() {
		return priorityLevel;
	}

	/**
	 * Sets the priority of the process.
	 * @param priority The priority to set the process to
	 */
	public void setPriority(int priority) {
		this.priorityLevel = priority;
	}

	/**
	 * Returns the time remaining to finish the process.
	 * @return The time remaining to finish the process
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Sets the time remaining to finish the process.
	 * @param timeRemaining The time remaining to be set
	 */
	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
	/**
	 * Reduces the time remaining to process.
	 */
	public void reduceTimeRemaining() {
		timeRemaining--;
		timeNotProcessed = 0;
	}
	
	/**
	 * Resets the time not processed to 0.
	 */
	public void resetTimeNotProcessed() {
		timeNotProcessed = 0;
	}
	
	/**
	 * Returns the time not processed.
	 * @return The time not processed.
	 */
	public int getTimeNotProcessed() {
		return timeNotProcessed;
	}
	
	/**
	 * Increments the time not processed by 1.
	 */
	public void UpdateTimeNotProcessed() {
		timeNotProcessed++;
	}

	/**
	 * Returns the time the process arrived at the CPU.
	 * @return The time of arrival
	 */
	public int getArrivalTime() {
		return ARRIVAL_TIME;
	}

	
	/**
	 * Returns whether the job is finished or not.
	 * @return True if the time remaining is 0, false otherwise
	 */
	public boolean finish() {
		return timeRemaining == 0;
	}

	@Override
	public int compareTo(Process arg0) {
		Process p = (Process) arg0;
		if(priorityLevel > p.getPriority()) {
			return -1;
		}
		else if(priorityLevel < p.getPriority()) {
			return 1;
		}
		else if(ARRIVAL_TIME < p.getArrivalTime()) {
			return -1;
		}
		else if(ARRIVAL_TIME > p.getArrivalTime()){
			return 1;
		}
		return 0;
	}
}