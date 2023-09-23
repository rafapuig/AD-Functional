package es.rafapuig.exercises.randommap;

import java.util.HashMap;
import java.util.Map;

public class RandomMapDemo {

    public static void main(String[] args) {

        Map<Integer, Long> results = new HashMap<>();

        for (int i = 0; i < 200000; i++) {
            int key = (int) (Math.random() * 10);

            if (!results.containsKey(key)) {
                results.put(key, 1L);
            } else {
                long value = results.get(key);
                results.put(key, ++value);
            }
        }

        for (Integer key : results.keySet()) {
            long value = results.get(key);
            System.out.printf("El numero %d ha salido %d veces\n", key, value);
        }

        System.out.println(results);
    }
}
