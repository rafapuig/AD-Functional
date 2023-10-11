package model.astronomy;

import java.util.*;


public class Planets {
    public static final Set<Planet> SOLAR_SYSTEM_PLANETS;

    static {
        Planet mercury = new Planet.Builder("Mercurio")
                .setMass(3.30E23).setRadius(2_439.7).build();

        Planet venus = new Planet.Builder("Venus")
                .setMass(4.87E24).setRadius(6_051.8).build();

        Planet earth = new Planet.Builder("La Tierra")
                .setMass(5.97E24)
                .setRadius(6.371)
                .addSatellite("Luna").build();

        Planet mars = new Planet.Builder("Marte")
                .setMass(6.42E23)
                .setRadius(3_389.5)
                .addSatellites("Phobos", "Deimos").build();

        Planet jupiter = new Planet.Builder("Jupiter")
                .setMass(1.90E27)
                .setRadius(69_911)
                .addSatellites("Calixto", "Io", "Europa", "Ganimedes")
                .build();

        Planet saturn = new Planet.Builder("Saturno")
                .setMass(5.68E26)
                .setRadius(58_232)
                .addSatellites("Titan", "Rea", "Jápeto", "Dione", "Tetis", "Encelado")
                .build();

        Planet uranus = new Planet.Builder("Urano")
                .setMass(8.68E25)
                .setRadius(25_362)
                .addSatellites("Titania", "Oberon", "Ariel", "Umbriel", "Miranda")
                .build();

        Planet neptune = new Planet.Builder("Neptuno")
                .setMass(1.02E26)
                .setRadius(24_622)
                .addSatellites("Tritón", "Proteo", "Nereida")
                .build();

        SOLAR_SYSTEM_PLANETS = Collections.unmodifiableSet(
                new LinkedHashSet<>(
                        List.of(mercury, venus, earth, mars, jupiter, saturn, uranus, neptune)));
    }


}
