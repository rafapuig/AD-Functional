package model.astronomy;

import java.util.*;


public class Planets {
    public static final Set<Planet> SOLAR_SYSTEM_PLANETS;

    static {
        Planet mercury = new Planet.Builder("Mercurio")
                .setMass(3.30 * 1023).setRadius(2_439.7).build();

        Planet venus = new Planet.Builder("Venus")
                .setMass(4.87 * 1024).setRadius(6_051.8).build();

        Planet earth = new Planet.Builder("La Tierra")
                .setMass(5.97 * 1024)
                .addSatellite("Luna").build();

        Planet mars = new Planet.Builder("Marte")
                .setMass(6.42 * 1023)
                .setRadius(3_389.5)
                .addSatellites("Phobos", "Deimos").build();

        Planet jupiter = new Planet("Jupiter",
                List.of(new Satellite("Calixto"),
                        new Satellite("Io"),
                        new Satellite("Europa"),
                        new Satellite("Ganimedes")));

        Planet saturn = new Planet("Saturno",
                List.of(new Satellite("Titan"),
                        new Satellite("Encelado")));

        Planet uranus = new Planet.Builder("Urano")
                .setMass(8.68 * 1025)
                .setRadius(25_362)
                .addSatellites("Titania", "Oberon", "Ariel", "Umbriel", "Miranda")
                .build();

        SOLAR_SYSTEM_PLANETS = Collections.unmodifiableSet(
                new LinkedHashSet<>(
                        List.of(mercury, venus, earth, mars, jupiter, saturn, uranus)));
    }


}
