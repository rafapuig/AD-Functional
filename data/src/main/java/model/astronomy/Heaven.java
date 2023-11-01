package model.astronomy;

import java.util.ArrayList;
import java.util.List;

public class Heaven {

    public static List<CelestialBody> BODIES = new ArrayList<>() {{
        add(Stars.SUN);
        addAll(Planets.SOLAR_SYSTEM_PLANETS);
        Planets.SOLAR_SYSTEM_PLANETS.stream()
                .flatMap(planet -> planet.getSatellites().stream())
                .forEach(this::add);
        /*for (Planet planet : Planets.SOLAR_SYSTEM_PLANETS) {
            addAll(planet.getSatellites());
        }*/
    }};


}
