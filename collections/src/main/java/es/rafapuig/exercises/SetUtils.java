package es.rafapuig.exercises;

import java.util.*;

/*************** EJERCICIO 3 ****************************/

public class SetUtils {

    /**
     * A partir de un Set de elementos genera un nuevo conjunto con los mismos elementos
     * pero permutados de manera aleatoria
     * @param set conjunto fuente con los elementos para generar la versíon permutada del conjunto
     * @return un nuevo conjunto con los elementos permutados de forma aleatoria
     * @param <E> tipo de los elementos del conjunto
     */
    public static <E> Set<E> shuffleSet(Set<E> set) {

        // Utilizamos el constructor que crea una colección a partir de otra colección
        // Todas las clases de colección del API tienen un constructor de este tipo
        List<E> list = new LinkedList<>(set);

        //Necesitamos generar una colección de tipo List porque la clase Collections
        // consta de un método shuffle que desordena una lista

        // Desordena la lista proporcionada
        Collections.shuffle(list);

        //Ahora creamos de nuevo un Set a partir de los elementos de la lista
        //Como queremos que se preserve el orden, el conjunto será de tipo LinkedHashSet
        return new LinkedHashSet<>(list);
    }

    /**
     * Version mutable, desordena directamente el propio Set proporcionado
     * @param set
     * @return
     * @param <E>
     */
    public static <E> void shuffle(Set<E> set) {
        Set<E> shuffled = shuffleSet(set);
        set.clear();
        set.addAll(shuffled);
    }
}
