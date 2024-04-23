package es.rafapuig.exercises.domino;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

public class Player {

    private final Collection<Dominoes> pieces = new LinkedHashSet<Dominoes>();

    public Collection<Dominoes> getDominoes() {
        return pieces;
    }

    public void removeDominoes(Dominoes dominoes) {
        pieces.remove(dominoes);
    }

    @Override
    public String toString() {
        return pieces.toString();
    }
}
