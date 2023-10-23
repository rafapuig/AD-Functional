package es.rafapuig.exercises.cartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.ToIntFunction;

public class Naipes {

    public static final List<Naipe> BARAJA =
            Collections.unmodifiableList(generateBaraja());

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

    public static boolean isFigura(Naipe naipe) {
        EnumSet<Valor> figurasSet = EnumSet.of(Valor.SOTA, Valor.CABALLO, Valor.REY);
        return figurasSet.contains(naipe.getValor());
    }

    public static List<Naipe> getAses(List<Naipe> mazo) {
        List<Naipe> naipes = new ArrayList<>(); //inicializo el objeto contenedor

        for (Naipe naipe : mazo) {               //iteracion externa sobre la coleccion
            if (naipe.getValor() == Valor.AS) {  //filtro si es un AS
                naipes.add(naipe);              //recolecto para el resultado
            }
        }
        return naipes;
    }

    public static List<Naipe> getNaipesFilteredByPalo(List<Naipe> mazo, Palo palo) {
        List<Naipe> naipes = new ArrayList<>(); //inicializo el objeto contenedor

        for (Naipe naipe : mazo) {               //iteracion externa sobre la coleccion
            if (naipe.getPalo().equals(palo)) {  //filtro si es del palo proporcionado
                naipes.add(naipe);              //recolecto para el resultado
            }
        }
        return naipes;
    }


    public static List<Naipe> getNaipesFilteredBy(List<Naipe> mazo, NaipeFilter filter) {
        List<Naipe> naipes = new ArrayList<>();

        for (Naipe naipe : mazo) {
            if (filter.filter(naipe)) {
                naipes.add(naipe);
            }
        }
        return naipes;
    }

}
