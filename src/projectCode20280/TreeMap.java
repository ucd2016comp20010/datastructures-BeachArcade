package projectCode20280;

import java.util.ArrayList;
import java.util.Comparator;


//An implementation of a sorted map using a binary search tree.
public class TreeMap<K, V> extends AbstractSortedMap<K, V> {
    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();


    //Constructs an empty map using the natural ordering of keys.
    public TreeMap() {
        super(); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
    }

    // Constructs an empty map using the given comparator to order keys.
    public TreeMap(Comparator<K> comp) {
        super(comp); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
    }

    // Returns the number of entries in the map.
    @Override
    public int size() {
        return (tree.size() - 1) / 2; // only internal nodes have entries
    }

    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        return tree.restructure(x);
    }


    //Rebalances the tree after an insertion of specified position. This version of
    //the method does not do anything, but it can be overridden by subclasses.
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
    }


    // Rebalances the tree after a child of specified position has been removed.
    // This version of the method does not do anything, but it can be overridden by
    // subclasses.
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
    }


    //Rebalances the tree after an access of specified position. This version of
    // the method does not do anything, but it can be overridden by a subclasses.
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
    }

    //Utility used when inserting a new entry at a leaf of the tree
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    // Some notational shorthands for brevity (yet not efficiency)
    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);

    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected Entry<K, V> remove(Position<Entry<K, V>> p) {
        return tree.remove(p);
    }

    // Returns the position's subtree having the key/leaf
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> pos, K key) {
        if (isExternal(pos))
            return pos;
        int comp = compare(key, pos.getElement());
        if (comp == 0)
            return pos;
        else if (comp < 0)
            return treeSearch(left(pos), key);
        else
            return treeSearch(right(pos), key);

    }


    //Returns position with the minimum key in the subtree rooted at a position.
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> pos) {
        Position<Entry<K, V>> curr = pos;
        while (isInternal(curr)) {
            curr = left(curr);
        }
        return parent(curr);
    }

    //Returns the position with the maximum key in the subtree rooted at a position.
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> pos) {
        Position<Entry<K, V>> curr = pos;
        while (isInternal(curr)) {
            curr = right(curr);
        }
        return parent(curr);
    }

    // Returns the value associated with the specified key
    @Override
    public V get(K key) throws IllegalArgumentException {
//		checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        rebalanceAccess(p);
        if (isExternal(p)) {
            return null;
        }
        return p.getElement().getValue();
    }


    // Associates the given value with the given key. If an entry with the key was
    // already in the map, this replaced the previous value with the new one and
    // returns the old value. Otherwise, a new entry is added and null is returned.
    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> entry = new MapEntry<>(key, value);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isExternal(p)) {
            expandExternal(p, entry);
            rebalanceInsert(p);
            return null;
        } else {
            V old = p.getElement().getValue();
            set(p, entry);
            rebalanceAccess(p);
            return old;
        }
    }

    //Removes the entry with the specified key, if present, and returns its
    // associated value. Otherwise does nothing and returns null.
    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key), sib, newNode;
        if (isExternal(p)) {
            rebalanceAccess(p);
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) {
                newNode = treeMax(left(p));
                set(p, newNode.getElement());
                p = newNode;
            }
            sib = sibling((isExternal(left(p)) ? left(p) : right(p)));
            remove((isExternal(left(p)) ? left(p) : right(p)));
            remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }

    //Returns the entry having the least key (or null if map is empty).

    @Override
    public Entry<K, V> firstEntry() {
        return isEmpty() ? null : treeMin(root()).getElement();
    }

    //Returns the entry having the greatest key (or null if map is empty).
    @Override
    public Entry<K, V> lastEntry() {
        return isEmpty() ? null : treeMax(root()).getElement();
    }

    //Returns the entry with least key greater than or equal to given key (or null
    // if no such key exists).
    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> foundKey = treeSearch(root(), key);

        if (isInternal(foundKey)) {
            return foundKey.getElement();
        }
        while (!isRoot(foundKey)) {
            if (foundKey != left(parent(foundKey))) {
                foundKey = parent(foundKey);
            } else {
                return parent(foundKey).getElement();
            }
        }
        return null;
    }

    //Returns the entry with greatest key less than or equal to given key.
    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> foundKey = treeSearch(root(), key);
        if (isInternal(foundKey)) {
            return foundKey.getElement();
        }
        while (!isRoot(foundKey)) {
            if (foundKey != right(parent(foundKey))) {
                foundKey = parent(foundKey);
            } else {
                return parent(foundKey).getElement();
            }
        }
        return null;
    }

    // Returns the entry with greatest key strictly less than given key
    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> foundKey = treeSearch(root(), key);
        if (isInternal(foundKey) && isInternal(left(foundKey)))
            return treeMax(left(foundKey)).getElement();
        while (!isRoot(foundKey)) {
            if (foundKey != right(parent(foundKey))) {
                foundKey = parent(foundKey);
            } else {
                return parent(foundKey).getElement();
            }
        }
        return null;
    }

    // Returns the entry with least key strictly greater than given key
    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(right(p)))
            return treeMax(right(p)).getElement();
        while (!isRoot(p)) {
            if (p == left(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    // Support for iteration

    // Returns an iterable collection of all key-value entries of the map.
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> mapArr = new ArrayList<>(size());
        for (Position<Entry<K, V>> p : tree.inorder()) {
            if (isInternal(p)) {
                mapArr.add(p.getElement());
            }
        }
        return mapArr;
    }

    public String toString() {
        return entrySet().toString();
    }

    // Returns an all entries with keys from fromKey to toKey.
    @Override
    public Iterable<Entry<K, V>> subMap(K from, K to) throws IllegalArgumentException {
        ArrayList<Entry<K, V>> mapArr = new ArrayList<>(size());
        if (compare(from, to) < 0) {
            subMapUtil(from, to, root(), mapArr);
        }
        return mapArr;
    }

    //Utililty method for subMap
    private void subMapUtil(K from, K to, Position<Entry<K, V>> p, ArrayList<Entry<K, V>> mapArr) {
        if (isInternal(p)) {
            if (compare(from, p.getElement()) <= 0) {
                subMapUtil(from, to, left(p), mapArr);
                if (compare(to, p.getElement()) > 0) {
                    mapArr.add(p.getElement());
                    subMapUtil(from, to, right(p), mapArr);
                }
            } else {
                subMapUtil(from, to, right(p), mapArr);
            }
        }
    }

    protected void rotate(Position<Entry<K, V>> p) {
        tree.rotate(p);
    }


    //Prints textual representation of tree structure (for debug purpose only).
    protected void dump() {
        dumpRecurse(root(), 0);
    }

    // This exists for debugging only
    private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
        if (isExternal(p))
            System.out.println(indent + "leaf");
        else {
            System.out.println(indent + p.getElement());
            dumpRecurse(left(p), depth + 1);
            dumpRecurse(right(p), depth + 1);
        }
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>((LinkedBinaryTree<Entry<K, V>>) this.tree);
        return btp.print();
    }

}
