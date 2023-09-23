package es.rafapuig.exercises.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStackIterator<E> implements Iterator<E> {

    LinkedStack<E> stack;
    Node<E> current;

    protected LinkedStackIterator(LinkedStack<E> linkedStack) {
        this.stack = linkedStack;
        this.current = linkedStack.head;
    }

    @Override
    public boolean hasNext() {
        return this.current != null;
    }

    Node<E> toRemove;   //Nodo a eliminar si se llama a remove

    @Override
    public E next() {
        if (current == null) throw new NoSuchElementException();
        toRemove = current;
        E item = current.item;
        current = current.link;
        return item;
    }

    @Override
    public void remove() {
        //Iterator.super.remove();

        //Si no se ha llamado a next() or multiple llamada a remove
        if (toRemove == null) throw new IllegalStateException();

        //System.err.println(toRemove.item + " " + toRemove.link);

        remove(toRemove);

        toRemove = null; //marcamos que ya hemos procesado la eliminacion
    }

    protected void remove(Node<E> toRemove) {
        //Si el nodo a eliminar esta en la cabeza (cima)
        if (toRemove == stack.head) {
            stack.pop();    //Simplemente lo desapilamos

        } else { //si no lo buscamos y nos quedamos con una referencia al nodo previo
            Node<E> prev = stack.head;
            Node<E> node = prev.link;

            while (node != toRemove) {
                prev = node;
                node = node.link;
            }
            //Hacemos que el nodo siguiente al previo se salte el nodo a eliminar
            prev.link = node.link;
        }
    }
}
