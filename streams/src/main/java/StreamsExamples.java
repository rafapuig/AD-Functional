import model.astronomy.Planet;
import model.astronomy.Planets;
import model.astronomy.Satellite;
import model.geography.City;
import model.geography.Countries;
import model.geography.Country;
import model.geography.Provincia;
import model.people.Empleados;
import model.people.Persona;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamsExamples {

    public static void main(String[] args) {
        //countriesInfo();
        //worldCapitals();

        //getProvincias();

        //getAverageRadius();
        //getPlanetRadiusMap();
        //getHeaviestPlanet();
        //getLightestPlanet();
        //getPlanetNameToMoonsMap();
        allEmpleadosHablanEspañol();
        allMujeresHablanIngles();
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


    static void getProvincias() {
        var provincias = Countries.SPAIN.getAutonomias().stream()
                .flatMap(autonomia -> autonomia.getProvincias().stream())
                //.map(Provincia::getName)
                .sorted(Comparator.comparing(Provincia::getName))
                .toList();

        System.out.println(provincias);
        provincias.forEach(System.out::println);
    }


    static void allEmpleadosHablanEspañol() {
        boolean allEspañol = Empleados.EMPLEADOS
                .stream()
                .allMatch(empleado -> empleado.getIdiomas().contains(Persona.Idioma.ESPAÑOL));
        System.out.println("Todos los empleados hablan español ? = " + allEspañol);
    }

    static void allMujeresHablanIngles() {
        boolean allEspañol = Empleados.EMPLEADOS
                .stream()
                .filter(Persona::isMujer)
                .allMatch(empleado -> empleado.getIdiomas().contains(Persona.Idioma.INGLES));
        System.out.println("Todos las empleadas hablan ingles ? = " + allEspañol);
    }



}
