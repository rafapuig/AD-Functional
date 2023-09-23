package es.rafapuig.exercises.stack;

import java.util.Iterator;
import java.util.List;

public class StackTest {

    public static void main(String[] args) {

        Stack<Integer> stack = new LinkedStack<>(List.of(1,2,3,4));
        
        /*stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);*/
        stack.push(5);
        stack.push(6);
        stack.push(7);

        for(int i : stack) {
            System.out.println("i = " + i);
        }

        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if(i % 2 == 1)
                iterator.remove();
                //iterator.remove(); IllegalStateException
        }

        for(int i : stack) {
            System.out.println("i = " + i);
        }
    }
    
}
