package projectCode20280;

import javafx.geometry.Pos;

import java.util.Comparator;
import java.util.logging.Logger;


/**
 * An implementation of a sorted map using an AVL tree.
 */

public class AVLTreeMap<K, V> extends TreeMap<K, V> {
    static Logger log = Logger.getLogger(AVLTreeMap.class.getName());

    /**
     * Constructs an empty map using the natural ordering of keys.
     */
    public AVLTreeMap() {
        super();
    }

    /**
     * Constructs an empty map using the given comparator to order keys.
     *
     * @param comp comparator defining the order of keys in the map
     */
    public AVLTreeMap(Comparator<K> comp) {
        super(comp);
    }


    /**
     * Returns the height of the given tree position.
     */
    protected int height(Position<Entry<K, V>> p) {
        return tree.getData(p);
    }


    /**
     * Recomputes the height of the given position based on its children's heights.
     */
    protected void recomputeHeight(Position<Entry<K, V>> p) {
        tree.setData(p, 1 + Math.max(height(left(p)), height(right(p))));
    }

    /**
     * Returns whether a position has balance factor between -1 and 1 inclusive.
     */
    protected boolean isBalanced(Position<Entry<K, V>> p) {
        int num = height(right(p)) - height(left(p));
        return (num > -2 && num < 2);
    }

    /**
     * Returns a child of p with height no smaller than that of the other child.
     */
    protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> node;
        if (height(left(p)) > height(right(p)) || (p == left(parent(p))) || (isRoot(p))) {
            node = left(p);
        } else {
            node = right(p);
        }
        return node;
    }

    /**
     * Utility used to rebalance after an insert or removal operation. This
     * traverses the path upward from p, performing a trinode restructuring when
     * imbalance is found, continuing until balance is restored.
     */
    protected void rebalance(Position<Entry<K, V>> p) {
        int oldHeight = 0, newHeight = 0;
         while ((oldHeight != newHeight) && p != null) {
            oldHeight = height(p);
            if (!isBalanced(p)) {
                p = restructure(tallerChild(tallerChild(p)));
                recomputeHeight(left(p));
                recomputeHeight(right(p));
            }
            recomputeHeight(p);
            newHeight = height(p);
            p = parent(p);
        }
    }


    /**
     * Overrides the TreeMap rebalancing hook that is called after an insertion.
     */
    //@Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        rebalance(p);
    }

    /**
     * Overrides the TreeMap rebalancing hook that is called after a deletion.
     */
    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        if (!isRoot(p)) {
            rebalance(parent(p));
        }
    }

}
