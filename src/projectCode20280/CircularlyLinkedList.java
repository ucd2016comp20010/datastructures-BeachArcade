package projectCode20280;

import java.util.Iterator;

import projectCode20280.List;

public class CircularlyLinkedList<E> implements List<E> {
    private Node<E> head, tail = null;
    private int size = 0;

    /* Accessors */
    /* Looks at the first element of the list */
    public E first() {
        return (isEmpty() ? null : tail.getNext().getElement());
    }

    /* Looks at the last element in the list */
    public E last() {
        return (isEmpty() ? null : tail.getElement());
    }

    @Override
    public E get(int pos) throws IndexOutOfBoundsException {
        Node<E> currentNode = null;
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
        currentNode = tail.next;
        for (int i = 0; i <= pos; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getElement();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }



    /* writes list as a string */
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            Node<E> curr = tail.getNext();
            String str = "[";
            str += (tail.getNext().getElement());
            while (curr.getNext() != tail.getNext()) {
                curr = curr.next;
                str += ", ";
                str += (curr.getElement());

            }
            str += "]";
            return str;
        }
    }

    /* Mutators */

    @Override
    /* adds a node at a given position*/
    public void add(int pos, E e) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("Node is out of bounds!");
        }
        Node<E> node = new Node<>(e, null);
        Node<E> temp = tail.next;
        for (int i = 0; i <= size; i++) {
            temp = temp.getNext();
            if (i == pos - 1) {
                node.setNext(temp.getNext());
                temp.setNext(node);
                break;
            }
        }
        size++;
    }

    /* Adds a new node e to the front of the list */
    public void addFirst(E data) {
        if (size == 0) {
            tail = new Node<>(data, null);
            tail.setNext(tail);
        } else {
            Node<E> newNode = new Node<>(data, tail.getNext());
            tail.setNext(newNode);
        }
        size++;
    }

    /* adds a new node to the end of the list */
    public void addLast(E data) {
        addFirst(data);
        tail = tail.getNext();
    }

    @Override
    public E set(int pos, E data) throws IndexOutOfBoundsException {
        Node<E> node = new Node<E>(data, null);
        head = tail.getNext();
        for (int i = 0; i < pos; i++) {
            head = head.getNext();
        }
        head = node;
        return head.getElement();
    }

    @Override
    /* removes a node a given position */
    public E remove(int pos) throws IndexOutOfBoundsException {
        //Node initialization
        Node<E> prev = null, removed, temp = tail.next;

        if (temp != null && temp.getElement().equals(pos)) {
            removed = tail.getNext();
            return removed.getElement();
        }

        while (temp != null && !temp.getElement().equals(pos)) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null) {
            return null;
        }
        removed = temp;
        prev.setNext(temp.getNext());

        return removed.getElement();
    }
    /* Removes the first node */

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<E> head = tail.getNext();
        if (head == tail) {
            tail = null;
        } else {
            tail.setNext(head.getNext());
        }
        size--;
        return head.getElement();
    }

    /* Put first element to the back of the list */
    public void rotate() {
        if(!isEmpty()) {
            //tail = tail.getNext();
        }
    }

    /* Iterator */
    @Override
    public Iterator<E> iterator() {
        return new CircularLinkedListIterator();
    }

    private class CircularLinkedListIterator implements Iterator<E> {
        Node<E> curr;
        int iteratorIndex = 0;

        public CircularLinkedListIterator() {
            curr = tail.getNext();
        }

        @Override
        public boolean hasNext() {
            return iteratorIndex < size;
        }

        @Override
        public E next() {
            iteratorIndex++;
            E next = curr.getElement();
            curr = curr.getNext();
            return next;
        }
    }

    /* Node class */
    private static class Node<E> {
        private E elem;
        private Node<E> next;

        Node(E data, Node<E> nextNode) {
            elem = data;
            next = nextNode;
        }

        /* returns next node */
        public Node<E> getNext() {
            return next;
        }

        /* Returns the data inside the Node */
        public E getElement() {
            return elem;
        }

        /* sets the next node */
        void setNext(Node<E> next) {
            this.next = next;
        }
    }
}