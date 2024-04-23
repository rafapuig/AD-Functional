package functional;

import model.geography.City;
import model.geography.Countries;
import model.geography.Country;
import model.geography.Region;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FunctionalMapsDemo {

    public static void main(String[] args) {
        highOrderFunctionsMapDemo1();
        worldCupCountriesPalmaresWithMap();
        worldCountriesMapMergeTest();
    }


    public static void highOrderFunctionsMapDemo1() {

        //Crear el mapa
        Map<String, Country> countryMap = new HashMap<>();

        Countries.WORLD_COUNTRIES.forEach(
                country -> countryMap.put(country.getIso3(), country)
        );

        System.out.println(countryMap);

        // Establecer el número de habitantes de la capital de España si no está presente
        countryMap.computeIfPresent("ESP",
                (k, v) -> v.getCapital().getPopulation().isPresent() ?
                        v : //Si ya está presente, devolvemos el valor actual (no hay nada que hacer)
                        new Country( //Si no, creamos un clon del Pais pero con el dato de la población establecido
                                k,  // la ISO3 del pais
                                v.getName(), // el nombre del pais
                                Region.SOUTHERN_EUROPE,
                                new City(
                                        v.getCapital().getName(),
                                        6_000_000L)));

        System.out.println(countryMap.get("ESP"));

        BiConsumer<? super String, ? super Country> printEntry =
                (k, v) -> System.out.println("key=" + k + ", value=" + v);

        countryMap.forEach(printEntry);

        countryMap.forEach(
                (k, v) -> System.out.println("key=" + k + ", value=" + v)
        );
    }


    static void worldCupCountriesPalmaresWithMap() {

        //Crear el mapa
        Map<Country, Integer> palmares = new HashMap<>();

        //Si ya ha ganado, estará presente en el mapa
        // y lo que haremos es incrementar en una unidad las victorias
        palmares.computeIfPresent(Countries.SPAIN, (k, v) -> v + 1);
        //Si todavía no ha ganado iniciamos con 1 victoria
        palmares.computeIfAbsent(Countries.SPAIN, k -> 1);

        //Podemos tratar los 2 casos a la vez con el método "compute"
        palmares.compute(Countries.ITALY, (k, v) -> v == null ? 1 : v + 1);

        BiFunction<Country, Integer, Integer> computeAWin = (k, v) -> v == null ? 1 : v + 1;

        palmares.compute(Countries.ITALY, computeAWin);
        palmares.compute(Countries.SPAIN, computeAWin);

        BiConsumer<Country, Integer> printCountryWins = (k, v) ->
                System.out.println(k.getName() + " ha ganado " + v + " veces.");

        palmares.forEach(
                (k, v) -> System.out.println(k.getName() + " ha ganado " + v + " veces."));


        //Si solo le queda una victoria poner a null para que se borre la entrada del mapa, si no, decrementamos
        BiFunction<Country, Integer, Integer> computeALose = (k, v) -> v == 1 ? null : v - 1;

        palmares.compute(Countries.USA, computeAWin);
        palmares.compute(Countries.USA, computeAWin);
        System.out.println(palmares.get(Countries.USA));

        palmares.computeIfPresent(Countries.USA, computeALose);
        System.out.println(palmares.get(Countries.USA));

        palmares.computeIfPresent(Countries.USA, computeALose);
        System.out.println(palmares.get(Countries.USA));

        printCountryWins.accept(
                Countries.ARGENTINA,
                palmares.get(Countries.ARGENTINA));


        // --------------------------------------------
        palmares.replaceAll((k, v) -> v == 1 ? null : v - 1); //Restamos a todos una
        palmares.replaceAll((k, v) -> v == 1 ? null : v - 1); //Restamos a todos una, pero no borra si null

        BiConsumer<Country, Integer> printCountryWinTimes =
                (k, v) -> System.out.println(k.getName() + " ha ganado " + v + " veces.");

        palmares.forEach(printCountryWinTimes);
    }


    static private final BiConsumer<Country, Integer> printCountryWinTimes =
            (country, wins) -> System.out.println(country.getName() + " ha ganado " + wins + " veces.");


    static void worldCountriesMapMergeTest() {

        Map<Country, Integer> palmares = new HashMap<>();

        palmares.put(Countries.SPAIN, 1);
        palmares.put(Countries.ARGENTINA, 2);

        //Merge como alternativa a compute
        palmares.merge(
                Countries.ARGENTINA,
                1, //Valor a asociar si la clave (pais) no se encuentra en el mapa o a combinar con el existente
                (oldValue, newValue) -> oldValue + newValue);

        palmares.merge(
                Countries.ARGENTINA,
                1,
                Integer::sum); //Se puede usar una referencia a método

        palmares.forEach(printCountryWinTimes);
    }


}
