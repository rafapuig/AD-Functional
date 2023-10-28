import es.rafapuig.OptionalComparator;
import model.astronomy.Planet;
import model.astronomy.PlanetType;
import model.astronomy.Planets;
import model.astronomy.Satellite;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsExamplesPlanets {
    public static void main(String[] args) {
        //printPlanets();
        //getPlanetMoonsFlat();
        //getMoonlessPlanets();
        //getMapPlanetsByName();
        //getMapPlanetRadiusByName();

        //getPlanetsSortedByMass();
        //getPlanetsSortedByMass2();

        //getAverageRadius();
        //getPlanetRadiusMap();

        //getHeaviestPlanet();
        getHeaviestPlanet2();

        //getLightestPlanet();
        getLightestPlanet3();

        //getPlanetNameToMoonsMap();

        //getPlanetMoonsCount();

        //getExistAnyDwarfPlanetWithSatellites();
        //getAnyDwarfPlanetWithSatellites();

        //getFirstPlanetBiggerThanEarth();

        getPlanetNamesToDiameterMap();

        getPlanetsByTypeMap();
        getLightestPlanetsByTypeMap();
        getLightestPlanetsByTypeMap2();

        getSatellitesByPlanetType();

        getPlanetNamesOrbitalPeriodMoreThanAYear();
        getSatellitesOfPlanetsOrbitalPeriodLessOEqualThanAYear();

        //getPlanetsSortByPeriodoOrbital();

        //getPlanetsPartitionByOrbitalPeriodMoreThan1();
        //getPlanetNamesPartitionByOrbitalPeriodMoreThan1();

        //getPlanetNamesPartitionByOrbitalPeriodMoreThan1Finished();

    }

    static <T, U extends Comparable<U>> Comparator<T> getOptionalComparator(
            Function<? super T, Optional<U>> keyExtractor) {

        return Comparator.comparing(keyExtractor,
                Comparator.comparing(opt -> opt.orElse(null),
                        Comparator.nullsFirst(Comparator.naturalOrder())));
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


    static void getPlanetsSortedByMass() {
        List<Planet> planets = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .sorted(Comparator.comparing(
                        planet -> planet.getMassInKg().orElse(null),
                        Comparator.nullsFirst(
                                Comparator.naturalOrder())))
                .toList();

        planets.forEach(System.out::println);
    }

    static void getPlanetsSortedByMass1() {
        List<Planet> planets = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .sorted(getOptionalComparator(Planet::getMassInKg))
                .toList();

        planets.forEach(System.out::println);
    }


    static void getPlanetsSortedByMass2() {
        Set<Planet> planets = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .sorted(OptionalComparator.comparingEmptyLast(Planet::getMassInKg))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        planets.forEach(System.out::println);
    }

    static void getPlanetsSortedByMass3() {
        Set<Planet> planets = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(OptionalComparator
                                .comparingEmptyFirst(Planet::getMassInKg))));

        planets.forEach(System.out::println);
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

    static void getHeaviestPlanet2() {
        Optional<Planet> heaviestPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                //.filter(planet -> planet.getMassInKg().isPresent())
                .max(Comparator.comparing(
                        planet -> planet.getMassInKg().orElse(null),
                        Comparator.nullsFirst(Comparator.naturalOrder())));

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

    static void getLightestPlanet2() {
        Optional<Planet> lightestPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                //.filter(planet -> planet.getMassInKg().isPresent())
                .collect(Collectors.minBy(
                        Comparator.comparing(
                                planet -> planet.getMassInKg().orElse(null),
                                Comparator.nullsLast(Comparator.naturalOrder()))));


        lightestPlanet.ifPresent(
                planet -> System.out.println(
                        "El planeta más ligero es " + planet.getName() +
                                " con " + planet.getMassInKg().get() / 1000 + " Toneladas"));

    }

    static void getLightestPlanet3() {
        Optional<Planet> lightestPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                //.filter(planet -> planet.getPlanetType() != PlanetType.DWARF)
                .filter(planet -> planet.getMassInKg().isPresent())
                .collect(Collectors.minBy(
                        Comparator.comparingDouble(
                                planet -> planet.getMassInKg().orElse(null).doubleValue())));


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
                                        .collect(Collectors.joining(", ", "'", "'")),
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


    // Encontrar si existe algún planeta enano con satellites
    static void getExistAnyDwarfPlanetWithSatellites() {

        Predicate<Planet> dwarf = planet -> planet.getType() == PlanetType.DWARF;
        Predicate<Planet> withSatellites = planet -> !planet.getSatellites().isEmpty();
        Predicate<Planet> dwarfWithSatellites = dwarf.and(withSatellites);

        boolean exist = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .anyMatch(dwarfWithSatellites);

        System.out.println("Existe algun planeta enano con satelites ? " + exist);
    }

    static void getAnyDwarfPlanetWithSatellites() {

        Predicate<Planet> dwarf = planet -> planet.getType() == PlanetType.DWARF;
        Predicate<Planet> withSatellites = planet -> !planet.getSatellites().isEmpty();
        Predicate<Planet> dwarftWithSatellites = dwarf.and(withSatellites);

        Optional<Planet> anyPlanet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(dwarf)
                .filter(withSatellites)
                .findAny();

        System.out.println("Un planeta enano con satelites = "
                + anyPlanet.map(Planet::getName).orElse("Ninguno."));

        System.out.println("Un planeta enano con satelites = "
                + (anyPlanet.map(planet -> planet.getName()
                + " -> satelites "
                + planet.getSatellites()).orElse("Ninguno.")));

        System.out.println("Un planeta enano con satelites = "
                + (anyPlanet.isPresent() ? anyPlanet.get().getName()
                + " -> satelites "
                + anyPlanet.get().getSatellites() : "Ninguno."));
    }


    static void getFirstPlanetHeavierThanEarth() {

        double earthMass = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getName().equals("La Tierra"))
                .findAny()
                .flatMap(Planet::getMassInKg)
                .orElse(0.0);


        Comparator<Planet> massInKgComparator =
                Comparator.comparing(
                        Planet::getMassInKg,
                        Comparator.comparing(opt -> opt.orElse(null),
                                Comparator.nullsFirst(Comparator.naturalOrder()))
                );


        Optional<Planet> planet = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(p -> p.getMassInKg().isPresent())
                .sorted(massInKgComparator)
                .sorted(Comparator.comparing(Planet::getMassInKg, OptionalComparator.emptyFirst()))
                .sorted(OptionalComparator.comparingEmptyFirst(Planet::getMassInKg))
                .peek(System.out::println)
                .filter(p -> p.getMassInKg().get() > earthMass)
                .findFirst();

        System.out.println("El siguiente planeta, mayor que La Tierra es: "
                + (planet.isPresent() ? planet.get().getName() : "Ninguno."));
    }

    static void getPlanetNamesToDiameterMap() {

        Map<String, Optional<Double>> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Planet::getName,
                                //planet -> planet.getRadiusInKm().map(r -> r * 2),
                                Planet::getRadiusInKm,
                                (o, n) -> n,
                                LinkedHashMap::new),
                        result -> {
                            System.out.println(result);
                            result.keySet().forEach(
                                    key -> result.computeIfPresent(
                                            key,
                                            (k, v) -> v.map(x -> x * 2)));
                            return result;
                        }));

        System.out.println(map);
    }

    static void getPlanetsByTypeMap() {
        Map<PlanetType, List<Planet>> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.groupingBy(
                        Planet::getType,
                        Collectors.toList()));

        map.entrySet().forEach(System.out::println);
    }

    static void getLightestPlanetsByTypeMap() {
        Map<PlanetType, Optional<Planet>> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.groupingBy(
                        Planet::getType,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                result -> result
                                        .stream()
                                        .max(getOptionalComparator(Planet::getMassInKg)))));

        map.entrySet().forEach(System.out::println);
    }

    static void getLightestPlanetsByTypeMap2() {

        var c = OptionalComparator.comparingEmptyLast(Planet::getMassInKg);

        Map<PlanetType, Planet> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.toMap(
                        Planet::getType,
                        Function.identity(),
                        (Planet h, Planet o) -> Stream.of(h, o).max(
                                OptionalComparator.comparingEmptyFirst(Planet::getMassInKg)
                        ).orElse(null)));

        map.entrySet().forEach(System.out::println);
    }

    static void getSatellitesByPlanetType() {

        Map<PlanetType, List<Satellite>> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.groupingBy(
                        Planet::getType,
                        Collectors.flatMapping(
                                planet -> planet.getSatellites().stream(),
                                Collectors.toList()
                        )
                ));

        map.entrySet().forEach(System.out::println);

    }

    static void getSatellitesByPlanetTypeLightersThanUranus() {

        Map<PlanetType, List<Satellite>> map = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.groupingBy(
                        Planet::getType,
                        Collectors.filtering(
                                planet -> planet.getMassInKg().orElse(Double.POSITIVE_INFINITY)
                                        < Planets.URANUS.getMassInKg().orElse(0.0),
                                Collectors.flatMapping(
                                        planet -> planet.getSatellites().stream(),
                                        Collectors.toList()
                                )
                        )));

        map.entrySet().forEach(System.out::println);

    }



    static void getPlanetNamesOrbitalPeriodMoreThanAYear() {
        List<String> planetNames = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .filter(planet -> planet.getOrbitalPeriod() > 1.0)
                .map(Planet::getName)
                .toList();

        System.out.println(planetNames);
    }

    static void getSatellitesOfPlanetsOrbitalPeriodLessOEqualThanAYear() {
        List<Satellite> satellites = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .filter(planet -> planet.getOrbitalPeriod() <= 1.0)
                .flatMap(planet -> planet.getSatellites().stream())
                .toList();

        System.out.println(satellites);
    }


    static void getPlanetsSortByPeriodoOrbital() {

        List<Planet> planets = Planets.SOLAR_SYSTEM_PLANETS.stream()
                .sorted(Comparator.comparing(
                        Planet::getOrbitalPeriod,
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();

        planets.forEach(System.out::println);

    }

    static void getPlanetsPartitionByOrbitalPeriodMoreThan1() {

        Map<Boolean, List<Planet>> planetsMap = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.partitioningBy(
                        planet -> planet.getOrbitalPeriod() <= 1.0));

        planetsMap.entrySet().forEach(System.out::println);

    }

    static void getPlanetNamesPartitionByOrbitalPeriodMoreThan1() {

        Map<Boolean, List<String>> planetsMap = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .collect(Collectors.partitioningBy(
                        planet -> planet.getOrbitalPeriod() <= 1.0,
                        Collectors.mapping(Planet::getName, Collectors.toList())));


        planetsMap.entrySet().forEach(System.out::println);

    }

    //Obtener los nombres de los planetas que no sean enanos particionados por periodo orbital <= 1
    // Los que cumplen su clave será el String Interiores y los que no Exteriores
    static void getPlanetNamesPartitionByOrbitalPeriodMoreThan1Finished() {

        Map<String, List<String>> planetsMap = Planets.SOLAR_SYSTEM_PLANETS
                .stream()
                .filter(planet -> planet.getType() != PlanetType.DWARF)
                .collect(Collectors.collectingAndThen(
                        Collectors.partitioningBy(
                                planet -> planet.getOrbitalPeriod() <= 1.0,
                                Collectors.mapping(Planet::getName, Collectors.toList())),
                        result -> result.entrySet()
                                .stream().collect(Collectors.
                                        toMap(e -> e.getKey() ? "Interiores" : "Exteriores",
                                                Map.Entry::getValue))));


        planetsMap.entrySet().forEach(System.out::println);
    }


}
