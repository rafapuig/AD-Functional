import model.astronomy.Planets;
import model.astronomy.Satellite;
import model.geography.City;
import model.geography.Countries;
import model.geography.Country;

public class StreamsExamples {

    public static void main(String[] args) {
        countriesInfo();
        worldCapitals();
        getPlanetMoonsFlat();
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

    static void getPlanetMoonsFlat() {
        Planets.SOLAR_SYSTEM_PLANETS.stream()
                .flatMap(planet -> planet.getSatellites().stream())
                .forEach(System.out::println);
    }

}
