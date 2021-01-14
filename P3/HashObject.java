/**
 * An object to be inserted into an array using the hashing method
 * 
 * @author CesarRaymundo
 *
 * @param <T>
 */
public class HashObject<T> {

	private int duplicates = 0; // int to keep track of how many duplicates are in array
	private int probes = 0; // int to keep track of how many probes it took to insert this object into the array
	private boolean deleted = false; // boolean to track if this object was removed from the array
	private final T key; // generic element being tracked by this object

	/**
	 * Constructor for HashObject
	 * 
	 * @param object The object to be used as the key of the HashObject
	 */
	public HashObject(T object) {
		key = object;
	}

	/**
	 * Return the object's key, same key used in the constructor
	 * 
	 * @return The object's key
	 */
	public T getKey() {
		return key;
	}

	/**
	 * Returns the number of probes for the object to be inserted
	 * 
	 * @return The number of probes
	 */
	public int getProbes() {
		return probes;
	}

	/**
	 * Returns the number of duplicates in the list this object is in
	 * 
	 * @return The number of duplicates
	 */
	public int getDuplicates() {
		return duplicates;
	}

	/**
	 * Increments the number of duplicates
	 */
	public void incrementDuplicates() {
		duplicates++;
	}

	/**
	 * Increments the number of probes
	 */
	public void incrementProbes() {
		probes++;
	}

	/**
	 * Deletes a duplicate if there are any Delete is set to true if there isn't any
	 */
	public void delete() {
		if (duplicates > 0) {
			duplicates--;
		} else {
			deleted = true;
		}
	}

	/**
	 * Return true if the object has been deleted from the list
	 * 
	 * @return True if the object has been deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	
	/**
	 * Returns true if the object given is equivalent to the this object
	 * @param object A HashObject to be compared
	 * @return True if the object given and this object are equivalent
	 */
	public boolean equals(HashObject<?> object) {
		Class<?> class1 = key.getClass();
        Class<?> class2 = object.getKey().getClass();
        
        if ((class1 == Long.class || class1 == Integer.class) && (class2 == Long.class || class2 == Integer.class)) {
            return ((Number) key).longValue() == ((Number) object.getKey()).longValue();
        }
        if ((class1 == String.class) && (class2 == String.class)) {
            return key.toString().equals(object.getKey().toString());
        }
        return false;	
	}

	@Override
	public String toString() {
		return key + " " + duplicates + " " + probes;
	}
}