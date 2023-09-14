package exercises.stack;

import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class StackTest {

    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        int value = stack.peek();
        System.out.println("value = " + value);

        while (!stack.isEmpty()) {
            value = stack.pop();
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

        for (Integer i : stack) {
            System.out.println(i);
        }

        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        Stack<String> stringStack = new LinkedStack<>(List.of("Rafa", "Emilio", "Raul"));
        for (String s : stringStack) {
            System.out.println(s);
        }

    }
}
