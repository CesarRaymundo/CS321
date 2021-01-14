import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Description: Tests the HashTable and HashObject classes
 * 
 * @author Cesar Raymundo
 *
 */
public class HashTest {

	/**
	 * The main program
	 * 
	 * @param args String array of arguments
	 */
	public static void main(String[] args) {

		int inputType = 0;
		double loadFactor = 0.0;
		int debugLevel = 2;

		// check length
		if (args.length > 3 || args.length < 2) {
			System.out.println("Usage: java HashTest <input type> <load factor> [<debug level>]");
			System.exit(-1);
		}

		// check input type
		String input = args[0];
		if (input.equals("1")) {
			inputType = 1;
		} else if (input.equals("2")) {
			inputType = 2;
		} else if (input.equals("3")) {
			inputType = 3;
		} else {
			System.out.println("Usage: java HashTest <input type> <load factor> [<debug level>]");
			System.exit(-1);
		}

		// checks load factor
		try {
			loadFactor = Double.parseDouble(args[1]);
		} catch (Exception e) {
			System.out.println("Usage: java HashTest <input type> <load factor> [<debug level>]");
		}

		// check debug level
		if (args.length == 3 && args[2].equals("0")) {
			debugLevel = 0;
		} else if (args.length == 3 && args[2].equals("1")) {
			debugLevel = 1;
		} else if (args.length == 3) {
			System.out.println("Usage: java HashTest <input type> <load factor> [<debug level>]");
		}

		// get primes needed for the test
		PrimeGen gen = new PrimeGen(95500, 96000);
		int highPrime = gen.getHighPrime();

		// start the test for the hashtables
		double alpha = 0;
		int tableSize = highPrime;
		HashTable linearTable = new HashTable(tableSize);
		HashTable doubleTable = new HashTable(tableSize);

		HashObject<?> currentObjectLinear = null;
		HashObject<?> currentObjectDouble = null;
		Random rand = null;
		File file = null;
		Scanner scanner = null;

		if (inputType == 1) {
			rand = new Random();
		} else if (inputType == 3) {
			file = new File("word-list");
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		while (alpha < loadFactor) {

			if (inputType == 1) {
				int randNum = rand.nextInt();
				currentObjectLinear = new HashObject<Integer>(randNum);
				currentObjectDouble = new HashObject<Integer>(randNum);

			} else if (inputType == 2) {
				Long currentTime = System.currentTimeMillis();
				currentObjectLinear = new HashObject<Long>(currentTime);
				currentObjectDouble = new HashObject<Long>(currentTime);

			} else {
				if (scanner.hasNextLine()) {
					String currentLine = scanner.nextLine();
					currentObjectLinear = new HashObject<String>(currentLine);
					currentObjectDouble = new HashObject<String>(currentLine);

				}
			}

			int primaryHashValue = getHashValue(currentObjectLinear, tableSize);

			linearTable.insertLinear(primaryHashValue, currentObjectLinear);
			doubleTable.insertDouble(primaryHashValue, currentObjectDouble);

			alpha = linearTable.getTotalNonDuplicates() / ((double) highPrime);
		}

		// calc summary
		System.out.println("A good table size is found: " + tableSize);
		System.out.println("Data source type: " + sourceType(inputType) + "\n\n");

		System.out.println("Using Linear Hashing....");
		long sumDuplicatesLin = linearTable.getTotalDuplicates();
		long totalElementsLin = (linearTable.getTotalNonDuplicates() + sumDuplicatesLin);
		double avgProbesLin = linearTable.getAvgProbesLinear();

		System.out.println("Input " + totalElementsLin + " elements, of which " + sumDuplicatesLin + " duplicates");
		System.out.println("load factor = " + loadFactor + ", Avg. no. of probes " + avgProbesLin);

		System.out.println("\n\nUsing Double Hashing....");
		long sumDuplicatesDub = doubleTable.getTotalDuplicates();
		long totalElementsDub = (doubleTable.getTotalNonDuplicates() + sumDuplicatesDub);
		double avgProbesDub = doubleTable.getAvgProbesDouble();

		System.out.println("Input " + totalElementsDub + " elements, of which " + sumDuplicatesDub + " duplicates");
		System.out.println("load factor = " + loadFactor + ", Avg. no. of probes " + avgProbesDub);

		// dump files
		if (debugLevel == 1) {
			try {
				linearTable.createLinearDump();
				doubleTable.createDoubleDump();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	} // end main

	/**
	 * Returns the hash value of the given object
	 * 
	 * @param currObject The object from which the hash value is obtained
	 * @param tableSize  The size of the table in which it will be entered
	 * @return The hash value
	 */
	private static <T> int getHashValue(HashObject<?> currentObject, int tableSize) {
		T key = (T) currentObject.getKey();
		int code = key.hashCode();
		int primaryHashValue = code % tableSize;
		if (primaryHashValue < 0) {
			primaryHashValue += tableSize;
		}
		return primaryHashValue;
	}

	/**
	 * Returns a String of the data being used
	 * 
	 * @param inputType
	 * @return The data being used
	 */
	private static String sourceType(int inputType) {
		if (inputType == 1) {
			return "integer";

		} else if (inputType == 2) {
			return "System.currentTimeMillis";

		} else {
			return "word-list";

		}

	}

}
