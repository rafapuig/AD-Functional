package es.rafapuig.exercises.cartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static es.rafapuig.exercises.cartas.Naipes.generateBaraja;

public class CartasDemo {

    public static void main(String[] args) {

        List<Naipe> baraja = generateBaraja();

        System.out.println(baraja);

        Collections.shuffle(baraja);

        System.out.println(baraja);

        //Crear los abanicos de los jugadores
        List<List<Naipe>> abanicos = new ArrayList<>();

        int numJugadores = 8;
        for (int i = 0; i < numJugadores; i++) {
            List<Naipe> abanico = new LinkedList<>();
            abanicos.add(abanico);
        }

        //Repartir las cartas en los abanicos de los jugadores
        int counter = 0;
        for (Naipe naipe : baraja) {
            abanicos.get(counter++ % abanicos.size()).add(naipe);
        }

        for(List<Naipe> abanico : abanicos) {
            System.out.println(abanico);
        }
    }

}
