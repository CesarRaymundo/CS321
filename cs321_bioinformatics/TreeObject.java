
public class TreeObject{

    private int subLength;
    private int frequency;
    private long key;

    /**
     * Creates a tree object with only the key
     * This will be used when no duplicates are found 
     * within the tree
     * @param key
     */
    public TreeObject(long key, int subsequenceLength){
        this.key = key;
        this.frequency = 1;
        this.subLength = subsequenceLength;
    }

    /**
     * Increments the frequency count I
     */
    public void incrementFreq(){
        frequency++;  
    }

    /**
     * Returns the frequency of the object
     */
    public int getFreq(){
        return frequency;
    }
    
    public void setFreq(int freq) {
        this.frequency = freq;
    }

    /**
     * Returns the key from the object
     */
    public long getKey(){
        return key;
    }

    @Override
    public String toString(){
        return SequenceUtils.toStringSubsequence(key, subLength) + ": " + frequency;
    }

}
