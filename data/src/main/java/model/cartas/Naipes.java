package model.cartas;

import model.cartas.scoring.NaipeScorer;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Naipes {

    public static final List<Naipe> BARAJA = generateBaraja(); //ya se devuelve una lista no modificable
            //Collections.unmodifiableList(generateBaraja());

    public static List<Naipe> generateBaraja() {

        return Arrays.stream(Palo.values())
                .flatMap(palo -> Arrays.stream(Valor.values())
                        .map(valor -> new Naipe(palo,valor)))
                .toList();
    }

    public static ToIntFunction<Naipe> defaultScorer = NaipeScorer::getDefaultScore;

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
        return figurasSet.stream().
                anyMatch(valor -> valor.equals(naipe.getValor()));
    }

    public static List<Naipe> getAses(List<Naipe> mazo) {
        return mazo.stream()
                .filter(naipe -> naipe.getValor().equals(Valor.AS))
                .collect(Collectors.toList());
    }

    public static List<Naipe> getNaipesFilteredByPalo(List<Naipe> mazo, Palo palo) {
        return mazo.stream()
                .filter(naipe -> naipe.getPalo().equals(palo))
                .collect(Collectors.toList());
    }


    public static List<Naipe> getNaipesFilteredBy(List<Naipe> mazo, Predicate<Naipe> filter) {
        return mazo.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public static List<Naipe> getNaipesFilteredBy(List<Naipe> mazo, NaipeFilter filter) {
        return mazo.stream()
                .filter(filter::filter)
                .collect(Collectors.toList());
    }

}
