package es.rafapuig.exercises.cartas.scoring;

import model.cartas.Naipe;

public class BriscaScorer implements NaipeScorer {
    @Override
    public int score(Naipe naipe) {
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
