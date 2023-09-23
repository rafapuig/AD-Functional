package es.rafapuig.exercises.cartas;

import java.util.ArrayList;
import java.util.List;

public class Naipes {

    public static List<Naipe> generateBaraja() {

        List<Naipe> baraja = new ArrayList<>();

        for (Palo palo : Palo.values()) {
            for (Valor valor : Valor.values()) {
                Naipe naipe = new Naipe(palo, valor);
                baraja.add(naipe);
            }
        }
        return baraja;
    }



}
