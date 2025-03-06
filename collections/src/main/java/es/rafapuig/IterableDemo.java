package es.rafapuig;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class IterableDemo {

    static String[] NAMES = {"Rafa", "Raul", "Rodrigo", "Ruben", "Ramon"};

    public static void main(String[] args) {
        new IterableDemo().run();
    }

    private void run() {
        testIterateOnArrays();
        testIterableForEach();
        testIterateGeneric();
        testIterableIterator();
        testIterableForEachMethod();
        testIteratorForEachRemaining();
    }

    @Test
    void testIterateOnArrays() {
        //Los arrays se pueden iterar con un bucle for-each
        for(String name : NAMES) {
            System.out.println(name);
        }
    }

    @Test
    void testIterableForEach() {
        // Adaptamos el array a una lista
        // (esto es lo que hace implícitamente Java para hacer un for-each con arrays)
        Iterable<String> iterable = List.of(NAMES);

        // La referencia que ponemos después de los : debe ser
        // a una instancia de un objeto cuya clase implemente Iterable<T>
        for (String name : iterable) {
            System.out.println("name = " + name);
        }
    }

    // -- iterar método genérico
    static <T> void iterateGeneric(Iterable<T> iterable) {
        for (T item : iterable) {
            System.out.println("item = " + item);
        }
    }

    @Test void testIterateGeneric() {
        Iterable<?> iterable = List.of(NAMES);
        iterateGeneric(iterable);
    }

    // --- Usar el iterador
    @Test
    void testIterableIterator() {
        Iterable<String> iterableNames = List.of(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterableNames.iterator();

        //Repetimos el bucle mientras el iterador diga que hay elem siguiente
        while(iterator.hasNext()) {
            String name = iterator.next();  //Obtenemos el siguiente elem
            System.out.println("name = " + name);
        }
    }

    //--- Los ejemplos que vienen a continuación utilizan PROGRAMACIÓN FUNCIONAL

    static void testIterableForEachMethod() {
        Iterable<String> iterable = List.of(NAMES);

        //Función de orden superior (programación funcional)
        //forEach(Consumer<? super T> consumer)

        //Se asigna a la variable consumer una expresión lambda (PF)
        Consumer<? super String> consumer = name -> System.out.println(name);
        iterable.forEach(consumer);

        //Llamada directa mediante una expresión lambda
        iterable.forEach(name -> System.out.println(name));


        // Una referencia a método de instancia explicita (tema PF)
        consumer = System.out::println;  //instancia::método
        iterable.forEach(consumer);

        //Llamada directa al método de orden superior forEach
        // con la referencia a método como argumento de llamada
        iterable.forEach(System.out::println);

        //forEach es equivalente a hacer un bucle for de este tipo
        for(String s : iterable) {
            consumer.accept(s);
        }

    }

    static void testIteratorForEachRemaining() {
        Iterable<String> iterableNames = List.of(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterableNames.iterator();

        //Repetimos el bucle
        // mientras el iterador diga que hay elem siguiente
        // y no hayamos consumido más de 2 elementos
        int count = 0;
        while(iterator.hasNext() && count < 2) {
            String name = iterator.next();  //Obtenemos el siguiente elem
            System.out.println("name = " + name); //Consumimos el elemento
            count++; //Indicamos que hemos consumido otro elemento incrementando la cuenta
        }

        //Vamos a consumir los elementos restantes
        iterator.forEachRemaining(name -> System.out.println("otro name = " + name));
    }

}
