import java.util.ArrayList;

public class GeneBankCreateBTree
{
    public static final int OPTIMAL_DEGREE = 127;

    public static void main(String[] args) throws Exception
    {
        GeneBankCreateBTreeArguments BTreeArguments = parseArgumentsAndHandleExceptions(args);
        BTree bTree = new BTree(BTreeArguments);

        //get sequences from gbk file
        ArrayList<String> sequences = SequenceUtils.getDNASequencesFromGBKGenomeFile(BTreeArguments.gbkFileName);
       
        //convert sequences to subsequences of specified length
        ArrayList<Long> subsequences = SequenceUtils.getDNASubsequences(sequences, BTreeArguments.degree);
       
        //get rid of the list of sequences to make that memory available again just in case
        sequences = null;

        //pass each of the subsequences to the BTree
        for (long key : subsequences)
            bTree.BTreeInsert(key);

        if(BTreeArguments.debugLevel == 1) {
            bTree.dump();
        }
    }

    private static GeneBankCreateBTreeArguments parseArgumentsAndHandleExceptions(String[] args)
    {
        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = null;
        try
        {
            geneBankCreateBTreeArguments = parseArguments(args);
        }
        catch (ParseArgumentException e)
        {
            printUsageAndExit(e.getMessage());
        }
        return geneBankCreateBTreeArguments;
    }

    private static void printUsageAndExit(String errorMessage)
    {
        System.err.println("Usage: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length>[<cache size>] [<debug level>]");
        System.exit(1);
    }

    public static GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        //check for proper number of args
        ParseArgumentUtils.verifyRanges(args.length, 4, 6);

        //handle first argument
        int args0 = ParseArgumentUtils.convertStringToInt(args[0]);
        ParseArgumentUtils.verifyRanges(args0, 0, 1);
        boolean useCache = (args0 == 1);

        //handle second argument
        int degree = ParseArgumentUtils.convertStringToInt(args[1]);
        if (degree == 0)
            degree = OPTIMAL_DEGREE;

        //handle third argument
        String gbkFileName = args[2];

        //handle fourth argument
        int subsequenceLength = ParseArgumentUtils.convertStringToInt(args[3]);
        ParseArgumentUtils.verifyRanges(subsequenceLength, 1, 31);

        //check for and handle fifth argument
        int cacheSize = -1;
        if(args.length >= 5 && useCache == true) {
            cacheSize = ParseArgumentUtils.convertStringToInt(args[4]);
        }

        //check for sixth argument and handle it
        int debugLevel = 0;
        if (args.length == 5 && useCache == false) {
            debugLevel = ParseArgumentUtils.convertStringToInt(args[4]);
        } else if(args.length == 6){
            debugLevel = ParseArgumentUtils.convertStringToInt(args[5]);
        }
        ParseArgumentUtils.verifyRanges(debugLevel, 0, 1);

        return new GeneBankCreateBTreeArguments(useCache, degree, gbkFileName, subsequenceLength, cacheSize, debugLevel);
    }
}
