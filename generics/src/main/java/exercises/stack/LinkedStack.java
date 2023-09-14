package exercises.stack;

import java.util.Collection;
import java.util.Iterator;

public class LinkedStack<T> implements Stack<T> {


    class LinkedStackIterator implements Iterator<T> {

        Node<T> current;

        LinkedStackIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.data;
            current = current.link;
            return data;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedStackIterator();
    }

    static class Node<T> {
        T data;
        Node<T> link;

        public Node(T data, Node<T> link) {
            this.data = data;
            this.link = link;
        }
    }

    Node<T> head;

    public LinkedStack() {
        head = null;
    }

    public LinkedStack(Collection<T> collection) {
        this();
        for(T elem : collection) {
            push(elem);
        }
    }

    public void push(T info) {
        Node<T> node = new Node<>(info, head);
        head = node;
    }

    public T peek() {
        if (!isEmpty()) {
            return peekCore();
        }
        return null;
    }

    private T peekCore() {
        return head.data;
    }

    public T pop() {
        if (!isEmpty()) {
            T data = peekCore();
            head = head.link;
            return data;
        } else return null;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
