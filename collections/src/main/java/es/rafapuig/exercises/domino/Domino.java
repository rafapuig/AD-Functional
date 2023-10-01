package es.rafapuig.exercises.domino;

import java.util.*;

public class Domino {

    public static Set<Dominoes> createDominoesSet() {

        Set<Dominoes> dominoesSet = new LinkedHashSet<>();

        //Generar el conjunto de fichas de domino
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Dominoes piece = new Dominoes(i, j);
                dominoesSet.add(piece);
                //System.out.println(piece);
                //System.out.printf("%d : %d\n", i, j);
            }
        }
        return dominoesSet;
    }

    public static Set<Dominoes> shuffle(Set<Dominoes> dominoes) {
        return SetUtils.shuffleSet(dominoes);
    }

    /** Devuelve una lista con las fichas de domino que bien el lado superior o
     * en el inferior coincide con el valor del parametro value
     */

    public static List<Dominoes> containsDominoesWith(Collection<Dominoes> set, int value) {
        List<Dominoes> result = new LinkedList<>();

        //Realizamos una iteracion sobre los elementos de la coleccion
        // y para cada un aplicamos el proceso (filtro) que de pasarlo lo añadimos
        // al resultado
        for (Dominoes dominoes : set) {
            if(dominoes.getTop() == value || dominoes.getBottom() == value) {
                result.add(dominoes);
            }
        }

        return result;
    }

    public static List<Dominoes> containsDominoesWithStreams(Collection<Dominoes> set, int value) {

        //Mediante stream no hay iteracion (bucle)
        return set.stream()
                .filter(d -> d.getTop() == value || d.getBottom() == value)
                .toList();
    }
}
