package model.cartas.scoring;

import model.cartas.Naipe;
import model.cartas.Naipes;

public class BriscaScorer implements NaipeScorer {
    @Override
    public int score(Naipe naipe) {
        return Naipes.scoreByBriscaValue(naipe);
    }

}
