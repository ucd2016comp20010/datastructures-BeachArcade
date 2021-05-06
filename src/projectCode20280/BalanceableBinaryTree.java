package projectCode20280;

public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {

    //returns aux
    public int getData(Position<Entry<K, V>> p) {
        return ((BSTNode<Entry<K, V>>) p).getData();
    }
    //set aux
    public void setData(Position<Entry<K, V>> p, int value) {
        ((BSTNode<Entry<K, V>>) p).setData(value);
    }

    // makes new node
    @Override
    protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left, Node<Entry<K, V>> right) {
        return new BSTNode<>(e, parent, left, right);
    }

    /// links a parent and child
    private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
    }

    // Rotates position above its parent.
    public void rotate(Position<Entry<K, V>> p) {
        Node<Entry<K, V>> node = validate(p), parent = node.getParent();

        if (parent == this.root) {
            this.root = node;
            node.setParent(null);
        } else {
            relink(parent.getParent(), node, parent.getParent().getLeft() == parent);
        }

        if (node == parent.getLeft()) {
            relink(parent, node.getRight(), true);
            relink(node, parent, false);
        } else {
            relink(parent, node.getLeft(), false);
            relink(node, parent, true);
        }
    }
    public Position<Entry<K, V>> restructure(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> parent = parent(p);
        Position<Entry<K, V>> grandparent = parent(parent);
        if ((right(parent) == p) == (right(grandparent) == parent)) {
            rotate(parent);
            return parent;
        }
        rotate(p);
        rotate(p);
        return p;
    }
    protected class BSTNode<E> extends Node<E> {
        int data = 0;
        BSTNode(E data, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
            super(data, parent, leftChild, rightChild);
        }
        public int getData() {
            return data;
        }
        public void setData(int value) {
            data = value;
        }
    }
}