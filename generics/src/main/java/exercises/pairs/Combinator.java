package exercises.pairs;

public interface Combinator<T, U, R> {
    Pair<? super R> combine(Pair<? extends T> a, Pair<? extends U> b);
}
