import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The driver class that tests the implentation of Cache and can be used for one
 * or two level cache
 * 
 * @author CesarRaymundo
 *
 */
public class Test {

	/**
	 * Tests the Cache class. Then prints out results of first level or second level
	 * cache, with global hit ratio, hit number, and references numbered.
	 * 
	 * @param args the array of args will be used since we are using command line
	 *             arguments in this class.
	 * 
	 */
	public static void main(String[] args) {
		// Instantiating Cache levels
		Cache<String> cacheLevel1 = null;
		Cache<String> cacheLevel2 = null;

		// Variable used in the driver class
		double HR1 = 0; // 1st level cache hit ratio
		double HR2 = 0; // 2nd level cache hit ratio
		double HR = 0; // global cache hit ratio
		double NH1 = 0; // number of 1st level cache hits
		double NH2 = 0; // number of 2nd level cache hits
		double NH = 0; // total number of cache hits
		double NR1 = 0; // number of references to 1st level cache
		double NR2 = 0; // number of references to 2nd level cache
		double NR = 0; // total references to cache 
		
		String testPrompt = "To test 1 level Cache: 1 <Cache Size> <Input textfile name>" + "\n"
				+ "To test 2 level Cache: 2 <1st-Level Cache Size> <2nd-Level Cache Size> <Input Textfile Name>" + "\n"
				+ "\n";

		// When cache level is 1
		if (args.length == 3 && args[0].equals("1")) {
			File file = new File(args[2]);
			cacheLevel1 = new Cache<String>(Integer.parseInt(args[1]));

			try {
				Scanner lineScan;
				lineScan = new Scanner(file);
				while (lineScan.hasNext()) {
					String currentLine = lineScan.next();
					Scanner wordScan = new Scanner(currentLine);
					while (wordScan.hasNext()) {
						String currentWord = wordScan.next();
						if (cacheLevel1.contains(currentWord)) {
							cacheLevel1.addFirst(currentWord);
							NH1++;
						} else {
							cacheLevel1.addObject(currentWord);
						}
						NR1++;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.print(testPrompt);
			}

		}
		// when cache level is 2
		else if (args.length == 4 && args[0].equals("2")) {
			File file = new File(args[3]);
			cacheLevel1 = new Cache<String>(Integer.parseInt(args[1]));
			cacheLevel2 = new Cache<String>(Integer.parseInt(args[2]));

			try {
				Scanner lineScan = new Scanner(file);
				while (lineScan.hasNextLine()) {
					String currLine = lineScan.nextLine();
					Scanner wordScan = new Scanner(currLine);
					while (wordScan.hasNext()) {
						String currWord = wordScan.next();
						if (cacheLevel1.contains(currWord)) {
							cacheLevel1.addFirst(currWord);
							if (cacheLevel2.contains(currWord)) {
								cacheLevel2.addFirst(currWord);
							}
							NH1++;
						} else if (!cacheLevel1.isFull()) {
							cacheLevel1.addObject(currWord);
							cacheLevel2.addObject(currWord);
							NR2++;
						} else {
							if (cacheLevel2.contains(currWord)) {
								cacheLevel2.addFirst(currWord);
								cacheLevel1.addObject(currWord);
								NH2++;
							} else {
								cacheLevel2.addObject(currWord);
								cacheLevel1.addObject(currWord);
							}
							NR2++;
						}
						NR1++;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.print(testPrompt);
			}
		} else {
			System.out.println(testPrompt);
			System.exit(1);
		}

		// Calculating the hit ratio of Cache level 1 and 2
		HR1 = NH1 / NR1;
		HR2 = NH2 / NR2;
		// This only prints when only 1 level cache is created.
		if (args[0].equals("1")) {
			HR = NH / NR;
			System.out.println("First level cache with " + cacheLevel1.size() + " entries has been created");
			System.out.println("------------------------------------------------------------");
			System.out.println("The number of references: " + NR1);
			System.out.println("The number of cache hits: " + NH1);
			System.out.println("The cache hit ratio: " + HR1);
		}

		// Prints when a 2 level cache is created.
		else if (args[0].equals("2")) {
			NR = NR1;
			NH = NH1 + NH2;
			HR = (NH1 + NH2) / NR1;

			System.out.println("First level cache with " + cacheLevel1.size() + " entries has been created");
			System.out.println("Second level cache with " + cacheLevel2.size() + " entries has been created");
			System.out.println("------------------------------------------------------------");
			System.out.println("The number of global references: " + NR);
			System.out.println("The number of global cache hits: " + NH);
			System.out.println("The global hit ratio: " + HR + "\n");
			System.out.println("The number of 1st-level references: " + NR1);
			System.out.println("The number of 1st-level cache hits: " + NH1);
			System.out.println("The 1st-level cache hit ratio: " + HR1 + "\n");
			System.out.println("The number of 2nd-level references: " + NR2);
			System.out.println("The number of 2nd-level cache hits: " + NH2);
			System.out.println("The 2nd-level cache hit ratio: " + HR2 + "\n");
		}

	}

}