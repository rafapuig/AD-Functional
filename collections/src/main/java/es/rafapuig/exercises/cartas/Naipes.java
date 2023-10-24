package es.rafapuig.exercises.cartas;

import es.rafapuig.exercises.cartas.filtering.NaipeFilter;
import es.rafapuig.exercises.cartas.scoring.BriscaScorer;
import es.rafapuig.exercises.cartas.scoring.NaipeScorer;
import model.cartas.Naipe;
import model.cartas.Palo;
import model.cartas.Valor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

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



    public static int scoring(Naipe naipe) {
        return scoring(naipe, NaipeScorer.DEFAULT);
    }

    public static int scoring(Naipe naipe, NaipeScorer scorer) {
        return scorer.score(naipe);
    }

    public static int scoreByBriscaValue(Naipe naipe) {
        return new BriscaScorer().score(naipe);
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
