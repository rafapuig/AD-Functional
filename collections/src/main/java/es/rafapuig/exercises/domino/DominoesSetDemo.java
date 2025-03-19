package es.rafapuig.exercises.domino;

import java.util.*;

public class DominoesSetDemo {

    public static void main(String[] args) {
        generateDominoesSetTest();
        duplicatePieceTest();
        sortedPiecesTest();
        sortedPiecesByPointsTest();
    }

    static void generateDominoesSetTest() {
        Set<Domino> dominoSet = Dominoes.createDominoesSet();
        System.out.println(dominoSet);
    }

    static void duplicatePieceTest() {
        Set<Domino> dominoSet = new LinkedHashSet<>();
        boolean added;

        added = dominoSet.add(new Domino(4,5));
        System.out.println("added = " + added);
        System.out.println(dominoSet);

        added = dominoSet.add(new Domino(4,5));
        System.out.println("added = " + added);
        System.out.println(dominoSet);

        added = dominoSet.add(new Domino(5,4));
        System.out.println("added = " + added);
        System.out.println(dominoSet);

    }

    static void sortedPiecesTest() {
        Set<Domino> set = new TreeSet<>();

        set.add(new Domino(2,4));
        set.add(new Domino(1,5));
        set.add(new Domino(6,6));
        set.add(new Domino(5,5));
        set.add(new Domino(4,6));
        set.add(new Domino(5,6));
        set.add(new Domino(1,4));
        set.add(new Domino(3,0));

        System.out.println(set);
    }

    static void sortedPiecesByPointsTest() {
        Set<Domino> set = new TreeSet<>(
                Comparator
                        .comparingInt(Domino::getPoints)
                        .thenComparing(Domino::hashCode));

        set.add(new Domino(2,4));
        set.add(new Domino(1,5));
        set.add(new Domino(6,6));
        set.add(new Domino(5,5));
        set.add(new Domino(4,6));
        set.add(new Domino(5,6));
        set.add(new Domino(1,4));
        set.add(new Domino(3,0));
        System.out.println(set);
    }

}
