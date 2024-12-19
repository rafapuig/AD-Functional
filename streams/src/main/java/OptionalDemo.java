import model.people.Persona;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OptionalDemo {

    public static void main(String[] args) {
        new OptionalDemo().run();
    }

    void run() {
        testNullPointerExceptionIssue();
        testCreateOptionals();
        testOptionalIfPresent();
        testExampleStreamAPI1();
        testExampleStreamAPI2();
        testOtherOptionalMethods();
    }

    @Test
    void testNullPointerExceptionIssue() {
        Persona victor =
                new Persona(1, "Victor", "Nado", Persona.Sexo.HOMBRE, null);

        // getNacimiento() devuelve una referencia null
        // Llamar a un método de instancia mediante una referencia null provoca una NullPointerException
        // Aquí lo que realmente estamos haciendo es null.getYear()

        int year = victor.getNacimiento().getYear();  //Lanza una NullPointerException
        System.out.println("Victor nació en el año " + year);
    }

    //En Java contamos con la clase java.util.Optional<T>
    //Los métodos en los que haya casos que no deben devolver nada (null)
    //Deberían devolver un objeto Optional en lugar de devolver null

    @RepeatedTest(10)
    void testCreateOptionals() {
        //Crear un Optional vacío
        Optional<String> empty = Optional.empty();

        //Crear un Optional con el texto "Hola"
        Optional<String> text = Optional.of("Hola");

        //"Echar a suertes" si se instancia un nuevo objeto o no
        boolean instantiatePerson = new Random().nextInt(0, 2) == 0;

        //Crear un Optional a partir de una referencia que puede ser nulo (depende de instantiatePerson)
        Persona p = instantiatePerson ?
                new Persona(1, "Pedro", "Gado", Persona.Sexo.HOMBRE, null) :
                null;

        // Meter la referencia contenida en la variable p en un contenedor Optional<Persona>
        Optional<Persona> persona = Optional.ofNullable(p);

        if (persona.isPresent()) {
            Persona value = persona.get();
            System.out.println("Optional contiene:" + value);
        } else {
            System.out.println("Optional está vacío");
        }
    }

    @Test
    void testOptionalIfPresent() {

        //Crear un Optional a partir de una referencia que puede ser nulo
        boolean instantiatePerson = new Random().nextInt(0, 2) == 0;

        Optional<Persona> persona = Optional.ofNullable(
                instantiatePerson ?
                        new Persona(1, "Pedro", "Gado", Persona.Sexo.HOMBRE, null) :
                        null);

        persona.ifPresent(value -> System.out.println("Optional contiene: " + value));
    }

    @RepeatedTest(10)
    void testExampleStreamAPI1() {
        //Obtiene el máximo número impar desde el stream
        OptionalInt maxOdd = new Random().ints(1,1, 10)
                .filter(n -> n % 2 == 1)
                .max();

        if (maxOdd.isPresent()) {
            int value = maxOdd.getAsInt();
            System.out.println("Máximo impar: " + value);
        } else {
            System.out.println("El Stream esta vacío.");
        }
        //O mediante programación funcional
        maxOdd.ifPresent(n -> System.out.println("Max: " + n));
    }

    static void testExampleStreamAPI2() {

        //Array de nombres
        String[] nombres = {"Rafa", "Ramon", "Alfonso", "Federico"};

        //Obtener el primer nombre que empieza por A
        Optional<String> startWithA = Stream.of(nombres)
                .filter(n -> n.startsWith("A"))
                .findFirst();


        if (startWithA.isPresent()) {
            System.out.println("Primer nombre con A: " + startWithA.get());
        } else {
            System.out.println("No hay nombres con A");
        }
        //O mediante programación funcional y el método de orden superior
        startWithA.ifPresent(System.out::println);

        System.out.println(
                startWithA.orElse("No encontrado"));

        System.out.println(
                startWithA.orElseGet(() -> "No encontrado"));


        //Obtener el nombre más largo
        Optional<String> longestName = Arrays.stream(nombres)
                .max(Comparator.comparingInt(String::length));

        // Mediante programación funcional (nos ahorramos el if-else)
        longestName.ifPresentOrElse(
                System.out::println, //Consumer
                () -> System.out.println("Vacío.") //Runnable
        );
    }

    @Test
    void testOtherOptionalMethods() {
        List<Optional<Integer>> optionalList = List.of(
                Optional.of(1),
                Optional.empty(),
                Optional.of(2),
                Optional.empty(),
                Optional.of(3),
                Optional.empty(),
                Optional.of(4));

        //ifPresentOrElse
        optionalList.stream()
                .forEach(opt -> opt.ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Vacío")));

        System.out.println("Ejemplo de uso de Optional.or()");
        //or
        optionalList.stream()
                .map(opt -> opt.or(() -> Optional.of(0))) //Si no hay valor presente el supplier actúa
                .forEach(System.out::println);

        System.out.println("Ejemplo de uso de Optional.map()");
        //map
        optionalList.stream()
                .map(opt -> opt.map(value -> value * value)) //Si hay valor presente el mapper actúa
                .forEach(System.out::println);


        //Obtener los valores presentes en una lista, (sin vacíos)
        optionalList.stream()
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .forEach(System.out::println);

        //Obtener los valores presentes en una lista, (sin vacíos)
        System.out.println("Ejemplo de uso de Optional.stream()");
        //Con el método stream de Optional se devuelve un stream
        //por cada Optional (Optional --> stream)
        //Como tenemos un stream de streams aplicamos un flatMap
        optionalList.stream()
                .flatMap(Optional::stream)
                .forEach(System.out::println);
    }
}
