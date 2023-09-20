package es.rafapuig;

import es.rafapuig.model.Persona;

import static es.rafapuig.model.Personas.*;

import java.util.*;
import java.util.function.Function;

public class SetDemo {

    static String[] frutas =
            {"pera", "manzana", "limon", "kiwi", "cereza", "uva", "melocoton", "piña", "naranja"};

    public static void main(String[] args) {

        //hashSetTest1();
        //hashSetTest2();
        //hashSetTest();
        //hashSetTest3();
        //hashSetTest4();
        hashSetTest5();

        //linkedHashSetTest1();
        //linkedHashSetTest2();
        //linkedHashSetTest();
        //linkedHashSetTest3();
        //linkedHashSetEqualsTest();
        //sortedSetTest();
        //sortedSetTest2();
        //sortedSetTest3();

    }

    static void print(Set<?> set) {
        System.out.println(set);
    }

    static void containsTest(Set<?> set, Object o) {
        System.out.println("set.contains(" + o + ") = " + set.contains(o));
    }

    static void addPersona(Set<Persona> set, Persona persona) {
        System.out.print("Añadiendo la persona: " + persona.getNombre() + "... ");

        System.out.print("set.contains('" + persona + "') = " + set.contains(persona) + " -> ");

        boolean addedOK = set.add(persona); //Añadir el elemento al Set

        System.out.println(addedOK ? "Añadida OK" : "No se ha podido añadir");
    }


    static <T,V> void hashcodeTest(Set<T> set, T original, T clone, Function<T, V> map) {
        System.out.println(
                "original: '" + map.apply(original) + "'.hashCode() = " + original.hashCode());
        System.out.println(
                "clone: '" + map.apply(clone) + "'.hashCode() = " + clone.hashCode());
    }

    static void hashcodeTest(Set<Persona> set) {
        //Es necesario que la clase implemente hashcode para que no haya duplicados en colecciones que usan tabla hash

        Persona fulano = new Persona("Fulanito de Tal", "2000-01-01");
        //System.out.println("fulano: '" + fulano.getNombre() + "' hashCode() = " + fulano.hashCode());

        Persona mengano = fulano.cloneMe();
        //System.out.println("mengano: '" + mengano.getNombre() + "' hashCode() = " + mengano.hashCode());

        hashcodeTest(set, fulano, mengano,Persona::toString);
    }

    static <T> void equalsTest(Set<T> set, T original, T clone) {

        System.out.println("---EQUALS TEST---");

        //Se necesita el metodo equals para que haya igualdad por valor o equivalencia
        //Si no, un objeto solo puede ser igual a si mismo

        System.out.println("original = " + original);
        System.out.println("clone = " + clone);
        System.out.println("clone.equals(original) = " + clone.equals(original));

        //containsTest(set, amadorDenador);
        System.out.println("set.contains(original) = " + set.contains(original));
        System.out.println("set.contains(clone) = " + set.contains(clone));
    }

    static void equalsTest(Set<Persona> set) {
        equalsTest(set, amadorDenador, amadorDenador.cloneMe());
    }



    private static void hashSetTest1() {
        Set<Integer> set = new HashSet<>();

        set.add(7);
        set.add(3);
        set.add(11);
        set.add(2);
        set.add(5);
        set.add(13);
        set.add(1);

        System.out.println(set);

        //System.out.println(set.stream().map(Object::hashCode).toList());
    }

    private static void hashSetTest2() {

        Set<String> set = new HashSet<>(List.of(frutas));

        //No se respeta el orden en que son insertados
        System.out.println(set);

        //System.out.println(set.stream().map(Object::hashCode).toList());
    }

    private static void hashSetTest3() {
        Set<Persona> personaSet = new HashSet<>();

        addPersona(personaSet, aitorTilla);
        addPersona(personaSet, belenTilla);
        addPersona(personaSet, armandoBronca);
        addPersona(personaSet, armandoBronca.cloneMe());

        print(personaSet);
    }

    private static void hashSetTest4() {
        Set<Persona> personaSet = new HashSet<>();

        for (Persona p : personas) {
            addPersona(personaSet, p);
        }
        print(personaSet);
    }

    private static void hashSetTest5() {

        //Un HashSet necesita y se basa para comprobar duplicados y si contiene ya el elemento en que la clase
        //de los elementos implemente no solo equals si tambien hashcode (los 2 metodos a la vez)

        Set<Persona> personaSet = new HashSet<>(personaList);

        containsTest(personaSet, armandoBronca.cloneMe());

        //El metodo contains para un HashSet se basa en la igualdad del hashocde y de equals
        addPersona(personaSet, armandoBronca.cloneMe());
        print(personaSet);

        hashcodeTest(personaSet);
        equalsTest(personaSet, amadorDenador, amadorDenador.cloneMe());

        print(personaSet);
    }

    private static void linkedHashSetTest1() {
        Set<Integer> set = new LinkedHashSet<>();

        set.add(7);
        set.add(3);
        set.add(11);
        set.add(2);
        set.add(5);
        set.add(13);
        set.add(1);

        //Se repeta el orden en el que son insertados los elementos
        System.out.println(set);
    }

    private static void linkedHashSetTest2() {

        Set<String> set = new LinkedHashSet<>(List.of(frutas));

        //No se respeta el orden en que son insetados, se colocan segun su hashcode
        System.out.println(set);

        //System.out.println(set.stream().map(Object::hashCode).toList());
    }

    private static void linkedHashSetTest() {
        Set<Persona> personaSet = new LinkedHashSet<>();

        //Se respete el orden de insercion
        personaSet.add(aitorTilla);
        personaSet.add(belenTilla);
        personaSet.add(armandoBronca);

        hashcodeTest(personaSet);

        equalsTest(personaSet, amadorDenador, amadorDenador.cloneMe());
    }

    private static void linkedHashSetEqualsTest() {
        Set<Persona> personaSet = new LinkedHashSet<>(personaList);
        equalsTest(personaSet, amadorDenador, amadorDenador.cloneMe());
    }

    static void linkedHashSetTest3() {
        Set<Persona> personaSet = new LinkedHashSet<>();

        performTests(personaSet);

    }

    private static void sortedSetTest() {
        SortedSet<Persona> personaSet =
                new TreeSet<>(Comparator.comparing(Persona::getFechaNacimiento)); //Persona::compareByEdad);

        personaSet.add(aitorTilla);
        personaSet.add(belenTilla);
        personaSet.add(armandoBronca);

        //No se añade en un TreeSet porque se hace mediante el comparador la comprobacion de duplicado
        personaSet.add(new Persona(armandoBronca.getNombre(), armandoBronca.getFechaNacimiento()));

        personaSet.add(sandraMatica);
        personaSet.add(amadorDenador);
        personaSet.add(estherMalgin);


        System.out.println(personaSet);

        System.out.println("personaSet.first() = " + personaSet.first());
        System.out.println("personaSet.last() = " + personaSet.last());
        System.out.println("personaSet.headSet(Armando Bronca) = " + personaSet.headSet(new Persona("Armando Bronca", "1982-08-13")));
        System.out.println("personaSet.tailSet(Armando Bronca) = " + personaSet.tailSet(new Persona("Armando Bronca", "1982-08-13")));
    }

    private static void sortedSetTest2() {

        SortedSet<Integer> sortedSet = new TreeSet<>(Comparator.<Integer>naturalOrder().reversed());

        sortedSet.add(7);
        sortedSet.add(3);
        sortedSet.add(2);
        sortedSet.add(5);
        sortedSet.add(1);

        System.out.println(sortedSet);

        System.out.println("first() = " + sortedSet.first());
        System.out.println("last() = " + sortedSet.last());
        System.out.println("headSet(3) = " + sortedSet.headSet(3));
        System.out.println("tailSet(3) = " + sortedSet.tailSet(3));
    }

    static void sortedSetTest3() {

        System.out.println("\nSORTED SET TEST");

        //El TreeSet solamante se basa en el comparador para comprobar si existen duplicador
        //Realiza la comprobacion de igualdad mediante el comparador
        //No necesita que la clase de los elementos implemente equals ni hashcode (es ignorado)
        //Si implementamos el hashcode, podremos escribir un comparador mas conveniente que ordene por el criterio que queramos
        // pero que no considere 2 elementos iguales si no lo son del todo, y solo lo son por el criterio de ordenacion

        SortedSet<Persona> personaSet = new TreeSet<>(
                Comparator.comparing(Persona::getFechaNacimiento)
                        .thenComparing(Persona::getNombre));
        //.thenComparing(Persona::hashCode)); //Si añadimos el hashcode, conseguimos que se tenga en cuanta el hascode para añadir

        performTests(personaSet);

    }

    private static void performTests(Set<Persona> personaSet) {
        for (Persona p : personas) {
            addPersona(personaSet, p);
        }
        print(personaSet);

        //Un TreeSet se basa para saber si contiene un elemento en el comparador, NOOO en el hash code, no lo necesita, necesita el equals
        addPersona(personaSet, new Persona("Perico Palotes", armandoBronca.getFechaNacimiento()));
        addPersona(personaSet, new Persona(armandoBronca.getNombre(), armandoBronca.getFechaNacimiento().plusDays(1)));

        containsTest(personaSet, armandoBronca.cloneMe());

        equalsTest(personaSet, amadorDenador, amadorDenador.cloneMe());

        hashcodeTest(personaSet);
    }

}
