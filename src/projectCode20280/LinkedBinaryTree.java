package projectCode20280;

// Concrete implementation of a binary tree using a node-based, linked structure.

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {
    protected Node<E> root = null;
    private int size = 0; // number of nodes in tree

    //makes empty tree
    public LinkedBinaryTree() {
    }

    //creates a new node storing element e.
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // Returns the number of nodes in the tree.
    @Override
    public int size() {
        return size;
    }

    // Replaces the element at Position p with element e and returns the replaced element
    public E set(Position<E> pos, E data) throws IllegalArgumentException {
        Node<E> node = validate(pos);
        E nodeData = node.getElement();
        node.setElement(data);
        return nodeData;
    }

    //Returns the root Position of the tree (or null if tree is empty).
    @Override
    public Position<E> root() {
        return root;
    }

    // Returns the Position of p's parent (or null if p is root).
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    // gets right child of a position
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getRight();
    }

    // gets left child of a position
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    //Places element e at the root of an empty tree and returns its new Position.
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("The tree is not empty");
        }
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    //Creates a new left child of a position
    public Position<E> addRight(Position<E> pos, E data) throws IllegalArgumentException {
        Node<E> parent = validate(pos), childNode;
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("Position isn't empty!");
        }
        childNode = createNode(data, parent, null, null);
        parent.setRight(childNode);
        size++;
        return childNode;
    }

    //Creates a new left child of a position
    public Position<E> addLeft(Position<E> pos, E data) throws IllegalArgumentException {
        Node<E> parent = validate(pos), childNode;
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("Position isn't empty!");
        }
        childNode = createNode(data, parent, null, null);
        parent.setLeft(childNode);
        size++;
        return childNode;
    }

    // Attaches trees tree1 and tree2, respectively, as the left and right subtree of the
    // leaf Position p. As a side effect, tree1 and tree2 are set to empty trees.
    public void attach(Position<E> p, LinkedBinaryTree<E> treeA, LinkedBinaryTree<E> treeB) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) {
            throw new IllegalArgumentException("Not a leaf node!");
        }
        size += (treeA.size + treeB.size);
        if (!treeA.isEmpty()) {
            treeA.root.setParent(node);
            node.setLeft(treeA.root);
            treeA.root = null;
            treeB.size = 0;
        }
        if (!treeB.isEmpty()) {
            treeB.root.setParent(node);
            node.setRight(treeB.root);
            treeB.root = null;
            treeB.size = 0;
        }
    }

    // Removes the node at a position and replaces it with its child, if any
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p), child, parent;
        E data;
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("Cannot remove a node with 2 children");
        }
        if (node.getLeft() != null) {
            child = node.getLeft();
        } else {
            child = node.getRight();
        }
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = child;
        } else {
            parent = node.getParent();

            if (node == parent.getRight()) {
                parent.setRight(child);
            } else {
                parent.setLeft(child);
            }
        }
        size--;
        data = node.getElement();
        node.setParent(node);
        node.setElement(null);
        node.setRight(null);
        node.setLeft(null);
        return data;
    }

    // Verifies that a Position belongs to the appropriate class, and is not one
    // that has been previously removed. Note that our current implementation does
    // not actually verify that the position belongs to this particular list
    // instance.
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;       // safe cast
        if (node.getParent() == node)     // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }


    public void createLevelOrder(E[] arr) {
        root = cloUtil(arr, null, 0);
    }

    private Node<E> cloUtil(E[] arr, Node<E> parent, int pos) {
        Node<E> ret = parent, node;
        if (pos < arr.length) {
            size++;

            node = createNode(arr[pos], parent, null, null);
            node.setLeft(cloUtil(arr, node.getLeft(), 2 * pos + 1));
            node.setRight(cloUtil(arr, node.getRight(), 2 * pos + 2));

            ret = node;
        }
        return ret;

    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (Position<E> p : positions()) {
            str.append(p.getElement().toString() + ", ");
        }
        str.delete(str.length() - 2, str.length());
        str.append("]");
        return str.toString();
    }

    //node class
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> leftChild, rightChild, parent;


        public Node(E data, Node<E> above, Node<E> left, Node<E> right) {
            this.element = data;
            this.parent = above;
            this.rightChild = right;
            this.leftChild = left;
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }

        public void setElement(E data) {
            element = data;
        }

        //returns the parent
        public Node<E> getParent() {
            return parent;
        }

        //returns the right child
        public Node<E> getRight() {
            return rightChild;
        }

        //returns the left child
        public Node<E> getLeft() {
            return leftChild;
        }

        //sets the right child to node
        public void setRight(Node<E> node) {
            rightChild = node;
        }

        //sets the left child to node
        public void setLeft(Node<E> node) {
            leftChild = node;
        }

        //sets the parent to a node
        public void setParent(Node<E> node) {
            parent = node;
        }

        //returns the element as a string
        public String toString() {
            return (element == null ? "N/A" : element.toString());
        }
    }
}