import es.rafapuig.OptionalComparator;
import model.geography.*;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsExamplesGeography {

    public static void main(String[] args) {
        printCountries();
        getAllEuropeanCountries();
        getAllEuropeanCountriesSorted();
        getAllAmericanCountriesSortedByName();
        getAllEuropeanCountriesSortedBySurface();

        getAllCountriesNamesSorted();
        getCountriesNamesByContinent();
        getAllCountriesWithKnownSurface();
        getAllCountriesWithUnknownSurface();
        getAllCountriesNotInEurope();
        getAllCountriesSorted();

        getMostPopulatedCountry();
        getLeastPopulatedCountry();
        getLeastAndMostPopulatedCountry();
        getLeastAndMostPopulatedCountryByContinent();
        getWorldPopulation();
        //getWorldPopulation2();

        getEuropePopulation();
        getPopulationByContinent();
        getPopulationByContinentSortedByPopulation();

        getFiveMostPopulatedCountries();
        getThreeMostPopulatedCountriesSum();
        getThreeMostPopulatedCountriesPercent();
        getTwoMostPopulatedCountriesPercentByContinent();
        getMostPopulatedCountryPercentByContinent();
        //getMostPopulatedCountryPercentByContinent2();
        getMostPopulatedCountryPercentByContinent3();
        getEuropeanPopulationPercentOfWorld();

        getAllCountriesMorePopulatedThanSpain();

        getFiveLargestCountries();
        getFiveDenserCountries();
        //getThreeLargestCountries2();

        getCountryNamesFromAsia();
        getCountryCapitalsFromAfrica();
        getCountryCapitalNamesFromEurope();

        getAllContinents();
        //getAllContinents2();
        //getAllContinents3();
        //getAllContinents4();
        //getAllContinentsNameStartsWithA();
        //getAllContinentsNameStartsWithAInSet();
        //getAllContinentsNameStartsWithAInEnumSet();
        //getAllContinentsNameStartsWithAInEnumSet2();
        //getAllRegionsInEurope();
        getRegionsByContinentMap();

        //getCountriesByISO3Map();
        //getEuropeanCountriesByISO3Map();
        //getEuropeanCountriesByISO3SortedMap();
        //getEuropeanCountriesByISO3SortedBySurfaceMap();
        //getEuropeanCountriesByISO3SortedByCapitalHabMap();
        //getEuropeanCapitalsByRegionMap();

        getLargestCountryByContinent();
        getMostPopulatedCountryByContinent();
        getMostPopulatedCountryByRegionSortedByContinent();
        getPopulationPercentByContinentSortedByPopulation();
        getThreeLargestCountriesByContinent();
        getThreeLargestCountriesByRegion();
        getThreeMostPopulatedCountriesByContinent();
        getThreeMostPopulatedCountriesByRegion();
        //getAverageCountrySurfaceByContinent();
        getFiveMostPopulatedCapitalWorld();
        getThreeMostPopulatedCapitalsByContinent();
        getThreeMostPopulatedCapitalsByRegion();

        getCountriesCountByContinent();
        getCountriesCountByContinentSortedByCount();

        getRegionsByContinentSortedByCount();

        //getContinentsListOfRegionsList();
    }

    record NameSurfaceTuple(String name, Optional<Area> surface) {
        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner(", ", "[", "]")
                    //.add(StringUtils.padRightToLength(name, 40))
                    .add("'" + name + "'");

            surface().ifPresent(surface -> joiner.add(surface().get().toString()));
            return joiner.toString();
        }
    }

    record NamePopulationTuple(String name, Long population) {
        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner(", ", "[", "]")
                    //.add(StringUtils.padRightToLength(name, 40))
                    .add("'" + name + "'");

            if (population != null) joiner.add(NumberFormat.getNumberInstance().format(population) + " habs");
            return joiner.toString();
        }
    }

    record NameDensityTuple(String name, Double density) {
        @Override
        public String toString() {
            StringJoiner joiner = new StringJoiner(", ", "[", "]")
                    //.add(StringUtils.padRightToLength(name, 40))
                    .add("'" + name + "'");

            if (density != null) joiner.add(NumberFormat.getNumberInstance().format(density) + " habs / km2");
            return joiner.toString();
        }
    }

    static void printCountries() {
        Countries.WORLD_COUNTRIES.forEach(System.out::println);
    }


    /**
     * Obtiene todos los paises de Europa
     */
    static void getAllEuropeanCountries() {

        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .toList();

        System.out.println("\nTodos los países de " + Continent.EUROPE + ":");
        System.out.println("---------------------------------------------");
        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getAllEuropeanCountriesSorted() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .sorted()
                .toList();

        System.out.println("\nTodos los países de " + Continent.EUROPE + " ordenados por orden natural:");
        System.out.println("---------------------------------------------------------------------------");
        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getAllAmericanCountriesSortedByName() {

        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.AMERICAS)
                .sorted(Comparator.comparing(Country::getName))
                //.sorted(Comparator.comparing(Country::getName, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        System.out.println("\nTodos los países de " + Continent.AMERICAS + " ordenados por nombre:");
        System.out.println("----------------------------------------------------------------------");
        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getAllEuropeanCountriesSortedBySurface() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .sorted(Comparator.comparing(
                        country -> country.getSurface().orElse(null), //keyExtractor
                        Comparator.nullsFirst(Comparator.reverseOrder())))  //keyComparator
                .toList();

        System.out.println("\nTodos los países de " + Continent.EUROPE + " ordenados por superficie:");
        System.out.println("----------------------------------------------------------------------");
        result.forEach(country -> System.out.println(
                new NameSurfaceTuple(country.getName(), country.getSurface())));
    }

    static void getAllCountriesNamesSorted() {
        List<String> result = Countries.WORLD_COUNTRIES.stream()
                .map(Country::getName)
                .sorted()
                //.sorted(Comparator.nullsLast(Comparator.naturalOrder()))
                .toList();

        System.out.println("\nTodos los nombres países ordenados:");
        System.out.println("-------------------------------------");
        result.forEach(System.out::println);
    }

    static void getCountriesNamesByContinent() {
        Map<Continent, List<String>> result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.mapping(
                                Country::getName,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> list.stream()
                                                .sorted()
                                                .toList()))));

        System.out.println("\nTodos los nombres países por continente:");
        System.out.println("------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getAllCountriesWithKnownSurface() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getSurface().isPresent())
                .toList();

        System.out.println("\nTodos los países con superficie conocida:");
        System.out.println("-------------------------------------------");
        result.forEach(country ->
                System.out.println(new NameSurfaceTuple(country.getName(), country.getSurface())));

    }

    static void getAllCountriesWithUnknownSurface() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getSurface().isEmpty())
                .toList();

        System.out.println("\nTodos los países con superficie desconocida:");
        System.out.println("----------------------------------------------");
        result.forEach(country ->
                System.out.println(new NameSurfaceTuple(country.getName(), country.getSurface())));

    }

    static void getAllCountriesNotInEurope() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(Predicate
                        .not(country -> country.getContinent() == Continent.EUROPE))
                .toList();

        System.out.println("\nTodos los países que no están en Europa:");
        System.out.println("-------------------------------------------");
        result.forEach(country -> System.out.println(country.getName() + " -> " + country.getContinent()));
    }

    static void getAllCountriesSorted() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .sorted()
                .toList();

        System.out.println("\nTodos los países ordenados por orden natural:");
        System.out.println("-----------------------------------------------");
        result.forEach(country -> System.out.println(country.getName()));
    }


    static void getMostPopulatedCountry() {
        Optional<Country> country = Countries.WORLD_COUNTRIES.stream()
                .max(Comparator.comparing(
                        Country::getPopulation,
                        Comparator.nullsFirst(Comparator.naturalOrder())));

        System.out.println("Pais más poblado = " +
                country.map(
                        c -> new NamePopulationTuple(
                                c.getName(),
                                c.getPopulation())).orElse(null));
    }

    static void getLeastPopulatedCountry() {
        Optional<Country> country = Countries.WORLD_COUNTRIES.stream()
                .min(Comparator.comparing(
                        Country::getPopulation,
                        Comparator.nullsLast(Comparator.naturalOrder())));

        System.out.println("Pais menos poblado = "
                + country.map(
                c -> new NamePopulationTuple(
                        c.getName(),
                        c.getPopulation())).orElse(null));
    }

    static void getLeastAndMostPopulatedCountry() {
        List<Optional<Country>> countries = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(Comparator.comparing(
                                Country::getPopulation,
                                Comparator.nullsLast(Comparator.naturalOrder()))),
                        Collectors.maxBy(Comparator.comparing(
                                Country::getPopulation,
                                Comparator.nullsFirst(Comparator.naturalOrder()))),
                        List::of));


        System.out.println("Pais menos poblado = "
                + countries.get(0).map(
                c -> new NamePopulationTuple(
                        c.getName(),
                        c.getPopulation())).orElse(null));

        System.out.println("Pais mas poblado = "
                + countries.get(1).map(
                c -> new NamePopulationTuple(
                        c.getName(),
                        c.getPopulation())).orElse(null));
    }

    static void getLeastAndMostPopulatedCountryByContinent() {
        Map<Continent, List<Optional<Country>>> countries = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.teeing(
                                Collectors.minBy(Comparator.comparing(
                                        Country::getPopulation,
                                        Comparator.nullsLast(Comparator.naturalOrder()))),
                                Collectors.maxBy(Comparator.comparing(
                                        Country::getPopulation,
                                        Comparator.nullsFirst(Comparator.naturalOrder()))),
                                List::of)));


        countries.forEach((key, value) -> {

            System.out.println("Pais menos poblado de " + key.getName() + " = "
                    + value.get(0).map(
                    c -> new NamePopulationTuple(
                            c.getName(),
                            c.getPopulation())).orElse(null));

            System.out.println("Pais mas poblado de " + key.getName() + " = "
                    + value.get(1).map(
                    c -> new NamePopulationTuple(
                            c.getName(),
                            c.getPopulation())).orElse(null));
        });

    }

    static void getWorldPopulation() {
        long population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .mapToLong(Country::getPopulation)
                .sum();

        System.out.println("Poblacion total mundial = "
                + NumberFormat.getNumberInstance().format(population) + " habs.");
    }

    static void getWorldPopulation2() {
        long population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .reduce(0L, (sum, country) -> sum + country.getPopulation(), Long::sum);

        System.out.println("Población total mundial = "
                + NumberFormat.getNumberInstance().format(population) + " habs.");
    }

    static void getEuropePopulation() {
        long population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .filter(country -> country.getPopulation() != null)
                .reduce(0L, (sum, country) -> sum + country.getPopulation(), Long::sum);

        System.out.println("Población total de " + Continent.EUROPE + " = " +
                NumberFormat.getNumberInstance().format(population) + " habs.");
    }

    static void getPopulationByContinent() {
        Map<Continent, Long> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(Country::getContinent,
                        Collectors.mapping(Country::getPopulation,
                                Collectors.mapping(Long::longValue,
                                        Collectors.reducing(0L, Long::sum)))));

        System.out.println("\nPoblación por continente:");
        System.out.println("---------------------------");
        result.forEach((key, value) -> System.out.println(new NamePopulationTuple(key.getName(), value)));
    }

    static void getPopulationByContinentSortedByPopulation() {
        Map<Continent, Long> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Country::getContinent,
                                Collectors.mapping(Country::getPopulation,
                                        Collectors.mapping(Long::longValue,
                                                Collectors.reducing(0L, Long::sum)))),
                        map -> map.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1, e2) -> e2,
                                        LinkedHashMap::new))));

        System.out.println("\nPoblación por continente ordenados por población:");
        System.out.println("---------------------------------------------------");
        result.forEach((key, value) -> System.out.println(new NamePopulationTuple(key.getName(), value)));
    }

    static void getPopulationPercentByContinentSortedByPopulation() {
        Map<Continent, Double> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.teeing(
                        Collectors.mapping(
                                Country::getPopulation,
                                Collectors.reducing(0L, Long::sum)),
                        Collectors.groupingBy(Country::getContinent,
                                Collectors.mapping(Country::getPopulation,
                                        Collectors.mapping(Long::longValue,
                                                Collectors.reducing(0L, Long::sum)))),
                        (total, map) -> map.entrySet().stream()
                                .sorted(Comparator
                                        .comparing(entry -> entry.getValue() / (double) total,
                                                Comparator.reverseOrder()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> entry.getValue() / (double) total,
                                        (e1, e2) -> e2,
                                        LinkedHashMap::new))));

        System.out.println("\nPorcentaje población por continente:");
        System.out.println("---------------------------------------");
        result.forEach((key, value) ->
                System.out.println("En " + key + " vive el = " +
                        NumberFormat.getNumberInstance().format(value * 100) + " %"));
    }


    static void getFiveMostPopulatedCountries() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .sorted(Comparator.comparing(
                        Country::getPopulation,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(5)
                .toList();

        System.out.println("\nLos 5 países más poblados del mundo:");
        System.out.println("-----------------------------------------");
        result.forEach(value -> System.out.println(
                new NamePopulationTuple(
                        value.getName(),
                        value.getPopulation())));
    }

    static void getThreeMostPopulatedCountriesSum() {
        long population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .sorted(Comparator.comparing(Country::getPopulation).reversed())
                .limit(3)
                .mapToLong(Country::getPopulation)
                .reduce(0L, Long::sum);

        System.out.println("\nPoblación total de los 3 países más poblados del mundo = "
                + NumberFormat.getNumberInstance().format(population) + " habs.");
    }

    static void getThreeMostPopulatedCountriesPercent() {
        var population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .sorted(Comparator.comparing(Country::getPopulation).reversed())
                .map(Country::getPopulation)
                .map(Long::longValue)
                .collect(Collectors.teeing(
                        Collectors.reducing(0L, Long::sum),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .limit(3)
                                        .reduce(0L, Long::sum)),
                        (total, sum3) -> sum3 / (double) total));


        System.out.println("\nPorcentaje de población de los 3 países más habitados = "
                + NumberFormat.getNumberInstance().format(population * 100) + " %");
    }

    static void getTwoMostPopulatedCountriesPercentByContinent() {
        Map<Continent, Double> population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                countryList -> countryList.stream()
                                        .sorted(Comparator.comparing(Country::getPopulation).reversed())
                                        .map(Country::getPopulation)
                                        .map(Long::longValue)
                                        .collect(Collectors.teeing(
                                                Collectors.reducing(0L, Long::sum),
                                                Collectors.collectingAndThen(
                                                        Collectors.toList(),
                                                        list -> list.stream()
                                                                .limit(2)
                                                                .reduce(0L, Long::sum)),
                                                (total, sum3) -> sum3 / (double) total)))));


        System.out.println("\nPorcentajes de población de los 2 países más poblados por continente:");
        System.out.println("-----------------------------------------------------------------------");
        population.forEach((key, value) -> System.out.println(
                key + " = " + NumberFormat.getNumberInstance().format(value * 100) + " %"));

    }

    static void getMostPopulatedCountryPercentByContinent() {
        Map<Continent, Double> population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                countryList -> countryList.stream()
                                        .sorted(Comparator.comparing(Country::getPopulation).reversed())
                                        .map(Country::getPopulation)
                                        .map(Long::longValue)
                                        .collect(Collectors.teeing(
                                                Collectors.reducing(0L, Long::sum),
                                                Collectors.collectingAndThen(
                                                        Collectors.toList(),
                                                        list -> list.stream()
                                                                .limit(1)
                                                                .reduce(0L, Long::sum)),
                                                (total, sum3) -> sum3 / (double) total)))));


        System.out.println("\nPorcentajes de población del pais más habitado por continente:");
        System.out.println("----------------------------------------------------------------");
        population.forEach((key, value) -> System.out.println(
                key + " = " + NumberFormat.getNumberInstance().format(value * 100) + " %"));

    }

    static void getMostPopulatedCountryPercentByContinent2() {
        Map<Continent, Double> population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                countryList -> countryList.stream()
                                        .sorted(Comparator.comparing(Country::getPopulation).reversed())
                                        .map(Country::getPopulation)
                                        .map(Long::longValue)
                                        .collect(Collectors.teeing(
                                                Collectors.reducing(0L, Long::sum),
                                                Collectors.collectingAndThen(
                                                        Collectors.toList(),
                                                        list -> list.get(0)),
                                                (total, sum3) -> sum3 / (double) total)))));

        System.out.println("\nPorcentajes de población del pais más habitado por continente:");
        System.out.println("----------------------------------------------------------------");
        population.forEach((key, value) -> System.out.println(
                "Porcentaje de población del pais más habitado de " + key + " = "
                        + NumberFormat.getNumberInstance().format(value * 100) + " %"));

    }

    static void getMostPopulatedCountryPercentByContinent3() {

        record CountryDoubleTuple(Country country, double percent) {
        }

        Map<Continent, CountryDoubleTuple> population = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                countryList -> countryList.stream()
                                        .sorted(Comparator.comparing(Country::getPopulation).reversed())
                                        //.map(Country::getPopulation)
                                        //.map(Long::longValue)
                                        .collect(Collectors.teeing(
                                                Collectors.mapping(Country::getPopulation,
                                                        Collectors.reducing(0L, Long::sum)),
                                                Collectors.collectingAndThen(
                                                        Collectors.<Country>toList(),
                                                        list -> list.get(0)),
                                                (total, country) ->
                                                        new CountryDoubleTuple(
                                                                country,
                                                                country.getPopulation() / (double) total))))));


        System.out.println("\nPorcentajes de población del pais más habitado por continente:");
        System.out.println("----------------------------------------------------------------");
        population.forEach((key, value) -> System.out.println("En (" +
                value.country().getName() + ") vive de " + key + " el = "
                + NumberFormat.getNumberInstance().format(value.percent() * 100)
                + " %"));

    }

    static void getEuropeanPopulationPercentOfWorld() {
        var percent = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.teeing(
                        Collectors.summingLong(Country::getPopulation),
                        Collectors.filtering(
                                country -> country.getContinent() == Continent.EUROPE,
                                Collectors.summingLong(Country::getPopulation)),
                        (total, european) -> european / (double) total));

        System.out.println("\nPorcentaje de habitantes en " +
                Continent.EUROPE + " = " +
                NumberFormat.getNumberInstance().format(percent * 100) + "%");
    }

    static void getAllCountriesMorePopulatedThanSpain() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .sorted(Comparator.comparing(Country::getPopulation))
                .dropWhile(country -> country.getPopulation() <= Countries.SPAIN.getPopulation())
                .toList();

        System.out.println("\nPaíses con mayor población que :" + Countries.SPAIN.getName());
        System.out.println("----------------------------------------------------");
        result.stream()
                .map(c -> new NamePopulationTuple(c.getName(), c.getPopulation()))
                .forEach(System.out::println);
    }

    static void getFiveLargestCountries() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getSurface().isPresent())
                .sorted(Comparator.<Country, Integer>comparing(
                        country -> country.getSurface().get().value()
                ).reversed())
                .limit(5)
                .toList();

        System.out.println("\nLos 5 países con mayor superficie del mundo:");
        System.out.println("----------------------------------------------");
        result.forEach(country -> System.out.println(
                new NameSurfaceTuple(
                        country.getName(),
                        country.getSurface())));
    }

    static void getFiveDenserCountries() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getDensity().isPresent())
                .sorted(Comparator.<Country, Double>comparing(
                        country -> country.getDensity().get()
                ).reversed())
                .limit(5)
                .toList();

        System.out.println("\nLos 5 países con mayor densidad de población del mundo:");
        System.out.println("---------------------------------------------------------");
        result.forEach(country -> System.out.println(
                new NameDensityTuple(
                        country.getName(),
                        country.getDensity().get())));
    }

    static void getThreeLargestCountries2() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                //.filter(country -> country.getSurfaceArea().isPresent())
                .sorted(OptionalComparator
                        .comparingEmptyFirst(Country::getSurface).reversed())
                .limit(3)
                .toList();

        System.out.println("\nLos 3 países con mayor superficie del mundo:");
        System.out.println("----------------------------------------------");
        result.forEach(country -> System.out.println(
                new NameSurfaceTuple(
                        country.getName(),
                        country.getSurface())));
    }

    static void getCountryNamesFromAsia() {
        List<String> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.ASIA)
                .map(Country::getName)
                .toList();

        System.out.println("\nPaíses de " + Continent.ASIA);
        System.out.println("-------------------------------");
        System.out.println(result);
    }

    static void getCountryCapitalsFromAfrica() {
        List<City> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.AFRICA)
                .map(Country::getCapital)
                .toList();

        System.out.println("\nCapitales de países de " + Continent.AFRICA);
        System.out.println("--------------------------------------------");
        System.out.println(result);
    }

    static void getCountryCapitalNamesFromEurope() {
        List<String> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .map(Country::getCapital)
                .map(City::getName)
                .sorted()
                .toList();

        System.out.println("\nNombres de capitales de países de " + Continent.EUROPE);
        System.out.println("-------------------------------------------------------");
        System.out.println(result);
    }

    static void getAllContinents() {
        List<Continent> result = Arrays.stream(Continent.values()).toList();

        System.out.println("\nContinentes: ");
        System.out.println("---------------");
        System.out.println(result);
    }

    static void getAllContinents2() {
        EnumSet<Continent> result = EnumSet.allOf(Continent.class);
        System.out.println(result);
    }

    static void getAllContinents3() {
        EnumSet<Continent> result = Stream.of(Continent.values())
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Continent.class)));
        System.out.println(result);
    }

    static void getAllContinents4() {
        EnumSet<Continent> result = EnumSet.of(Continent.EUROPE, Continent.values());
        System.out.println(result);
    }


    static void getAllContinentsNameStartsWithA() {
        List<Continent> result = EnumSet.allOf(Continent.class).stream()
                .filter(continent -> continent.getName().toLowerCase().startsWith("a"))
                .toList();

        System.out.println(result);
    }

    static void getAllContinentsNameStartsWithAInSet() {
        Set<Continent> result = EnumSet.allOf(Continent.class).stream()
                .filter(continent -> continent.getName().toLowerCase().startsWith("a"))
                .collect(Collectors.toSet());

        System.out.println(result);
    }

    static void getAllContinentsNameStartsWithAInSet2() {
        EnumSet<Continent> result = EnumSet.allOf(Continent.class).stream()
                .filter(continent -> continent.getName().toLowerCase().startsWith("a"))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Continent.class)));

        System.out.println(result);
    }

    static void getAllContinentsNameStartsWithAInEnumSet() {
        EnumSet<Continent> result = EnumSet.allOf(Continent.class).stream()
                .filter(continent -> continent.getName().toLowerCase().startsWith("a"))
                .collect(
                        () -> EnumSet.noneOf(Continent.class),
                        EnumSet::add,
                        EnumSet::addAll);

        System.out.println(result);
    }

    static void getAllContinentsNameStartsWithAInEnumSet2() {
        EnumSet<Continent> result = EnumSet.allOf(Continent.class).stream()
                .filter(continent -> continent.getName().toLowerCase().startsWith("a"))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Continent.class)));

        System.out.println(result);
    }

    static void getAllRegionsInEurope() {
        List<Region> result = EnumSet.allOf(Region.class).stream()
                .filter(region -> region.getContinent() == Continent.EUROPE)
                .toList();

        System.out.println(result);
    }

    //Una tonteria
    static void getAllRegionsInEurope2() {
        List<Region> result = Stream.of(Continent.EUROPE)
                .map(continent -> EnumSet.allOf(Region.class))
                .flatMap(EnumSet::stream)
                .filter(region -> region.getContinent() == Continent.EUROPE)
                .toList();

        System.out.println(result);
    }

    //Regiones agrupadas por continente
    static void getRegionsByContinentMap() {
        Map<Continent, List<Region>> result = EnumSet.allOf(Region.class).stream()
                .collect(Collectors.groupingBy(Region::getContinent));

        System.out.println("\nRegiones agrupadas por continente ");
        System.out.println("------------------------------------");
        result.entrySet().forEach(System.out::println);
    }


    static void getCountriesByISO3Map() {
        Map<String, Country> result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.toMap(Country::getIso3, Function.identity()));

        result.entrySet().forEach(System.out::println);
    }

    static void getEuropeanCountriesByISO3Map() {
        Map<String, Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .collect(Collectors.toMap(Country::getIso3, Function.identity()));

        result.entrySet().forEach(System.out::println);
    }

    static void getEuropeanCountriesByISO3SortedMap() {
        Map<String, Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .collect(Collectors.toMap(
                        Country::getIso3,
                        Function.identity(),
                        (country, country2) -> country2,
                        TreeMap::new));

        result.entrySet().forEach(System.out::println);
    }

    static void getEuropeanCountriesByISO3SortedBySurfaceMap() {
        Map<String, Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .sorted(OptionalComparator.comparingEmptyLast(Country::getSurface))
                .collect(Collectors.toMap(
                        Country::getIso3,
                        Function.identity(),
                        (country, country2) -> country2,
                        LinkedHashMap::new));

        result.entrySet().forEach(System.out::println);
    }

    static void getEuropeanCountriesByISO3SortedByCapitalHabMap() {
        Map<String, Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .sorted(OptionalComparator.comparingEmptyLast(country -> country.getCapital().getPopulation()))
                .collect(Collectors.toMap(
                        Country::getIso3,
                        Function.identity(),
                        (country, country2) -> country2,
                        LinkedHashMap::new));

        result.entrySet().forEach(System.out::println);
    }

    static void getEuropeanCapitalsByRegionMap() {
        Map<Region, List<City>> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .collect(Collectors.groupingBy(Country::getRegion,
                        Collectors.mapping(Country::getCapital,
                                Collectors.toList())));

        result.entrySet().forEach(System.out::println);
    }

    static void getLargestCountryByContinent() {
        Map<Continent, Optional<Country>> result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.maxBy(OptionalComparator.comparingEmptyFirst(Country::getSurface))));

        System.out.println("\nPais más grande de cada continente:");
        System.out.println("-------------------------------------");
        result.forEach((key, value) ->
                System.out.println(key.getName() + " = " +
                        (value.isPresent() ?
                                new NameSurfaceTuple(value.get().getName(), value.get().getSurface())
                                : "(Vacío)")));
    }

    static void getMostPopulatedCountryByContinent() {
        Map<Continent, Optional<Country>> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(Country::getContinent,
                        Collectors.maxBy(Comparator.comparing(Country::getPopulation))));

        System.out.println("\nPais más poblado de cada continente:");
        System.out.println("--------------------------------------");
        result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new NamePopulationTuple(
                                entry.getValue().get().getName(),
                                entry.getValue().get().getPopulation())))
                .entrySet().forEach(System.out::println);
    }

    static void getMostPopulatedCountryByRegionSortedByContinent() {
        Map<Region, Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Country::getRegion,
                                Collectors.maxBy(Comparator.comparing(Country::getPopulation))),
                        map -> map.entrySet().stream()
                                .filter(entry -> entry.getValue().isPresent())
                                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Region::getContinent)))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> entry.getValue().orElse(null),
                                        (e1, e2) -> e2,
                                        LinkedHashMap::new))));

        //result.entrySet().forEach(System.out::println);

        System.out.println("\nPais más poblado de cada region (ordenados por continente):");
        System.out.println("-------------------------------------------------------------");
        result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new NamePopulationTuple(
                                entry.getValue().getName(),
                                entry.getValue().getPopulation()),
                        (e1, e2) -> e2,
                        LinkedHashMap::new))
                .entrySet().forEach(System.out::println);
    }

    static void getThreeLargestCountriesByContinent() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(OptionalComparator
                                                .comparingEmptyFirst(Country::getSurface)
                                                .reversed())
                                        .limit(3)
                                        .map(country -> new NameSurfaceTuple(
                                                country.getName(),
                                                country.getSurface()))
                                        .toList())));

        System.out.println("\nLos 3 países más grandes de cada continente:");
        System.out.println("----------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getThreeLargestCountriesByRegion() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(OptionalComparator
                                                .comparingEmptyFirst(Country::getSurface)
                                                .reversed())
                                        .limit(3)
                                        .map(country -> new NameSurfaceTuple(
                                                country.getName(),
                                                country.getSurface()))
                                        .toList())));

        System.out.println("\nLos 3 países más grandes de cada region:");
        System.out.println("------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getThreeMostPopulatedCountriesByContinent() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator
                                                .comparing(Country::getPopulation)
                                                .reversed())
                                        .limit(3)
                                        .map(country -> new NamePopulationTuple(
                                                country.getName(),
                                                country.getPopulation()))
                                        .toList())));

        System.out.println("\nLos 3 países más poblados de cada continente:");
        System.out.println("-----------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getThreeMostPopulatedCountriesByRegion() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getPopulation() != null)
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator
                                                .comparing(Country::getPopulation)
                                                .reversed())
                                        .limit(3)
                                        .map(country -> new NamePopulationTuple(
                                                country.getName(),
                                                country.getPopulation()))
                                        .toList())));

        System.out.println("\nLos 3 países más poblados de cada región:");
        System.out.println("-------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getAverageCountrySurfaceByContinent() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getSurface().isPresent())
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.averagingInt(country -> country.getSurface().get().value()),
                                v -> new Area(v.intValue(), SIPrefix.KILO))
                ));

        System.out.println(result);
    }

    static void getFiveMostPopulatedCapitalWorld() {

        List<City> result = Countries.WORLD_COUNTRIES.stream()
                .sorted(OptionalComparator
                        .<Country, Long>comparingEmptyFirst(country -> country.getCapital().getPopulation())
                        .reversed())
                .limit(5)
                .map(Country::getCapital)
                .toList();

        System.out.println("\nLas 5 ciudades más pobladas del mundo:");
        System.out.println("----------------------------------------");
        result.forEach(city -> System.out.println(
                new NamePopulationTuple(city.getName(), city.getPopulation().orElse(null))
        ));

    }

    static void getThreeMostPopulatedCapitalsByContinent() {

        var result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(
                                                OptionalComparator
                                                        .comparingEmptyLast(
                                                                country -> country.getCapital().getPopulation(),
                                                                Comparator.<Long>reverseOrder())
                                        )
                                        .limit(3)
                                        .map(Country::getCapital)
                                        .toList())));

        System.out.println("\nLas 3 ciudades más pobladas por continente:");
        System.out.println("---------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getThreeMostPopulatedCapitalsByRegion() {

        var result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getRegion,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(
                                                OptionalComparator
                                                        .comparingEmptyLast(
                                                                country -> country.getCapital().getPopulation(),
                                                                Comparator.<Long>reverseOrder())
                                        )
                                        .limit(3)
                                        .map(Country::getCapital)
                                        .toList())));

        System.out.println("\nLas 3 ciudades más pobladas por región:");
        System.out.println("------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getCountriesCountByContinent() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.groupingBy(
                        Country::getContinent,
                        Collectors.counting()));

        System.out.println("\nCuenta de países por cada continente:");
        System.out.println("---------------------------------------");
        System.out.println(result);
    }

    static void getCountriesCountByContinentSortedByCount() {
        var result = Countries.WORLD_COUNTRIES.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Country::getContinent,
                                Collectors.counting()),
                        map -> map.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1, e2) -> e2,
                                        LinkedHashMap::new))));

        System.out.println("\nCuenta de países por cada continente (ordenados por cantidad):");
        System.out.println("----------------------------------------------------------------");
        System.out.println(result);
    }

    static void getRegionsByContinentSortedByCount() {

        //record

        var result = Arrays.stream(Region.values())
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Region::getContinent),
                        map -> map.entrySet().stream()
                                .sorted(Map.Entry
                                        .comparingByValue(Comparator.comparing(List::size)))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> List.of(entry.getValue().size(), entry.getValue()),
                                        (e1, e2) -> e2,
                                        LinkedHashMap::new))));

        System.out.println("\nCuenta de regiones por cada continente (ordenados por cantidad):");
        System.out.println("------------------------------------------------------------------");
        result.entrySet().forEach(System.out::println);
    }

    static void getContinentsListOfRegionsList() {
        record RegionsPerContinent(Continent continent, List<Region> regions) {
        }

        var result = EnumSet.allOf(Region.class).stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Region::getContinent),
                        map -> map.entrySet().stream()
                                .map(e -> new RegionsPerContinent(e.getKey(), e.getValue()))
                                .toList()));

        System.out.println(result);
    }


}
