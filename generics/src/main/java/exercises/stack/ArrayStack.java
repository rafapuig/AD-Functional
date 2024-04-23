package exercises.stack;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class ArrayStack<T> implements Stack<T> {

    private T[] elements;

    private int head = -1;

    public ArrayStack(int maxSize) {
        elements = (T[]) new Object[maxSize];
    }

    public ArrayStack(Class<?> type, int maxSize) {
        elements = (T[]) Array.newInstance(type, maxSize);
    }

    //Construir una pila a partir de una colección de elementos
    public ArrayStack(Collection<T> collection) {
        this(collection.toArray()[0].getClass(), collection.size());
        for (T elem : collection) {  //Y recorremos la colección
            push(elem);             //para ir apilando elemento a elemento
        }
    }

    @Override
    public void push(T info) {
        if (!isFull())
            elements[++head] = info;
    }

    @Override
    public T peek() {
        if (isEmpty()) return null;
        return peekCore();
    }

    private T peekCore() {
        return elements[head];
    }

    @Override
    public T pop() {
        if (isEmpty()) return null;
        return elements[head--];
    }

    @Override
    public boolean isEmpty() {
        return head == -1;
    }

    private boolean isFull() {
        return head == elements.length - 1;
    }

    private class ArrayStackIterator implements Iterator<T> {

        int currentIndex = ArrayStack.this.head;

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public T next() {
            /*
            T value = elements[currentIndex];
            currentIndex--;
            return value;
            */
            return elements[currentIndex--];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayStackIterator();
    }
}
