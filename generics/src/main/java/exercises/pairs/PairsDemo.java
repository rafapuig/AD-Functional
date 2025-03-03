package exercises.pairs;

import java.util.Comparator;

import static java.util.Comparator.comparing;

public class PairsDemo {

    public static void main(String[] args) {

        Pair<String> stringPair = new Pair<>("Blanco", "Negro");

        System.out.println(stringPair.getFirst());
        System.out.println(stringPair.getSecond());


        Pair<Integer> integerPair = new Pair<>(1, 2);

        System.out.println(integerPair.getFirst());
        System.out.println(integerPair.getSecond());


        Pair<Number> numberPair = new Pair<>(1.5f, 2);

        System.out.println(numberPair.getFirst());
        System.out.println(numberPair.getSecond());

        Person perico = new Person("Perico Palotes", 45);
        Person amador = new Person("Amador Denador", 32);

        Pair<Person> personPair = new Pair<>(amador, perico);
        System.out.println(personPair.getFirst());
        System.out.println(personPair.getSecond());

        personPair = personPair.swap();
        System.out.println(personPair.getFirst());
        System.out.println(personPair.getSecond());

        Employee armando = new Employee("Armando", 36, 1500);
        Pair<Person> pair2 = new Pair<>(armando, amador);

        Employee belen = new Employee("Belen Tilla", 39, 1800);
        Pair<Employee> empPair = new Pair<>(belen, armando);

        Pair<? extends Person> pair = empPair;
        pair = personPair;
        //pair = numberPair;

        Pair<Character> surrogated = new Pair<>('\uD83C', '\uDC63');


        Combinator<CharSequence> stringCombinator = new Combinator<>() {

            @Override
            public CharSequence combine(CharSequence a, CharSequence b) {
                return a.toString() + b.toString();
            }
        };

        String result = (String) stringCombinator.combine("a", "b");




    }
}
