package es.rafapuig.exercises.iterable;

import java.util.Iterator;
import java.util.List;

public class ExerciseIterable {

    public static void main(String[] args) {

        // El metodo factoría estática List.of nos crea una List (inmodificable) a partir de los elementos
        // Podemos asignar una referencia de tipo List<String> en una variable de tipo Iterable<String>
        // porque la interface List hereda de Iterable (hay un upcasting implícito)
        Iterable<String> ingredients = List.of("azucar", "leche", "harina", "huevos");

        //Obtener el objeto iterador llamando al metodo iterator de Iterable
        // El objeto Iterator<String> es la estrategia para recorrer (iterar) una colección
        // Es el equivalente a los WalkBehaviors (Iterable sería el equivalente al Walkable)
        Iterator<String> iterator = ingredients.iterator();

        // El metodo hasNext no devuelve true si todavía quedan elementos en la colección por recorrer
        while (iterator.hasNext()) {
            // El metodo next devuelve una referencia al siguiente elemento por procesar, iterar, recorrer, etc.
            // El cursor avanzará una posición y se posicionará sobre el siguiente para la próxima llamada a next
            // devolver el siguiente del que ha devuelto ahora
            String ingredient = iterator.next();

            System.out.println(ingredient);
        }

        // El bucle foreach se encarga de obtener el iterador y llamar al metodo hasNext para saber cuando
        // termina el bucle
        // Llama al metodo next y guarda el valor en la variable String ingredient
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
        }


    }
}
