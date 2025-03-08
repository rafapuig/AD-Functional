package es.rafapuig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SequencedCollection;

public class SequencedCollectionDemo {

    static <T> void print(SequencedCollection<T> collection) {
        System.out.println(collection);
    }

    public static void main(String[] args) {

        String[] countries = {"España", "Francia", "Portugal", "Italia", "Bélgica"};

        SequencedCollection<String> sequenced = new ArrayList<>(Arrays.asList(countries));
        print(sequenced);

        System.out.println("Añadiendo Reino Unido al final... ");
        sequenced.addLast("Reino Unido");
        print(sequenced);

        System.out.println("Añadiendo Alemania al principio... ");
        sequenced.addFirst("Alemania");
        print(sequenced);

        System.out.println("Eliminando al primero... ");
        sequenced.removeFirst();
        print(sequenced);

        System.out.println("Eliminado al último... ");
        sequenced.removeLast();
        print(sequenced);

        System.out.println("Obteniendo una vista invertida... ");
        SequencedCollection<String> reversed = sequenced.reversed(); // Devuelve una vista en orden inverso de la colección
        print(reversed);

        System.out.println("Eliminando al primero de la secuencia original... ");
        // Eliminar un elemento en la secuencia original, equivale a que también desaparece de la vista invertida
        sequenced.removeFirst();

        System.out.println("Secuencia original: ");
        print(sequenced);

        System.out.println("Secuencia invertida: ");
        print(reversed);

        System.out.println("Eliminando al primero de la vista invertida de la secuencia... ");
        // Eliminamos el primero de la vista invertida (equivale a eliminar el último de la original)
        reversed.removeFirst();
        System.out.println("Secuencia original: ");
        print(sequenced);

        System.out.println("Secuencia invertida: ");
        print(reversed);

        System.out.println("Primero de la secuencia: " + sequenced.getFirst());
        System.out.println("Último de la secuencia: " + sequenced.getLast());

        System.out.println("Primero de la secuencia invertida: " + reversed.getFirst());
        System.out.println("Último de la secuencia invertida: " + reversed.getLast());

    }
}
