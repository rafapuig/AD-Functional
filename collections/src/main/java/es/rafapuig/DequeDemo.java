package es.rafapuig;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DequeDemo {

    public static void main(String[] args) {

        String[] ciudadesArray = {"Madrid", "Valencia", "Barcelona"};

        Deque<String> ciudades = new ArrayDeque<>(Arrays.asList(ciudadesArray));

        ciudades.add("Sevilla"); //addLast
        ciudades.addLast("Bilbao");
        ciudades.addFirst("Zaragoza");
        print(ciudades);

        ciudades.offerLast("Cadiz");
        ciudades.offer("Malaga");
        ciudades.offerFirst("Murcia");
        print(ciudades);

        ciudades.push("Caceres"); //addFirst
        print(ciudades);

        String ciudad = ciudades.pop(); //removeFirst
        System.out.println("ciudad = " + ciudad);
        print(ciudades);

        ciudad = ciudades.peek(); //peekFirst
        System.out.println("ciudad = " + ciudad);
        print(ciudades);
    }

    static void print(Deque<?> deque) {
        System.out.println(deque);
    }

}
