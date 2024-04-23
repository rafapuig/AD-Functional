package functional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import model.astronomy.Planet;
import model.astronomy.PlanetType;
import model.astronomy.Planets;

public class FunctionalCollectionsDemo {

    public static void main(String[] args) {

        // Métodos de orden superior de la interfaz Iterable: forEach

        Consumer<? super Planet> printPlanet = System.out::println;

        // Tres formas de hacer lo mismo: por cada planeta de la colección, imprimirlo
        Planets.SOLAR_SYSTEM_PLANETS.forEach(planet -> System.out.println(planet)); //expresión lambda
        Planets.SOLAR_SYSTEM_PLANETS.forEach(System.out::println);      // referencia a método
        Planets.SOLAR_SYSTEM_PLANETS.forEach(printPlanet);              // instancia de interfaz funcional

        Consumer<? super Planet> printPlanetName = planet -> System.out.println(planet.getName());

        Planets.SOLAR_SYSTEM_PLANETS.forEach(
                planet -> System.out.println(planet.getName())
        );

        Planets.SOLAR_SYSTEM_PLANETS.forEach(printPlanetName);



        //Crear una lista modificable a partir del conjunto de planetas SOLAR_SYSTEM_PLANETS
        List<Planet> planets = new ArrayList<>(Planets.SOLAR_SYSTEM_PLANETS);

        // ----- default method from Collection interface higher-order removeIf

        //Expresión lambda que, Dado un planeta indicar si NO tiene satélites
        Predicate<? super Planet> planetWithoutSatellites = planet -> planet.getSatellites().isEmpty();

        // Eliminar los planetas que no tienen satélites de la lista
        planets.removeIf(
                planet -> planet.getSatellites().isEmpty()
        );

        // Otra forma usando la variable que contiene la instancia de la interfaz funcional / lambda
        //planets.removeIf(planetWithoutSatellites);

        System.out.println("\nPlanetas con satélites");
        planets.forEach(System.out::println);


        //SequencedCollection<Planet> sc = planets;

        Planet first = planets.getFirst();
        System.out.println("\nPrimer planeta de la lista:");
        System.out.println(first);

        // -----------------------------------------------
        planets.addFirst(new Planet("Mercurio", PlanetType.ROCKY, null));
        planets.addLast(new Planet("Urano", PlanetType.GAS_GIANT, null));

        System.out.println("\nPlanetas, con nuevos planetas añadidos");
        planets.forEach(System.out::println);

        // --- Funciones de orden superior de la interfaz List: replaceAll y sort
        // -----------------------------------------------
        UnaryOperator<Planet> planetToUpperCase =
                planet -> new Planet(
                        planet.getName().toUpperCase(),
                        planet.getType(), null, planet.getSatellites());

        planets.replaceAll(
                planet -> new Planet(
                        planet.getName().toUpperCase(),
                        planet.getType(), null, planet.getSatellites()));

        //planets.replaceAll(planetToUpperCase);

        System.out.println("\nPlanetas reemplazados por su nombre en mayúscula:");
        planets.forEach(printPlanet);

        // --------------------sort ---------------------------
        Function<Planet,String> keyExtractorNameFromPlanet = Planet::getName;
        Comparator<Planet> planetByNameComparator = Comparator.comparing(keyExtractorNameFromPlanet);

        planets.sort(Comparator.comparing(Planet::getName));
        //planets.sort(planetByNameComparator);

        System.out.println("\nNombres planetas ordenados por nombre:");
        //planets.forEach(planet -> System.out.println(planet.getName()));
        planets.forEach(printPlanetName);
    }
}
