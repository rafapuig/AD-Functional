package es.rafapuig;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class IterableDemo {

    static String[] NAMES = {"Rafa", "Raul", "Rodrigo", "Ruben", "Ramon"};

    public static void main(String[] args) {
        testIterateOnArrays();
        testIterableForEach();
        testIterateGeneric();
        testIterableIterator();
        testIterableForEachMethod();
        testIteratorForEachRemaining();
    }

    static void testIterateOnArrays() {
        //Los arrays se pueden iterar con un bucle for-each
        for(String name : NAMES) {
            System.out.println(name);
        }
    }

    static void testIterableForEach() {
        // Adaptamos el array a un lista
        // (esto es lo que hace implicitamente Java para hacer un for-each con arrays)
        Iterable<String> iterable = List.of(NAMES);

        // La referencia que ponemos despues de los : debe ser
        // a una instancia de un objeto cuya clase implemente Iterable<T>
        for (String name : iterable) {
            System.out.println("name = " + name);
        }
    }

    // -- iterar metodo generico
    static <T> void iterateGeneric(Iterable<T> iterable) {
        for (T item : iterable) {
            System.out.println("item = " + item);
        }
    }

    static void testIterateGeneric() {
        Iterable<?> iterable = List.of(NAMES);
        iterateGeneric(iterable);
    }

    // --- Usar el iterador
    static void testIterableIterator() {
        Iterable<String> iterableNames = List.of(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterableNames.iterator();

        //Repetimos el bucle mientras el iterador diga que hay elem siguiente
        while(iterator.hasNext()) {
            String name = iterator.next();  //Obtenemos el siguiente elem
            System.out.println("name = " + name);
        }
    }

    //--- Los ejemplos que vienen a continuacion utilizan PROGRAMACION FUNCIONAL

    static void testIterableForEachMethod() {
        Iterable<String> iterable = List.of(NAMES);

        //Funcion de orden superior (programacion funcional)
        //forEach( Consumer<? super T> consumer)

        //Se asigna a la variable consumer una expresion lambda (PF)
        Consumer<? super String> consumer = name -> System.out.println(name);
        iterable.forEach(consumer);

        //Llamada directa mediante una expresion lamda
        iterable.forEach(name -> System.out.println(name));


        // Una referencia a metodo de instacia explicita (tema PF)
        consumer = System.out::println;  //instancia::metodo
        iterable.forEach(consumer);

        //Llamada directa al metodo de orden superior
        // con la rferencia a metodo
        iterable.forEach(System.out::println);
    }

    static void testIteratorForEachRemaining() {
        Iterable<String> iterableNames = List.of(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterableNames.iterator();

        //Repetimos el bucle
        // mientras el iterador diga que hay elem siguiente
        // y no hayamos consumido mas de 2 elementos
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
