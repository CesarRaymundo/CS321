public class BTreeNode
{
    public static final int sizeOfNodeMetaData = 21;
    public static final int sizeOfTreeObjectsData = 12;
    
    
    private TreeObject[] keys;
    private int[] childAddresses;
    private int degree;
    private int parentAddress;
    private int address;            //byte address of the node in the file
    private int keysCount;          //current number of objects in node
    private int childCount;         //current number of child pointers in the node (should always be 1 different than keysCount)
    private boolean isLeaf;
    private int index;

    //construct new node
    public BTreeNode(int degreeOfTree, int count)
    {
        //other variable
        this.degree = degreeOfTree;
        this.keys = new TreeObject[(2*degree)-1];        //initialize to maximum size
        this.childAddresses = new int[2*degree];         //initialize to maximum size

        //metadata written to file
        this.isLeaf = false;
        this.index = count;
        this.address = getOffset();
        this.keysCount = 0;
        this.childCount = 0;
        this.parentAddress = -1;    //initialize to impossible file location
    }
	
    //Constructor for building node from BTree binary file
    public BTreeNode(int degreeOfTree, int maxKeys, int maxKids) {
        this.degree = degreeOfTree;  
        this.keys = new TreeObject[(2*degree) - 1];            //initialize to maximum size
        this.childAddresses = new int[2 * degree];     //initialize to maximum size
            //All other metadata resides in the file and will be assigned to the node during the readNode method in BTree
    }

    //add new object into node if notFull, otherwise split
    public void addKey (TreeObject key)
    {
        if(keysCount < keys.length)
            {
            keys[keysCount] = key;
            keysCount++;
            }
        else
            //BTreeInsert should be checking for this so this line should never be reached.
            System.err.println("Node is full, could not add key.");
    }

    public void setKeyNonFull(int index, TreeObject currentTreeObj) {
    	keys[index] = currentTreeObj;
    }
	
    //will need to be able to remove keys from parent during splits
    public TreeObject removeKey(int i)
    {
        TreeObject key = keys[i];
        keys[i] = null;
        keysCount--;
        return key;
    }

    //add new child pointers
    public void addChild(int pointer)
    {
        childAddresses[childCount] = pointer;
        childCount++;
    }

    public int getChildAddress(int i)
    {
        return childAddresses[i];
    }

    public int removeChild(int i)
    {
        int ChildAddress = childAddresses[i];
        childAddresses[i] = -1;
        return ChildAddress;
    }

    //return offset from beginning of file
    public int getOffset() {
    	return BTree.metaDataSize + index * BTree.nodeSize;
    }


    public void setParentAddress(int parentAddy)
    {
        parentAddress = parentAddy;
    }

    public int getParentAddress()
    {
        return parentAddress;
    }

    public void setLocation(int location)
    {
        address = location;
    }
    
    public int getKeyCount() {
		return keysCount;
	}
    
    public TreeObject getKey(int i) {
		return keys[i];
	}

    //return byte location of this node
    public int getAddress()
    {
        return address;
    }

    public void setLeaf(boolean leaf)
    {
        isLeaf = leaf;
    }

    //return 
    public boolean isLeaf()
    {
        return (childCount == 0);
    }

    public boolean isFull()
    {
        if(keysCount == keys.length)
            return true;
        else
            return false;
    }
	
    public void setNumKeys(int numKeys) {
	this.keysCount = numKeys;
    }
    
    public int getChildCount()
    {
        return childCount;
    }

    public void setChildCount(int children) {
	this.childCount = children;
    }
	
    public void setAddress(int address) {
	this.address = address;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
	
    public void setChildAddress(int childAddress, int index) {
    	childAddresses[index] = childAddress;
    }
}
