package model.cartas.filtering;

import model.cartas.Naipe;

@FunctionalInterface
public interface NaipeFilter {

    boolean filter(Naipe naipe);
}
