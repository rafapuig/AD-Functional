package es.rafapuig.exercises.cartas.scoring;

import model.cartas.Naipe;
import model.cartas.Naipes;

@FunctionalInterface
public interface NaipeScorer {

    int score(Naipe naipe);

    static int getDefaultScore(Naipe naipe) {
        return naipe.getValor().getNumber();
    }

    public static NaipeScorer DEFAULT = new NaipeScorer() {
        @Override
        public int score(Naipe naipe) {
            return getDefaultScore(naipe);
        }
    };

}
