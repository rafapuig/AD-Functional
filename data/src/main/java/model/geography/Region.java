package model.geography;

import static model.geography.Continent.*;

public enum Region {
    NORTH_AMERICA(AMERICAS),
    SOUTH_AMERICA(AMERICAS),
    CENTRAL_AMERICA(AMERICAS),
    CARIBBEAN(AMERICAS),
    WESTERN_EUROPE(EUROPE),
    EASTER_EUROPE(EUROPE),
    NORTHERN_EUROPE(EUROPE),
    SOUTHERN_EUROPE(EUROPE),
    WESTERN_ASIA(ASIA),
    EAST_ASIA(ASIA),
    SOUTHEAST_ASIA(ASIA),
    CENTRAL_ASIA(ASIA),
    SOUTH_ASIA(ASIA);



    private final Continent continent;

    public Continent getContinent() {
        return continent;
    }

    Region(Continent continent) {
        this.continent = continent;
    }
    }
