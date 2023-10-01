import functional.FunctionalUtil;
import functional.Item;
import functional.Mapper;
import model.Persona;
import model.Personas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

public class FunctionalDemo {

    public static <T> void printMapping(T[] from, int[] to) {
        for (int i = 0; i < from.length; i++) {
            System.out.println(from[i] + " mapeado a " + to[i]);
        }
    }

    public static void main(String[] args) {
        anonymousVsLambdasTest();
        lambdasBridging();
        methodReferencesTest();
        //mapperTest();
        //functionTRtest();
        //functionTRDefaultAndStaticTest();
        //predicateTtest();
        //functionalUtilTest();
        //methodReferenceIntroTest();
        //staticMethodReferenceTest();
        //instanceMethodReferenceTest();
        //superInstanceMethodReferenceTest();
        //constructorReferencesTest();
    }

    static void anonymousVsLambdasTest() {

        interface HelloFunctional {
            String sayHello(String name);
        }

        var helloAnonymous = new HelloFunctional() {
            @Override
            public String sayHello(String name) {
                return "Hola, " + name + "!(anonymous)";
            }
        };

        HelloFunctional helloLambda = name -> "Hola, " + name + "!(lambda)";

        String result1 = helloAnonymous.sayHello("Fulanito");
        String result2 = helloLambda.sayHello("Menganito");

        System.out.println(result1);
        System.out.println(result2);

        //Expresion lambda con cuerpo definido mediante un bloque
        HelloFunctional other1 =
                (final String name) -> {
                    return "Hola, " + name + "!(lambda)";
                };

        System.out.println(other1.sayHello("Zutanito"));
    }


    static void lambdasBridging() {
        //Una expresion lambda es una instancia de una interfaz funcional
        //Crear una nueva instancia requiere que el tipo este definido en el lado izquierdo
        Predicate<String> isNull = text -> text == null;
        Predicate<String> isNull1 = Objects::isNull; //Mediante referencia a metodo Objets.isNull(Object)

        //var isNull = text -> text == null; //NO COMPILA pq no se puede inferir el tipo
        //La firma del metodo SAM de la interfaz Predicate<String> se puede inferir:
        //boolean test(String input)

        interface LikePredicate<T> {
            boolean test(T value); //El SAM es identico a la de Predicate<T>
        }

        LikePredicate<String> isNull2 = value -> value == null;
        //Predicate<String> isNull3 =  isNull2; //NO COMPILA
        //Predicate<String> isNull4 = (Predicate<String>) isNull2; //COMPILA pero provocara una ClassCastException

        Predicate<String> isNull5 = isNull2::test; // Puentear mediante una referencia a metodo

        //Debido a esta incompatibilidad, no debemos definir nuevos interfaces funcionales
        //sino hacer uso de las interfaces funcionales disponibles en el paquete java.util.function

        class Filters {
            static List<String> filter1(List<String> values, Predicate<String> predicate) {
                List<String> result = new ArrayList<>();
                for(String value : values) {
                    if (predicate.test(value)) {
                        result.add(value);
                    }
                }
                return result;
            }

            static List<String> filter2(List<String> values, LikePredicate<String> predicate) {
                //return filter1(values, predicate::test); //Puentear
                return values.stream()
                        .filter(predicate::test)
                        .toList();
            }

            //Este metodo devuelve una funcion lambda  (una instancia de Predicate<Integer>)
            static Predicate<Integer> isGreaterThan(int value) {
                //Lexical scoping, captura del parametro value por la expresion lambda
                return compareValue -> compareValue > value;
            }
        }

        var values = Arrays.asList("a", null, "c");
        //Lambdas creadas ad hoc como argumentos de llamada no sufren la incompatibilidad
        //El compilador infiere correctamente a partir de la firma del metodo
        var result1 = Filters.filter1(values, value -> value == null);
        var result2 = Filters.filter2(values, value -> value == null);

        //De nuevo tendremos que puentear
        LikePredicate<Integer> isGreaterThan10 = Filters.isGreaterThan(10)::test;

        //Probar si 9 es mayor que 20 llamando a la lambda
        Filters.isGreaterThan(20).test(9);

        //Para ejecutar la expresion lambda tenemos que llamar al SAM de la interfaz funcional
        isGreaterThan10.test(19);
    }

    private static void mapperTest() {
        System.out.println("Mapear nombres a su longitud:");

        String[] names = {"Rafael", "Raul", "Emilio", "Ramon"};

        int[] lengthMapping = Mapper.mapToInt(
                names,
                name -> name.length());

        printMapping(names, lengthMapping);


        System.out.println("Mapear enteros a sus cuadrados:");

        Integer[] numbers = {1, 2, 3, 4};

        int[] countMapping = Mapper.mapToInt(numbers, n -> n * n);

        printMapping(numbers, countMapping);
    }


    //Function<T,R>

    static void functionTRtest() {
        Function<Integer, Integer> square1 = x -> x * x;
        IntFunction<Integer> square2 = x -> x * x;
        ToIntFunction<Integer> square3 = x -> x * x;
        UnaryOperator<Integer> square4 = x -> x * x;

        System.out.println(square1.apply(5));
        System.out.println(square2.apply(5));
        System.out.println(square3.applyAsInt(5));
        System.out.println(square4.apply(5));
    }

    static void functionTRDefaultAndStaticTest() {
        //Creear las funciones
        Function<Long, Long> square = x -> x * x;
        Function<Long, Long> addOne = x -> x + 1;

        //Componer funciones
        // default <V> Function<T,V> andThen(Function<? super R, ? extends V> after)
        Function<Long, Long> squareAddOne = square.andThen(addOne); //after

        // default <V> Function<V,R> compose(Function<? super V, ? extends T> before)
        Function<Long, Long> addOneSquare = square.compose(addOne); //before

        // static <T> Function<T,T> identity()
        Function<Long, Long> identity = Function.<Long>identity();

        long num = 5L;
        System.out.println("Numero: " + num);
        System.out.println("Cuadrado y despues añadir uno: " +
                squareAddOne.apply(num));
        System.out.println("Añadir uno y despues al cuadrado: " +
                addOneSquare.apply(num));

        System.out.println("Identidad: " + identity.apply(num));
    }

    // Predicate<T>

    // default Predicate<T> negate()
    // default Predicate<T> and(Predicate<? super T> other)
    // default Predicate<T> or(Predicate<? super T> other)

    //static <T> Predicate<T> isEqual(Object targetRef)

    static void predicateTtest() {
        Predicate<Integer> greaterThanTen = x -> x > 10;
        Predicate<Integer> divisibleByThree = x -> x % 3 == 0;
        Predicate<Integer> divisibleByFive = x -> x % 5 == 0;
        Predicate<Integer> equalToTen = Predicate.isEqual(10);

        Predicate<Integer> notGreaterThanTen = greaterThanTen.negate();
        Predicate<Integer> divisibleByThreeAndFive =
                divisibleByThree.and(divisibleByFive);

        Predicate<Integer> divisibleByThreeOrFive =
                divisibleByThree.or(divisibleByFive);

        int num = 10;
        System.out.println("Numero: " + num);
        System.out.println("greaterThanTen = " + greaterThanTen.test(num));
        System.out.println("divisibleByThree = " + divisibleByThree.test(num));
        System.out.println("divisibleByFive = " + divisibleByFive.test(num));
        System.out.println("notGreaterThanTen = " + notGreaterThanTen.test(num));
        System.out.println("divisibleByThreeAndFive = " +
                divisibleByThreeAndFive.test(num));
        System.out.println("divisibleByThreeOrFive = " +
                divisibleByThreeOrFive.test(num));
        System.out.println("equalToTen = " + equalToTen.test(num));
    }

    static void functionalUtilTest() {
        List<Persona> list = Personas.getPersonas();

        System.out.println("Lista original de personas: ");
        FunctionalUtil.forEach(list,
                p -> System.out.println(p));

        //Filtrar solo hombres
        List<Persona> maleList = FunctionalUtil.filter(list,
                p -> p.getSexo() == Persona.Sexo.HOMBRE);

        System.out.println("Lista de hombres: ");
        FunctionalUtil.forEach(maleList,
                p -> System.out.println(p));

        //Mapear cada persona con su año de nacimiento
        List<Integer> yearList = FunctionalUtil.map(list,
                p -> p.getNacimiento().getYear());

        System.out.println("Lista de años de nacimiento de las personas: ");
        FunctionalUtil.forEach(yearList,
                p -> System.out.println(p));

        //Aplicar la accion de añadir un año a cada hombre
        FunctionalUtil.forEach(maleList,
                p -> p.setNacimiento(p.getNacimiento().plusYears(1)));

        System.out.println("Lista de hombres despues de añadirles un año: ");
        FunctionalUtil.forEach(maleList,
                p -> System.out.println(p));
    }


    static void methodReferencesTest() {
        List<Persona> personas = Personas.PERSONAS;
        String[] result = personas.stream()
                .filter(persona -> persona.isMujer())
                .map(persona -> persona.getNombre())
                .map(nombre -> nombre.toUpperCase())
                .peek(nombre -> System.out.println(nombre))
                .toArray(count -> new String[count]);

        System.out.println(Arrays.toString(result));

        result = personas.stream()
                .filter(Persona::isMujer)
                .map(Persona::getNombre)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .toArray(String[]::new);

    }
    static void methodReferenceIntroTest() {
        ToIntFunction<String> lengthFunction = s -> s.length();
        String name = "Aitor Tilla";
        int length = lengthFunction.applyAsInt(name);
        System.out.println("name = " + name + ", length = " + length);

        lengthFunction = String::length;
        name = "Amador Denador";
        length = lengthFunction.applyAsInt(name);
        System.out.println("name = " + name + ", length = " + length);
    }

    static void staticMethodReferenceTest() {

        Function<Integer, String> func1 = x -> Integer.toBinaryString(x);
        System.out.println(func1.apply(192));

        Function<Integer, String> func2 = Integer::toBinaryString;
        System.out.println(func2.apply(180));

        BiFunction<Integer, Integer, Integer> func3 =
                (x, y) -> Integer.sum(x, y);
        System.out.println(func3.apply(14, 21));

        BiFunction<Integer, Integer, Integer> func4 = Integer::sum;
        System.out.println(func4.apply(14, 21));

        //Metodo sobrecargado Integer.valueOf
        //Usa static Integer valueOf(int i)
        Function<Integer, Integer> func21 = Integer::valueOf;
        //Usa static Integer valueOf(String s)
        Function<String, Integer> func22 = Integer::valueOf;
        //Usa static Integer valueOf(String s, int radix)
        BiFunction<String, Integer, Integer> func23 = Integer::valueOf;

        System.out.println(func21.apply(15));
        System.out.println(func22.apply("15"));
        System.out.println(func23.apply("1111", 2));

        Supplier<List<Persona>> supplier = Personas::getPersonas;
        List<Persona> personas = supplier.get();
        FunctionalUtil.forEach(personas, p -> System.out.println(p));
        //FunctionalUtil.forEach(personas, System.out::println);
    }

    static void instanceMethodReferenceTest() {
        Supplier<Integer> supplier = () -> "Armando".length();
        System.out.println(supplier.get());

        Supplier<Integer> supplier1 = "Armando"::length;
        System.out.println(supplier1.get());

        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("Hola");

        Consumer<String> consumer1 = System.out::println;
        consumer1.accept("Adios");

        List<Persona> personaList = Personas.getPersonas();
        FunctionalUtil.forEach(personaList, System.out::println);

        Function<Persona, String> nombreFunction = p -> p.getNombre();
        Function<Persona, String> nombreFunction1 = Persona::getNombre;


        Function<String, Integer> stringLength = String::length;
        String name = "Santiago";
        int len = stringLength.apply(name);
        System.out.println("nombre= " + name + ", longitud= " + len);

        String greeting = "Hola";
        String name1 = "Federico";
        BiFunction<String, String, String> concat = (s1, s2) -> s1.concat(s2);
        System.out.println(concat.apply(greeting, name1));

        BiFunction<String, String, String> concat1 = String::concat;
        System.out.println(concat1.apply(greeting, name1));

        List<String> nombresPersonas = FunctionalUtil.map(
                personaList,
                Persona::getNombre
        );
        FunctionalUtil.forEach(nombresPersonas, System.out::println);

    }

    static void superInstanceMethodReferenceTest() {

        // TypeName.super::instanceMethod
        Item surface = new Item("Surface", 0.75);
        surface.test();
    }

    static void constructorReferencesTest() {
        Supplier<String> func1 = () -> new String();
        Function<String, String> func2 = s -> new String(s);

        Supplier<String> func11 = String::new;
        Function<String, String> func22 = String::new;

        Supplier<Item> func31 = () -> new Item();
        Function<String, Item> func32 = name -> new Item(name);
        BiFunction<String, Double, Item> func33 =
                (name, price) -> new Item(name, price);

        System.out.println(func31.get());
        System.out.println(func32.apply("Melocoton"));
        System.out.println(func33.apply("Manzana", 0.56));

        Supplier<Item> func_31 = Item::new;
        Function<String, Item> func_32 = Item::new;
        BiFunction<String, Double, Item> func_33 = Item::new;

        System.out.println(func_31.get());
        System.out.println(func_32.apply("Melocoton"));
        System.out.println(func_33.apply("Manzana", 0.56));
    }

    static void arrayConstructorReferencesTest() {
        //Usando una expresion lambda
        IntFunction<int[]> arrayCreator = size -> new int[size];
        int[] nums = arrayCreator.apply(10);

        //Usando una referencia a un constructor de array
        IntFunction<int[]> arrayCreator1 = int[]::new;
        int[] nums1 = arrayCreator1.apply(10);
    }

    static void genericMethodReferencesTest() {
        // static <T> List<T> asList(T... a) de java.util.Arrays
        List<String> names = Arrays.asList("Rafael", "Ramon", "Emilio");
        List<String> names1 = Arrays.asList(new String[]{"Ramon", "Emilio"});

        Function<String[], List<String>> asListFunction = Arrays::asList;
        String[] namesArray = {"Rafael", "Ramon", "Emilio"};
        List<String> namesList = asListFunction.apply(namesArray);
        FunctionalUtil.forEach(namesList, System.out::println);
    }
}
