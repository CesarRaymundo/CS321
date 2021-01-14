import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Extract DNA Sequences and Generate DNA Subsequences
 */
public class SequenceUtils
{
    public static final byte a = 0b00, t = 0b11, c = 0b01, g = 0b10;
    private static final String MARKER_START_DNA_SEQUENCE = "ORIGIN";
    private static final String MARKER_END_DNA_SEQUENCE = "//";
    private static final String NO_SEQUENCES_FOUND = "No sequences found in file provided.";

    public static ArrayList<String> getDNASequencesFromGBKGenomeFile(String gbkGenomeFileName) throws FileNotFoundException, IOException
    {
        //first, check to make sure the file exists
        if (!new File(gbkGenomeFileName).exists())
            throw new FileNotFoundException("The .gbk file wasn't found.");


        ArrayList<String> DNASequences = new ArrayList<String>();
        FileReader fr = new FileReader(gbkGenomeFileName);
        BufferedReader bReader = new BufferedReader(fr);
        String currentLine = "";
        String sequence = "";
        char currentChar;

        while ((currentLine = bReader.readLine()) != null)
        {
            if (currentLine.contains(MARKER_START_DNA_SEQUENCE))
            {
                do
                {
                    currentLine = bReader.readLine();
                    for (int i = 0; i < currentLine.length(); i++)
                    {
                        currentChar = currentLine.charAt(i);
                        if (currentChar == 'n') //n designates early sequence termination due to unknowns
                        {
                            if (!sequence.isEmpty())
                            {
                                DNASequences.add(sequence);
                                sequence = "";
                            }
                            else
                                continue;
                        }
                        else if (currentChar == 'a' || currentChar == 't' || currentChar == 'c' || currentChar == 'g')
                            sequence += currentChar;
                    }
                } while (!currentLine.contains(MARKER_END_DNA_SEQUENCE));
                //add sequence to List and reset it
                DNASequences.add(sequence);
                sequence = "";
            }
        }
        bReader.close();
        fr.close();

        if (DNASequences.isEmpty())
            System.err.println(NO_SEQUENCES_FOUND);

        return DNASequences;
    }

    //use linkedlist as the sliding window to create a list of (long)subsequences to then be made into TreeObjects
    public static ArrayList<Long> getDNASubsequences(ArrayList<String> input, int subsequenceLength)
    {
        ArrayList<Long> subsequences = new ArrayList<Long>();
        if (input.get(0) == NO_SEQUENCES_FOUND)
        {
            System.err.println(NO_SEQUENCES_FOUND);
            return subsequences;
        }

        LinkedList<Character> window;
        for (String str : input)
        {
            window = new LinkedList<Character>();
            char[] sequence = str.toCharArray();
            
            //for each loop to go through characters
            for (char currentChar : sequence)
            {
                if (window.size() < subsequenceLength)
                    window.addLast(currentChar);
                else
                {
                    subsequences.add(toLong(toLongSubsequence(window)));
                    window.removeFirst();
                    window.addLast(currentChar);
                }
            }
            subsequences.add(toLong(toLongSubsequence(window)));   //add the last subsequence that didn't get added during the for loop
        }

        return subsequences;
    }

    //turn a (binary)long back into a string for printing
    public static String toStringSubsequence(long subsequence, int subsequenceLength)
    {
        String result = "";
        byte current;
        for (int i = 0; i < subsequenceLength; i++)
        {
            current = (byte)(subsequence & 0b11);
            subsequence >>= 2;
            result += fromByte(current);
        }

        return reverseString(result);
    }

    //turn a list "window" into a String
    private static String toLongSubsequence(LinkedList<Character> window)
    {
        String result = "";
        for (int i = 0; i < window.size(); i++)
            result+=window.get(i).toString();

        return result;
    }

    //turn a String into a (long)binary representation 
    public static long toLong(String sub)
    {
        long result = 0;
        char[] subsequence = sub.toCharArray();

        for (char current : subsequence)
        {
            //check for uppercase and change if necessary
            if (Character.isUpperCase(current));
                current = Character.toLowerCase(current);

            //make sure none of the incoming chars are illegal
            if (current == 'a' || current == 't' || current == 'g' || current == 'c')
            {
            result <<= 2;
            result += toByte(current);
            }
        }

        return result;
    }

    //read a character and turn it into its (byte)binary representation
    private static byte toByte(char character)
    {
        switch(character)
        {
            case 'a': return a;
            case 't': return t;
            case 'c': return c;
            case 'g': return g;
        }

        //hopefully this code is never reached...
        System.err.println("SequenceUtils.toByte encountered invalid character. Exiting...");
        System.exit(1);
        return 0;
    }

    //change a byte to its genome char
    private static char fromByte(byte curr)
    {
        switch (curr)
            {
                case 0b00: return 'a';
                case 0b11: return 't';
                case 0b01: return 'c';
                case 0b10: return 'g';
            }
        return 'z'; //this line should never be reached
    }

    private static String reverseString(String s)
    {
        String result = "";
        char[] str = s.toCharArray();
        for (int i = str.length-1; i >= 0; i--)
            result += str[i];

        return result;
    }
}