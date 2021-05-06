package projectCode20280;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class SplayTreeMap<K, V> extends TreeMap<K, V> {
    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public SplayTreeMap() {
        super();
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the map
     */
    public SplayTreeMap(Comparator<K> comp) {
        super(comp);
    }

    /**
     * Utility used to rebalance after a map operation.
     */
    private void splay(Position<Entry<K, V>> p) {
        while (!isRoot(p)) {
            Position<Entry<K, V>> parent = parent(p), grandparent = parent(parent(p));
            if (grandparent == null) {
                rotate(p);
            } else if (p == left(parent) == (parent == left(grandparent))) {
                rotate(parent);
                rotate(p);
            } else {
                rotate(p);
                rotate(p);
            }
        }
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a node access.
     *
     * @param p
     */
    @Override
    protected void rebalanceAccess(Position<Entry<K, V>> p) {
        if (isExternal(p)) {
            p = parent(p);
        }
        if (p != null) {
            splay(p);
        }
    }
    /**
     * Overrides the TreeMap rebalancing hook that is called after an insertion.
     *
     * @param p
     */
    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        splay(p);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a deletion.
     *
     * @param p
     */
    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if(!isRoot(p)) {
            splay(parent(p));
        }
    }

    public String toString() {
        return keySet().toString();
    }
}
