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
        //printPlanets();
        //getPlanetMoonsFlat();
        //getProvincias();
        //getAverageRadius();
        getPlanetRadiusMap();
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

}
