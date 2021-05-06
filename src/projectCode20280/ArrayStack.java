package projectCode20280;

public class ArrayStack<E> implements Stack<E> {
    private E[] data;
    private int index;

    public ArrayStack(int size) {
        data = (E[]) new Object[size];
        index = -1;
    }

    @Override
    public int size() {
        return(index + 1);
    }

    @Override
    public boolean isEmpty() {
        return(index == -1);
    }

    @Override
    public void push(E element) {
        if(size() == data.length){
            throw new IllegalArgumentException("Stack is full!");
        }
        data[++index] = element;
    }

    @Override
    public E top() {
        if(isEmpty()) return null;
        return isEmpty() ? null : data[index];
    }

    @Override
    public E pop() {
        if(isEmpty()) {
            return null;
        }
        E element = data[index];
        data[index--] = null;
        return element;
    }

}
