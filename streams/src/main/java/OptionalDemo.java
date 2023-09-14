import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OptionalDemo {

    public static void main(String[] args) {
        //example1();
        example2();
    }

    static void example1() {
        OptionalInt maxOdd = IntStream.of(10,20,30)
                .filter(n-> n%2 ==1)
                .max();

        if(maxOdd.isPresent()) {
            int value = maxOdd.getAsInt();
            System.out.println("Maximo impar: " + value);
        } else {
            System.out.println("El Stream esta vacio.");
        }
        maxOdd.ifPresent(n -> System.out.println("Max: " + n));

        String[] nombres = {"Rafa","Ramon","Alfonso","Federico"};

        Optional<String> startWithA = Stream.of(nombres)
                .filter(n -> n.startsWith("A"))
                .findFirst();

        startWithA.ifPresent(value -> System.out.println(value));
        if(startWithA.isPresent()) {
            System.out.println("Primer nombre con A: " + startWithA.get());
        } else {
            System.out.println("No hay nombres con A");
        }

        Optional<String> longestName = Arrays.stream(nombres)
                .max(Comparator.comparingInt(String::length));

        longestName.ifPresentOrElse(
                n -> System.out.println(n),
                () -> System.out.println("Vacio.")
        );


    }

    static  void example2() {
        List<Optional<Integer>> optionalList = List.of(
                Optional.of(1),
                Optional.empty(),
                Optional.of(2),
                Optional.empty(),
                Optional.of(3),
                Optional.empty(),
                Optional.of(4));

        optionalList.stream()
                .forEach(i -> i.ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Vacio")));

        optionalList.stream()
                .map(i -> i.or(() -> Optional.of(0)))
                .forEach(System.out::println);

        optionalList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);

        optionalList.stream()
                .flatMap(Optional::stream)
                .forEach(System.out::println);

    }
}
