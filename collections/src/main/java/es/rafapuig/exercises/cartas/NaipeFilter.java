package es.rafapuig.exercises.cartas;

import model.cartas.Naipe;

@FunctionalInterface
public interface NaipeFilter {

    boolean filter(Naipe naipe);
}
