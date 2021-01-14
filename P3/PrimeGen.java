import java.util.Random;

/**
 * Description: Finds the two twin primes from a given range of values
 * 
 * @author Cesar Raymundo
 *
 */
public class PrimeGen {

	private int lowPrime = 0; // the lowest of the twin primes
	private int highPrime = 0; // the highest of the twin primes

	/**
	 * Constructor to find primes
	 * 
	 * @param min Lower range bound
	 * @param max Upper range bound
	 */
	public PrimeGen(int min, int max) {
		int i = min;
		boolean lowSet = false;

		while (lowSet == false && (min < max)) {
			if (isPrime(i) && isPrime(i + 2)) {
				lowPrime = i;
				highPrime = i + 2;
				lowSet = true;
			}
			i++;
		}
	}


	/**
	 * Returns true if the argument is prime
	 * 
	 * @param testn The number to be tested
	 * @return True if the argument is prime
	 */
	private boolean isPrime(int testn) {
		boolean rtn = false;
		Random rand = new Random();
		int base = rand.nextInt(testn - 2) + 2;

		if (aModP(base, testn - 1) == 1) {
			base = rand.nextInt(testn - 2) + 2;
			if (aModP(base, testn - 1) == 1) {
				rtn = true;
			}
		}

		return rtn;
	}

	/**
	 * Converts a given int to binary
	 */
	private int[] intToBinary(int n) {
		    String s = "";
		    while (n > 0)
		    {
		        s =  ( (n % 2 ) == 0 ? "0" : "1") +s;
		        n = n / 2;
		    }
		

		int[] result = new int[s.length()];
		for (int i = 0; i < result.length; i++) {
			if (s.charAt(i) == '1') {
				result[i] = 1;
			} else {
				result[i] = 0;
			}
		}

		return result;
	}

	/**
	 * Returns base^power mod power+1
	 */
	private long aModP(int base, int power) {
		long rtn = base;
		// Convert to binary
		int[] powerBinary = intToBinary(power);
		for (int i = 1; i < powerBinary.length; i++) {
			if (powerBinary[i] == 1) {
				rtn = (rtn * rtn) % (power + 1);
				rtn = (rtn * base) % (power + 1);
			} else {
				rtn = (rtn * rtn) % (power + 1);
			}
		}

		return rtn;
	}

	/**
	 * Returns the lower twin prime
	 */
	public int getLowPrime() {
		return lowPrime;
	}

	/**
	 * Returns the higher twin prime
	 */
	public int getHighPrime() {
		return highPrime;
	}

	@Override
	public String toString() {
		String rtn = lowPrime + " " + highPrime;
		return rtn;
	}
}
