package exercises.stack;

import java.util.Iterator;
import java.util.List;

public class NonGenericStackDemo {

    public static void main(String[] args) {
        nonGenericStackUseExample();
    }

    static void nonGenericStackUseExample() {
        LinkedStack stack = new LinkedStack();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        int value = (int) stack.peek();
        System.out.println("value = " + value);

        while (!stack.isEmpty()) { //Mientras haya elementos en la pila
            value = (int) stack.pop();    //Desapilamos y mostramos el valor
            System.out.println("value = " + value);
        }

        stack.push(80);
        stack.push(90);
        stack.push(100);

        for (Object i : stack) {   //Comprobar que implementa Iterable
            System.out.println(i);
        }

        // Iterar "a mano" mediante el iterador
        Iterator iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        //Crear una pila a partir de una lista de elementos
        Stack stringStack = new LinkedStack(List.of("Rafa", "Emilio", "Raul"));
        for (Object s : stringStack) {
            System.out.println(s);
        }

    }
}
