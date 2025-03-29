package es.rafapuig;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NavigableSetDemo {

    public static final NavigableSet<Integer> navigableSet = new TreeSet<>(
            List.of(1, 2, 3, 5, 8, 13, 21, 34, 55)
    );

    public static void main(String[] args) {
        new NavigableSetDemo().run();
    }

    void run() {
        testLower();
        testFloor();
        testHigher();
        testCeiling();

        testPollFirst();
        testPollLast();

        testHeadSet();
        testTailSet();
        testSubSet();
    }


    //---------- LOWER y HIGHER , FLOOR y CEILING

    /**
     * El metodo lower devuelve el menor estricto al valor proporcionado
     */
    @Test
    public void testLower() {
        Integer lowerThan7 = navigableSet.lower(7);
        System.out.println(lowerThan7); // 5 < 7
        assertEquals(5, lowerThan7);

        Integer lowerThan8 = navigableSet.lower(8);
        System.out.println(lowerThan8); // 5 < 8
        assertEquals(5, lowerThan8);

        // Si probamos con el valor en la primera posición (el mínimo)
        // No va a existir un valor estrictamente menor que el menor de todos (mínimo)
        Integer lowest = navigableSet.first();
        Integer lowerThanLowest = navigableSet.lower(lowest);
        System.out.println(lowerThanLowest);
        assertNull(lowerThanLowest);
    }


    /**
     * El metodo lower devuelve el menor o igual al valor proporcionado
     */
    @Test
    public void testFloor() {
        Integer lowerThan7 = navigableSet.floor(7);
        System.out.println(lowerThan7); // 5 <= 7
        assertEquals(5, lowerThan7);

        Integer lowerThan8 = navigableSet.floor(8);
        System.out.println(lowerThan8); // 8 <= 8
        assertEquals(8, lowerThan8);

        Integer lowest = navigableSet.first();
        Integer lowerThanLowest = navigableSet.floor(lowest);
        System.out.println(lowerThanLowest);
        assertEquals(lowest, lowerThanLowest);

        // Si probamos con 0 no existe un valor <= 0 en el Set -> floor(0) devuelve null
        Integer lowerThan0 = navigableSet.floor(0);
        System.out.println(lowerThan0);
        assertNull(lowerThan0);
    }


    /**
     * El metodo higher devuelve el mayor estricto al valor proporcionado
     */
    @Test
    public void testHigher() {
        Integer higherThan7 = navigableSet.higher(7);
        System.out.println(higherThan7); // 8 > 7
        assertEquals(8, higherThan7);

        Integer higherThan8 = navigableSet.higher(8);
        System.out.println(higherThan8); // 13 > 8
        assertEquals(13, higherThan8);

        // Si probamos con el valor en la última posición (el máximo)
        // No va a existir un valor estrictamente mayor que el mayor de todos
        Integer highest = navigableSet.last();
        Integer lowerThanHighest = navigableSet.higher(highest);
        System.out.println(lowerThanHighest);
        assertNull(lowerThanHighest);
    }


    /**
     * El metodo ceiling devuelve el mayor estricto al valor proporcionado
     */
    @Test
    public void testCeiling() {
        Integer higherThan7 = navigableSet.ceiling(7);
        System.out.println(higherThan7); // 8 >= 7
        assertEquals(8, higherThan7);

        Integer higherThan8 = navigableSet.ceiling(8);
        System.out.println(higherThan8); // 8 >= 8
        assertEquals(8, higherThan8);

        // Si probamos con el valor en la última posición (el mayor de todos)
        // No va a existir un valor estrictamente mayor que el mayor de todos (máximo)
        Integer highest = navigableSet.last();
        Integer higherThanHighest = navigableSet.ceiling(highest);
        System.out.println(higherThanHighest);
        assertEquals(highest, higherThanHighest);

        // Si probamos con el valor 56 entero no existe un valor >= 56 en el Set -> ceiling(56) devuelve null
        Integer higherThan56 = navigableSet.ceiling(56);
        System.out.println(higherThan56);
        assertNull(higherThan56);
    }


    //-------------- POLL FIRST y POLL LAST

    /**
     * El metodo pollFirst devuelve el primer elemento del Set, el mínimo
     */
    @Test
    public void testPollFirst() {
        // Creamos una copia
        NavigableSet<Integer> copy = new TreeSet<>(navigableSet);

        Integer first = copy.pollFirst();
        System.out.println(first);
        assertEquals(1, first);

        Integer second = copy.pollFirst();
        System.out.println(second);
        assertEquals(2, second);

        // Vaciamos el conjunto
        copy.clear();

        // Si intentamos recuperar el primer elemento de un TreeSet vacío el metodo devuelve null
        Integer value = copy.pollFirst();
        System.out.println(value);
        assertNull(value);
    }


    /**
     * El metodo pollLast devuelve el último elemento del Set, el máximo
     */
    @Test
    public void testPollLast() {
        // Creamos una copia
        NavigableSet<Integer> copy = new TreeSet<>(navigableSet);

        Integer last = copy.pollLast();
        System.out.println(last);
        assertEquals(55, last);

        Integer beforeLast = copy.pollLast();
        System.out.println(beforeLast);
        assertEquals(34, beforeLast);

        // Vaciamos el conjunto
        copy.clear();

        // Si intentamos recuperar el primer elemento de un TreeSet vacío el metodo devuelve null
        Integer value = copy.pollLast();
        System.out.println(value);
        assertNull(value);
    }


    // -- SUBCONJUNTOS

    /**
     * El metodo headSet devuelve un subconjunto formado por los elementos menores que el valor
     * proporcionado, que incluya o no al propio valor depende del segundo parámetro
     * que es un boolean para indicar si se incluye o no
     */
    @Test
    void testHeadSet() {
        NavigableSet<Integer> to8Inclusive = navigableSet.headSet(8, true);
        System.out.println(to8Inclusive);

        NavigableSet<Integer> to8Exclusive = navigableSet.headSet(8, false);
        System.out.println(to8Exclusive);

        // Si el valor está por encima del máximo el subconjunto contiene todos los elementos
        NavigableSet<Integer> headSet = navigableSet.headSet(100, true);
        System.out.println(headSet);
    }


    /**
     * El metodo tailSet devuelve un subconjunto formado por los elementos mayores que el valor
     * proporcionado, que incluya o no al propio valor depende del segundo parámetro
     * que es un boolean para indicar si se incluye o no
     */
    @Test
    void testTailSet() {
        NavigableSet<Integer> from8Inclusive = navigableSet.tailSet(8, true);
        System.out.println(from8Inclusive);

        NavigableSet<Integer> from8Exclusive = navigableSet.tailSet(8, false);
        System.out.println(from8Exclusive);

        // Si el valor está por debajo del mínimo el subconjunto contiene todos los elementos
        NavigableSet<Integer> tailSet = navigableSet.tailSet(0, true);
        System.out.println(tailSet);
    }


    /**
     * El metodo subSet devuelve un subconjunto formado por los elementos comprendidos entre
     * un valor from y un valor to proporcionados,
     * que incluya o no a los valores limite del rango depende de los parámetros de tipo boolean
     * que van seguidos del valor from y del to para indicar si se incluyen o no
     */
    @Test
    void testSubSet() {
        NavigableSet<Integer> from5to21BothInclusive =
                navigableSet.subSet(5, true,21, true);
        System.out.println(from5to21BothInclusive);

        NavigableSet<Integer> from5to21BothExclusive =
                navigableSet.subSet(5, false,21, false);
        System.out.println(from5to21BothExclusive);

        NavigableSet<Integer> from5InclusiveTo21Exclusive =
                navigableSet.subSet(5, true,21, false);
        System.out.println(from5InclusiveTo21Exclusive);

        NavigableSet<Integer> from5ExclusiveTo21Inclusive =
                navigableSet.subSet(5, false,21, true);
        System.out.println(from5ExclusiveTo21Inclusive);

        NavigableSet<Integer> subSet =
                navigableSet.subSet(0, true,100, true);
        System.out.println(subSet);
    }

    @Test
    void testDescendingSet1() {
        System.out.println(navigableSet);
        System.out.println(navigableSet.descendingSet());
    }

    @Test
    void testDescendingSet2() {
        // Hacemos una copia
        NavigableSet<Integer> originalSet = new TreeSet<>(navigableSet);

        System.out.println(originalSet);
        // Creamos una VISTA descendente del conjunto
        NavigableSet<Integer> descendingSet = originalSet.descendingSet();
        System.out.println(descendingSet);

        // Eliminamos el mínimo de la vista descendente (equivale a eliminar el máximo de la original)
        descendingSet.pollFirst();

        System.out.println(originalSet);
        System.out.println(descendingSet);

        // Eliminamos el máximo de la vista descendente (equivale a eliminar el mínimo de la original)
        descendingSet.pollLast();

        System.out.println(originalSet);
        System.out.println(descendingSet);

        // Seleccionamos un subconjunto cabeza en la vista
        NavigableSet<Integer> to8 = descendingSet.headSet(8, true);
        System.out.println(to8);

        //Equivale a subconjunto cola en el conjunto original e invertirlo
        NavigableSet<Integer> from8 = originalSet.tailSet(8, true);
        System.out.println(from8.descendingSet());
    }

}
