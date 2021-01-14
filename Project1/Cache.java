import java.util.LinkedList;
/**
 * This class is used as a Cache for quick memory access.
 * @author CesarRaymundo
 *
 * @param <E>
 */

public class Cache<E> {
	
	private LinkedList<E> list;
	private int currentSize;
	
	/**
	 * Constructor and implements linked list for data storage.
	 * @param size
	 */
	
	public Cache(int size) {
		list = new LinkedList<E>();
		currentSize = size;	
	}
	
	/**
	 * Adds element to the storage, but if cache is full 
	 * the last object in the cache is removed before adding
	 * the new element.
	 * @param element
	 */
	
	public void addObject(E element) {
		if(isFull()) {
			list.removeLast();
		}
		list.addFirst(element);
	}
	
	/***
	 * Uses the getObject method to remove the element from
	 * the cache and adds it to the front.
	 * @param element
	 */
	public void addFirst(E element) {
		getObject(element);
	}
	
	/**
	 * Finds the wanted element and removes it from current
	 * index and adds it to the the front of the cache.
	 * @param element
	 * @return targeted element
	 */
	public E getObject(E element) {
		E ele = list.get(list.indexOf(element));
		list.remove(element);
		list.addFirst(element);
		return ele;
	}
	
	/**
	 * Determines if the cache size is full
	 * @return true if it is full, if not then false.
	 */
	public boolean isFull() {
		if(list.size() >= currentSize) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if a certain element is in the cache.
	 * @param element
	 * @return true if specific element is in cache. If not, then false.
	 */
	public boolean contains(E element) {
		if(list.isEmpty()) {
			return false;
		}
		if(list.contains(element)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the current size of the cache.
	 * @return The current size of the cache
	 */
	public int size() {
		return list.size();
	}
}