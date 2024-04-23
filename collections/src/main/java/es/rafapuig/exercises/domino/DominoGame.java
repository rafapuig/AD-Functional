package es.rafapuig.exercises.domino;

import java.util.*;


public class DominoGame {

    public static void main(String[] args) {

        Set<Dominoes> set = Domino.createDominoesSet();

        Set<Dominoes> shuffledSet = Domino.shuffle(set);

        System.out.println(shuffledSet);

        //Crear 4 jugadores
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player());
        }

        int counter = 0;
        for (Dominoes dominoes : shuffledSet) {
            players.get(counter++ % players.size()).getDominoes().add(dominoes);
        }

        for (Player player : players) {
            System.out.println(player.getDominoes());
        }

        //Comprobar si el jugador 1 tiene seises
        List<Dominoes> dominoesList = Domino.containsDominoesWith(
                players.get(0).getDominoes(), 6);
        System.out.println(dominoesList);

        dominoesList = Domino.containsDominoesWithStreams(
                players.get(0).getDominoes(), 6);
        System.out.println(dominoesList);

        //Jugar la primera ficha del jugador 1 que tenga seis
        System.out.println(players.get(0));

        if (!dominoesList.isEmpty()) {
            players.get(0).removeDominoes(dominoesList.get(0));
        }

        System.out.println(players.get(0).getDominoes());
    }


}
