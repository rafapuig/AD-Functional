import es.rafapuig.OptionalComparator;
import model.geography.Continent;
import model.geography.Countries;
import model.geography.Country;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StreamExamplesGeography {

    public static void main(String[] args) {
        //getAllEuropeanCountries();
        //getAllAmericanCountriesSorted();
        //getAllCountriesNames();
        //getAllCountriesWithKnownSurface();
        //getAllCountriesNotInEurope();
        //getAllCountriesSorted();
        getThreeLargestCountries();
    }

    static void getAllEuropeanCountries() {

        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.EUROPE)
                .toList();

        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getAllAmericanCountriesSorted() {

        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getContinent() == Continent.AMERICAS)
                .sorted(Comparator.comparing(Country::getName))
                .toList();

        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getAllCountriesNames() {
        List<String> result = Countries.WORLD_COUNTRIES.stream()
                .map(Country::getName)
                .sorted()
                //.sorted(Comparator.nullsLast(Comparator.naturalOrder()))
                .toList();

        result.forEach(System.out::println);
    }

    static void getAllCountriesWithKnownSurface() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getSurfaceArea().isPresent())
                .toList();

        result.forEach(country ->
                System.out.println(
                        country.getName() + " " + country.getSurfaceArea().get()));
    }

    static void getAllCountriesNotInEurope() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(Predicate
                        .not(country -> country.getContinent() == Continent.EUROPE))
                .toList();

        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getAllCountriesSorted() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .sorted()
                .toList();

        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getThreeLargestCountries() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                .filter(country -> country.getSurfaceArea().isPresent())
                .sorted(Comparator.<Country, Integer>comparing(
                        country -> country.getSurfaceArea().get()
                ).reversed())
                .limit(3)
                .toList();

        result.forEach(country -> System.out.println(country.getName()));
    }

    static void getThreeLargestCountries2() {
        List<Country> result = Countries.WORLD_COUNTRIES.stream()
                //.filter(country -> country.getSurfaceArea().isPresent())
                .sorted(OptionalComparator
                        .comparingEmptyFirst(Country::getSurfaceArea).reversed())
                .limit(3)
                .toList();

        result.forEach(country -> System.out.println(country.getName()));
    }




}
