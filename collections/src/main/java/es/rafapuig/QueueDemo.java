package es.rafapuig;

import java.util.*;

public class QueueDemo {

    public static void main(String[] args) {

        Queue<String> words = new ArrayDeque<>(1); //16 elemetos inicialmente

        peekOrElementTest(words);

        words.add("boligrafo");
        peekOrElementTest(words);

        words.add("rotulador");
        words.add("borrador");
        words.offer("estuche");

        String headElement = words.poll();
        System.out.println(headElement);

        removeFrom(words);
        removeFrom(words);
        removeFrom(words);
        removeFrom(words);
        pollFrom(words);

        offerTo(words, "regla");
        offerTo(words,"lapiz");
        addTo(words,"compas");
        addTo(words,"libro");
        addTo(words,null); // NO se puede añadir null
        offerTo(words, null);


    }

    static void peekOrElementTest(Queue<String> words) {

        String word;

        try {
            word = words.element();
            System.out.println("word = " + word);
        } catch (NoSuchElementException e) {
            System.out.println(e.toString());
            System.out.println("La cola esta vacia.");
        }

        //No causa excepcion si la cola esta vacia
        if ((word = words.peek()) != null) {
            System.out.println("word = " + word);
        } else {
            System.out.println("word = " + word);
            System.out.println("La cola esta vacia.");
        }
    }


    static <T> void addTo(Queue<T> queue, T elem) {
        System.out.println("\n(ADD) Añadiendo un elemento al final de la cola");
        try {
            if (queue.add(elem)) {
                System.out.println("Elemento '" + elem + "' añadido");
                System.out.println(queue);
            }
        } catch (IllegalStateException e) {
            System.out.println("La cola esta llena.");
            System.out.println(e.toString());
        } catch (NullPointerException e) {
            System.out.println("No se puede añadir un elemento null.");
            System.out.println(e);
        }
    }

    static <T> void offerTo(Queue<T> queue, T elem) {
        System.out.println("\n(OFFER) Añadiendo un elemento al final de la cola");
        try {
            if (queue.offer(elem)) {
                System.out.println("Elemento '" + elem + "' añadido");
                System.out.println(queue);
            }
        } catch (NullPointerException e) {
            System.out.println("No se puede añadir un elemento null.");
            System.out.println(e.toString());
        }
    }

    static <T> void removeFrom(Queue<T> queue) {
        try {
            System.out.println("\n(REMOVE) Eliminando elemento cabeza de la cola");
            T elem = queue.remove();
            System.out.println("elem = " + elem);
            System.out.println("num elems restantes = " + queue.size());
            System.out.println(queue);

        } catch (NoSuchElementException e) {
            System.out.println(e.toString());
        }
    }

    static <T> void pollFrom(Queue<T> queue) {

        System.out.println("\n(POLL) Eliminando elemento cabeza de la cola");
        T elem;
        if((elem = queue.poll()) == null) {
            System.out.println("La cola esta vacia!");
        };
        System.out.println("elem = " + elem);
        System.out.println("num elems restantes = " + queue.size());
        System.out.println(queue);
    }

}
