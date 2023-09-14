package functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalUtil {

    //Aplica una accion a cada elemento de la lista
    public static <E> void forEach(List<E> list, Consumer<? super E> action) {
        for (E item : list) {
            action.accept(item);
        }
    }

    // Aplica un filtro a los elementos de la lista y devuelve la lista filtrada
    public static <E> List<E> filter(List<E> list, Predicate<? super E> predicate) {
        List<E> filteredList = new ArrayList<>();
        for (E item : list) {
            if (predicate.test(item)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    //Mapear cada elemento de la lista a un valor
    public static <E, R> List<R> map(List<E> list, Function<? super E, R> mapper) {
        List<R> mappedList = new ArrayList<>();
        for(E item: list) {
            mappedList.add(mapper.apply(item));
        }
        return mappedList;
    }

}
