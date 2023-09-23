package es.rafapuig.exercises.domino;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class Dominoes implements Comparable<Dominoes>{

    int top;
    int bottom;

    public Dominoes(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getPoints() {
        return top + bottom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dominoes dominoes)) return false;

        return top == dominoes.top && bottom == dominoes.bottom
                || top == dominoes.bottom && bottom == dominoes.top;
    }

    @Override
    public int hashCode() {
        int min = Math.min(top, bottom);
        int max = Math.max(top, bottom);
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return String.format("[%d : %d]", top, bottom);
    }

    @Override
    public int compareTo(Dominoes o) {

        int topComparison = Integer.compare(this.top, o.top);
        if(topComparison != 0) return topComparison;
        return Integer.compare(bottom, o.bottom);

        //int points = this.top + this.bottom;
        //int otherPoints = o.top + o.bottom;
        //return points - otherPoints;
    }


}
