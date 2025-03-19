package es.rafapuig.exercises.domino;

import es.rafapuig.exercises.SetUtils;

import java.util.*;

public class Dominoes {

    /**
     * Metodo para crear un set de fichas de dominó
     */
    public static Set<Domino> createDominoesSet() {

        Set<Domino> dominoSet = new LinkedHashSet<>(); // Un linkedHashSet preserva el orden de inserción

        //Generar el conjunto de fichas de dominó
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) { // j empieza a partir de i para no repetir fichas iguales invertidas
                Domino domino = new Domino(i, j);
                dominoSet.add(domino); // Se añadirá al conjunto si no existe otra igual
            }
        }
        return dominoSet;
    }

    /**
     * Mezclar (desordenar) un conjunto de fichas de dominó
     */
    public static Set<Domino> shuffle(Set<Domino> dominoes) {
        return SetUtils.shuffleSet(dominoes); // Delegamos en el mezclador de conjuntos
    }

    /** Devuelve una lista con las fichas de dominó que bien el lado superior o
     * en el inferior coincide con el valor del parámetro suit (el palo)
     */
    public static List<Domino> containsDominoesWith(Collection<Domino> dominoes, int suit) {
        List<Domino> result = new LinkedList<>();

        // Realizamos una iteración sobre los elementos de la colección
        // y para cada uno aplicamos el proceso (filtro), que de pasarlo, lo añadimos
        // a la lista resultado
        for (Domino domino : dominoes) {
            if(domino.getTop() == suit || domino.getBottom() == suit) {
                result.add(domino);
            }
        }

        return result;
    }

    //-------------- Programación funcional ------------------------------

    public static List<Domino> containsDominoWithFunctional(Collection<Domino> dominoes, int suit) {

        List<Domino> result = new LinkedList<>();

        // La iteración es interna (método forEach)
        // Proporcionamos el código que indica lo que debe hacer para procesar cada elemento
        dominoes.forEach(domino -> {
            if(domino.getTop() == suit || domino.getBottom() == suit) {
                result.add(domino);
            }
        });

        return result;
    }

    //-------------------- Stream API -------------------------------------

    public static List<Domino> containsDominoWithStreams(Collection<Domino> set, int suit) {

        //Mediante el stream API no hay iteración externa (bucle)
        return set.stream()
                .filter(d -> d.getTop() == suit || d.getBottom() == suit)
                .toList();
    }
}
