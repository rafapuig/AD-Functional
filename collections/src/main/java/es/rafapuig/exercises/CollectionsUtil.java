package es.rafapuig.exercises;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionsUtil {

    public static <T> boolean collectionEqualsIgnoreOrderImperative(Collection<T> c1, Collection<T> c2) {

        Map<T, Integer> map = new HashMap<>();  //Mapa que a cada elemento le asocia el número de veces que se repite

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la colección 1
        for (T item : c1) {  // Para cada elemento contenido en la colección 1
            if (!map.containsKey(item)) {   // Si el elemento aún no está en el mapa
                map.put(item, 1);           // Ponemos como valor inicial un 1, (ese elemento aparece una vez)
            } else {                        // Si el elemento ya está en el mapa
                Integer value = map.get(item);  // Buscamos su valor asociado, veces que lo hemos encontrado al iterar
                map.replace(item, value + 1);   // Ampliamos en una unidad el valor, para reflejar que está una vez más
            }
        }

        //Ahora comparamos con los elementos de la otra colección
        for (T item : c2) {
            //Si la otra colección tiene un elemento que no estaba en la primera entonces no son iguales
            if (!map.containsKey(item)) return false;
                //Si lo contiene restamos una unidad del mapa que cuenta cuantas repeticiones hay del elemento
            else {
                Integer oldValue = map.get(item);
                Integer newValue = oldValue > 1 ? oldValue - 1 : null;
                if (newValue != null) {
                    map.put(item, newValue);
                } else {
                    map.remove(item);
                }
                //map.computeIfPresent(item, (k, oldValue) -> oldValue > 1 ? oldValue - 1 : null);
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

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la colección 1
        c1.forEach(key -> map.merge(
                key,    //Clave
                1,      //Valor que se asigna si la clave todavía no existe en el map
                (oldValue, newValue) -> oldValue + 1)); // Si ya tiene valor nuevo = sumar 1 al valor que tenia

        //Comparamos con los elementos de la otra colección
        for (T item : c2) {
            //Si la otra colección tiene un elemento que no estaba en la primera entonces no son iguales
            if (!map.containsKey(item)) return false;
                //Si lo contiene restamos una unidad del mapa que cuenta cuantas repeticiones hay del elemento
            else {
                map.computeIfPresent(   //Método de orden superior (que hacer con valores de claves existentes)
                        item, //Clave a la que se va a asociar el nuevo valor (si se encuentra)
                        (key, oldValue) -> oldValue > 1 ? oldValue - 1 : null); //BiFunction<K,V,V> remapping
            }
        }

        return map.isEmpty();
    }

    public static <T> boolean collectionEqualsIgnoreOrderStreams(Collection<T> c1, Collection<T> c2) {

        //if (c1.size() != c2.size()) return false;

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        Map<T, Integer> map = c1.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        t -> 1,
                        (oldValue, newValue) -> oldValue + 1));

        Consumer<T> decrementElementCountOrRemoveIfOne = item ->
                map.computeIfPresent(
                        item,
                        (key, oldValue) -> oldValue > 1 ? oldValue - 1 : null);

        //Comparamos con los elementos de la otra colección
        long itemsCount = c2.stream()
                .takeWhile(map::containsKey)
                .peek(decrementElementCountOrRemoveIfOne)
                .count();

        if(itemsCount< c2.size()) return false;

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
