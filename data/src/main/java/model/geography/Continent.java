package model.geography;

import java.util.StringJoiner;

public enum Continent {
    AMERICAS("America"),
    EUROPE("Europa"),
    ASIA("Asia"),
    AFRICA("Africa"),
    AUSTRALIA("Australia"),
    OCEANIA("Oceanía");

    final String name;

    public String getName() {
        return name;
    }

    Continent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
