package es.rafapuig.exercises.cartas;

@FunctionalInterface
interface NaipeScorer {

    int score(Naipe naipe);

    static int getDefaultScore(Naipe naipe) {
        return naipe.getValor().getNumber();
    }

}
