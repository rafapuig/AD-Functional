package es.rafapuig.exercises.domino;

import java.util.*;

public class DominoGame {

    public static void main(String[] args) {

        Set<Dominoes> set = Domino.getDominoes();

        Set<Dominoes> shuffledSet = shuffleSet(set);

        System.out.println(shuffledSet);

        List<Set<Dominoes>> players = new ArrayList<>();

        players.add(new LinkedHashSet<>());
        players.add(new LinkedHashSet<>());
        players.add(new LinkedHashSet<>());
        players.add(new LinkedHashSet<>());

        int counter = 0;
        for(Dominoes dominoes : shuffledSet) {
            players.get(counter++ % players.size()).add(dominoes);
        }

        for(Set<Dominoes> player : players) {
            System.out.println(player);
        }

        //Comprobar si el jugador 1 tiene seises
        List<Dominoes> dominoes = Domino.containsDominoesWith(players.get(0), 6);
        System.out.println(dominoes);
        dominoes = Domino.containsDominoesWithStreams(players.get(0), 6);
        System.out.println(dominoes);

        //Jugar la primera ficha del jugador 1 que tenga seis
        System.out.println(players.get(0));

        if(!dominoes.isEmpty()) {
            players.get(0).remove(dominoes.get(0));
        }

        System.out.println(players.get(0));
    }

    static <E> Set<E> shuffleSet(Set<E> set) {

        List<E> list = new LinkedList<>(set);

        Collections.shuffle(list);

        return new LinkedHashSet<>(list);
    }
}
