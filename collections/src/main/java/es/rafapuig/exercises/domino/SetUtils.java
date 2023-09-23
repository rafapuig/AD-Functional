package es.rafapuig.exercises.domino;

import java.util.*;

public class SetUtils {
    static <E> Set<E> shuffleSet(Set<E> set) {

        List<E> list = new LinkedList<>(set);

        Collections.shuffle(list);

        return new LinkedHashSet<>(list);
    }

}
