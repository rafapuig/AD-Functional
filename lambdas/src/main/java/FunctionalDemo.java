import functional.FunctionalUtil;
import functional.Item;
import functional.Mapper;
import model.people.Persona;
import model.people.Empleados;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class FunctionalDemo {


    public static void main(String[] args) {

        //introFunctionalProgramming();


        //mapperTest();

        //functionTRTest();
        //functionTRDefaultAndStaticTest();
        //functionTRCompositionTest();
        functionTRCompositionTest2();

        //predicateTtest();
        //supplierTest();

        //functionalUtilTest();

        //methodReferenceIntroTest();
        //methodReferencesTest();

        //staticMethodReferenceTest();
        //instanceMethodReferenceExplicitTest();
        //instanceMethodReferenceImplicitTest();
        //superInstanceMethodReferenceTest();
        //constructorReferencesTest();
        //arrayConstructorReferencesTest();
        //genericMethodReferencesTest();
    }

    private static void introFunctionalProgramming() {

        @FunctionalInterface
        interface DoubleToDoubleFunction {
            double apply(double x);
        }

        DoubleToDoubleFunction triple = (double x) -> x * 3;

        double result = triple.apply(5);
        System.out.println(result);

        result = triple.apply(1.5);
        System.out.println(result);

        DoubleToDoubleFunction square = x -> x * x;

        result = square.apply(5);
        System.out.println(result);

        DoubleToDoubleFunction function;

        function = triple;
        result = function.apply(5);
        System.out.println(result);

        function = square;
        result = function.apply(5);
        System.out.println(result);

        class Test {
            static double applyFunctionToX(DoubleToDoubleFunction function, double x) {
                return function.apply(x);
            }
        }

        result = Test.applyFunctionToX(triple, 1.5);
        System.out.println(result);

        result = Test.applyFunctionToX(square, 1.5);
        System.out.println(result);
    }


    //--------- Function<T,R> -----------------------------------
    // Representa la operación que se aplica a un valor de tipo T para obtener como resultado un valor de tipo R
    // Su SAM es: R apply(T t)

    static void functionTRTest() {
        // Observar que el código de la expresión lambda es el mismo en los cuatro casos:
        // x -> x * x
        // Sin embargo, el tipo inferido por el compilador (target type) depende del contexto
        // En este caso la lambda se encuentra a la derecha de una asignación su tipo se infiere a partir
        // del tipo de la variable a la que se asigna la expresión lambda
        Function<Integer, Integer> square1 = x -> x * x; //Recibe un T, devuelve un R (T:Integer, R:Integer)
        IntFunction<Integer> square2 = x -> x * x;  //Recibe un int - Devuelve un R (Integer en este ejemplo)
        ToIntFunction<Integer> square3 = x -> x * x; //Devuelve un int - Recibe un T (Integer en este ejemplo)
        UnaryOperator<Integer> square4 = x -> x * x; //Recibe un T, devuelve también un T (Integer en ejemplo)

        //UnaryOperator<T> hereda de Function<T,R> para hacer que R sea del mismo tipo que T

        System.out.println(square1.apply(5));
        System.out.println(square2.apply(5));
        System.out.println(square3.applyAsInt(5)); //Devuelve un int (tipo primitivo)
        System.out.println(square4.apply(5));

        //Incompatibles entre si
        //UnaryOperator<Integer> unaryOperator = square3;

        //Solución, "puentear" usando una referencia al método SAM
        UnaryOperator<Integer> unaryOperator = square1::apply;
        unaryOperator = square3::applyAsInt;
        unaryOperator = square2::apply;

        //O puentear con una expresión lambda que invoque el SAM pasándole como argumento la entrada de la lambda
        unaryOperator = x -> square1.apply(x);
        unaryOperator = x -> square2.apply(x);
        unaryOperator = x -> square3.applyAsInt(x);

        IntUnaryOperator intUnaryOperator = square4::apply;
    }

    static void functionTRDefaultAndStaticTest() {
        //Crear las funciones
        Function<Long, Long> square = x -> x * x;   // f(x)
        Function<Long, Long> addOne = x -> x + 1;   // g(x)

        //Componer funciones, (métodos de orden superior) reciben 2 funciones y devuelve una función
        // default <V> Function<T,V> andThen(Function<? super R, ? extends V> after)
        Function<Long, Long> squareAddOne = square.andThen(addOne); //andOne after - g(f(x))

        // default <V> Function<V,R> compose(Function<? super V, ? extends T> before)
        Function<Long, Long> addOneSquare = square.compose(addOne); //addOne before - f(g(x))

        // static <T> Function<T,T> identity()
        Function<Long, Long> identity = Function.<Long>identity(); // Equivale a x -> x es decir f(x) = x

        long num = 5L;
        System.out.println("Numero: " + num);
        System.out.println("Cuadrado y después añadir uno: " +
                           squareAddOne.apply(num));
        System.out.println("Añadir uno y después al cuadrado: " +
                           addOneSquare.apply(num));

        System.out.println("Identidad: " + identity.apply(num));
    }

    static void functionTRCompositionTest() {

        Function<String, Integer> textLength = (String text) -> text.length();
        Function<Integer, String> textRepeatX = (Integer length) -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                stringBuilder.append("X");
            }
            return stringBuilder.toString();
        };

        Function<String, String> textLengthRepeatX = textLength.andThen(textRepeatX);

        System.out.println(textLengthRepeatX.apply("cinco"));


        Function<Integer, Integer> lengthRepeatedText = textLength.compose(textRepeatX);

        System.out.println(lengthRepeatedText.apply(10));
    }


    private static void functionTRCompositionTest2() {

        Function<Persona, LocalDate> fechaNacimientoPersona = // (Persona) -> LocalDate     g(x)
                (Persona persona) -> persona.getNacimiento();

        Function<LocalDate, Integer> añosTranscurridos =    // (LocalDate) -> Integer       f(x)
                (LocalDate fecha) -> Period.between(fecha, LocalDate.now()).getYears();

        // f(g(x))
        Function<Persona, Integer> añosPersona = fechaNacimientoPersona.andThen(añosTranscurridos);

        System.out.println(Empleados.ARMANDO.getNombreCompleto());
        System.out.println(fechaNacimientoPersona.apply(Empleados.ARMANDO));
        System.out.println(añosTranscurridos.apply(Empleados.ARMANDO.getNacimiento()));
        System.out.println(añosPersona.apply(Empleados.ARMANDO));

        // f(g(x)) - f º g (x)
        Function<Persona, Integer> añosPersona2 = añosTranscurridos.compose(fechaNacimientoPersona);
        System.out.println(añosPersona2.apply(Empleados.ARMANDO));
    }


    private static void functionTRCompositionTest2AnonymousTypesVersion() {

        Function<Persona, LocalDate> fechaNacimientoPersona = new Function<Persona, LocalDate>() {
            @Override
            public LocalDate apply(Persona persona) {
                return persona.getNacimiento();
            }
        };

        Function<LocalDate, Integer> añosTranscurridos = new Function<LocalDate, Integer>() {
            @Override
            public Integer apply(LocalDate date) {
                return Period.between(date, LocalDate.now()).getYears();
            }
        };

        // f(g(x))
        Function<Persona, Integer> añosPersona = fechaNacimientoPersona.andThen(añosTranscurridos);

        System.out.println(Empleados.ARMANDO.getNombreCompleto());
        System.out.println(fechaNacimientoPersona.apply(Empleados.ARMANDO));
        System.out.println(añosTranscurridos.apply(Empleados.ARMANDO.getNacimiento()));
        System.out.println(añosPersona.apply(Empleados.ARMANDO));

        // f(g(x)) - f º g (x)
        Function<Persona, Integer> añosPersona2 = añosTranscurridos.compose(fechaNacimientoPersona);
        System.out.println(añosPersona2.apply(Empleados.ARMANDO));
    }



    // ----------- Predicate<T> ----------------------------------

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

    static void supplierTest() {
        Supplier<List<? extends Persona>> supplier = () -> Empleados.EMPLEADOS;
        List<? extends Persona> personas = supplier.get();
        FunctionalUtil.forEach(personas, p -> System.out.println(p));
        //FunctionalUtil.forEach(personas, System.out::println);
    }

    static void functionalUtilTest() {
        List<? extends Persona> list = Empleados.EMPLEADOS;

        System.out.println("Lista original de personas: ");
        FunctionalUtil.forEach(list,
                p -> System.out.println(p));

        //Filtrar solo hombres
        List<? extends Persona> maleList = FunctionalUtil.filter(list,
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
        List<? extends Persona> personas = Empleados.EMPLEADOS;
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
    }

    static void instanceMethodReferenceExplicitTest() {
        Supplier<Integer> supplier = () -> "Armando".length();
        System.out.println(supplier.get());

        Supplier<Integer> supplier1 = "Armando"::length;
        System.out.println(supplier1.get());

        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("Hola");

        Consumer<String> consumer1 = System.out::println;
        consumer1.accept("Adios");

        //
        List<? extends Persona> personaList = Empleados.EMPLEADOS;
        FunctionalUtil.forEach(personaList, System.out::println);
    }

    static void instanceMethodReferenceImplicitTest() {

        //Ejemplo con Persona.getNombre() -- Recive min una Persona, Dev String
        Function<? super Persona, String> nombreFunction = p -> p.getNombre();
        Function<? super Persona, String> nombreFunction1 = Persona::getNombre;

        System.out.println(nombreFunction.apply(Empleados.AMADOR));

        //Ejemplo con String.length() -- Recibe instancia String - Devuelve Int
        Function<String, Integer> stringLength = String::length;
        String name = "Santiago";
        int len = stringLength.apply(name);
        System.out.println("nombre= " + name + ", longitud= " + len);

        // Otro ejemplo con String.concat(String) -> String
        String greeting = "Hola";
        String name1 = "Federico";
        BiFunction<String, String, String> concat = (s1, s2) -> s1.concat(s2);
        System.out.println(concat.apply(greeting, name1));

        BiFunction<String, String, String> concat1 = String::concat;
        System.out.println(concat1.apply(greeting, name1));

        // referencia a instancia implicita Clase::metodoInstancia
        // Function<? super Persona,String> -- obj.getNombre() -> String
        List<? extends Persona> personaList = Empleados.EMPLEADOS;
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
        int[] nums1 = arrayCreator1.apply(20);

        for (int i = 0; i < nums.length; i++) {
            nums[i] = i * i;
        }
        System.out.println(Arrays.toString(nums));

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = i * i;
        }
        System.out.println(Arrays.toString(nums1));
    }

    static void genericMethodReferencesTest() {
        // static <T> List<T> asList(T... a) de java.util.Arrays
        List<String> names = Arrays.asList("Rafael", "Ramon", "Emilio");
        List<String> names1 = Arrays.asList(new String[]{"Ramon", "Emilio"});

        /*
        //La lista no permite añadir
        try {
            names.add("Federico");
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }*/

        Function<String[], List<String>> asListFunction = Arrays::asList;
        String[] namesArray = {"Rafael", "Ramon", "Emilio", "Raul"};
        List<String> namesList = asListFunction.apply(namesArray);
        FunctionalUtil.forEach(namesList, System.out::println);
    }
}
