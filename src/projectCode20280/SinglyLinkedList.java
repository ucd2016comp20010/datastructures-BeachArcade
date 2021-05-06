package projectCode20280;

import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinglyLinkedList<E> implements Cloneable, Iterable<E>, List<E> {

    // instance variables of the SinglyLinkedList
    private Node<E> head = null, tail = null; //first and last elements
    private int size = 0;

    public SinglyLinkedList() {
    }

    /* accessors methods */

    /* looks at the first element */
    public E first() {
        return isEmpty() ? null : head.getElement();
    }

    /* looks at last element */
    public E last() {
        return get(size - 1);
    }

    /* gets the last element */
    public Node<E> getLast() {
        Node<E> last = head;
        while (last.getNext().getNext() != null) {
            last = last.getNext();
        }
        return last;
    }

    @Override
    public E get(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
        Node<E> currentNode = head;
        for (int i = 0; i <= pos; i++) {
            if (i != pos) {
                currentNode = currentNode.getNext();
            }
        }
        return (currentNode != null ? currentNode.getElement() : null);
    }

    /* returns size */
    public int size() {
        return size;
    }

    /* returns is empty or not */
    public boolean isEmpty() {
        return size == 0;
    }

    //Mutators

    @Override
    public E set(int pos, E data) throws IndexOutOfBoundsException {
        Node<E> newNode = new Node<E>(data, null);
        for (int i = 0; i < pos; i++) {
            head = head.getNext();
        }
        head = newNode;
        return head.getElement();
    }

    @Override
    public void add(int pos, E data) throws IndexOutOfBoundsException {
        if (pos < 0 || pos > size()) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }

        Node<E> newNode = new Node<>(data, null), temp = head;
        for (int i = 0; i <= size; temp = temp.getNext(), i++) {
            if (i == pos - 1) {
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
                break;
            }
        }
        size++;
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        //Initialize nodes
        Node<E> prev = null, ret = null, temp = head;

        if (temp != null && temp.getElement().equals(i)) {
            ret = head;
            head = temp.getNext();
            return ret.getElement();
        }

        while (temp != null && !temp.getElement().equals(i)) {
            prev = temp;
            temp = temp.getNext();
        }

        if (temp == null) {
            return null;
        }
        ret = temp;
        prev.setNext(temp.getNext());

        return ret.getElement();
    }

    //updater methods

    //Adds a node to the front of the list
    public void addFirst(E e) {
        head = new Node<E>(e, head);
        size++;
    }

    /* Adds an node to the end of the list.*/
    public void addLast(E e) {
        if (isEmpty())
            addFirst(e);
        else {
            Node<E> newNode = new Node<E>(e, null), curr = head;
            while (curr != null) {
                if (curr.getNext() == null) {
                    curr.setNext(newNode);
                    break;
                }
                curr = curr.getNext();
            }
            size++;
        }
    }

    //removes the first node
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        E data = head.getElement();
        head = head.getNext();
        size--;

        if (size == 0) {
            tail = null;
        }

        return data;
    }

    //removes the last node
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }

        E data = null;
        Node<E> curr = head;

        while (curr != null) {
            if (curr.getNext() == null) {
                data = curr.getElement();
                curr.setNext(null);
                size--;
                break;
            }
            curr = curr.getNext();
        }
        return data;
    }

    //misc methods

    @SuppressWarnings({"unchecked"})
    public boolean equals(Object o) {
        boolean isEqual = true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SinglyLinkedList<E> thatList = (SinglyLinkedList<E>) o;
        Node<E> thisNode = head, thatNode = thatList.head;

        while (thisNode != null) {
            if (!thisNode.getElement().equals(thatNode.getElement())) {
                isEqual = false;
            }
            thisNode = thisNode.getNext();
            thatNode = thatNode.getNext();
        }
        return isEqual;
    }

    @SuppressWarnings({"unchecked"})
    public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
        SinglyLinkedList<E> copy = (SinglyLinkedList<E>) super.clone();
        if (size > 0) {
            copy.head = new Node<E>(head.getElement(), null);
            Node<E> curr = head.getNext(), currCopy = copy.head;
            while (curr != null) {
                Node<E> newest = new Node<E>(curr.getElement(), null);
                currCopy.setNext(newest);
                currCopy = newest;
                curr = curr.getNext();
            }
        }
        return copy;
    }

    //makes a string
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        Node<E> curr = head;
        String str = "[";
        str += head.getElement();
        while (curr.getNext() != null) {
            curr = curr.next;
            str += ", ";
            str += curr.getElement();

        }
        str += "]";
        return str;
    }

    private class SinglyLinkedListIterator implements Iterator<E> {
        Node<E> curr = head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E next = curr.getElement();
            curr = curr.getNext();
            return next;
        }
    }

    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator();
    }

    /* Node class */
    private static class Node<E> {
        private E elem;
        private Node<E> next;

        public Node(E e, Node<E> nextNode) {
            elem = e;
            next = nextNode;
        }

        public E getElement() {
            return elem;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}

