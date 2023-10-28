package model.astronomy;

import java.util.*;


public class Planets {
    public static final Set<Planet> SOLAR_SYSTEM_PLANETS;

    public static Planet MERCURY = new Planet.Builder("Mercurio")
            .setType(PlanetType.ROCKY)
            .setOrbitalPeriod(0.241)
            .setMass(3.30E23).setRadius(2_439.7).build();

    public static Planet VENUS = new Planet.Builder("Venus")
            .setType(PlanetType.ROCKY)
            .setOrbitalPeriod(0.615)
            .setMass(4.87E24).setRadius(6_051.8).build();

    public static Planet EARTH = new Planet.Builder("La Tierra")
            .setType(PlanetType.ROCKY)
            .setOrbitalPeriod(1.0)
            .setMass(5.97E24)
            .setRadius(6_371)
            .addSatellite("Luna").build();

    public static Planet MARS = new Planet.Builder("Marte")
            .setType(PlanetType.ROCKY)
            .setOrbitalPeriod(1.88)
            .setMass(6.42E23)
            .setRadius(3_389.5)
            .addSatellites("Phobos", "Deimos").build();

    public static Planet CERES = new Planet.Builder("Ceres")
            .setType(PlanetType.DWARF)
            .setOrbitalPeriod(1682 / 365.0)
            .setMass(9.5E20)
            .setRadius(471).build();


    public static Planet JUPITER = new Planet.Builder("Jupiter")
            .setType(PlanetType.GAS_GIANT)
            .setOrbitalPeriod(11.86)
            .setMass(1.90E27)
            .setRadius(69_911)
            .addSatellites("Calixto", "Io", "Europa", "Ganimedes")
            .build();

    public static Planet SATURN = new Planet.Builder("Saturno")
            .setType(PlanetType.GAS_GIANT)
            .setOrbitalPeriod(29.46)
            .setMass(5.68E26)
            .setRadius(58_232)
            .addSatellites("Titan", "Rea", "Jápeto", "Dione", "Tetis", "Encelado")
            .build();

    public static Planet URANUS = new Planet.Builder("Urano")
            .setType(PlanetType.GAS_GIANT)
            .setOrbitalPeriod(84.01)
            .setMass(8.68E25)
            .setRadius(25_362)
            .addSatellites("Titania", "Oberon", "Ariel", "Umbriel", "Miranda")
            .build();

    public static Planet NEPTUNE = new Planet.Builder("Neptuno")
            .setType(PlanetType.GAS_GIANT)
            .setOrbitalPeriod(164.79)
            .setMass(1.02E26)
            .setRadius(24_622)
            .addSatellites("Tritón", "Proteo", "Nereida")
            .build();

    public static Planet PLUTO = new Planet.Builder("Plutón")
            .setType(PlanetType.DWARF)
            .setOrbitalPeriod(248.54)
            .setMass(1.305E22)
            .setRadius(1148)
            .addSatellites("Caronte", "Nix", "Hydra").build();


    static {
        SOLAR_SYSTEM_PLANETS = Collections.unmodifiableSet(
                new LinkedHashSet<>(
                        List.of(MERCURY, VENUS, EARTH, MARS, CERES, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO)));
    }


}
