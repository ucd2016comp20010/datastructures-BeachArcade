package projectCode20280;

public class LinkedCircularQueue<E> implements Queue<E> {

    private CircularlyLinkedList<E> queue;
    public LinkedCircularQueue(){
        queue = new CircularlyLinkedList<>();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public E first() {
        return queue.first();
    }

    @Override
    public E dequeue() {
        return queue.removeFirst();
    }

    @Override
    public void enqueue(E e) {
        queue.addLast(e);
    }

    @Override
    public String toString(){
        return queue.toString();
    }
}
