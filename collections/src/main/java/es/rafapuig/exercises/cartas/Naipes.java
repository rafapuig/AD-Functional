package es.rafapuig.exercises.cartas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

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

    static ToIntFunction<Naipe> defaultScorer =
            naipe -> naipe.getValor().getNumber();

    public static int scoring(Naipe naipe) {
        return scoring(naipe, defaultScorer);
    }

    public static int scoring(Naipe naipe, ToIntFunction<? super Naipe> scorer) {
        return scorer.applyAsInt(naipe);
    }

    public static int scoreByBriscaValue(Naipe naipe) {
        return switch (naipe.getValor()) {
            case AS -> 11;
            case TRES -> 10;
            case REY -> 4;
            case CABALLO -> 3;
            case SOTA -> 2;
            default -> 0;
        };
    }



}
