import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class StreamsDemo {

    public static void main(String[] args) {
        example1();
        example2();
        example3();

        // Selección: filtrado, orden
        filterExample();
        dropWhileExample();
        takeWhileExample();
        limitExample();
        skipExample();
        distinctExample();
        sortedExample();

        // Transformación: mapeo
        mapExample();
        flatMapExample();
        mapMultiExample();

        // Reducción
        reduceExample();
        reduceWithoutInitialValueExample();
        reduceWithoutMapping();

        countExample();
        sumExample();
        averageExample();
        statisticsExample();

        aggregateElementsDemo();

        toMapGroupingByCorners();
        toMapGroupingByToString();
        toMapGroupingByStringToCount();
        toMapGroupingByStringToSumCorners();
        toMapGroupingByStringToStatistics();

        toMapPartitionByHasCorners();
        toMapPartitionByHasCornersGroupingByCorners();

        joiningExample();

        toListExample();
        toArrayExample();

        findAnyExample();
        anyMatchExample();
        allMatchExample();

        testConcat();
    }

    // ¿Cómo calcularíamos la suma de los números impares elevados al cuadrado de una lista?
    static void example1() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5); // la lista de números

        // Con programación imperativa este sería el código
        int sum = 0;    // Una variable (mutable) va mutando mientras se itera
        for (int n : numbers) { // Se usa un bucle para iterar de forma externa sobre los elementos
            if (n % 2 == 1) {   // Se filtran los elementos que interesan (los impares)
                int square = n * n; // Se transforman (mapean) a su valor al cuadrado
                sum += square;  // Se acumulan (suman) a la suma parcial
            }
        }
        System.out.println(sum);    //En la variable sum ha quedado al final el resultado

        //Con Stream API generamos un stream a partir de la lista
        // Y aplicamos operaciones intermedias que devuelven un stream que se encadena
        // y una operación terminal al final sobre el último stream de la cadena
        // Estas operaciones sobre el stream pueden ser métodos de orden superior (PF)
        final int sum1 = numbers.stream()
                .filter(n -> n % 2 == 1) //operación intermedia / lazy
                .map(n -> n * n) //operación intermedia / lazy
                .reduce(0, Integer::sum); //op terminal / eager

        System.out.println(sum1);

        final int sum2 = numbers.stream()
                .filter(n -> n % 2 == 1)
                .map(n -> n * n)
                .reduce(0, (a, b) -> a + b); //equivalente con lambda

        System.out.println(sum2);
    }

    // Reescribimos la version funcional con Stream API pero con las instrucciones más detallas
    // para ver que sucede al detalle
    // Vemos un uso intensivo de las interfaces funcionales de la programación funcional
    // El uso de expresiones lambda facilita mucho la creación de las instancias de interfaces funcionales
    static void example2() {
        List<Integer> numbersList = List.of(1, 2, 3, 4, 5);

        // Obtener un stream a partir de la lista, llamando al método stream de la interfaz Collection
        Stream<Integer> numbersStream = numbersList.stream();

        //Obtener un stream de números impares
        Predicate<Integer> oddFilter = number -> number % 2 == 1;
        Predicate<Integer> evenFilter = Predicate.not(oddFilter); // o támbien oddFilter.negate();
        //Stream<Integer> oddNumbersStream = numbersStream.filter(n -> n % 2 == 1);
        Stream<Integer> oddNumbersStream = numbersStream.filter(oddFilter);

        //Obtener un stream de los cuadrados de los números impares
        UnaryOperator<Integer> squareNumber = number -> number * number;
        //Stream<Integer> squaredNumbersStream = oddNumbersStream.map(squareNumber);
        Stream<Integer> squaredNumbersStream = oddNumbersStream.map(n -> n * n);

        //Sumar todos los enteros del stream
        BinaryOperator<Integer> accumulator = (a, b) -> a + b;
        //BinaryOperator<Integer> accumulator = Integer::sum; //la clase Integer tiene método static sum
        int sum = squaredNumbersStream.reduce(0, accumulator);
        //int sum = squaredNumbersStream.reduce(0, (n1, n2) -> n1 + n2);

        System.out.println(sum);
    }

    //Versión con stream específico para el tipo primitivo int
    static void example3() {
        List<Integer> numbersList = List.of(1, 2, 3, 4, 5);

        // Obtener un stream a partir de la lista, llamando al método stream de la interfaz Collection
        Stream<Integer> stream = numbersList.stream();

        //Obtener un IntStream (int tipo primitivo) mapeando un Integer a su valor int
        IntStream numbersStream = stream.mapToInt(Integer::intValue);

        //Obtener un stream de números impares
        IntPredicate oddFilter = number -> number % 2 == 1;
        IntPredicate evenFilter = oddFilter.negate();
        //IntStream oddNumbersStream = numbersStream.filter(n -> n % 2 == 1);
        IntStream oddNumbersStream = numbersStream.filter(oddFilter);

        //Obtener un stream de los cuadrados de los números impares
        IntUnaryOperator squareNumber = number -> number * number;
        //IntStream squaredNumbersStream = oddNumbersStream.map(squareNumber);
        IntStream squaredNumbersStream = oddNumbersStream.map(n -> n * n);

        //Sumar todos los enteros del stream
        IntBinaryOperator accumulator = (a, b) -> a + b;
        //BinaryOperator<Integer> accumulator = Integer::sum; //la clase Integer tiene método static sum
        int sum = squaredNumbersStream.reduce(0, accumulator);
        //int sum = squaredNumbersStream.reduce(0, (n1, n2) -> n1 + n2);

        System.out.println(sum);
    }


    public record Shape(int corners) implements Comparable<Shape> {

        //MÉTODOS HELPER
        public boolean hasCorners() {
            return corners > 0;
        }

        public List<Shape> twice() {
            return List.of(this, this);
        }


        @Override
        public String toString() {
            return switch (corners) {
                case 0 -> "\u25CF";
                case 3 -> "\u25B2";
                case 4 -> "\u25A0";
                default -> throw new IllegalStateException("Unexpected value: " + corners);
            };
        }

        // Las formas se comparan en función del número de esquinas
        @Override
        public int compareTo(Shape other) {
            return Integer.compare(corners, other.corners());
        }

        //MÉTODOS FACTORÍA (crean nuevos objetos de tipo Shape)
        public static Shape circle() {
            return new Shape(0);
        }

        public static Shape triangle() {
            return new Shape(3);
        }

        public static Shape square() {
            return new Shape(4);
        }
    }

    // Fuente de datos para los ejemplos
    static Shape[] SAMPLE_SHAPES_ARRAY = new Shape[]{
            Shape.triangle(),
            Shape.square(),
            Shape.triangle(),
            Shape.circle(),
            Shape.triangle(),
            Shape.circle()
    };

    static <T> void printStream(Stream<T> stream) {
        System.out.print(stream.toList());
    }

    static void printShapes(List<Shape> list) {
        System.out.print(list);
    }

    static void printShapes(Shape... shapes) {
        printShapes(List.of(shapes));
    }


    // Selección de elementos

    static void filterExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        // Generar un stream a partir de un array
        //Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY); // otra forma de crear el array
        Stream<Shape> stream = Arrays.stream(SAMPLE_SHAPES_ARRAY);

        // Aplicar la operación intermedia filter para obtener un otro Stream que aplicará el filtro
        Stream<Shape> filtered = stream.filter(Shape::hasCorners);

        printStream(filtered);
        System.out.println(" <- .filter(Shape::hasCorners)");
    }

    static void dropWhileExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> droppedWhile = stream.dropWhile(Shape::hasCorners);

        printStream(droppedWhile);
        System.out.println(" <- .dropWhile(Shape::hasCorners)");
    }

    static void takeWhileExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> takenWhile = stream.takeWhile(Shape::hasCorners);

        printStream(takenWhile);
        System.out.println(" <- .takeWhile(Shape::hasCorners)");
    }

    static void limitExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> shapeStream = stream.limit(2L);

        printStream(shapeStream);
        System.out.println(" <- .limit(2L)");
    }

    static void skipExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> skipped = stream.skip(2L);

        printStream(skipped);
        System.out.println(" <- .skip(2L)");
    }

    static void distinctExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> distincted = stream.distinct();

        printStream(distincted);
        System.out.println(" <- .distinct()");
    }

    static void sortedExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> sorted = stream.sorted();

        printStream(sorted);
        System.out.println(" <- .sorted()");
    }


    // Mapeo de elementos

    static void mapExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Integer> sorted = stream.map(Shape::corners);

        printStream(sorted);
        System.out.println(" <- .map(Shape::corners)");
    }

    static void flatMapExample() {

        System.out.println("Ejemplo flatMap -------------------------------------");

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

        Stream<Shape> distinct = stream.distinct();

        printStream(distinct);
        System.out.println(" <- .distinct()");


        stream = Stream.of(SAMPLE_SHAPES_ARRAY).distinct();

        Stream<List<Shape>> listStream = stream.map(Shape::twice);

        printStream(listStream);
        System.out.println(" <- .map(Shape::twice)");


        Stream<Shape> flatMapped = Stream.of(SAMPLE_SHAPES_ARRAY)
                .distinct()
                .map(Shape::twice)
                .flatMap(List::stream);

        printStream(flatMapped);
        System.out.println(" <- .flatMap(List::stream)");
    }

    static void mapMultiExample() {

        System.out.println("Ejemplo mapMulti ------------------------------------");

        Stream<Shape> mappedMulti = Stream.of(SAMPLE_SHAPES_ARRAY)
                .distinct()
                .mapMulti((shape, shapeConsumer) ->
                        shape.twice().forEach(shapeConsumer));


        printStream(mappedMulti);
        System.out.println(" <- .mapMulti()");
    }

    // -------------Reducción -----------------------------------------
    // Reduce los elementos del Stream a un resultado individual o único
    // mediante la aplicación repetidamente del un operador acumulador

    // Tal operador acumulador usa el resultado previo para combinarlo con
    // el elemento actual en proceso para generar un nuevo resultado

    // Se supone que el acumulador siempre devuelve un valor nuevo, sin
    // requerir una estructura de datos intermedia que vaya mutando


    static Integer sumReductionImperative(Collection<Integer> numbers) {
        int result = 0; // Valor inicial, depende del objetivo

        for (Integer value : numbers) { // Aplicada a cada elemento
            result = Integer.sum(result, value); //Lógica de la reducción, función acumuladora
        }
        return result;  // El valor de la reducción
    }

    // Añadimos programación funcional y creamos un método de orden superior
    // El acumulador será comportamiento parametrizable
    // mediante una interfaz funcional BinaryOperator<T>
    static <T> T reduce(Collection<T> elements, T initialValue, BinaryOperator<T> accumulator) {
        T result = initialValue;

        for (T element : elements) {
            result = accumulator.apply(result, element);
        }
        return result;
    }

    // Reducción sin valor inicial o semilla
    static <T> Optional<T> reduce(Collection<T> elements, BinaryOperator<T> accumulator) {
        if (elements.isEmpty()) return Optional.empty();
        Iterator<T> iterator = elements.iterator();
        T result = iterator.next();
        while (iterator.hasNext()) {
            result = accumulator.apply(result, iterator.next());
        }
        return Optional.of(result);
    }

    //Operación sum() un tipo de reducción especializada consistente en sumar los elementos
    static Integer sum(Collection<Integer> numbers) {
        return reduce(numbers, 0, Integer::sum);
    }

    //Operación max(), una reducción que consiste en quedarse con el elemento mayor de todos
    static Optional<Integer> max(Collection<Integer> numbers) {
        return reduce(numbers, Math::max);
    }

    static void reduceTest() {
        Collection<Integer> numbers = List.of(1, 2, 3, 4, 5);
        int sum1 = sumReductionImperative(numbers);
        int sum2 = sum(numbers);
        int sum3 = reduce(numbers, 0, Integer::sum);
        Optional<Integer> max1 = max(numbers);
        Optional<Integer> max2 = reduce(numbers, Math::max);
    }


    static void reduceExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        long totalCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .map(Shape::corners)
                .reduce(0,
                        (partialSum, corners) -> {
                            System.out.println("Acumulado = " + partialSum);
                            System.out.println("Esquinas del elemento = " + corners);
                            return partialSum + corners;
                        });

        System.out.println("result = " + totalCorners);
    }

    static void reduceWithoutInitialValueExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        OptionalInt totalCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .mapToInt(Shape::corners)
                .reduce((minPartial, corners) -> {
                    System.out.println("Mínimo parcial = " + minPartial);
                    System.out.println("Esquinas del elemento = " + corners);
                    return Math.min(minPartial, corners);
                });

        System.out.println("result = " + totalCorners);
    }

    static void reduceWithoutMapping() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        int totalCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .reduce(0, (partialSum, shape) -> {
                    System.out.println("Acumulado = " + partialSum);
                    System.out.println("Esquinas del elemento = " + shape.corners());
                    return partialSum + shape.corners();
                }, Integer::sum);

        System.out.println("result = " + totalCorners);
    }

    static void reduceStringExample() {
        var reduceOnly = Stream.of("naranja", "manzana", "limón")
                .reduce(0,
                        (acc, str) -> acc + str.length(),
                        Integer::sum);

        var mapReduce = Stream.of("naranja", "manzana", "limón")
                .map(String::length)
                .reduce(0, (acc, length) -> acc + length);

        var mapReduce2 = Stream.of("naranja", "manzana", "limón")
                .map(String::length)
                .reduce(0, Integer::sum);

        var mapReducePrimitive = Stream.of("naranja", "manzana", "limón")
                .mapToInt(String::length)
                .reduce(0, Integer::sum);

        var mapReducePrimitive2 = Stream.of("naranja", "manzana", "limón")
                .mapToInt(String::length)
                .sum();
    }

    static void countExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        long totalShapes = Stream.of(SAMPLE_SHAPES_ARRAY)
                .mapToInt(Shape::corners)
                .count();

        System.out.println("Numero de formas = " + totalShapes);
    }

    static void sumExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        int totalCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .mapToInt(Shape::corners)
                .sum();

        System.out.println("totalCorners = " + totalCorners);
    }

    static void averageExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        OptionalDouble averageCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .mapToInt(Shape::corners)
                .average();

        System.out.println("Numero de esquinas medio = " + averageCorners);
    }

    static void statisticsExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        IntSummaryStatistics stats = Stream.of(SAMPLE_SHAPES_ARRAY)
                .mapToInt(Shape::corners)
                .summaryStatistics();

        System.out.println(stats);
    }


    // --- Reducción con operaciones de reducción mutable

    // Se puede usar el operador reduce del API de Streams,
    // Pero el acumulador NO PUEDE MUTAR la lista que recibe
    // El operador acumulador recibe la lista con los elementos acumulados
    // y el elemento actual
    // y devuelve una NUEVA lista, creando una lista con los mismos elementos
    // y añadiéndole el elemento actual
    static void aggregateElementsDemoInmutable() {
        var fruits = Stream.of("manzana", "naranja", "pera", "melocotón")
                .reduce(new ArrayList<String>(), // Un ArrayList vacío para empezar a acumular
                        (acc, fruit) -> { //accumulator: BiFunction<Acumulador,T,Acumulador>
                            var list = new ArrayList<>(acc); //Creamos una copia de la lista
                            list.add(fruit);    //Añadimos el elemento a la copia
                            return list;        //Devolvemos una copia de la lista con el elemento añadido
                        },
                        (lhs, rhs) -> { //combiner: BinaryOperator<Accumulador>
                            var list = new ArrayList<>(lhs); // Copia de la lista izquierda (left)
                            list.addAll(rhs);  // Le añadimos los elementos de la lista derecha (right)
                            return list; // Devolvemos una lista que combina 2 listas
                        });

        System.out.println(fruits);
    }

    //Podemos hacer "trampas" y modificar la colección inicial directamente sin hacer copias
    static void aggregateElementsDemoInmutableCheat() {
        var fruits = Stream.of("manzana", "naranja", "pera", "melocotón")
                .reduce(new ArrayList<String>(),
                        (acc, fruit) -> { // BiFunction<A,T,A>  accumulator
                            //var list = new ArrayList<>(acc);
                            acc.add(fruit); //Añadimos directamente a la lista origen
                            return acc;     //Devolvemos la referencia (es l misma lista origen)
                        },
                        (lhs, rhs) -> { //combiner
                            //var list = new ArrayList<>(lhs);
                            lhs.addAll(rhs);
                            return lhs;
                        });

        System.out.println(fruits);
    }

    static void aggregateElementsDemo() {
        aggregateElementsDemoInmutable();
        aggregateElementsDemoInmutableCheat();
        collectDemo1();
        collectDemo2();
        reduceVSCollect();
    }

    // https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/stream/package-summary.html#MutableReduction
    // Cuando no nos importe (o incluso nos convenga dado que es mas eficiente) mutar el acumulador
    // Disponemos de otro operador terminal de reducción: collect

    //A diferencia del método reduce, el método collect no usa BiFunctions sino BiConsumers
    // Se da por hecho que lo que va a hacer es mutar el acumulador proporcionado por el Supplier
    static void collectDemo1() {
        var fruits = Stream.of("manzana", "naranja", "pera", "melocotón")
                .collect(ArrayList::new, //Supplier<A>
                        ArrayList::add, //BiConsumer<A,T> accumulator
                        ArrayList::addAll); //BiConsumer<A,A> combiner

        System.out.println(fruits);
    }

    //Existe una segunda version de sobrecarga del método que recibe como argumento una referencia a un Collector
    // El Collector lo podemos obtener (entre otras formas) a partir del método factoría of de la clase Collector
    static void collectDemo2() {
        var fruits = Stream.of("manzana", "naranja", "pera", "melocotón")
                .collect(Collector.of(ArrayList::new,
                                ArrayList::add, //BiConsumer<A,T> accumulator
                                (l1, l2) -> {   //BinaryOperator<A> combiner
                                    l1.addAll(l2);
                                    return l1;
                                },
                                Function.identity()
                        )
                );
        System.out.println(fruits);
    }

    static void reduceVSCollect() {

        String[] nombres = new String[]{"Rafa", "Raul", "Emilio", "Ramon"};

        String result = Arrays.stream(nombres).reduce("", String::concat);

        System.out.println(result);


        StringJoiner stringJoiner = Arrays.stream(nombres).collect(
                () -> new StringJoiner(", ", "[", "]"), //Supplier<StringJoiner>
                StringJoiner::add,  //BiConsumer<StringJoiner, String> añade el String al StringJoiner
                StringJoiner::merge   //BiConsumer<StringJoiner, StringJoiner> combina 2 StringJoiner
        );
        System.out.println(stringJoiner.toString());
    }


    //-- Mapas

    static void toMapGroupingByCorners() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<Integer, List<Shape>> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.groupingBy(
                        Shape::corners));

        System.out.println(map);
    }

    static void toMapGroupingByToString() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<String, List<Shape>> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.groupingBy(
                        Shape::toString));

        System.out.println(map);
    }

    static void toMapGroupingByStringToCount() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<String, Long> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.groupingBy(
                        Shape::toString,    //classifier
                        Collectors.counting())); //downstream

        System.out.println(map);
    }

    static void toMapGroupingByStringToSumCorners() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<String, Integer> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.groupingBy(
                        Shape::toString,    //classifier
                        Collectors.summingInt(Shape::corners))); //downstream

        System.out.println(map);
    }

    static void toMapGroupingByStringToStatistics() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<String, IntSummaryStatistics> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.groupingBy(
                        Shape::toString,    //classifier
                        Collectors.summarizingInt(Shape::corners))); //downstream

        System.out.println(map);
    }

    static void toMapPartitionByHasCorners() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<Boolean, List<Shape>> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.partitioningBy(
                        Shape::hasCorners));

        System.out.println(map);
    }

    static void toMapPartitionByHasCornersGroupingByCorners() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Map<Boolean, Map<Integer, List<Shape>>> map = Stream.of(SAMPLE_SHAPES_ARRAY)
                .collect(Collectors.partitioningBy(
                        Shape::hasCorners,
                        Collectors.groupingBy(Shape::corners)));

        System.out.println(map);
    }


    static void joiningExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        String result = Stream.of(SAMPLE_SHAPES_ARRAY)
                .map(Shape::toString)
                .collect(Collectors.joining(" - ", "{", "}"));

        System.out.println(result);
    }


    static void toListExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        List<Shape> onlyTriangles = Stream.of(SAMPLE_SHAPES_ARRAY)
                .filter(shape -> shape.corners == 3)
                .toList();

        printShapes(onlyTriangles);
        System.out.println(" <- Lista solo triangulos");
    }

    static void toArrayExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Shape[] noSquares = Stream.of(SAMPLE_SHAPES_ARRAY)
                .filter(shape -> shape.corners < 4)
                .toArray(Shape[]::new);

        printShapes(noSquares);
        System.out.println(" <- Array sin cuadrados");
    }


    static void findAnyExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        Optional<Shape> shape = Stream.of(SAMPLE_SHAPES_ARRAY)
                .findAny();

        System.out.println(shape);
    }

    static void anyMatchExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        boolean anyHasCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .anyMatch(Shape::hasCorners);

        System.out.println("anyHasCorners = " + anyHasCorners);

        Shape[] onlyCircles = Stream.of(SAMPLE_SHAPES_ARRAY)
                .filter(Predicate.not(Shape::hasCorners))
                .toArray(Shape[]::new);
        printShapes(onlyCircles);

        System.out.println(" <- Original solo círculos");

        anyHasCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .filter(Predicate.not(shape -> shape.corners > 0))
                .anyMatch(Shape::hasCorners);
        System.out.println("anyHasCorners = " + anyHasCorners);
    }

    static void allMatchExample() {
        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        boolean allHasCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .allMatch(Shape::hasCorners);

        System.out.println("allHasCorners = " + allHasCorners);

        Stream<Shape> noCircles = Stream.of(SAMPLE_SHAPES_ARRAY)
                .filter(Shape::hasCorners);
        printStream(noCircles);
        System.out.println(" <- Original sin círculos");

        allHasCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .filter(shape -> shape.corners > 0)
                .allMatch(Shape::hasCorners);
        System.out.println("allHasCorners = " + allHasCorners);
    }

    static void testConcat() {

        List<Integer> list1 = Stream.concat(
                        Stream.of(1, 2, 3),
                        Stream.of(4, 5))
                .toList();

        List<Integer> list2 = Stream.of(
                        Stream.of(1, 2, 3),
                        Stream.of(4, 5))
                .flatMap(Function.identity())
                .toList();

        System.out.println(list1);
        System.out.println(list2);
    }


    //No hacer caso
    static void xxx() {
        IntSummaryStatistics stats = IntStream.of(1, 2)
                .summaryStatistics();

        DoubleSummaryStatistics dstats = DoubleStream.of(1.0, 2.0)
                .summaryStatistics();

        DoubleSummaryStatistics doubleSummaryStatistics = Stream.of(1.0, 2, 3, 4, 5)
                .collect(Collectors.summarizingDouble(Number::doubleValue));

        Stream<String> fruits = Stream.of("naranja", "pera", "limon");

        Long count = Stream.of("naranja", "pera", "limon")
                .map(e -> 1L)
                .collect(Collectors.reducing(0L, (aLong, aLong2) -> aLong + aLong2));

        Optional<String> max = Stream.of("naranja", "pera", "limon")
                .max(Comparator.naturalOrder());

        fruits.mapToInt(String::length).mapToObj(Integer::toString);

    }

}
