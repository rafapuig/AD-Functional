import model.astronomy.Planet;
import model.astronomy.Planets;
import model.astronomy.Satellite;
import model.geography.City;
import model.geography.Countries;
import model.geography.Country;
import model.geography.Provincia;

import java.util.Comparator;

public class StreamsExamples {

    public static void main(String[] args) {
        countriesInfo();
        worldCapitals();
        printPlanets();
        getPlanetMoonsFlat();
        getProvincias();
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

}
