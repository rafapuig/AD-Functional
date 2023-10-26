import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamsDemo {

    public static void main(String[] args) {
        example1();
        example2();
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


}
