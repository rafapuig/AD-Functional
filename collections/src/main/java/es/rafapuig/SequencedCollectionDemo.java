package es.rafapuig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SequencedCollection;

public class SequencedCollectionDemo {

    public static void main(String[] args) {

        String[] countries = {"España", "Francia", "Portugal", "Italia",
                "EE.UU.", "Canada", "Brasil", "Argentina", "Cuba", "Colombia"};

        SequencedCollection<String> sequenced = new ArrayList<>(Arrays.asList(countries));

        System.out.println(sequenced);

        sequenced.addLast("Reino Unido");
        sequenced.addFirst("Alemania");
        System.out.println(sequenced);

        sequenced.removeFirst();
        System.out.println(sequenced);

        sequenced.removeLast();
        System.out.println(sequenced);

        SequencedCollection<String> reversed = sequenced.reversed(); // Devuelve una vista en orden inverso de la colección

        System.out.println(reversed);

        // Eliminar un elemento en la secuencia original, equivale a que también desaparece de la vista invertida
        sequenced.removeFirst();
        System.out.println(sequenced);
        System.out.println(reversed);

        // Eliminamos el primero de la vista invertida (equivale a eliminar el último de la original)
        reversed.removeFirst();
        System.out.println(sequenced);
        System.out.println(reversed);

        System.out.println("Primero de la secuencia: " + sequenced.getFirst());
        System.out.println("Último de la secuencia: " + sequenced.getLast());

        System.out.println("Primero de la secuencia invertida: " + reversed.getFirst());
        System.out.println("Último de la secuencia invertida: " + reversed.getLast());

    }
}
