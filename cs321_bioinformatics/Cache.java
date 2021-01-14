import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A cache that uses BTreeNode object, This contains all methods to create a new cache
 * with add, remove and check. (Feel free to change anything that seems off)
 * 
 * @author CesarRaymundo
 *
 * @param <BTreeNode>
 *            For BTreeNode objects
 */
@SuppressWarnings("hiding")
public class Cache<BTreeNode>{
	private LinkedList<BTreeNode> list;
	private BTreeNode node = null;
	private final int MAX_SIZE;

	/**
	 * Creates a new cache with the size given from the parameter
	 * 
	 * @param size
	 */
	public Cache(int size) throws NumberFormatException {
		list = new LinkedList<BTreeNode>();
		MAX_SIZE = size;
	}

	/**
	 * Adds elements to the list, if already in cache, then the elements
	 * gets move to the front.
	 * 
	 * @param element
	 *            node to be added
	 */
	public BTreeNode add(BTreeNode element) {
		BTreeNode returnNode = null;
		if (isFull()) {
			returnNode = list.removeLast();
		}
		BTreeNode node = get(element);
		if (node == null) {
			list.addFirst(element);
		} else { // node in cache, add to front
			list.addFirst(node);
		}
		return returnNode;
	}

	/**
	 * Looks for BTreeNode in cache and returns and removes it if found.
	 * 
	 * @param element
	 *            node to find
	 * @return BTreeNode if found, null if not found
	 */
	public BTreeNode get(BTreeNode element) {
		if (check(element)) {
			return list.remove(indexOf(element));
		}
		return null;
	}
	
	/**
	 * Looks for BTreeNode in cache and returns+removes it if found.
	 * 
	 * @param index
	 *            node to find
	 * @return BTreeNode 
	 */
	public BTreeNode get(int index) {
		
		return list.remove(index);
	}

	/**
	 * Removes the last element in the list
	 * 
	 * @return the element removed
	 */
	public BTreeNode removeLast() {
		return list.removeLast();
	}

	/**
	 * Removes all element in the list
	 */
	public void clearCache() {
		while (!isEmpty()) {
			removeLast();
		}
	}

	/**
	 * Checks to see if the cache is full
	 * 
	 * @return boolean result of size compared to max_size
	 */
	public boolean isFull() {
		return size() == MAX_SIZE;
	}

	/**
	 * Checks to see if the target element is in the list
	 * 
	 * @param target
	 * @return a boolean result of target in the list
	 */
	public boolean check(BTreeNode target) {
		return list.contains(target);
	}

	/**
	 * Checks to see if the list is empty
	 * 
	 * @return boolean result of it being empty
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return size
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Returns the max size of the list
	 * 
	 * @return size
	 */
	public int maxSize() {
		return MAX_SIZE;
	}

	/**
	 * Finds the index of target element
	 * 
	 * @param element
	 * @return The index of that element
	 */
	public int indexOf(BTreeNode element) {
		return list.indexOf(element);
	}

	@Override
	public String toString() {
		return list.toString();
	}

	
}
