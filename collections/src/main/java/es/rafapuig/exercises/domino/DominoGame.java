package es.rafapuig.exercises.domino;

import java.util.*;


public class DominoGame {

    public static void main(String[] args) {

        Set<Domino> set = Dominoes.createDominoesSet();

        Set<Domino> shuffledSet = Dominoes.shuffle(set);

        System.out.println(shuffledSet);

        //Crear 4 jugadores
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player());
        }

        int counter = 0;
        for (Domino domino : shuffledSet) {
            players.get(counter++ % players.size()).getDominoes().add(domino);
        }

        for (Player player : players) {
            System.out.println(player.getDominoes());
        }

        //Comprobar si el jugador 1 tiene seises
        List<Domino> dominoList = Dominoes.containsDominoesWith(
                players.get(0).getDominoes(), 6);
        System.out.println(dominoList);

        dominoList = Dominoes.containsDominoWithStreams(
                players.get(0).getDominoes(), 6);
        System.out.println(dominoList);

        //Jugar la primera ficha del jugador 1 que tenga seis
        System.out.println(players.get(0));

        if (!dominoList.isEmpty()) {
            players.get(0).removeDominoes(dominoList.get(0));
        }

        System.out.println(players.get(0).getDominoes());
    }


}
