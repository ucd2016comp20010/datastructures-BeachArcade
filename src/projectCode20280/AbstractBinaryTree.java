package projectCode20280;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    //Returns the Position of p's sibling (or null if no sibling exists).

    @Override
    public Position<E> sibling(Position<E> p) {
        Position<E> sib;
        if (parent(p) == null) {
            sib  = null;
        } else if (p == left(parent(p))) {
            sib = right(parent(p));
        } else {
            sib = left(parent(p));
        }
        return sib;
}

    //Returns the number of children of Position p.
    @Override
    public int numChildren(Position<E> p) {
        int num = 0;
        if (right(p) != null) {
            num++;
        }
        if (left(p) != null) {
            num++;
        }
        return num;
    }

    // Returns an iterable collection of the Positions representing p's children.
    @Override
    public Iterable<Position<E>> children(Position<E> pos) {
        List<Position<E>> snap = new ArrayList<>(2);    // max capacity of 2
        if (left(pos) != null) {
            snap.add(left(pos));
        }
        if (right(pos) != null) {
            snap.add(right(pos));
        }
        return snap;
    }

    //Adds positions of the subtree rooted at Position p to the given
    // snapshot using an inorder traversal
    private void inorderSubtree(Position<E> pos, List<Position<E>> snap) {
        if (left(pos) != null) {
            inorderSubtree(left(pos), snap);
        }
        snap.add(pos);
        if (right(pos) != null) {
            inorderSubtree(right(pos), snap);
        }
    }

    // Returns an iterable collection of positions of the tree, reported in inorder.
    public Iterable<Position<E>> inorder() {
        List<Position<E>> snap = new ArrayList<>();
        //initialize the snap
        if (!isEmpty()){
            inorderSubtree(root(), snap);
        }
        return snap;
    }

    //Returns an iterable collection of the positions of the tree using inorder traversal
    @Override
    public Iterable<Position<E>> positions() {
        return inorder();
    }
}

