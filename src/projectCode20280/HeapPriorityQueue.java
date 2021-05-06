package projectCode20280;/*
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of a priority queue using an array-based heap.
 */

public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected ArrayList<Entry<K, V>> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys.
     */
    public HeapPriorityQueue() {
        super();
    }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the priority queue
     */
    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Creates a priority queue initialized with the respective key-value pairs. The
     * two arrays given will be paired element-by-element. They are presumed to have
     * the same length. (If not, entries will be created only up to the length of
     * the shorter of the arrays)
     *
     * @param keys   an array of the initial keys for the priority queue
     * @param values an array of the initial values for the priority queue
     */
    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        for (int j = 0; j < Math.min(keys.length, values.length); j++)
            heap.add(new PQEntry<K, V>(keys[j], values[j]));
        heapify();
    }

    // protected utilities
    protected int parent(int j) {
        return (j - 1) / 2;
    }

    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    /**
     * Exchanges the entries at indices i and j of the array list.
     */
    protected void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Moves the entry at index j higher, if necessary, to restore the heap property.
     */
    protected void upheap(int j) {
        int parentIndex;
        while(j != 0){
            parentIndex = parent(j);
            if(0 >= compare(heap.get(parentIndex), heap.get(j))) {
                break;
            }
            swap(j,parentIndex);
            j = parentIndex;
        }
    }

    /**
     * Moves the entry at index j lower, if necessary, to restore the heap property.
     */
    protected void downheap(int j) {
        int right, left, child;
        while(hasLeft(j)){
            left = left(j);
            child = left;

            if(hasRight(j)){
                right = right(j);

                if(compare(heap.get(left), heap.get(right)) > 0)
                    child = right;
            }
            if(0 >= compare(heap.get(j), heap.get(child))) {
                break;
            }
            swap(j,child);
            j = child;
        }
    }

    /**
     * Performs a bottom-up construction of the heap in linear time.
     */
    protected void heapify() {
        int start = parent(size() - 1);
        for (int j = start; j >= 0; j--) {
            downheap(j);
        }
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     *
     * @return number of items
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Returns (but does not remove) an entry with minimal key.
     *
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K, V> min() {
        return heap.get(0);
    }

    /**
     * Inserts a key-value pair and return the entry created.
     *
     * @param key   the key of the new entry
     * @param value the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newest = new PQEntry<>(key, value);
        heap.add(newest);
        upheap(heap.size() - 1);
        return newest;
    }

    /**
     * Removes and returns an entry with minimal key.
     *
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K, V> removeMin() {
        if (heap.isEmpty()) {
            return null;
        } else {
            Entry<K, V> entry = heap.get(0);
            swap(heap.size() - 1, 0);
            heap.remove(heap.size() - 1);
            downheap(0);
            return entry;
        }
    }

    /**
     * Used for debugging purposes only
     */
    private void sanityCheck() {
        for (int j = 0; j < heap.size(); j++) {
            int left = left(j);
            int right = right(j);
            if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0)
                System.out.println("Invalid left child relationship");
            if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0)
                System.out.println("Invalid right child relationship");
        }
    }

    public String toString() {
        return heap.toString();
    }
}
