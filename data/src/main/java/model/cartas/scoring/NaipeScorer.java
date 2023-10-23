package model.cartas.scoring;

import model.cartas.Naipe;

@FunctionalInterface
public interface NaipeScorer {

    int score(Naipe naipe);

    static int getDefaultScore(Naipe naipe) {
        return naipe.getValor().getNumber();
    }

    public static NaipeScorer DEFAULT =  NaipeScorer::getDefaultScore;
}
