import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class GeneBankSearch {
    public static void main(String[] args) throws FileNotFoundException, ParseArgumentException
    {
        //first check for acceptable arguments
        checkSearchArguments(args);
        
        //next scan through query file, performing searches using each query
        File queryFile = new File(args[2]);
        Scanner scan = new Scanner(queryFile);
        RandomAccessFile bTree = new RandomAccessFile(args[1], "r");

        bTree.seek(0);
        int rootAddress = bTree.readInt();  //the root address is the first thing written in the tree
        long currentSubsequence;
        int currentFreq;

        while (scan.hasNextLine())
        {
            currentSubsequence = SequenceUtils.toLong(scan.nextLine());
            currentFreq = BTree.BTreeSearch(rootAddress, currentSubsequence);
        }



        scan.close();
    }


    
    public static void checkSearchArguments(String[] args) throws ParseArgumentException
    {
        try
        {
        //check for proper number of args
        ParseArgumentUtils.verifyRanges(args.length, 3, 5);

        //handle first argument
        ParseArgumentUtils.verifyRanges(args[0].convertStringToInt, 0, 1);

        //handle second, third and fourth arguments don't need handling, just use them right
        //handle fifth argument
        if (args.length == 5)
            ParseArgumentUtils.verifyRanges(args[4].convertStringToInt, 0, 1);
        }
        catch (ParseArgumentException e) {
            printUsageAndExit(e.getMessage);
        }
    }

    private static void printUsageAndExit(String errorMessage)
    {
        System.err.println(errorMessage);
        System.err.println("Usage: java GeneBankSearch <0/1(no/with Cache)> <BTree file> <query file> [<cache size>] [<debug level>]");
        System.exit(1);
    }
}