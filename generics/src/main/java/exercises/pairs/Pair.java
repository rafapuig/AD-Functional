package exercises.pairs;

import java.util.Objects;

public interface Pair<T> {

    T getFirst();

    T getSecond();

    void setFirst(T first);

    void setSecond(T second);

    FieldPair<T> swap();

    boolean contains(T item);


    /** Pair UTILS **/

    /**
     * Los metodos estáticos no reciben el parámetro de tipo declarado en la clase
     * Por tanto, si son genéricos, deben declarar el parámetro de tipo antes del tipo de retorno
     */
    static <T> Pair<T> swap(Pair<T> pair) {
        Objects.requireNonNull(pair);
        return pair.swap();
    }

    static void printPair(Pair<?> pair) {
        System.out.println(pair.getFirst() + " y " + pair.getSecond());
    }

    static <T> Pair<Pair<? extends T>> join(Pair<? extends T> first, Pair<? extends T> second) {
        return new FieldPair<>(first, second);
    }

    static <T> void fill(T first, T second, Pair<? super T> pair) {
        pair.setFirst(first);
        pair.setSecond(second);
    }


}
