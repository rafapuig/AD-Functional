package es.rafapuig.exercises.stack;

public class Node<T> {
    protected T item;
    protected Node<T> link;

    public Node(T item, Node<T> link) {
        this.item = item;
        this.link = link;
    }
}
