import model.people.Empleado;
import model.people.Empleados;
import model.people.Persona;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperatorsDemo {

    public static void main(String[] args) {
        //testPeek();
        //testForEachGetFemaleDetails();
        //testForEachIncreaseFemalesIncome();
        //testMapGetEmployeesNames();
        //testMapIntStream();
        testFlatMapCountNumberOfAs();
    }

    static void testPeek() {
        int sum = IntStream.of(1, 2, 3, 4, 5)
                .peek(value -> System.out.println("Tomando entero: " + value))
                .filter(n -> n % 2 == 1)
                .peek(value -> System.out.println("Filtrado entero: " + value))
                .map(n -> n * n)
                .peek(value -> System.out.println("Mapeado entero: " + value))
                .reduce(0, Integer::sum);

        System.out.println("Suma = " + sum);
    }

    static void testForEachGetFemaleDetails() {
        Empleados.EMPLEADOS.stream()
                .filter(Persona::isMujer)
                .forEach(System.out::println);
    }

    static void testForEachIncreaseFemalesIncome() {
        List<Empleado> empleados = List.copyOf(Empleados.EMPLEADOS);
        System.out.println("Antes de aumento salarial");
        empleados.forEach(System.out::println);

        //Incrementar el salario de las mujeres un 20%
        empleados.stream()
                .filter(Persona::isMujer)
                .forEach(e -> e.setSueldo(e.getSueldo() * 1.2));

        System.out.println("Despues del aumento salarial");
        empleados.forEach(System.out::println);
    }

    static void testMapGetEmployeesNames() {
        Empleados.EMPLEADOS.stream()
                .map(Persona::getNombreCompleto)
                .forEach(System.out::println);
    }

    static void testMapIntStream() {
        IntUnaryOperator squareInt = n -> n * n;

        IntStream.rangeClosed(1, 4)
                .map(squareInt) //IntUnaryOperator
                .forEach(System.out::println); //IntConsumer

        IntStream.rangeClosed(1, 4)
                .mapToDouble(n -> n / 2.0) //IntToDoubleFunction
                .forEach(System.out::println);

        IntStream.rangeClosed(1, 4)
                .mapToLong(n -> n + 1_000_000L) //IntToLongFunction
                .forEach(System.out::println);

        IntStream.range(0, Empleados.EMPLEADOS.size())
                .mapToObj(n -> Empleados.EMPLEADOS.get(n)) //IntFunction<? extends Empleado>
                .forEach(System.out::println);

    }

    static void testFlatMapCountNumberOfAs() {

        long count = Stream.of("Rafael", "Ramon", "Raul", "Emilio")
                .map(String::chars)
                .flatMap(intStream -> intStream.mapToObj(n -> (char) n))
                .filter(ch -> ch == 'A' || ch == 'a')
                .count();

        count = Stream.of("Rafael", "Ramon", "Raul", "Emilio")
                .flatMap(name -> IntStream.range(0, name.length())
                        .mapToObj(name::charAt))
                .filter(ch -> ch == 'A' || ch == 'a')
                .count();

        System.out.println("Numero de A's = " + count);
    }
}
