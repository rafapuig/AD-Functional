package es.rafapuig;

import es.rafapuig.utils.Iterables;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        //Programación funcional
        testIterableForEachMethodWithFunctionalProgramming();
        testIteratorForEachRemainingWithFunctionalProgramming();
    }

    @Test
    void testIterateOnArrays() {
        //Los arrays se pueden iterar con un bucle for-each
        for (String name : NAMES) {
            System.out.println(name);
        }
    }


    @Test
    void testIterableForEach() {
        // Adaptamos el array a una lista
        // (esto es lo que hace implícitamente Java para hacer un for-each con arrays)
        Iterable<String> iterable = Arrays.asList(NAMES);

        // La referencia que ponemos después de los : debe ser
        // a una instancia de un objeto cuya clase implemente Iterable<T>
        for (String name : iterable) {
            System.out.println("name = " + name);
        }
    }


    // -- iterar metodo genérico
    public static <T> void iterateGeneric(Iterable<T> iterable) {
        for (T item : iterable) {
            System.out.println("item = " + item);
        }
    }

    @Test
    void testIterateGeneric() {
        Iterable<?> iterable = Arrays.asList(NAMES);
        iterateGeneric(iterable);
    }

    // --- Usar el iterador
    @Test
    void testIterableIterator() {
        Iterable<String> iterable = Arrays.asList(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterable.iterator();

        //Repetimos el bucle mientras el iterador diga que hay elemento siguiente
        while (iterator.hasNext()) {
            String name = iterator.next();  //Obtenemos el siguiente elemento
            System.out.println("nombre = " + name);
        }
    }

    @Test
    void testIterableForEachMethod() {
        Iterable<String> iterable = Arrays.asList(NAMES);

        // Función de orden superior (programación funcional)
        // forEach(Consumer<? super T> consumer)

        // Interface funcional genérico Consumer<T>

        // //Se asigna a la variable consumer a un objeto anónimo implementador de la interface
        Consumer<? super String> consumer = new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println(name);
            }
        };
        // Se llama al metodo foreach pasando el implementador de Consumer
        iterable.forEach(consumer);
    }


    @Test
    void testIteratorForEachRemaining() {
        // Obtenemos el iterable
        Iterable<String> iterable = Arrays.asList(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterable.iterator();

        //Repetimos el bucle
        // mientras el iterador diga que hay elem siguiente
        // y no hayamos consumido más de 2 elementos
        int count = 0;
        while (iterator.hasNext() && count < 2) {
            String name = iterator.next();  //Obtenemos el siguiente elemento
            System.out.println("nombre = " + name); //Consumimos el elemento
            count++; //Indicamos que hemos consumido otro elemento incrementando la cuenta
        }

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println("otro nombre = " + name);
            }
        };

        //Vamos a consumir los elementos restantes (pasando el consumer al metodo)
        iterator.forEachRemaining(consumer);
    }

    @Test
    void testMyForEach() {
        Iterable<String> iterable = Arrays.asList(NAMES);
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String name) {
                joiner.add(name);
            }
        };

        //iterable.forEach(consumer);
        Iterables.forEach(iterable, consumer);
        System.out.println(joiner.toString());
    }


    //--- Los ejemplos que vienen a continuación utilizan PROGRAMACIÓN FUNCIONAL

    @Test
    void testMyForEachMethodLambda() {
        Iterable<String> iterable = Arrays.asList(NAMES);
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        Consumer<String> consumer = name -> joiner.add(name);

        //iterable.forEach(consumer);
        Iterables.forEach(iterable, consumer);
        System.out.println(joiner.toString());
    }

    @Test
    void testMyForEachMethodReference() {
        Iterable<String> iterable = Arrays.asList(NAMES);
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        Consumer<String> consumer = joiner::add;
        //Arrays.asList(NAMES).forEach(joiner::add);

        //equivale a iterable.forEach(consumer);
        Iterables.forEach(iterable, consumer);
        System.out.println(joiner.toString());


        System.out.println(
                Arrays.stream(NAMES).collect(Collectors.joining(", ", "[", "]"))
        );
    }


    @Test
    void testIterableForEachMethodWithFunctionalProgramming() {
        Iterable<String> iterable = Arrays.asList(NAMES);

        //Función de orden superior (programación funcional)
        //forEach(Consumer<? super T> consumer)

        //Se asigna a la variable consumer una expresión lambda (PF)
        Consumer<? super String> consumer = name -> System.out.println(name);
        iterable.forEach(consumer);

        //Llamada directa mediante una expresión lambda
        iterable.forEach(name -> System.out.println(name));


        // Una referencia a metodo de instancia explícita (tema PF)
        consumer = System.out::println;  //instancia::metodo
        iterable.forEach(consumer);

        //Llamada directa al metodo de orden superior forEach
        // con la referencia a metodo como argumento de llamada
        iterable.forEach(System.out::println);

        //forEach es equivalente a hacer un bucle for de este tipo
        Objects.nonNull(consumer);
        for (String s : iterable) {
            consumer.accept(s);
        }

    }

    @Test
    void testIteratorForEachRemainingWithFunctionalProgramming() {
        Iterable<String> iterableNames = Arrays.asList(NAMES);

        //Obtenemos el iterador
        Iterator<String> iterator = iterableNames.iterator();

        //Repetimos el bucle
        // mientras el iterador diga que hay elem siguiente
        // y no hayamos consumido más de 2 elementos
        int count = 0;
        while (iterator.hasNext() && count < 2) {
            String name = iterator.next();  //Obtenemos el siguiente elem
            System.out.println("name = " + name); //Consumimos el elemento
            count++; //Indicamos que hemos consumido otro elemento incrementando la cuenta
        }

        //Vamos a consumir los elementos restantes
        iterator.forEachRemaining(name -> System.out.println("otro name = " + name));
    }

}
