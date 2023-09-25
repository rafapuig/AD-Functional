package es.rafapuig.exercises.cartas;

public class BriscaScorer implements Scorer {
    @Override
    public int score(Naipe naipe) {
        return Naipes.scoreByBriscaValue(naipe);
    }

}
