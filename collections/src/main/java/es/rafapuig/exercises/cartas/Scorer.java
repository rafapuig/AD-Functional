package es.rafapuig.exercises.cartas;

@FunctionalInterface
interface Scorer {

    int score(Naipe naipe);

    static int getDefaultScore(Naipe naipe) {
        return naipe.getValor().getNumber();
    }

}
