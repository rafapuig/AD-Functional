import model.astronomy.Planet;
import model.astronomy.Planets;
import model.astronomy.Satellite;
import model.geography.City;
import model.geography.Countries;
import model.geography.Country;
import model.geography.Provincia;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamsExamples {

    public static void main(String[] args) {
        //countriesInfo();
        //worldCapitals();
        printPlanets();
        //getPlanetMoonsFlat();
        //getProvincias();
        getMoonlessPlanets();
        //getAverageRadius();
        //getPlanetRadiusMap();
        //getHeaviestPlanet();
        //getLightestPlanet();
        //getPlanetNameToMoonsMap();
    }

    static void countriesInfo() {
        Countries.WORLD_COUNTRIES.stream()
                .forEach(System.out::println);
    }

    static void worldCapitals() {

        var result = Countries.WORLD_COUNTRIES.stream()
                .map(Country::getCapital)
                .map(City::getName)
                .sorted()
                .toList();

        System.out.println(result);
    }

    static void printPlanets() {
        var planets = Planets.SOLAR_SYSTEM_PLANETS.stream()
                //.map(Planet::getName)
                .toList();

        planets.forEach(System.out::println);

    }

    static void getPlanetMoonsFlat() {
        Planets.SOLAR_SYSTEM_PLANETS.stream()
                .flatMap(planet -> planet.getSatellites().stream())
                .forEach(System.out::println);
    }

    static void getProvincias() {
        var provincias = Countries.SPAIN.getAutonomias().stream()
                .flatMap(autonomia -> autonomia.getProvincias().stream())
                //.map(Provincia::getName)
                .sorted(Comparator.comparing(Provincia::getName))
                .toList();

        System.out.println(provincias);
        provincias.forEach(System.out::println);
    }

    static void getMoonlessPlanets() {
        Set<Planet> moonlessPlanets = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getSatellites().isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        System.out.println(moonlessPlanets);
    }

    static void getAverageRadius() {

        var map = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .collect(Collectors.toMap(Planet::getName, Function.identity()));

        var map2 = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .collect(Collectors.toMap(Planet::getName, Planet::getRadiusInKm, (o, n) -> n, LinkedHashMap::new));

        System.out.println(map2);


        var radius = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .filter(planet -> planet.getRadiusInKm().isPresent())
                .collect(Collectors.averagingDouble(planet -> planet.getRadiusInKm().get()));

        System.out.println("Radio medio = " + radius);
    }

    static void getPlanetRadiusMap() {
        var map2 = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .sorted(Comparator.comparing(planet -> planet.getRadiusInKm().get()))
                .collect(Collectors.toMap(
                        Planet::getName, Planet::getRadiusInKm, (o, n) -> n, LinkedHashMap::new));

        System.out.println(map2);

        System.out.println(
                Arrays.toString(
                        map2.values().stream()
                                .filter(Optional::isPresent)
                                .mapToDouble(Optional::get)
                                .toArray()));

    }

    static void getHeaviestPlanet() {
        Optional<Planet> heaviestPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getMassInKg().isPresent())
                .max(Comparator.comparing(planet -> planet.getMassInKg().get()));

        heaviestPlanet.ifPresent(
                planet -> System.out.println(
                            "El planeta más pesado es " + planet.getName() +
                                    " con " + planet.getMassInKg().get() / 1000 + " Toneladas"));

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

}
