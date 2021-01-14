# CS321 Final Project
Team: Taylor Poulsen, Theodore Ramey, Cesar Raymundo    
# Overview
In this project, we implemented a BTree to handle a large amount of DNA 
 string information to be able to determine the frequencies of different lengths read in from the GeneBank file.
 # Files Included
 * Btree.java
 * TreeObject.java 
 * BTreeNode.java
 * Cache.java
 * GeneBankCreateBTree.java(main)
 * GeneBankCreateBTreeArgument.java
 * GeneBankSearch.java(main)
 * ParseArgumentException.java
 * ParseArgumentsUtils.java
 * SequenceUtils.java
 # Compiling and Running
 	javac <fileName>.java
 	or to compile all at one use:
 	javac *.java 
  Once compiled, run this command	        
  
	GeneBankCreateBTree <0/1(no/withCache)> <degree> <gbk file> <sequence length>[<cache size>] [<debug level>]
 then
     
	java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>][<debug level>]
 The console will give the results after the program finishes.
  
 Arguments Definitions
 
	<degree> - degree size of the BTree being created
	<gbk file> - Gene Bank file being read
	<sequence length> - int that must be between 1-31 to specify the sequence length of each genome
	[<cache size>] - size of cache (if cache is being used)
	[<debug level>] - either a 0 or 1. 0 will print out any diagnostic messages and status to the stream. 
	<btree file> - BTree file to be used in search
	<query file> - query file to be used in search
# Notes
BTreeFile Layout on File(By columns) 

	rootAddress, degree, count, subLength

