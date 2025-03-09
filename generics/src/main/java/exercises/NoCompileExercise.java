package exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * La relación supertipo-subtipo no se mantiene
 * cuando usamos los tipos como valor del parámetro de tipo de un tipo genérico
 *
 * Si D es subtipo de B --> un objeto de tipo D se considera también un objeto de tipo B
 * se puede asignar una referencia de tipo D (subtipo) en una variable de tipo B (supertipo)
 *
 * Pero,
 * El tipo parametrizado Generico<D> no es se considera un subtipo del tipo parametrizado Generico<B>
 * (en realidad es el mismo tipo pero con distinto valor del parámetro de tipo)
 *
 */
public class NoCompileExercise {

    public static void main(String[] args) {
        List<Number> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        //list1 = list2; // Error, no son el mismo tipo parametrizado
        List<?> list3 = list2; // Si se puede asignar cuando el tipo parametrizado usa la wildcard sin límite
        List<? extends Number> list4 = list2;
        List<? super Long> list5 = list1;

        List<?> list = new ArrayList<>();
        //list.add("Hola"); // Error en tiempo de compilación

    }

}
