package es.rafapuig;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

public class DequeDemo {

    public static void main(String[] args) {
        new DequeDemo().run();
    }

    @SuppressWarnings("unchecked")
    static Deque<String>[] implementers = (Deque<String>[]) new Deque<?>[]{
            new LinkedList<>(),
            new ArrayDeque<>()
    };

    private void run() {
        for (Deque<String> implementer : implementers) {
            testDequeOperations(implementer);
            testDequeLIFO(implementer);
        }
    }

    /**
     * Testear con las dos implementaciones de Deque: ArrayDeque y LinkedList
     */
    static Deque<String>[] dequeImplementersProvider() {
        return implementers;
    }


    @ParameterizedTest
    @MethodSource("dequeImplementersProvider")
    void testDequeOperations(Deque<String> ciudades) {
        System.out.println("Testeando con un objeto de clase " + ciudades.getClass().getSimpleName());

        String[] ciudadesArray = {"Cadiz", "Madrid", "Malaga"};
        // Un Deque es una cola de dos extremos: First (Cabeza) y Last (Cola)
        //Deque<String> ciudades = new ArrayDeque<>(Arrays.asList(ciudadesArray));

        ciudades.addAll(Arrays.asList(ciudadesArray));

        // Añadir con excepciones
        ciudades.add("Murcia"); // añadir a la cola, equivale a addLast
        ciudades.addLast("Sevilla");
        ciudades.addFirst("Bilbao"); // añadir a la cabeza
        print(ciudades);

        // Añadir con valor especial
        ciudades.offer("Valencia"); // añadir a la cola, equivale a offerLast
        ciudades.offerLast("Zaragoza");
        ciudades.offerFirst("Barcelona"); // añadir a la cabeza
        print(ciudades);

        // Examinar con valor especial
        System.out.println("peekFirst() = " + ciudades.peekFirst()); // examina el primero: la cabeza
        System.out.println("peek() = " + ciudades.peek()); // equivale a pickFirst
        System.out.println("peekLast() = " + ciudades.peekLast()); // examina el último: la cola

        // Examinar con excepciones
        System.out.println("getFirst() = " + ciudades.getFirst()); // examina el primero: la cabeza
        System.out.println("element() = " + ciudades.element()); // equivale a getFirst
        System.out.println("getLast() = " + ciudades.getLast()); // examina el último: la cola

        // Eliminar con valor especial
        System.out.println("pollFirst() = " + ciudades.pollFirst()); // elimina el primero: la cabeza
        print(ciudades);
        System.out.println("poll() = " + ciudades.poll()); // equivale a pollFirst
        print(ciudades);
        System.out.println("pollLast() = " + ciudades.pollLast()); // elimina el último: la cola
        print(ciudades);

        // Eliminar con excepciones
        System.out.println("pollFirst() = " + ciudades.removeFirst()); // elimina el primero: la cabeza
        print(ciudades);
        System.out.println("remove() = " + ciudades.remove()); // equivale a removeFirst
        print(ciudades);
        System.out.println("pollLast() = " + ciudades.removeLast()); // elimina el último: la cola
        print(ciudades);
    }

    @ParameterizedTest
    @MethodSource("dequeImplementersProvider")
    void testDequeExceptions(Deque<String> deque) {
        System.out.println("Testeando con un objeto de clase " + deque.getClass().getSimpleName());
        deque.clear();

        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer removeFirst() en una cola vacía");
        }

        try {
            deque.remove();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer remove() en una cola vacía");
        }

        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer removeLast() en una cola vacía");
        }

        try {
            deque.getFirst();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer getFirst() en una cola vacía");
        }

        try {
            deque.element();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer element() en una cola vacía");
        }

        try {
            deque.getLast();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer getLast() en una cola vacía");
        }
    }

    /**
     * Probar el Deque como cola LIFO, es decir, como una Pila
     */
    @ParameterizedTest
    @MethodSource("dequeImplementersProvider")
    void testDequeLIFO(Deque<String> stack) {
        System.out.println("\nTesteando con un objeto de clase " + stack.getClass().getSimpleName());
        //Deque<String> stack = new ArrayDeque<>();
        printStack(stack);

        stack.push("uno");
        printStack(stack);

        // El metodo push equivale al addFirst, añadir por la cabeza de la cola
        stack.addFirst("dos");
        printStack(stack);

        stack.push("tres"); // NO equivale a add de Queue, add añade por la cola
        printStack(stack);

        System.out.println("isEmpty() -> " + stack.isEmpty());
        System.out.println("peek() -> " + stack.peek());
        System.out.println("getFirst() -> " + stack.getFirst()); // peek equivale a getFirst


        String value = stack.pop();
        System.out.println("pop() -> " + value);
        printStack(stack);

        // El metodo pop equivale a removeFirst, eliminar por la cabeza
        value = stack.removeFirst();
        System.out.println("pop() -> " + value);
        printStack(stack);

        value = stack.remove(); // El metodo pop equivale a removeFirst y este equivale a remove de Queue
        System.out.println("pop() -> " + value);
        printStack(stack);

        System.out.println("isEmpty() -> " + stack.isEmpty());

        try {
            value = stack.pop(); // El metodo pop equivale a removeFirst
            System.out.println("pop() -> " + value);
            printStack(stack);
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + ": Error al hacer pop en una pila vacía");
        }

    }

    static void printStack(Deque<String> stack) {
        System.out.print("peek() = " + stack.peek() + " -> ");
        print(stack);
    }

    static void print(Deque<?> deque) {
        System.out.println(deque);
    }

}
