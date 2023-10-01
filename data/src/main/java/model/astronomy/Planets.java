package model.astronomy;

import java.util.*;


public class Planets {
    public static final Set<Planet> SOLAR_SYSTEM_PLANETS;

    static {
        Planet mercury = new Planet("Mercurio", Collections.emptyList());
        Planet venus = new Planet("Venus", Collections.emptyList());
        Planet earth = new Planet("La Tierra",
                List.of(new Satellite("Luna")));
        Planet mars = new Planet("Marte",
                List.of(new Satellite("Phobos"),
                        new Satellite("Deimos")));
        Planet jupiter = new Planet("Jupiter",
                List.of(new Satellite("Calixto"),
                        new Satellite("Io"),
                        new Satellite("Europa"),
                        new Satellite("Ganimedes")));
        Planet saturn = new Planet("Saturno",
                List.of(new Satellite("Titan"),
                        new Satellite("Encelado")));

        SOLAR_SYSTEM_PLANETS = new LinkedHashSet<>(List.of(mercury, venus, earth, jupiter, saturn));
    }


}
