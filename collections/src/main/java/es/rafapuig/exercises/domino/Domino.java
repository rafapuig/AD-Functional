package es.rafapuig.exercises.domino;

import java.util.*;

public class Domino {

    public static Set<Dominoes> getDominoes() {

        Set<Dominoes> dominoesSet = new LinkedHashSet<>();

        //Generar el conjunto de fichas de domino
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Dominoes piece = new Dominoes(i, j);
                dominoesSet.add(piece);
                System.out.println(piece);
                System.out.printf("%d : %d\n", i, j);
            }
        }
        return dominoesSet;
    }

    public static List<Dominoes> containsDominoesWith(Set<Dominoes> set, int value) {
        List<Dominoes> result = new LinkedList<>();

        for (Dominoes dominoes : set) {
            if(dominoes.getTop() == value || dominoes.getBottom() == value) {
                result.add(dominoes);
            }
        }

        return result;
    }

    public static List<Dominoes> containsDominoesWithStreams(Set<Dominoes> set, int value) {

        return set.stream()
                .filter(d -> d.getTop() == value || d.getBottom() == value)
                .toList();
    }
}
