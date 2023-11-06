package es.rafapuig.exercises;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionsUtil {

    public static <T> boolean collectionEqualsIgnoreOrderImperative(Collection<T> c1, Collection<T> c2) {

        Map<T, Integer> map = new HashMap<>();

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        for (T key : c1) {
            Integer value = map.get(key);
            if (value == null) {
                map.put(key, 1);
            } else {
                map.replace(key, value + 1);
            }
        }

        //Comparamos con los elementos de la otra colección
        for (T key : c2) {
            //Si la otra coleccion tiene un elemento que no estaba en la primera entonces no son iguales
            if (!map.containsKey(key)) return false;
                //Si lo contiene restamos una unidad del mapa que cuenta cuantos repeticiones hay del elemento
            else {
                Integer oldValue = map.get(key);
                Integer newValue = oldValue > 1 ? oldValue - 1 : null;
                if (newValue != null) {
                    map.put(key, newValue);
                } else {
                    map.remove(key);
                }
                //map.computeIfPresent(key, (k, oldValue) -> oldValue > 1 ? oldValue - 1 : null);
            }
        }
        /*for (int i : map.values()) {
            if (i != 0) return false;
        }
        return true*/
        return map.isEmpty();
    }

    public static <T> boolean collectionEqualsIgnoreOrder(Collection<T> c1, Collection<T> c2) {
        Map<T, Integer> map = new HashMap<>();

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        c1.forEach(key -> map.merge(key, 1, (oldValue, newValue) -> oldValue + 1));

        //Comparamos con los elementos de la otra collecion
        for (T key : c2) {
            //Si la otra coleccion tiene un elemento que no estaba en la primera entonces no son iguales
            if (!map.containsKey(key)) return false;
                //Si lo contiene restamos una unidad del mapa que cuenta cuantos repeticiones hay del elemento
            else {
                map.computeIfPresent(key, (k, oldValue) -> oldValue > 1 ? oldValue - 1 : null);
            }
        }

        return map.isEmpty();
    }

    public static <T> boolean collectionEqualsIgnoreOrderStreams(Collection<T> c1, Collection<T> c2) {

        if (c1.size() != c2.size()) return false;

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        Map<T, Integer> map = c1.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        t -> 1,
                        (oldValue, newValue) -> oldValue + 1));

        Consumer<T> decrementElementCountOrRemoveIfOne = key ->
                map.computeIfPresent(
                        key,
                        (k, oldValue) -> oldValue > 1 ? oldValue - 1 : null);

        //Comparamos con los elementos de la otra collecion
        c2.stream()
                .takeWhile(map::containsKey)
                .forEach(decrementElementCountOrRemoveIfOne);

        return map.isEmpty();
    }

    public static <T> boolean collectionEqualsIgnoreOrderStreams2(Collection<T> c1, Collection<T> c2) {

        if (c1.size() != c2.size()) return false;

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        Map<T, Integer> map = c1.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Function.identity(),
                                t -> 1,
                                (oldCount, newCount) -> oldCount + 1),
                        result -> { //ahora al resultado le vamos quitando los elementos de c2
                            c2.stream()
                                    .takeWhile(result::containsKey)
                                    .forEach(key -> result.computeIfPresent(
                                            key,
                                            (k, oldCount) -> oldCount > 1 ? oldCount - 1 : null));
                            return result;
                        }
                ));

        return map.isEmpty(); //si el mapa ha quedado vacío es porque las listas eran iguales en contenido
    }


    public static <T> boolean collectionEqualsIgnoreOrderStreams3(Collection<T> c1, Collection<T> c2) {

        final Function<Map<T, Integer>, Consumer<T>> decrementElementCountOrRemoveIfOne =
                map -> key -> map.computeIfPresent(
                        key,
                        (aKey, oldCount) -> oldCount > 1 ? oldCount - 1 : null);

        if (c1.size() != c2.size()) return false;

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        Map<T, Integer> map = c1.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Function.identity(),
                                t -> 1,
                                (oldCount, newCount) -> oldCount + 1),
                        result -> { //ahora al resultado le vamos quitando los elementos de c2
                            c2.stream()
                                    .takeWhile(result::containsKey)
                                    .forEach(decrementElementCountOrRemoveIfOne.apply(result));
                            return result;
                        }
                ));

        return map.isEmpty(); //si el mapa ha quedado vacío es porque las listas eran iguales en contenido
    }

    public static <T> boolean collectionEqualsIgnoreOrderStreams4(Collection<T> c1, Collection<T> c2) {

        final Function<Map<T, Long>, Consumer<T>> decrementElementCountOrRemoveIfOne =
                map -> key -> map.computeIfPresent(
                        key,
                        (aKey, oldCount) -> oldCount > 1 ? oldCount - 1 : null);



        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        Map<T, Long> map = c1.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()),
                        result -> { //ahora al resultado le vamos quitando los elementos de c2
                            if (result.size() != c2.stream().distinct().count()) return result;
                            c2.stream()
                                    .takeWhile(result::containsKey) //AQUI FALLA
                                    .forEach(decrementElementCountOrRemoveIfOne.apply(result));
                            return result;
                        }
                ));


        return map.isEmpty(); //si el mapa ha quedado vacío es porque las listas eran iguales en contenido
    }

}
