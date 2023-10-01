package model.astronomy;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class Planet {

    private final String name;
    private final List<Satellite> satellites;

    public Planet(String name) {
        this(name, Collections.emptyList());
    }

    public Planet(String name, List<Satellite> satellites) {
        this.name = name;
        this.satellites = satellites;
    }

    public String getName() {
        return name;
    }

    public List<Satellite> getSatellites() {
        return satellites;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Planet.class.getSimpleName() + "[", "]")
                .add(name)
                .add("satellites=" + satellites)
                .toString();
    }
}
