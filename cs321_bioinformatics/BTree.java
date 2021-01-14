import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;



public class BTree
{
    public static final int metaDataSize = 16;//number of bytes taken up at the beginning of the file by metadata
    public static int nodeSize;
    
    private RandomAccessFile tree;
    private int subLength;              //length of the subsequences
    private int degree;                 //degree of the tree
    private int count;                  //current number of nodes stored in tree
    private int rootAddress;            //byte address of the root within the file
    private BTreeNode root, currentNode;  // The root, current, and next node in the BTree

    public BTree(GeneBankCreateBTreeArguments geneArguments) throws FileNotFoundException, IOException
    {
        //assign variables
        this.subLength = geneArguments.subsequenceLength;
        this.degree = geneArguments.degree;
        this.count = 0;
        this.rootAddress = metaDataSize;    //the root will change over time but this is what it will be at the beginning every time
        BTree.nodeSize = BTreeNode.sizeOfNodeMetaData + (BTreeNode.sizeOfTreeObjectsData * ((2 * degree) - 1) + (2*4 * degree));

        //initialize tree
        String treeFileName = geneArguments.gbkFileName + ".btree.data." + degree + "." + subLength;
        this.tree = new RandomAccessFile(treeFileName, "rws");

        //create initial root node
        root = currentNode = new BTreeNode(degree, count);
        root.setLeaf(true);  //leaf is assigned to false initially so we have to toggle it on the root

        //write tree metadata to file
        tree.setLength(metaDataSize + nodeSize);
        tree.writeInt(rootAddress);
        tree.writeInt(degree);
        tree.writeInt(count);
        tree.writeInt(subLength);
    }

    //add new node to the tree
    public void BTreeInsert(long key) throws IOException
    {
	    currentNode = root;
    	if(root.isFull()) { //root node is full
            count++;
            BTreeNode newNode = new BTreeNode(degree, count);
            root = newNode;
            rootAddress = root.getAddress();
            tree.seek(0);
            tree.writeInt(rootAddress);
            root.setLeaf(false);
            root.addChild(currentNode.getAddress());
            currentNode.setParentAddress(root.getAddress());
            writeNode(currentNode);
            BTreeSplitChild(root, 0);
            BTreeInsertNonFull(root, key);
	    } else {
		    BTreeInsertNonFull(currentNode, key);
	    }
    }
	
    public void BTreeInsertNonFull(BTreeNode node, long key) throws IOException {
    	int index = node.getKeyCount();
	if(node.isLeaf()) {
        for(int i = index; i > 0; i--) {
            //Non psuedo-code for checking frequency
	    	if(node.getKey(index) != null && node.getKey(index).getKey() == key) {
			    node.getKey(index).incrementFreq();
			    return;
            }
        }
		while(index >= 1 && (node.getKey(index - 1) == null || key < node.getKey(index - 1).getKey())) {
			
			node.setKeyNonFull(index, node.getKey(index - 1));
			index--;
        }

		node.setKeyNonFull(index, new TreeObject(key, subLength));
		node.setNumKeys(node.getKeyCount() + 1);
		writeNode(node);
	}
	else {
        for(int i = index; i > 0; i--) {
            //Non psuedo-code for checking frequency
	    	if(node.getKey(index) != null && node.getKey(index).getKey() == key) {
			    node.getKey(index).incrementFreq();
			    return;
            }
        }

		while(index >= 1 && (node.getKey(index) == null || key < node.getKey(index - 1).getKey())) {    
	      		index--;	
        }

		BTreeNode nodeReadIn = readNode(node.getChildAddress(index));
		if(nodeReadIn.isFull()) {
			BTreeSplitChild(node, index);
			if(key > node.getKey(index).getKey()) {
				index++;
			}
        }
        nodeReadIn = readNode(node.getChildAddress(index));
		BTreeInsertNonFull(nodeReadIn, key);
	}
    }

	public void BTreeSplitChild(BTreeNode t, int i) throws IOException {
        //Increment count and create new BTreeNode to pass in data to
        count++;
        BTreeNode z = new BTreeNode(degree, count);

        //Set parent address of new node to parent node
        z.setParentAddress(t.getAddress());

        //Read in the Node to split data from
        BTreeNode y = readNode(t.getChildAddress(i));
        z.setLeaf(y.isLeaf());

        //move top half of keys from y to z, deleting from y as we go
        for(int j = 0; j <= degree - 2; j++){
            z.addKey(y.getKey(j + degree));
            y.setKeyNonFull(j + degree, null);
            y.setNumKeys(y.getKeyCount() - 1);
        }

        //if y is not a leaf move the top half of children from y to z, deleting from y as we go
        if(!y.isLeaf()){
            for(int j = 0; j<=degree - 1; j++){
                z.addChild(y.getChildAddress(j + degree));
                y.setChildAddress(0, j + degree);
                y.setChildCount(y.getChildCount() - 1);
            }
        }

        //Move all child addresses in original node up one index in the array
        for(int j = (t.getKeyCount()); j >= i+1; j--){
            t.setChildAddress(t.getChildAddress(j), j+1);
        } 
        //insert child address of new node into space created for it
        t.setChildAddress(z.getAddress(), i+1);

        //Move keys in the original node up one index in the array
        for(int j = t.getKeyCount(); j >= i; j--){
            if(t.getKey(j) != null) {
                t.setKeyNonFull(j+1, t.getKey(j));
            }
        }
        
        //Move median key up to node t and increment t's NumKeys
        t.setKeyNonFull(i, y.getKey(degree - 1));
        t.setNumKeys(t.getKeyCount() + 1);
        y.setKeyNonFull(degree - 1, null);
        y.setNumKeys(y.getKeyCount() - 1);

        //Write all of this to the random access file
        writeNode(y);
        writeNode(z);
        writeNode(t);
    }
	
    //return the node containing the key being searched for or the node that is a predecessor of the key being searched for
    public int BTreeSearch(int rootAddress, long key) throws IOException 
    {
    	BTreeNode x = readNode(rootAddress);
     int i = 0;
     while(i <= x.getKeyCount() && key > x.getKey(i).getKey()){
        i = i + 1;
     }
     if(i <= x.getKeyCount() && key == x.getKey(i).getKey()){
    	return x.getKey(i).getFreq();
 }
    else if(x.isLeaf()){
        return 0;
    }
    else{
        readNode(x.getChildAddress(i));
        return BTreeSearch(rootAddress, key);
    }
}


    public void writeNode(BTreeNode node) throws IOException {
    	tree.seek((long)node.getOffset());
        tree.writeInt(node.getKeyCount());
        tree.writeInt(node.getChildCount());
        tree.writeInt(node.getIndex());
        tree.writeInt(node.getParentAddress());
        tree.writeInt(node.getOffset());
        tree.writeBoolean(node.isLeaf());
        for(int i = 0; i < 2 * degree - 1; i++) {
            if(node.getKey(i) != null) {
                tree.writeLong(node.getKey(i).getKey());
            } else {
                tree.writeLong(0);
            }
        }
        for(int i = 0; i < 2 * degree; i++) {
            if(node.getChildAddress(i) >= 16) {     
                tree.writeInt(node.getChildAddress(i));
            } else {
                tree.writeInt(0);
            }
        }
        for(int i = 0; i < 2 * degree - 1; i++) {
            if(node.getKey(i) != null) {
                tree.writeInt(node.getKey(i).getFreq());
            } else {
                tree.writeInt(0);
            }
        }
    }
	
    public BTreeNode readNode(int offset) throws IOException {
        tree.seek((long)offset);
        int maxKeys = tree.readInt();
        int maxKids = tree.readInt();
        BTreeNode nodeFromFile = new BTreeNode(degree, maxKeys, maxKids);
        nodeFromFile.setIndex(tree.readInt());
        nodeFromFile.setParentAddress(tree.readInt());
        nodeFromFile.setAddress(tree.readInt());
        nodeFromFile.setLeaf(tree.readBoolean());
        for(int i = 0; i < (2 * degree) - 1; i++) {
            if(i < maxKeys) {   
                nodeFromFile.addKey(new TreeObject(tree.readLong(), subLength));
            } else {
                tree.readLong();
            }
        }
        for(int i = 0; i < 2 * degree; i++) {
            if(i < maxKids) {
                nodeFromFile.addChild(tree.readInt());
            } else {
                tree.readInt();
            }
        }
        for(int i = 0; i < 2 * degree - 1; i++) {
            if(i < maxKeys) {   
                nodeFromFile.getKey(i).setFreq(tree.readInt());
            } else {
                tree.readInt();
            }
        }
        return nodeFromFile;
    }
	
    //return byte address of the root BTreeNode
    public int getRoot()
    {
        return rootAddress;
    }

    //set the byte address of the root BTreeNode
    public void setRoot(int address)
    {
        rootAddress = address;
    }

    //return number of nodes in the tree
    public int getCount()
    {
        return count;
    }

    //return degree of the tree
    public int getDegree()
    {
        return degree;
    }

    public int getNodeSize()
    {
        return nodeSize;
    }

    public void dump() throws IOException
    {
        File dumpfile = new File("dump");
        dumpfile.delete();
        if (!dumpfile.createNewFile())
            System.out.println("Dump file failed to initialize...");
        FileWriter fwriter = new FileWriter(dumpfile);

        printInOrder(fwriter, dumpfile, rootAddress);
    }

    private void printInOrder(FileWriter fw, File dumpfile, int currentNodeAddress) throws IOException
    {
        BTreeNode currentNode = readNode(currentNodeAddress);

        for(int i = 0; i < currentNode.getKeyCount(); i++) {
            fw.write(currentNode.getKey(i).toString() + "\n");
        }
        /*
        //if currentNode is a leaf, print all of its keys in order
        if (currentNode.isLeaf())
            for (int i = 0; i < currentNode.getKeyCount(); i++)
            {
                fw.write(currentNode.getKey(i).toString() + "\n");
            }
        else    //currentNode isn't a leaf so we have to print all data to the left of each key
        {       //and then print that key and then increment until we get to the end
            for (int i = 0; i < currentNode.getKeyCount(); i++)
            {
                //recur on children from left to right
                printInOrder(fw, dumpfile, currentNode.getChildAddress(i));
                //print data of currentKey
                fw.write(currentNode.getKey(i).toString() + "\n");
            }
            //print the last child on the right before finishing
            fw.write(currentNode.getKey(currentNode.getChildCount()-1).toString() + "\n");
        }*/
    }
}
