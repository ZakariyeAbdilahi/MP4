package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key..
 *
 * @author Zakariye Abdilahi
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>(null, null)).getClass(),
    DEFAULT_CAPACITY);

    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> newArray = new AssociativeArray<>();

    // Copy key-value pairs from the original array to the new one
    for (int i = 0; i < size; i++) {
        newArray.pairs[i] = new KVPair<>(pairs[i].key, pairs[i].value);
    }

    newArray.size = this.size; // Update the size of the new array
    return newArray;
}

  /**
   * Convert the array to a string.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("{ ");
    for (int i = 0; i < size; i++) {
        sb.append(pairs[i].key).append(": ").append(pairs[i].value).append(", ");
    }
    if (size > 0) {
        sb.setLength(sb.length() - 2); // Remove the last comma and space
    }
    sb.append(" }");
    return sb.toString();
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) throws NullKeyException {
    if (key == null) {
        throw new NullKeyException();
    }
    for (int i = 0; i < size; i++) {
        if (pairs[i].key.equals(key)) {
            pairs[i].value = value;
            return;
        }
    }
    if (size == pairs.length) {
        expand();
    }
    pairs[size++] = new KVPair<>(key, value);
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    if (key == null) {
        throw new KeyNotFoundException();
    }
    for (int i = 0; i < size; i++) {
        if (pairs[i].key.equals(key)) {
            return pairs[i].value;
        }
    }
    throw new KeyNotFoundException();
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    if (key == null) {
        return false;
    }
    for (int i = 0; i < size; i++) {
        if (pairs[i].key.equals(key)) {
            return true;
        }
    }
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    if (key == null) {
        return;
    }
    for (int i = 0; i < size; i++) {
        if (pairs[i].key.equals(key)) {
            for (int j = i; j < size - 1; j++) {
                pairs[j] = pairs[j + 1];
            }
            pairs[size - 1] = null;
            size--;
            return;
        }
    }
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    pairs = java.util.Arrays.copyOf(pairs, pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    if (key == null) {
        throw new KeyNotFoundException();
    }
    for (int i = 0; i < size; i++) {
        if (pairs[i].key.equals(key)) {
            return i; // Return the index if key is found
        }
    }
    throw new KeyNotFoundException(); // Throw exception if key is not found
} // find(K)

} // class AssociativeArray
