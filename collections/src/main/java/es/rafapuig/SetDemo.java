package es.rafapuig;

import es.rafapuig.model.Persona;
import es.rafapuig.utils.PrintUtils;
import org.junit.jupiter.api.Test;

import static es.rafapuig.model.Personas.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class SetDemo {

    static String[] frutas =
            {"pera", "manzana", "limón", "kiwi", "cereza", "uva", "melocotón", "piña", "naranja"};

    public static void main(String[] args) {
        new SetDemo().run();
    }

    void run() {

        Set<String> setOfFruits = Set.of(frutas);

        System.out.println(setOfFruits.getClass().toString());

        hashSetTest1();
        //hashSetTest2();
        //hashSetTest();
        //hashSetTest3();
        //hashSetTest4();
        //hashSetTest5();

        //linkedHashSetTest1();
        //linkedHashSetTest2();
        //linkedHashSetTest();
        //linkedHashSetTest3();
        //linkedHashSetEqualsTest();
        //sortedSetTest();
        //sortedSetTest2();
        //sortedSetTest3();
    }

    /**
     * Imprimir un Set por consola
     */
    static void print(Collection<?> set) {
        System.out.println(set);
    }

    /**
     * Imprimir un Set por consola
     */
    static void print(Set<?> set) {
        System.out.println(set);
    }

    /**
     * Test del metodo contains aplicado a un Set
     */
    static void containsTest(Set<?> set, Object o) {
        System.out.println("set.contains(" + o + ") = " + set.contains(o));
    }

    static <T> void addToSet(Set<T> set, T item) {
        System.out.print("Añadiendo: " + item + "... ");

        System.out.print("set.contains(" + item + "' = " + set.contains(item) + " -> ");

        boolean addedOK = set.add(item); //Añadir el elemento al Set

        System.out.println(addedOK ? "OK" : "No se ha podido añadir");
    }


    static void addPersona(Set<Persona> set, Persona persona) {
        System.out.print("Añadiendo la persona: " + persona.getNombre() + "... ");

        System.out.print("set.contains('" + persona + "') = " + set.contains(persona) + " -> ");

        boolean addedOK = set.add(persona); //Añadir el elemento al Set

        System.out.println(addedOK ? "Añadida OK" : "No se ha podido añadir");
    }


    static <T, V> void hashcodeTest(Set<T> set, T original, T clone, Function<T, V> map) {
        System.out.println(
                "original: " + map.apply(original) + ".hashCode() = " + original.hashCode());
        System.out.println(
                "clone:    " + map.apply(clone) + ".hashCode() = " + clone.hashCode());
    }

    static void hashcodeTest(Set<Persona> set) {
        //Es necesario que la clase implemente hashcode para que no haya duplicados en colecciones que usan tabla hash

        Persona fulano = new Persona("Fulanito de Tal", "2000-01-01");
        //System.out.println("fulano: '" + fulano.getNombre() + "' hashCode() = " + fulano.hashCode());

        Persona mengano = fulano.clone();
        //System.out.println("mengano: '" + mengano.getNombre() + "' hashCode() = " + mengano.hashCode());

        hashcodeTest(set, fulano, mengano, Persona::toString);
    }

    static <T> void hashcodeTest(Set<T> set, T original, T clone) {
        System.out.println("---HASH CODE TEST---");
        //Es necesario que la clase implemente hashcode para que no haya duplicados
        // en colecciones que usan tabla hash
        hashcodeTest(set, original, clone, T::toString);
    }

    static <T> void equalsTest(T original, T clone) {

        System.out.println("---EQUALS TEST---");

        //Se necesita el metodo equals para que haya igualdad por valor o equivalencia
        //Si no, un objeto solo puede ser igual a si mismo

        System.out.println("original = " + original);
        System.out.println("clone    = " + clone);
        System.out.println("clone.equals(original) = " + clone.equals(original));

        Set<T> set = new HashSet<>();
        set.add(original);

        System.out.println("set.contains(original) = " + set.contains(original));
        System.out.println("set.contains(clone)    = " + set.contains(clone));
    }


    @Test
    void equalsTest() {
        equalsTest(amadorDenador, amadorDenador.clone());
    }


    @Test
    void hashSetTest1() {
        Set<Integer> set = new HashSet<>();

        set.add(7);
        set.add(3);
        set.add(11);
        set.add(2);
        set.add(5);
        set.add(13);
        set.add(1);

        print(set);

        // Imprimir la lista de los códigos hash de los elementos del set
        //System.out.println(set.stream().map(Object::hashCode).toList());
    }

    @Test
    void hashSetTest2() {

        Set<String> set = new HashSet<>(20, 1);// List.of(frutas));
        set.addAll(Arrays.asList(frutas));

        //En un HashSet NO se respeta el orden en que son insertados
        System.out.println(set);

        System.out.println(Stream.of(frutas).toList());
        System.out.println(Stream.of(frutas).map(Object::hashCode).map(code -> code % 20).toList());
        System.out.println(Stream.of(frutas).map(Object::hashCode).toList());


        System.out.println(set.stream().map(Object::hashCode).map(code -> code % 20).toList());
    }


    @Test
    void hashSetTest3() {
        Set<Persona> personaSet = new HashSet<>();

        addPersona(personaSet, aitorTilla);
        addPersona(personaSet, belenTilla);
        addPersona(personaSet, armandoBronca);

        // En un set no se pueden añadir elementos duplicados
        // Un elemento E es duplicado si es igual a algún elemento del Set
        addPersona(personaSet, armandoBronca.clone()); // Añadir un elemento igual

        print(personaSet);
    }


    @Test
    void hashSetTest4() {
        Set<Persona> personaSet = new HashSet<>();

        for (Persona p : personas) {
            addPersona(personaSet, p);
        }
        print(personaSet);
    }

    @Test
    void hashSetTest5() {

        // Un HashSet necesita que la clase de los elementos implemente
        // no solo equals si también hashcode (los 2 metodos a la vez)
        // El HasSet se basa es esos metodos para comprobar duplicados y si ya contiene el elemento

        Set<Persona> personaSet = new HashSet<>(personaList);

        containsTest(personaSet, armandoBronca.clone());

        //El metodo contains para un HashSet se basa en la igualdad del hashCode y de equals
        addPersona(personaSet, armandoBronca.clone());
        print(personaSet);

        hashcodeTest(personaSet, amadorDenador, amadorDenador.clone());
        equalsTest(amadorDenador, amadorDenador.clone());

        print(personaSet);
    }


    @Test
    void linkedHashSetTest1() {
        Set<Integer> set = new LinkedHashSet<>();

        set.add(7);
        set.add(3);
        set.add(11);
        set.add(2);
        set.add(5);
        set.add(13);
        set.add(1);

        //Se respeta el orden en el que son insertados los elementos al iterarlo
        System.out.println(set);
    }

    @Test
    void linkedHashSetTest2() {

        Set<String> set = new LinkedHashSet<>(List.of(frutas));

        System.out.print("Lista:");
        System.out.println(List.of(frutas));

        System.out.print("Set:  ");
        ;
        //Se respeta el orden en el que son insertados los elementos al iterarlo
        System.out.println(set);
    }

    @Test
    void linkedHashSetTest() {
        Set<Persona> personaSet = new LinkedHashSet<>();

        //Se respeta el orden de inserción
        personaSet.add(aitorTilla);
        personaSet.add(belenTilla);
        personaSet.add(armandoBronca);

        hashcodeTest(personaSet);

        equalsTest(amadorDenador, amadorDenador.clone());
    }

    private static void linkedHashSetEqualsTest() {
        Set<Persona> personaSet = new LinkedHashSet<>(personaList);
        equalsTest(amadorDenador, amadorDenador.clone());
    }

    @Test
    void linkedHashSetTest3() {
        Set<Persona> personaSet = new LinkedHashSet<>();
        performTests(personaSet);
    }


    // -------------- Sorted sets -----------------------------------------------------------


    @Test
    void testSortedSetUsesComparisonToEquality() {
        // Comparador de personas según su fecha de nacimiento
        Comparator<Persona> comparator = Comparator.comparing(Persona::getFechaNacimiento);

        // Crear un Set ordenado cuyo criterio es la fecha de nacimiento de los elementos (Persona)
        SortedSet<Persona> personaSet = new TreeSet<>(comparator); //Persona::compareByEdad);

        // Añadimos tres personas al conjunto
        personaSet.add(aitorTilla);
        personaSet.add(belenTilla);
        personaSet.add(armandoBronca);

        print(personaSet);

        // Creamos una nueva persona con la misma fecha de nacimiento que una persona ya incluida en el Set
        Persona perico = new Persona("Perico Palotes", armandoBronca.getFechaNacimiento());

        //No se añadirá en un TreeSet porque la comprobación de duplicado se hace mediante el comparador
        // Y Perico tiene la misma edad que un elemento del conjunto: Armando
        addToSet(personaSet, perico); //equivale a personaSet.add(perico); pero con info
    }

    @Test
    void sortedSetTest2() {
        // Comparador de personas según su fecha de nacimiento
        Comparator<Persona> comparator = Comparator.comparing(Persona::getFechaNacimiento);

        // Crear un Set ordenado cuyo criterio es la fecha de nacimiento de los elementos (Persona)
        SortedSet<Persona> personaSet = new TreeSet<>(comparator); //Persona::compareByEdad);

        // Añadimos seis personas al conjunto (de menor a mayor fecha de nacimiento)
        personaSet.add(estherMalgin);
        personaSet.add(aitorTilla);
        personaSet.add(belenTilla);
        personaSet.add(armandoBronca);
        personaSet.add(amadorDenador);
        personaSet.add(sandraMatica);

        // El metodo first devuelve el primer elemento de un conjunto ordenado
        // Persona con fecha nacimiento menor del conjunto (la de más edad)
        System.out.println("personaSet.first() = " + personaSet.first());

        // El metodo last devuelve el último elemento de un conjunto ordenado
        // Persona con fecha nacimiento mayor del conjunto (la de menos edad)
        System.out.println("personaSet.last() = " + personaSet.last());

        // El metodo headSet devuelve una vista porción del Set que contiene los elementos estrictamente menores
        // Personas nacidas antes que Armando Bronca
        System.out.println("personaSet.headSet(" + armandoBronca + ") = " + personaSet.headSet(armandoBronca));

        // El metodo taiSet devuelve una vista porción del Set que contiene los elementos mayores o iguales
        // Personas nacidas el mismo día o después que Armando Bronca
        System.out.println("personaSet.tailSet(" + armandoBronca + ") = " + personaSet.tailSet(armandoBronca));
    }

    private static void sortedSetTest3() {

        SortedSet<Integer> sortedSet = new TreeSet<>(Comparator.<Integer>naturalOrder().reversed());

        sortedSet.add(7);
        sortedSet.add(3);
        sortedSet.add(2);
        sortedSet.add(5);
        sortedSet.add(1);

        System.out.println(sortedSet);

        // Persona mas joven del conjunto
        System.out.println("first() = " + sortedSet.first());
        // Persona mas
        System.out.println("last() = " + sortedSet.last());
        System.out.println("headSet(3) = " + sortedSet.headSet(3));
        System.out.println("tailSet(3) = " + sortedSet.tailSet(3));
    }


    @Test
    void sortedSetTest4() {

        System.out.println("\nSORTED SET TEST");

        // El TreeSet solamente se basa en el comparador para comprobar si existen duplicador
        // Realiza la comprobación de igualdad mediante el comparador
        // No necesita que la clase de los elementos implemente equals ni hashcode (es ignorado)
        // Si implementamos el hashcode,
        // podremos escribir un comparador más conveniente que ordene por el criterio que queramos
        // pero que no considere 2 elementos iguales si no lo son del todo, y solo lo son por el criterio de ordenación

        SortedSet<Persona> personaSet = new TreeSet<>(
                Comparator.comparing(Persona::getFechaNacimiento)
                        .thenComparing(Persona::getNombre));
        //.thenComparing(Persona::hashCode));
        // Si añadimos el hashcode, conseguimos que se tenga en cuanta el hashCode para poder añadir elementos

        performTests(personaSet);

    }


    void performTests(Set<Persona> personaSet) {
        for (Persona p : personas) {
            addPersona(personaSet, p);
        }
        print(personaSet);

        //Un TreeSet se basa para saber si contiene un elemento en el comparador, NOOO en el hash code, no lo necesita, necesita el equals
        addPersona(personaSet, new Persona("Perico Palotes", armandoBronca.getFechaNacimiento()));
        addPersona(personaSet, new Persona(armandoBronca.getNombre(), armandoBronca.getFechaNacimiento().plusDays(1)));

        containsTest(personaSet, armandoBronca.clone());

        equalsTest(amadorDenador, amadorDenador.clone());

        hashcodeTest(personaSet);
    }

    @Test
    void testSetAddingOrderIsPreserved() {
        // Crear un conjunto no modificable de números enteros
        List<Integer> numbersList = List.of(5, 8, 3, 1, 9, 7, 0, 2, 6, 4);

        // Si lo imprimo vemos que el iterador que lo recorre no lo hace en un orden determinado
        PrintUtils.print(numbersList);

        // Si creamos un LinkedHashSet con los mismos elementos de la colección (conjunto de números enteros)
        Set<Integer> navigableSet = new LinkedHashSet<>(numbersList);

        // Y lo recorremos con el iterador, podemos ver que hay un orden de aparición establecido
        // Los elementos "aparecen" por el orden en el que fueron añadidos al conjunto
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Integer i : navigableSet) {
            joiner.add(i.toString());
        }
        System.out.println(joiner.toString());

        print(navigableSet);
    }


    @Test
    void testSetIsSorted() {
        // Crear un conjunto no modificable de números enteros
        Set<Integer> numbersSet = Set.of(5, 8, 3, 1, 9, 7, 0, 2, 6, 4);

        // Si lo imprimo vemos que el iterador que lo recorre no lo hace en un orden determinado
        print(numbersSet);

        // Si creamos un TreeSet con los mismos elementos de la colección (conjunto de números enteros)
        Set<Integer> set = new TreeSet<>(numbersSet);

        // Y lo recorremos con el iterador, podemos ver que hay on orden de aparición establecido
        // Los elementos "aparecen" por su orden natural de ordenación
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Integer i : set) {
            joiner.add(i.toString());
        }
        System.out.println(joiner.toString());

        print(set);
    }

}
