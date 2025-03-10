package exercises.pairs;

import static java.util.Comparator.comparing;

public class PairsDemo {

    public static void main(String[] args) {

        test();
        testPairOfPairs();


    }

    private static void test() {

        Pair<String> stringPair = new FieldPair<>("Blanco", "Negro");

        System.out.println(stringPair.getFirst());
        System.out.println(stringPair.getSecond());


        Pair<Integer> integerPair = new FieldPair<>(1, 2);

        System.out.println(integerPair.getFirst());
        System.out.println(integerPair.getSecond());


        Pair<Number> numberPair = new FieldPair<>(1.5f, 2);

        System.out.println(numberPair.getFirst());
        System.out.println(numberPair.getSecond());

        Person perico = new Person("Perico Palotes", 45);
        Person amador = new Person("Amador Denador", 32);

        Pair<Person> personPair = new FieldPair<>(amador, perico);
        System.out.println(personPair.getFirst());
        System.out.println(personPair.getSecond());

        personPair = personPair.swap();
        System.out.println(personPair.getFirst());
        System.out.println(personPair.getSecond());

        Employee armando = new Employee("Armando", 36, 1500);
        FieldPair<Person> pair2 = new FieldPair<>(armando, amador);

        Employee belen = new Employee("Belen Tilla", 39, 1800);
        Pair<Employee> empPair = new FieldPair<>(belen, armando);

        Pair<? extends Person> pair = empPair;
        pair = personPair;
        //pair = numberPair;

        FieldPair<Character> surrogated = new FieldPair<>('\uD83C', '\uDC63');


        /*Combinator<CharSequence, CharSequence> stringCombinator = new Combinator<, CharSequence, >() {

            @Override
            public CharSequence combine(CharSequence a, CharSequence b) {
                return a.toString() + b.toString();
            }
        };

        String result = (String) stringCombinator.combine("a", "b");*/

        Pair<Pair<String>> pairOfPairs = new FieldPair<>(
                new FieldPair<>("Hola", "Adios"), new FieldPair<>("Gato", "Perro")
        );

        Pair<Pair<? extends Person>> pairOfPersons = new FieldPair<>(
                new FieldPair<>(belen, armando),
                empPair
        );
    }

    static void testPairOfPairs() {
        Person jose = new Person("Jose", 45);
        Person maria = new Person("Maria", 32);

        Employee armando = new Employee("Armando Bronca", 36, 1500);
        Employee belen = new Employee("Belen Tilla", 39, 1800);


        Pair<Person> joseAndMariaPair = new FieldPair<>(jose, maria);

        Pair<Employee> armandoAndBelenPair = new FieldPair<>(armando, belen);



        Pair<Person> personPair = joseAndMariaPair;

        Pair<? extends Person> personPair2 = armandoAndBelenPair;


        Pair.printPair(joseAndMariaPair);

        Pair.printPair(armandoAndBelenPair);


        Pair<Object> objectPair = new FieldPair<>(null, null);

        Pair.fill(objectPair, armando, belen);

        Pair<Employee> employeePair = new FieldPair<>(null, null);

        Pair.fill(employeePair, armando, belen);

        Pair<Pair<? extends Person>> xxxx = Pair.join(joseAndMariaPair, armandoAndBelenPair);

    }

    static void fillPair(Person p1, Person p2, FieldPair<? super Person> personPair) {
        personPair.setFirst(p1);
        personPair.setSecond(p2);
    }

    static void testCombine() {
        FieldPair<Integer> pair1 = new FieldPair<>(1, 2);
        FieldPair<Integer> pair2 = new FieldPair<>(3, 4);

        Combinator<Integer,Integer,Integer> combinator = new Combinator<Integer, Integer, Integer>() {
            @Override
            public FieldPair<Integer> combine(FieldPair<? extends Integer> a, FieldPair<? extends Integer> b) {
                return new FieldPair<>(a.getFirst() + b.getFirst(), a.getSecond() + b.getSecond());
            }
        };



        FieldPair<Short> pair3 = new FieldPair<>((short) 5, (short) 6);



    }


}
