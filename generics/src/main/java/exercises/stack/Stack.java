package exercises.stack;

import java.util.Iterator;

public interface Stack<T> extends Iterable<T> {
    public void push(T info);
    T peek();
    T pop();
    boolean isEmpty();
}
