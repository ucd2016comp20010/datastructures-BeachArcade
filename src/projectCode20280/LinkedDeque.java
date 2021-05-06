package projectCode20280;

import java.util.LinkedList;

public class LinkedDeque<E> implements Deque<E> {

    public static void main(String[] args) {
        LinkedDeque<Integer> ass= new LinkedDeque<Integer>();
        ass.addFirst(10);
    }
    LinkedDeque<E> lDeque = new LinkedDeque<>();
    @Override
    public int size() {
        return lDeque.size();
    }

    @Override
    public boolean isEmpty() {
        return lDeque.isEmpty();
    }

    @Override
    public E first() {
        return lDeque.first();
    }

    @Override
    public E last() {
        return lDeque.last();
    }

    @Override
    public void addFirst(E e) {
        lDeque.addFirst(e);

    }

    @Override
    public void addLast(E e) {
        lDeque.addLast(e);

    }

    @Override
    public E removeFirst() {
        return lDeque.removeFirst();
    }

    @Override
    public E removeLast() {
        return lDeque.removeLast();
    }

}
