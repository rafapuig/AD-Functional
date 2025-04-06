package es.rafapuig;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapDemo {

    // Mapa con claves de tipo String y valores asociados de tipo Integer
    // Crear una instancia de objeto de clase anónima derivado de la clase LinkedHashSet
    static final Map<String, Integer> testMap = new LinkedHashMap<>() {
        // Llamada al inicializador de instancia de la clase
        {
            put("Uno", 1);
            put("Dos", 2);
            put("Tres", 3);
            put("Cuatro", 4);
            put("Cinco", 5);
        }
    };

    // Llamada al inicializador estático de la clase MapDemo
    static {
        testMap.put("Seis", 6);
        testMap.put("Siete", 7);
    }

    public static void main(String[] args) {
        new MapDemo().run();
    }

    void run() {
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

    @Test
    void mapTest() {
        // Imprimir el mapa
        System.out.println(testMap);
        // Imprimir la vista entrySet del mapa, es un Set de elementos Entry<K,V>
        System.out.println(testMap.entrySet());
    }


    @Test
    void hashMapTest() {
        System.out.println("hashMapTest");

        Map<String, Integer> map = new HashMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);

        //Un HashMap NO preserva al iterar el orden de inserción
        System.out.println(map);
    }

    @Test
    void linkedHashMapTest() {
        System.out.println("linkedHashMapTest");

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);

        //Un linkedHashMap SI preserva al iterar el orden de inserción
        System.out.println(map);
    }


    @Test
    void treeMapTest() {
        System.out.println("treeMapTest");

        // Al instanciar el objeto TreeMap es el momento de proporcionar el comparador de sus elementos
        // Si no se proporciona un comparador como argumento al constructor
        // El TreeMap ordena sus elementos por su orden natural
        // El tipo K de los objetos claves debe ser de una clase que implemente Comparable<K>
        Map<String, Integer> map = new TreeMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);

        //Un TreeMap NO preserva el orden de inserción,
        // ordena según un comparador natural o un Comparator especificado
        System.out.println(map.entrySet());
    }


    @Test
    void mapOfTest() {
        System.out.println("mapOfTest");

        // Crear un mapa inmodificable mediante el metodo factoría of
        Map<String, Integer> map =
                Map.of("Rafa", 46,
                        "Emilio", 47,
                        "Ramon", 48,
                        "Raul", 45);

        // Comprobamos el nombre de la clase del mapa generado
        System.out.println(map.getClass().getCanonicalName());

        // El orden de aparición de los elementos al iterar sobre el mapa no es por orden de inserción
        System.out.println(map);

        //El orden no está garantizado
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }


    @Test
    void mapOfEntriesTest() {
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


    @Test
    void mapCopyOfTest() {
        System.out.println("mapCopyOfTest");

        Map<String, Integer> sourceMap = new LinkedHashMap<>() {
            {
                put("Uno", 1);
                put("Dos", 2);
                put("Tres", 3);
                put("Cuatro", 4);
            }
        };

        // copyOf es el metodo factoría que crea un mapa inmodificable con las mismas entradas
        // que el mapa proporcionado como argumento
        Map<String, Integer> map = Map.copyOf(sourceMap);

        // Comprobamos el nombre de la clase del mapa generado
        System.out.println(map.getClass().getCanonicalName());

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }


    static <K, V> void iterateMapCollectionViews(Map<K, V> map) {

        //Vista conjunto de claves del mapa
        System.out.println(map.keySet());

        //Iterar la vista conjunto de claves del mapa
        for (K key : map.keySet()) {
            System.out.println("key = " + key);
        }

        //Vista colección de valores asociados a las claves
        System.out.println(map.values());

        //Iterar la vista colección de valores
        for (V value : map.values()) {
            System.out.println("value = " + value);
        }


        //Vista Colección conjunto de entradas (Entry) del mapa
        System.out.println(map.entrySet());

        //Iterar la vista conjunto de entradas del mapa
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            System.out.println("key = " + key + " - value = " + value);
        }
    }


    @Test
    void iterateCollectionViewsMapTest() {
        iterateMapCollectionViews(testMap);
    }


    @Test
    void putTest() {
        //Creamos una copia del mapa
        Map<String, Integer> map = new LinkedHashMap<>(testMap);

        map.put("Veinte", 200);
        System.out.println(map);

        Integer previous = map.put("Veinte", 20); //No inserta otra entrada, modifica el valor 
        System.out.println("previous = " + previous);
        System.out.println(map);

        Integer value = map.putIfAbsent("Veinte", 2000); //NO inserta porque ya existe
        System.out.println("value = " + value); //imprime el valor anterior
        System.out.println(map);

        value = map.putIfAbsent("Diez", 10); //inserta y devuelve null pq no existe esa clave
        System.out.println("value = " + value);
        System.out.println(map);

        // El metodo putAll añade al mapa todas las entradas del mapa proporcionado
        map.putAll(Map.of("Ocho", 8, "Nueve", 9, "Diez", 10));
        System.out.println(map);

        // Si las claves del mapa proporcionado ya existen en el mapa destino
        // Se reemplaza el valor asociado
        map.putAll(Map.of("Ocho", 80, "Nueve", 90, "Diez", 100));
        System.out.println(map);
    }


    @Test
    void testGet() {
        Map<String, Integer> map = testMap;

        // Cuando la clave se encuentra entre las entradas del mapa
        int value = map.get("Uno");
        System.out.println(value);

        Integer nullValue = map.get("Cero");
        System.out.println(nullValue);

        int cero = map.getOrDefault("Cero", 0);
        System.out.println(cero);
    }


}
