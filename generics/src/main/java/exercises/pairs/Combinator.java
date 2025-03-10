package exercises.pairs;

public interface Combinator<T, U, R> {
    FieldPair<? super R> combine(FieldPair<? extends T> a, FieldPair<? extends U> b);
}
