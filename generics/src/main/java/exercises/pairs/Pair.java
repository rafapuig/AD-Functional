package exercises.pairs;

import java.util.Objects;

public class Pair<T> {

    private T first;
    private T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public Pair<T> swap() {
       return swap(this);
    }

    /**
     * Los metodos estáticos no reciben el parámetro de tipo declarado en la clase
     * Por tanto, si son genéricos, deben declarar el parámetro de tipo antes del tipo de retorno
     */
    public static <T> Pair<T> swap(Pair<T> pair) {
        Objects.requireNonNull(pair);
        return new Pair<>(pair.second, pair.first);
    }

    public static <T> Pair<T> combine(Pair<T> pair, Pair<T> pair2, Combinator<Pair<T>> combinator) {
        return combinator.combine(pair, pair2);
    }

}
