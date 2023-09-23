package es.rafapuig.exercises.domino;

import java.util.*;

public class DominoesSetDemo {

    public static void main(String[] args) {
        //duplicatePieceTest();
        sortedPiecesTest();
        sortedPiecesByPointsTest();
    }

    static void duplicatePieceTest() {
        Set<Dominoes> dominoesSet = new LinkedHashSet<>();
        boolean added;

        added = dominoesSet.add(new Dominoes(4,5));
        System.out.println("added = " + added);
        System.out.println(dominoesSet);

        added = dominoesSet.add(new Dominoes(4,5));
        System.out.println("added = " + added);
        System.out.println(dominoesSet);

        added = dominoesSet.add(new Dominoes(5,4));
        System.out.println("added = " + added);
        System.out.println(dominoesSet);

    }

    static void sortedPiecesTest() {
        Set<Dominoes> set = new TreeSet<>();

        set.add(new Dominoes(2,4));
        set.add(new Dominoes(1,5));
        set.add(new Dominoes(6,6));
        set.add(new Dominoes(5,5));
        set.add(new Dominoes(4,6));
        set.add(new Dominoes(5,6));
        set.add(new Dominoes(1,4));
        set.add(new Dominoes(3,0));

        System.out.println(set);
    }

    static void sortedPiecesByPointsTest() {
        Set<Dominoes> set = new TreeSet<>(
                Comparator
                        .comparingInt(Dominoes::getPoints)
                        .thenComparing(Dominoes::hashCode));

        set.add(new Dominoes(2,4));
        set.add(new Dominoes(1,5));
        set.add(new Dominoes(6,6));
        set.add(new Dominoes(5,5));
        set.add(new Dominoes(4,6));
        set.add(new Dominoes(5,6));
        set.add(new Dominoes(1,4));
        set.add(new Dominoes(3,0));
        System.out.println(set);

    }

    private static Set<Dominoes> getDominoes() {
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
}
