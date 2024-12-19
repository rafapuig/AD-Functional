import model.astronomy.*;

import java.util.*;
import java.util.stream.Collectors;

public class StreamsExamplesAstronomy {
    public static void main(String[] args) {
        getAllBodies();
        getAllStars();
        getAllStars2();
        getAllPlanets();
        getAllSatellites();
        getAllPlanetsAndStars();
        getSatelliteToPlanetMap();
        getSatelliteToPlanetNameMap();
        getCelestialBodiesCountByClass();
        getCelestialBodiesCountByClassMap();
        getCelestialBodiesAndCountByClass();
    }

    static void getAllBodies() {
        List<CelestialBody> bodies = Sky.BODIES.stream().toList();

        bodies.forEach(System.out::println);
    }

    static void getAllStars() {
        List<Star> stars = Sky.BODIES.stream()
                .filter(celestialBody -> celestialBody instanceof Star)
                .map(celestialBody -> (Star) celestialBody)
                .toList();

        stars.forEach(System.out::println);
    }

    static void getAllStars2() {
        List<Star> stars = Sky.BODIES.stream()
                .filter(Star.class::isInstance) //Comprobar si es es una subclase Star
                .map(Star.class::cast)
                .toList();

        stars.forEach(System.out::println);
    }

    static void getAllPlanets() {
        List<Planet> planets = Sky.BODIES.stream()
                .filter(Planet.class::isInstance)
                .map(Planet.class::cast)
                .toList();

        planets.forEach(System.out::println);
    }

    static void getAllSatellites() {
        List<Satellite> planets = Sky.BODIES.stream()
                .filter(Satellite.class::isInstance)
                .map(Satellite.class::cast)
                .toList();

        planets.forEach(System.out::println);
    }

    static void getAllPlanetsAndStars() {
        List<CelestialBody> bodies = Sky.BODIES.stream()
                .collect(Collectors.teeing(
                        Collectors.filtering(Star.class::isInstance, Collectors.toList()),
                        Collectors.filtering(Planet.class::isInstance, Collectors.toList()),
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }));

        bodies.forEach(System.out::println);
    }

    static void getSatelliteToPlanetMap() {
        Map<Satellite, Planet> map = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .flatMap(planet -> planet.getSatellites()
                        .stream()
                        .map(satellite -> Map.entry(satellite, planet)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (o, n) -> n,
                        LinkedHashMap::new));

        map.entrySet().forEach(System.out::println);

    }

    static void getSatelliteToPlanetNameMap() {
        Map<Satellite, String> map = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .flatMap(planet -> planet.getSatellites()
                        .stream()
                        .map(satellite -> Map.entry(satellite, planet.getName())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (o, n) -> n,
                        TreeMap::new)); //CelestialBody implementa Comparable mediante el nombre

        map.entrySet().forEach(System.out::println);
    }

    static void getCelestialBodiesCountByClass() {
        Map<Class<?>, Long> map = Sky.BODIES.stream()
                .collect(Collectors.groupingBy(
                        CelestialBody::getClass,
                        Collectors.counting()));

        map.entrySet().forEach(System.out::println);
    }

    static void getCelestialBodiesCountByClassMap() {
        Map<String, Long> map = Sky.BODIES.stream()
                .collect(Collectors.toMap(
                        celestialBody -> celestialBody.getClass().getSimpleName(),
                        celestialBody -> 1L,
                        (oldCount, newCount) -> oldCount + 1L));

        map.entrySet().forEach(System.out::println);
    }

    static void getCelestialBodiesAndCountByClass() {

        record CountList(Long count, List<String> list) {}

        //Map<Integer, List<String>> valuesMap = new HashMap<>();
        //valuesMap.put("count", count);
        //valuesMap.put("list", list);
        Map<String, CountList> map = Sky.BODIES.stream()
                .collect(Collectors.groupingBy(
                        celestialBody -> celestialBody.getClass().getSimpleName(),
                        Collectors.teeing(
                                Collectors.counting(),
                                Collectors.mapping(
                                        CelestialBody::getName,
                                        Collectors.toList()),
                                CountList::new
                        )));

        map.entrySet().forEach(System.out::println);
    }
}
