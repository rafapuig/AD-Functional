package es.rafapuig.exercises.domino;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Player {

    private final Collection<Domino> pieces = new LinkedHashSet<Domino>();

    public Collection<Domino> getDominoes() {
        return pieces;
    }

    public void removeDominoes(Domino domino) {
        pieces.remove(domino);
    }

    @Override
    public String toString() {
        return pieces.toString();
    }
}
