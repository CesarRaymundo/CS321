import java.util.Random;

/**
 * Creates processes with random values but not the time of arrival value.
 * @author CesarRaymundo
 *
 */
public class ProcessGenerator {

	public final double PROBABILITY; //The probability a new process is generated
	
	/**
	 * Constructor to establish probability.
	 * @param probability; The probability that a new process is created
	 */
	public ProcessGenerator(double prob) {
		PROBABILITY = prob;
	}
	
	/**
	 * Use the probability of the Process Generator to determine whether to create a process or not
	 * @return True if a new process is to be made, false otherwise
	 */
	public boolean query() {
		Random rand = new Random();
		double event = rand.nextDouble();
		if(event < PROBABILITY) {
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a new process with random values, except for arrival time.
	 * @param currentTime The current time slice.
	 * @param maxProcessTime The maximum required process time allowed.
	 * @param maxLevel The maximum priority level allowed
	 * @return
	 */
	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		Random rand = new Random();
		int ct = currentTime;
		int mpt = rand.nextInt(maxProcessTime)+1;
		int ml = rand.nextInt(maxLevel)+1;
		return new Process(ct, mpt, ml);
	}
}
