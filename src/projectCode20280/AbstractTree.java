package projectCode20280;

import projectCode20280.LinkedQueue;
import projectCode20280.Position;
import projectCode20280.Queue;
import projectCode20280.Tree;

import javax.swing.text.ElementIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public abstract class AbstractTree<E> implements Tree<E> {

    // Returns true if Position p has one or more children.
    @Override
    public boolean isInternal(Position<E> pos) {
        return numChildren(pos) > 0;
    }

    // Returns true if Position p does not have any children.
    @Override
    public boolean isExternal(Position<E> pos) {
        return numChildren(pos) == 0;
    }

    @Override
    //returns pos is if root
    public boolean isRoot(Position<E> pos) {
        return pos == root();
    }

    //returns the number of children of Position p.
    @Override
    public int numChildren(Position<E> pos) {
        int num = 0;
        for(Position<E> e : children(pos))
            num++;
        return num;
    }

    //Returns the number of nodes in the tree.
    @Override
    public int size() {
        int size = 0;
        for(Position<E> e : positions()) {
            size++;
        }
        return size;
    }



    @Override
    //returns if tree is empty
    public boolean isEmpty() {
        return size() == 0;
    }


    //Returns depth of a node.
    public int depth(Position<E> p) throws IllegalArgumentException {
        return isRoot(p) ? 0 : depth(parent(p)) + 1;
    }

    // Returns the height of the tree.
    private int heightBad() {
        int h = 0;
        for (Position<E> p : positions())
            if (isExternal(p)) {
                h = Math.max(h, depth(p));
            }
        return h;
    }

    // Returns the height of the subtree rooted at Position p.
    public int height(Position<E> pos) throws IllegalArgumentException {
        int height = 0;
        for(Position<E> p : children(pos)) {
            height = Math.max(height, 1 + height(p));
        }
        return height;
    }


    // Returns an iterator of the elements stored in the tree.
    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    // Returns an iterable collection of the positions of the tree.
    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }

    //Adds positions of the subtree rooted at Position p to the
    // given snapshot using a preorder traversal
    private void preorderSubtree(Position<E> p, List<Position<E>> snap) {
        snap.add(p);
        for(Position<E> c : children(p)){
            postorderSubtree(c, snap);
        }
    }

    // Returns an iterable collection of positions of the tree, reported in preorder.

    public Iterable<Position<E>> preorder() {
        List<Position<E>> snap = new ArrayList<>();
        if(!isEmpty()) {
            postorderSubtree(root(), snap);
        }
        return snap;

    }

    //Adds positions of the subtree rooted at Position p to the given
    // snapshot using a postorder traversal
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        for (Position<E> child : children(p)) {
            postorderSubtree(child, snapshot);
        }
        snapshot.add(p);
    }

    //Returns an iterable collection of positions of the tree, reported in postorder.
    public Iterable<Position<E>> postorder() {
        List<Position<E>> snap = new ArrayList<>();
        if(!isEmpty()) {
            postorderSubtree(root(), snap);
        }
        return snap;
    }

    // Returns an iterable collection of positions of the tree in breadth-first order.
    public Iterable<Position<E>> breadthfirst() {
        Position<E> currPos;
        List<Position<E>> pos = new ArrayList<>();
        if(!isEmpty()){
            Queue<Position<E>> queue = new LinkedQueue<>();
            queue.enqueue(root());

            while(!queue.isEmpty()) {
                currPos = queue.dequeue();
                pos.add(currPos);

                for(Position<E> currChild: children(currPos)) {
                    queue.enqueue(currChild);
                }
            }
        }
        return pos;
    }


    // This class adapts the iteration produced by positions() to return elements.
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        public boolean hasNext() {
            return posIterator.hasNext();
        }

        public E next() {
            return posIterator.next().getElement();
        }

        public void remove() {
            posIterator.remove();
        }
    }
}