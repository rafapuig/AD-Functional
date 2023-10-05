import model.people.Persona;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OptionalDemo {

    public static void main(String[] args) {
        //testNullPointerExceptionIssue();
        //testCreateOptionals();
        testOptionalIfPresent();
        testExampleStreamAPI1();
        testExampleStreamAPI2();
        testOtherOptionalMethods();
    }

    static void testNullPointerExceptionIssue() {
        Persona victor =
                new Persona(1, "Victor", "Nado", Persona.Sexo.HOMBRE, null);
        int year = victor.getNacimiento().getYear();  //Lanza una NullPointerException
        System.out.println("Victor nació en el año " + year);
    }

    //En Java contamos con la clase java.util.Optional<T>
    //Los metodos en los que haya casos en que no deben devolver nada
    //Deberian devolver un objeto Optional en lugar de devolver null

    static void testCreateOptionals() {
        //Crear un Optional vacio
        Optional<String> empty = Optional.empty();
        //Crear un Optional con el texto "Hola"
        Optional<String> text = Optional.of("Hola");
        //Crear un Optional a partir de una referncia que puede ser nulo
        boolean instantiatePerson = new Random().nextInt(0, 2) == 0;

        Persona p = instantiatePerson ?
                new Persona(1, "Pedro", "Gado", Persona.Sexo.HOMBRE, null) :
                null;

        Optional<Persona> persona = Optional.ofNullable(p);

        if (persona.isPresent()) {
            Persona value = persona.get();
            System.out.println("Optional contiene:" + value);
        } else {
            System.out.println("Optional esta vacio");
        }
    }

    static void testOptionalIfPresent() {

        //Crear un Optional a partir de una referncia que puede ser nulo
        boolean instantiatePerson = new Random().nextInt(0, 2) == 0;

        Optional<Persona> persona = Optional.ofNullable(
                instantiatePerson ?
                        new Persona(1, "Pedro", "Gado", Persona.Sexo.HOMBRE, null) :
                        null);

        persona.ifPresent(value -> System.out.println("Optional contiene: " + value));
    }

    static void testExampleStreamAPI1() {
        //Obtiene el  maximo numero impar desde el stream
        OptionalInt maxOdd = IntStream.of(10, 20, 30)
                .filter(n -> n % 2 == 1)
                .max();

        if (maxOdd.isPresent()) {
            int value = maxOdd.getAsInt();
            System.out.println("Maximo impar: " + value);
        } else {
            System.out.println("El Stream esta vacio.");
        }
        //O mediante programacion funcional
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
        //O mediante programacion funcional y el metodo de orden superior
        startWithA.ifPresent(System.out::println);

        System.out.println(
                startWithA.orElse("No encontrado"));

        System.out.println(
                startWithA.orElseGet(()->"No encontrado"));


        //Obtener el nombre mas largo
        Optional<String> longestName = Arrays.stream(nombres)
                .max(Comparator.comparingInt(String::length));

        longestName.ifPresentOrElse(
                System.out::println, //Consumer
                () -> System.out.println("Vacio.") //Runnable
        );
    }

    static void testOtherOptionalMethods() {
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
                .forEach(i -> i.ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Vacio")));

        //or
        optionalList.stream()
                .map(i -> i.or(() -> Optional.of(0)))
                .forEach(System.out::println);


        //Obtener los valores presentes en una lista, (sin vacios)
        optionalList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);

        //Con el metodo stream de Optional se devuelve un stream
        //por cada Optional (Optional --> stream)
        //Como tenemos un stream de streams aplicamos un flatMap
        optionalList.stream()
                .flatMap(Optional::stream)
                .forEach(System.out::println);
    }
}
