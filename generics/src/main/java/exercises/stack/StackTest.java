package exercises.stack;

import java.util.Iterator;
import java.util.List;

//Clase para probar el funcionamiento de la clase Stack<T>
public class StackTest {

    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        int value = stack.peek();
        System.out.println("value = " + value);

        while (!stack.isEmpty()) { //Mientras haya elementos en la pila
            value = stack.pop();    //Desapilamos y mostramos el valor
            System.out.println("value = " + value);
        }

        stack.push(50);
        stack.push(60);
        stack.pop();
        stack.push(70);

        while (!stack.isEmpty()) {
            value = stack.pop();
            System.out.println("value = " + value);
        }

        stack.push(80);
        stack.push(90);
        stack.push(100);

        for (Integer i : stack) {   //Comprobar que implementa Iterable
            System.out.println(i);
        }

        // Iterar "a mano" mediante el iterador
        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        //Crear una pila a partir de una lista de elementos
        Stack<String> stringStack = new LinkedStack<>(List.of("Rafa", "Emilio", "Raul"));
        for (String s : stringStack) {
            System.out.println(s);
        }

    }
}
