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

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public Pair<T> swap() {
        return swap(this);
    }

    /*** Pair UTILS ***/

    /**
     * Los metodos estáticos no reciben el parámetro de tipo declarado en la clase
     * Por tanto, si son genéricos, deben declarar el parámetro de tipo antes del tipo de retorno
     */
    public static <T> Pair<T> swap(Pair<T> pair) {
        Objects.requireNonNull(pair);
        return new Pair<>(pair.second, pair.first);
    }

    static void printPair(Pair<?> pair) {
        System.out.println(pair.getFirst() + " y " + pair.getSecond());
    }

    public static <T> Pair<Pair<? extends T>> join(Pair<? extends T> first, Pair<? extends T> second) {
        return new Pair<>(first, second);
    }

    static <T> void fill(T first, T second, Pair<? super T> pair) {
        pair.setFirst(first);
        pair.setSecond(second);
    }

}
