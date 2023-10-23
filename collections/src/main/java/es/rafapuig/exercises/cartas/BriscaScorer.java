package es.rafapuig.exercises.cartas;

public class BriscaScorer implements NaipeScorer {
    @Override
    public int score(Naipe naipe) {
        return Naipes.scoreByBriscaValue(naipe);
    }

}
