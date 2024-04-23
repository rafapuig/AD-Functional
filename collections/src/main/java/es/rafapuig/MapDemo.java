package es.rafapuig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {

    static Map<String, Integer> testMap = new LinkedHashMap<>() {
        {
            put("Uno", 1);
            put("Dos", 2);
            put("Tres", 3);
            put("Cuatro", 4);
            put("Cinco", 5);
        }
    };

    static {
        testMap.put("Seis", 6);
        testMap.put("Siete", 7);
    }

    public static void main(String[] args) {
        mapTest();

        hashMapTest();
        linkedHashMapTest();
        treeMapTest();

        mapOfTest();
        mapOfEntriesTest();
        mapCopyOfTest();
        iterateCollectionViewsMapTest();
        putTest();

    }

    static void mapTest() {
        System.out.println(testMap);
        System.out.println(testMap.entrySet());
    }

    static void hashMapTest() {
        System.out.println("hashMapTest");

        Map<String, Integer> map = new HashMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);

        //Un HashMap NO preserva el orden de inserción
        System.out.println(map.entrySet());
    }

    static void linkedHashMapTest() {
        System.out.println("linkedHashMapTest");

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);

        //Un linkedHashMap SI preserva el orden de inserción
        System.out.println(map.entrySet());
    }

    static void treeMapTest() {
        System.out.println("treeMapTest");

        Map<String, Integer> map = new TreeMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);

        //Un TreeMap NO preserva el orden de inserción,
        // ordena según un comparador natural o especificado
        System.out.println(map.entrySet());
    }


    static void mapOfTest() {
        System.out.println("mapOfTest");

        Map<String, Integer> map =
                Map.of("Rafa", 46,
                        "Emilio", 47,
                        "Ramon", 48,
                        "Raul", 45);

        // Comprobamos el nombre de la clase del mapa generado
        System.out.println(map.getClass().getCanonicalName());

        //El orden no está garantizado
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

    static void mapOfEntriesTest() {
        System.out.println("mapOfEntriesTest");

        Map<String, Integer> map = Map.ofEntries(
                Map.entry("Rafa", 46),
                Map.entry("Emilio", 47),
                Map.entry("Ramon", 48),
                Map.entry("Raul", 45)
        );

        // Comprobamos el nombre de la clase del mapa generado
        System.out.println(map.getClass().getCanonicalName());

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

    static void mapCopyOfTest() {
        System.out.println("mapCopyOfTest");

        Map<String, Integer> sourceMap = new LinkedHashMap<>() {
            {
                put("Uno", 1);
                put("Dos", 2);
                put("Tres", 3);
                put("Cuatro", 4);
            }
        };

        Map<String, Integer> map = Map.copyOf(sourceMap);

        // Comprobamos el nombre de la clase del mapa generado
        System.out.println(map.getClass().getCanonicalName());

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }

    static <K, V> void iterateMapCollectionViews(Map<K, V> map) {

        //Colección de claves
        System.out.println(map.keySet());

        //Iterar la vista conjunto de claves del mapa
        for (K key : map.keySet()) {
            System.out.println("key = " + key);
        }

        //Colección de valores asociados a las claves
        System.out.println(map.values());

        //Iterar la vista coleccion de valores
        for (V value : map.values()) {
            System.out.println("value = " + value);
        }


        //Colección de entradas (Entry) del mapa
        System.out.println(map.entrySet());

        //Iterar la vista conjunto de entradas del mapa
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            System.out.println("key = " + key + " - value = " + value);
        }
    }

    static void iterateCollectionViewsMapTest() {
        iterateMapCollectionViews(testMap);
    }

    static void putTest() {
        //Creamos una copia del mapa
        Map<String, Integer> map = new LinkedHashMap<>(testMap);

        map.put("Veinte", 200);
        System.out.println(map.entrySet());

        Integer previous = map.put("Veinte", 20); //No inserta otra entrada, modifica el valor 
        System.out.println("previous = " + previous);
        System.out.println(map.entrySet());

        Integer value = map.putIfAbsent("Veinte", 2000); //NO inserta pq ya existe
        System.out.println("value = " + value); //imprime el valor anterior
        System.out.println(map.entrySet());

        value = map.putIfAbsent("Diez", 10); //inserta y devuelve null pq no existe esa clave
        System.out.println("value = " + value);
        System.out.println(map.entrySet());

        map.putAll(Map.of("Ocho", 8, "Nueve", 9, "Diez", 10));
        System.out.println(map.entrySet());
    }


}
