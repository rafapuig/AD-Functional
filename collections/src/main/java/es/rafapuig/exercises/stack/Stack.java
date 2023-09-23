package es.rafapuig.exercises.stack;

import java.util.Iterator;

public interface Stack<E> extends Iterable<E> {

    void push(E item);

    E peek();

    E pop();

    boolean isEmpty();

}
