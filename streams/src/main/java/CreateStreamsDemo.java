import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CreateStreamsDemo {

    public static void main(String[] args) {
        //createStreamFromValues();
        //createStreamFromArray();
        //createStreamFromStreamBuilder();
        //createIntegerOrLongStream();
        //createInfiniteStream();
        createFromCollection();
    }

    static void createStreamFromValues() {

        Stream<String> stream1 = Stream.of("Hola");

        Stream<String> stream2 = Stream.of("Rafa", "Raul", "Emilio", "Ramon");

        List<String> namesStartWithR = stream2
                .filter(s -> s.startsWith("R"))
                .toList();

        System.out.println(namesStartWithR);

        int sum = Stream.of(1, 2, 3, 4, 5)
                .filter(n -> n % 2 == 1)
                .map(n -> n * n)
                .reduce(0, Integer::sum);

        System.out.println("Suma = " + sum);
    }

    static void createStreamFromArray() {

        //Para tipos por referencia
        String[] nombres = {"Rafa", "Ramon", "Raul", "Emilio"};

        Stream<String> nombresStream = Stream.of(nombres);
        Stream<String> nombresStream2 = Arrays.stream(nombres);

        String csv = "Rafa,Ramon,Raul,Emilio";
        Stream<String> splitNombres = Stream.of(csv.split(","));
        Stream<String> splitNombres2 = Arrays.stream(csv.split(","));

        List<String> nombresList = splitNombres2.toList();
        System.out.println(nombresList);


        //Para tipos por valor / primitivos
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        IntStream intStream = Arrays.stream(numbers);

        int sum = intStream.sum(); //Caso especial de reduccion equivale a reduce(0,Integer::sum)
        System.out.println("Suma = " + sum);
    }

    static void createStreamFromStreamBuilder() {
        Stream<String> nombresStream =
                Stream.<String>builder()
                        .add("Rafa")
                        .add("Ramon")
                        .add("Raul")
                        .add("Emilio")
                        .build();

        List<String> nombresList =
                nombresStream
                        .filter(n -> n.startsWith("R"))
                        .toList();

        System.out.println(nombresList);

        //Obtener el builder por separado
        Stream.Builder<String> builder = Stream.builder(); // El compilador infiere el tipo
        Stream<String> stream = builder
                .add("Rafa")
                .add("Luis")
                .add("Paco")
                .build();

        String[] nombres = stream.toArray(String[]::new);

        System.out.println(Arrays.toString(nombres));

    }

    static void createIntegerOrLongStream() {
        IntStream oneToTen = IntStream.range(1, 11);
        System.out.println(Arrays.toString(oneToTen.toArray()));

        oneToTen = IntStream.rangeClosed(1, 10);
        System.out.println(Arrays.toString(oneToTen.toArray()));

        LongStream oneToMillion = LongStream.rangeClosed(1, 1_000_000);
    }

    static void createInfiniteStream() {
        IntStream oneToTen = IntStream.iterate(1, i -> i <= 10, i -> i + 1);

        oneToTen.forEach(n-> System.out.print(n + ", "));

        //Equivalente programacion funcional
        int seed = 1;
        IntPredicate hasNext = i -> i <= 10;
        IntUnaryOperator next = i -> i + 1;
        for(int index = seed; hasNext.test(index); index = next.applyAsInt(index)) {
            System.out.print(index + ", ");
        }

        //Serie infinita
        Stream<Long> naturalNumbers = Stream.iterate(1L, n -> n + 1);
        naturalNumbers
                .limit(1_000_000)
                .forEach(System.out::println);

        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

    static void createFromCollection() {
        Set<String> nombres = Set.of("Rafa", "Raul", "Ramon", "Emilio");
        Stream<String> sequential = nombres.stream();

        Stream<String> parallel = nombres.parallelStream();

        List<String> result = sequential
                .filter(n -> n.startsWith("R"))
                .toList();

        System.out.println(result);

    }
}
