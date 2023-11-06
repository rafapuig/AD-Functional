package model.geography;

import java.text.DecimalFormat;
import java.util.StringJoiner;

public record Area(int value, SIPrefix prefix) implements Comparable<Area> {
    @Override
    public String toString() {
        return DecimalFormat.getNumberInstance().format(value)
                + " " + prefix.getSymbol() + "m²";
    }

    public Area(int value) {
        this(value, SIPrefix.KILO);
    }

    @Override
    public int compareTo(Area other) {
        return Long.compare(this.value * this.prefix().multiplier, other.value * this.prefix.getMultiplier());
    }
}
