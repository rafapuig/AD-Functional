package es.rafapuig.exercises.stack;

import java.util.Collection;
import java.util.Iterator;

public class LinkedStack<E> implements Stack<E> {

    Node<E> head;

    public LinkedStack() {
        super();
    }

    public LinkedStack(Collection<E> collection) {
        this();
        for(E elem : collection) {
            push(elem);
        }
    }


    @Override
    public void push(E item) {
        head = new Node<>(item, head);
    }

    @Override
    public E peek() {
        if(isEmpty()) return null;
        return head.item;
    }

    @Override
    public E pop() {
        if(isEmpty()) return null;
        E item = head.item;
        head = head.link;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedStackIterator<E>(this);
    }
}
