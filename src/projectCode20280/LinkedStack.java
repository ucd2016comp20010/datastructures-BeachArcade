package projectCode20280;

public class LinkedStack<E> implements Stack<E> {
    private SinglyLinkedList<E> stack = new SinglyLinkedList<>();

    public LinkedStack(){ }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void push(E data) {
        stack.addFirst(data);
    }

    @Override
    public E top() {
        return stack.first();
    }

    @Override
    public E pop() {
        return stack.removeFirst();
    }

    public String toString(){
        return stack.toString();
    }

}
