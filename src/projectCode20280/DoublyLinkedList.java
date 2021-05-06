package projectCode20280;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {
    private Node<E> header, trailer; //first and last nodes of the list
    private int size = 0;

    // Constructor
    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    // accessors

    // looks at first node
    public E first() {
        return isEmpty() ? null : header.getNext().getElement();
    }

    // looks at last node
    public E last() {
        return (isEmpty() ? null : trailer.getPrev().getElement());
    }

    //returns the size
    public int size() {
        return size;
    }

    //returns if the list is empty or not
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    //returns the node at a given position
    public E get(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node<E> curr = header;
        for (int i = 0; i <= pos; i++) {
            if (i == pos)
                return curr.getNext().getElement();
            curr = curr.getNext();
        }
        return null;
    }

    //updater methods
    @Override
    //sets the node at a current position
    public E set(int pos, E data) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<E> newNode = new Node<E>(data, null, null);
        for (int cycle = 0; cycle < pos; cycle++) {
            header = header.getNext();
        }
        header = newNode;
        return header.getElement();
    }

    @Override
    //adds a node aat the given position
    public void add(int pos, E data) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        //case when pos is the first pos
        if (pos == 0) {
            addFirst(data);
            //case when pos is the last pos
        } else if (pos == size) {
            addLast(data);
        } else {
            Node<E> curr = header.getNext();
            for (int i = 0; i < size; curr = curr.getNext(), i++) {
                if (i == pos) {
                    addBetween(data, curr.getPrev(), curr);
                    break;
                }
            }
        }
    }

    @Override
    //removes the node at a given position
    public E remove(int pos) throws IndexOutOfBoundsException {
        Node<E> curr = header.getNext(), ret;

        if (curr != null && curr.element.equals(pos)) {
            ret = header;
            header = curr.getNext();
            return ret.getElement();
        }
        //traverse list
        while (curr != null && !curr.element.equals(pos - 1)) {
            curr = curr.getNext();
        }

        if (curr == null) {
            return null;
        }

        ret = curr.getNext();
        curr.setNext(curr.getNext().getNext());
        size--;

        return ret.element;
    }

    //adds an element at the head
    public void addFirst(E e) {
        addBetween(e, header, header.getNext());
    }

    //adds an element at the trailer
    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }

    //removes first element
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        E node = header.getNext().getElement();
        Node<E> nextNode = header.getNext().getNext();
        header.setNext(nextNode);
        nextNode.setPrev(header);
        size--;
        return node;
    }

    //removes the last element
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        E node = trailer.getPrev().getElement();
        Node<E> prevNode = trailer.getPrev().getPrev();
        trailer.setPrev(prevNode);
        prevNode.setNext(trailer);
        size--;
        return node;
    }

    private void addBetween(E e, Node<E> nodeA, Node<E> nodeB) {
        Node<E> newest = new Node<>(e, nodeA, nodeB);
        nodeA.setNext(newest);
        nodeB.setPrev(newest);
        size++;
    }

    //removes the given node
    private E remove(Node<E> node) {
        Node<E> nodeA = node.getPrev();
        Node<E> nodeB = node.getNext();
        nodeA.setNext(nodeB);
        nodeB.setPrev(nodeA);
        size--;
        return node.getElement();
    }

    //returns the list as a string
    public String toString() {
        if (first() == null) {
            return "[]";
        }
        String result = "[" + first();
        Node<E> curr = header.getNext().getNext();
        while (curr != trailer) {
            result += ", " + curr.getElement();
            curr = curr.next;
        }
        result += "]";
        return result;
    }

    //iterator
    private class DoublyLinkedListIterator implements Iterator<E> {
        Node<E> curr = header.getNext();

        @Override
        public boolean hasNext() {
            return curr != trailer;
        }

        @Override
        public E next() {
            E next = curr.getElement();
            curr = curr.getNext();
            return next;
        }
    }

    //iterator class
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator();
    }

    //node class
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        //ArrayList<String> all;
        //LinkedList<String> ll;
        DoublyLinkedList<String> ll = new DoublyLinkedList<String>();

        String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

        for (String s : alphabet) {
            ll.addFirst(s);
            ll.addLast(s);
        }
        System.out.println(ll.toString());

        for (String s : ll) {
            System.out.print(s + ", ");
        }
    }
}
