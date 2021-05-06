package projectCode20280;

public class LinkedQueue<E> implements Queue<E> {

    private SinglyLinkedList<E> list = new SinglyLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }


    @Override
    public E first() throws Exception {
        return list.first();
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }

    @Override
    public void enqueue(E data) {
        list.addLast(data);
    }

    public String toString() {
        return list.toString();
    }

}
