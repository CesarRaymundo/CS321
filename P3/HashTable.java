import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
/**
 * Description: A class with two arrays. One is used for linear hashing and the
 * second is used for double hashing
 * 
 * @author Cesar Raymundo
 *
 */
public class HashTable {

	private int tableSize; // the size of the arrays
	private HashObject<?>[] doubleHashTable; // array using double hashing
	private HashObject<?>[] linearHashTable; // array using linear hashing
	private long totalNonDuplicates = 0; // keeps track of how many objects entered are not duplicates that are added to
	// array
	private long totalDuplicates = 0; // keeps track of how many duplicates are entered in the array

	/**
	 * Constructor to build arrays and establish variables
	 * 
	 * @param highPrime: The higher of two twin primes to be used as the table size
	 */
	public HashTable(int highPrime) {
		tableSize = highPrime;
		doubleHashTable = new HashObject[tableSize];
		linearHashTable = new HashObject[tableSize];
	}

	/**
	 * Inserts an object into the linear hashing array using linear hashing
	 * 
	 * @param primaryHashValue: The primary hash value to be used
	 * @param object:           The object to be inserted
	 */
	public void insertLinear(int primaryHashValue, HashObject<?> object) {
		int i = 0;
		int j = primaryHashValue;
		while (i < tableSize) {
			if (linearHashTable[j] != null && linearHashTable[j].equals(object)) {
				linearHashTable[j].incrementDuplicates();
				totalDuplicates++;
				return;
			} else if (linearHashTable[j] == null || linearHashTable[j].isDeleted() == true) {
				linearHashTable[j] = object;
				object.incrementProbes();
				totalNonDuplicates++;
				return;
			}
			object.incrementProbes();
			i++;
			j++;
			if(j >= tableSize) {
				j = j - tableSize;
			}
		}
	}

	/**
	 * Inserts an object into the double hashing array using double hashing
	 * 
	 * @param keyVal: The primary hash value of the object to be inserted
	 * @param object: The object to be inserted into the array
	 */
	public void insertDouble(int keyVal, HashObject<?> object) {
		int i = 0;
		int j = keyVal;
		int h2key = 1 + ((object.getKey().hashCode()) % (tableSize - 2));
		if (h2key < 0) {
			h2key += (tableSize - 2);
		}

		while (i < tableSize) {
			if (doubleHashTable[j] != null && doubleHashTable[j].equals(object)) {
				doubleHashTable[j].incrementDuplicates();
				totalDuplicates++;
				return;
			} else if (doubleHashTable[j] == null || doubleHashTable[j].isDeleted() == true) {
				doubleHashTable[j] = object;
				object.incrementProbes();
				totalNonDuplicates++;
				return;
			}
			object.incrementProbes();
			i++;

			j = (keyVal + i * h2key) % tableSize;

		}
	}

	/**
	 * Returns the amount of non-duplicate elements in the array
	 * 
	 * @return The amount of non-duplicate elements in the array
	 */
	public long getTotalNonDuplicates() {
		return totalNonDuplicates;
	}

	/**
	 * Returns the amount of duplicate elements in the array
	 * 
	 * @return The amount of duplicate elements in the array
	 */
	public long getTotalDuplicates() {
		return totalDuplicates;
	}

	/**
	 * Adds up all the probes of every non-duplicate element in the linear array
	 * 
	 * @return The sum of all the probes of every non-duplicate element in the
	 *         linear array
	 */
	public long linearSum() {
		long sum = 0;
		for (int i = 0; i < tableSize; i++) {
			if (linearHashTable[i] != null) {
				sum += linearHashTable[i].getProbes();
			}
		}
		return sum;
	}

	/**
	 * Adds up all the probes of every non-duplicate element in the double array
	 * 
	 * @return The sum of all the probes of every non-duplicate element in the
	 *         double array
	 */
	public long doubleSum() {
		long sum = 0;
		for (int i = 0; i < tableSize; i++) {
			if (doubleHashTable[i] != null) {
				sum += doubleHashTable[i].getProbes();
			}
		}
		return sum;
	}

	/**
	 * Returns the average amount of probes per non-duplicate element in the linear
	 * array
	 * 
	 * @return The average amount of probes per non-duplicate element in the linear
	 *         array
	 */
	public double getAvgProbesLinear() {
		return (linearSum() / (double) (totalNonDuplicates));
	}

	/**
	 * Returns the average amount of probes per non-duplicate element in the double
	 * array
	 * 
	 * @return The average amount of probes per non-duplicate element in the double
	 *         array
	 */
	public double getAvgProbesDouble() {
		return (doubleSum() / (double) (totalNonDuplicates));
	}

	/**
	 * Creates a file called linear-dump with the linear hash array printed
	 * 
	 * @throws IOException
	 */
	public void createLinearDump() throws IOException {

		File file = null;
		Writer writer = null;

		file = new File("linear-dump");
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			for (int i = 0; i < tableSize; i++) {
				if (linearHashTable[i] != null) {
					writer.write("table[" + i + "]: " + linearHashTable[i].toString() + "\n");
				}
			}
		} catch (IOException ex) {

		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}

	}

	/**
	 * Creates a file called double-dump with the double hash array printed
	 * 
	 * @throws IOException
	 */
	public void createDoubleDump() throws IOException {
		File file = null;
		Writer writer = null;

		file = new File("double-dump");
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			for (int i = 0; i < tableSize; i++) {
				if (doubleHashTable[i] != null) {
					writer.write("table[" + i + "]: " + doubleHashTable[i].toString() + "\n");
				}
			}
		} catch (IOException ex) {

		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
		
	}
}