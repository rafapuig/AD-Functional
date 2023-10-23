package es.rafapuig.exercises;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CollectionsUtil {

    public static <T> boolean collectionEqualsIgnoreOrderImperative(Collection<T> c1, Collection<T> c2) {

        Map<T, Integer> map = new HashMap<>();

        //Vamos a contar cuantas repeticiones tenemos de cada elemento de la coleccion 1
        for (T key : c1) {
            Integer value = map.get(key);
            if(value == null) {
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
                if(newValue != null) {
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
        /*for (int i : map.values()) {
            if (i != 0) return false;
        }
        return true*/
        return map.isEmpty();
    }
}
