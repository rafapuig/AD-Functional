import model.astronomy.Planet;
import model.astronomy.Planets;
import model.astronomy.Satellite;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamsExamplesPlanets {
    public static void main(String[] args) {
        //printPlanets();
        //getPlanetMoonsFlat();
        //getMoonlessPlanets();
        //getMapPlanetsByName();
        //getMapPlanetRadiusByName();

        getAverageRadius();
        getPlanetRadiusMap();

        getHeaviestPlanet();
        getLightestPlanet();

        getPlanetNameToMoonsMap();

        getPlanetMoonsCount();

    }

    static void printPlanets() {
        var planets = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .map(Planet::getName)
                .toList();

        planets.forEach(System.out::println);
    }

    //Operadores flatMap
    static void getPlanetMoonsFlat() {
        List<Satellite> satellites = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .flatMap(planet -> planet.getSatellites().stream())
                .toList();

        satellites.forEach(System.out::println);
    }

    static void getMoonlessPlanets() {
        Set<Planet> moonlessPlanets = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getSatellites().isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new)); //para preservar el orden

        System.out.println(moonlessPlanets.stream().map(Planet::getName).toList());
    }

    static void getMapPlanetsByName() {
        var map = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .collect(Collectors
                        .toMap(Planet::getName, Function.identity()));

        System.out.println(map);
    }

    static void getMapPlanetRadiusByName() {
        var map = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .collect(Collectors
                        .toMap(Planet::getName,
                                Planet::getRadiusInKm,
                                (o, n) -> n,
                                LinkedHashMap::new));

        System.out.println(map);
    }

    static void getPlanetRadiusMap() {
        var map2 = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .sorted(Comparator
                        .comparing(planet -> planet.getRadiusInKm().orElse(0.0)))
                .collect(Collectors.toMap(
                        Planet::getName,
                        Planet::getRadiusInKm,
                        (o, n) -> n,
                        LinkedHashMap::new));

        System.out.println(map2);

        System.out.println(
                Arrays.toString(
                        map2.values().stream()
                                .filter(Optional::isPresent)
                                .mapToDouble(Optional::get)
                                .toArray()));

    }

    static void getAverageRadius() {
        var radius = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .filter(planet -> planet.getRadiusInKm().isPresent())
                .collect(Collectors
                        .averagingDouble(
                                planet -> planet.getRadiusInKm().get()));

        System.out.println("Radio medio = " + radius);
    }


    static void getHeaviestPlanet() {
        Optional<Planet> heaviestPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getMassInKg().isPresent())
                .max(Comparator.comparing(planet -> planet.getMassInKg().get()));

        heaviestPlanet.ifPresent(
                planet -> System.out.println(
                        "El planeta más pesado es " +
                                planet.getName() +
                                " con " +
                                planet.getMassInKg().get() / 1_000 +
                                " Toneladas"));
    }

    static void getLightestPlanet() {
        Optional<Planet> lightestPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getMassInKg().isPresent())
                .collect(Collectors.minBy(
                        Comparator.comparing(planet -> planet.getMassInKg().get())));


        lightestPlanet.ifPresent(
                planet -> System.out.println(
                        "El planeta más ligero es " + planet.getName() +
                                " con " + planet.getMassInKg().get() / 1000 + " Toneladas"));

    }

    static void getPlanetNameToMoonsMap() {
        Map<String, String> planetNameToMoonsNamesMap =
                Planets.SOLAR_SYSTEM_PLANETS
                        .stream()
                        .collect(Collectors.toMap(
                                Planet::getName,
                                planet -> planet.getSatellites()
                                        .stream()
                                        .map(Satellite::getName)
                                        .collect(Collectors.joining(", ","'","'")),
                                (oldValue, newValue) -> newValue,
                                LinkedHashMap::new
                        ));

        System.out.println(planetNameToMoonsNamesMap);
    }

    static void getPlanetMoonsCount() {
        Map<String, Integer> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.toMap(
                        Planet::getName,
                        planet -> planet.getSatellites().size(),
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new
                ));

        System.out.println(map);
    }

}
