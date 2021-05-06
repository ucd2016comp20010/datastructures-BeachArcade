package projectCode20280;

import java.util.ArrayList;

public class ArrayQueue<E> implements Queue<E> {
    private E[] data;
    private int front, size;

    public ArrayQueue(int size) {
        data = (E[]) new Object[size];
        front = 0;
        this.size = 0;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return( size == 0);
    }

    @Override
    public void enqueue(E element){
        if(size == data.length){
            throw new IllegalArgumentException("Queue is full!");
        }
        int pos = (front + size) % data.length;
        data[pos] = element;
        size++;

    }

    @Override
    public E first() {
        return isEmpty() ? null: data[front];
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            return null;
        E element = data[front];
        data[front] = null;
        front = (front++) % data.length;
        size--;
        return element;
    }
}
