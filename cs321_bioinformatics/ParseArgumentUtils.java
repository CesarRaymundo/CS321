public class ParseArgumentUtils
{
    /**
     * Verifies if lowRangeInclusive <= argument <= highRangeInclusive
     */
    public static void verifyRanges(int argument, int lowRangeInclusive, int highRangeInclusive) throws ParseArgumentException
    {
        if (argument < lowRangeInclusive || argument > highRangeInclusive)
            throw new ParseArgumentException("Argument out of range.");
    }

    public static int convertStringToInt(String argument) throws ParseArgumentException
    {
        int result = -1;
        if (isInt(argument))
            result = Integer.parseInt(argument);
        return result;
    }

    public static boolean isInt(String input) throws ParseArgumentException
    {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Argument is not an int.");
        }
    }
}
