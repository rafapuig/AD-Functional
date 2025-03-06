package exercises.pairs;

import static java.util.Comparator.comparing;

public class PairsDemo {

    public static void main(String[] args) {

        test();
        testPairOfPairs();

    }

    private static void test() {
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


        /*Combinator<CharSequence, CharSequence> stringCombinator = new Combinator<, CharSequence, >() {

            @Override
            public CharSequence combine(CharSequence a, CharSequence b) {
                return a.toString() + b.toString();
            }
        };

        String result = (String) stringCombinator.combine("a", "b");*/

        Pair<Pair<String>> pairOfPairs = new Pair<>(
                new Pair<>("Hola", "Adios"), new Pair<>("Gato", "Perro")
        );

        Pair<Pair<? extends Person>> pairOfPersons = new Pair<>(
                new Pair<>(belen, armando),
                empPair
        );
    }

    static void testPairOfPairs() {
        Person jose = new Person("Jose", 45);
        Person maria = new Person("Maria", 32);

        Employee armando = new Employee("Armando Bronca", 36, 1500);
        Employee belen = new Employee("Belen Tilla", 39, 1800);

        Pair<Person> joseAndMariaPair = new Pair<>(jose, maria);
        Pair<Employee> armandoAndBelenPair = new Pair<>(armando, belen);


        Pair<Person> personPair = joseAndMariaPair;
        Pair<? extends Person> personPair2 = armandoAndBelenPair;

        Pair.printPair(joseAndMariaPair);
        Pair.printPair(armandoAndBelenPair);

        Pair<Object> objectPair = new Pair<>(null, null);
        Pair.fill(armando, belen, objectPair);
        Pair<Employee> employeePair = new Pair<>(null, null);
        Pair.fill(armando, belen, employeePair);

        Pair<Pair<? extends Person>> xxxx = Pair.join(joseAndMariaPair, armandoAndBelenPair);

    }

    static void fillPair(Person p1, Person p2, Pair<? super Person> personPair) {
        personPair.setFirst(p1);
        personPair.setSecond(p2);
    }

    static void testCombine() {
        Pair<Integer> pair1 = new Pair<>(1, 2);
        Pair<Integer> pair2 = new Pair<>(3, 4);

        Combinator<Integer,Integer,Integer> combinator = new Combinator<Integer, Integer, Integer>() {
            @Override
            public Pair<Integer> combine(Pair<? extends Integer> a, Pair<? extends Integer> b) {
                return new Pair<>(a.getFirst() + b.getFirst(), a.getSecond() + b.getSecond());
            }
        };



        Pair<Short> pair3 = new Pair<>((short) 5, (short) 6);



    }


}
