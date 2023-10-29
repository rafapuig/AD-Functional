import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsDemo {

    public static void main(String[] args) {
        example1();
        example2();

        // Seleccion: filtrado, orden
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
        reduceWithoutInitialExample();
        reduceWithoutMapping();

        countExample();
        sumExample();
        averageExample();
        statisticsExample();

        toMapGroupingByCorners();
        toMapGroupingByToString();
        toMapGroupingByStringToCount();
        toMapGroupingByStringToSumCorners();
        toMapGroupingByStringToStatistics();

        toMapPartitionByHasCorners();

    }

    static void example1() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        int sum = 0;
        for (int n : numbers) {
            if (n % 2 == 1) {
                int square = n * n;
                sum += square;
            }
        }
        System.out.println(sum);

        sum = numbers.stream()
                .filter(n -> n % 2 == 1) //operacion intermedia / lazy
                .map(n -> n * n) //operacion intermedia / lazy
                .reduce(0, Integer::sum); //op terminal / eager

        System.out.println(sum);

        sum = numbers.stream()
                .filter(n -> n % 2 == 1)
                .map(n -> n * n)
                .reduce(0, (a, b) -> a + b); //equivalente

        System.out.println(sum);

    }

    static void example2() {
        List<Integer> numbersList = List.of(1, 2, 3, 4, 5);

        // Obtener un stream a partir de la lista
        Stream<Integer> numbersStream = numbersList.stream();

        //Obtener un stream de numeros impares
        Predicate<Integer> oddFilter = number -> number % 2 == 1;
        Predicate<Integer> evenFilter = Predicate.not(oddFilter); // o tambien oddFilter.negate();
        //Stream<Integer> oddNumbersStream = numbersStream.filter(n -> n % 2 == 1);
        Stream<Integer> oddNumbersStream = numbersStream.filter(oddFilter);

        //Obtener un stream de los cuadrados de los numeros impares
        Function<Integer, Integer> squareNumber = number -> number * number;
        //Stream<Integer> squaredNumbersStream = oddNumbersStream.map(squareNumber);
        Stream<Integer> squaredNumbersStream = oddNumbersStream.map(n -> n * n);

        //Sumar todos los enteros del stream
        BinaryOperator<Integer> accumulator = (a, b) -> a + b;
        //BinaryOperator<Integer> accumulator = Integer::sum; //la clase entero tiene metodo static sum
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

        @Override
        public int compareTo(Shape other) {
            return Integer.compare(corners, other.corners());
        }

        //MÉTODOS FACTORÍA
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

        Stream<Shape> stream = Stream.of(SAMPLE_SHAPES_ARRAY);

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

        Stream<Shape> mappedMulti = Stream.of(SAMPLE_SHAPES_ARRAY)
                .distinct()
                .mapMulti((shape, shapeConsumer) ->
                        shape.twice().forEach(shapeConsumer));


        printStream(mappedMulti);
        System.out.println(" <- .mapMulti()");
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

    static void reduceWithoutInitialExample() {

        printShapes(SAMPLE_SHAPES_ARRAY);
        System.out.println(" <- Original");

        OptionalInt totalCorners = Stream.of(SAMPLE_SHAPES_ARRAY)
                .mapToInt(Shape::corners)
                .reduce((minPartial, corners) -> {
                    System.out.println("Minimo parcial = " + minPartial);
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
    }

}
