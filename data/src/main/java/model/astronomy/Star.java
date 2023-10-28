package model.astronomy;

import java.util.*;

public class Star extends CelestialBody {

    Set<Planet> orbitingPlanets = new LinkedHashSet<>();

    public Star(String name) {
        super(name);
    }

    public Star(String name, Collection<Planet> planets) {
        super(name);
        orbitingPlanets.addAll(planets);
    }

    public void addPlanets(Planet... planets) {
        orbitingPlanets.addAll(Arrays.asList(planets));
    }



}
