package projectCode20280;

import projectCode20280.AbstractHashMap;

import java.util.ArrayList;

/**
 * Map implementation using hash table with separate chaining.
 */

public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    // a fixed capacity array of UnsortedTableMap that serve as buckets
    private UnsortedTableMap<K, V>[] table; // initialized within createTable

    /**
     * Creates a hash table with capacity 11 and prime factor 109345121.
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ChainHashMap(int cap, int p) {
        super(cap, p);
    }

    /**
     * Creates an empty table having length equal to current capacity.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
        table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[capacity];
    }

    /**
     * Returns value associated with key k in bucket with hash value h. If no such
     * entry exists, returns null.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
        UnsortedTableMap<K,V> bucket = table[h];
        return bucket == null ? null : bucket.get(k);
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning the
     * previously associated value, if any.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @param v the value to be associated
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
        int size;
        V entry;
        UnsortedTableMap<K, V> bucket = table[h];
        if(bucket == null) {
            bucket = table[h] = new UnsortedTableMap<>();
        }
        size = bucket.size();
        //add entry
        entry = bucket.put(k,v);
        n += (bucket.size() - size);
        return entry;
    }

    /**
     * Removes entry having key k from bucket with hash value h, returning the
     * previously associated value, if found.
     *
     * @param h the hash value of the relevant bucket
     * @param k the key of interest
     * @return previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
        int size;
        V entry;
        UnsortedTableMap<K,V> bucket = table[h];
        if(bucket == null) {
            return null;
        }
        size = bucket.size();
        //remove entry
        entry = bucket.remove(k);
        n -= (size - bucket.size());
        return entry;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> arr = new ArrayList<>();
        for (int i = 0; i < capacity; i++)
            if (table[i] != null) {
                for (Entry<K, V> entry : table[i].entrySet()) {
                    arr.add(entry);
                }
            }
        return arr;
    }

    public String toString() {
        return entrySet().toString();
    }
}
