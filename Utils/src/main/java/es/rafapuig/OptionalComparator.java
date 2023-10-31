package es.rafapuig;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class OptionalComparator {

    static <T, U> Comparator<T> comparing(
            Function<? super T, Optional<U>> keyExtractor,
            Comparator<U> comparator,
            Function<Comparator<? super U>, Comparator<U>> whereEmpties) {
        Objects.requireNonNull(keyExtractor);

        /* return Comparator.comparing(keyExtractor, emptyWhere(comparator, whereEmpties));*/

        return Comparator.comparing(keyExtractor,
                Comparator.comparing(opt -> opt.orElse(null),
                        whereEmpties.apply(comparator)));

        /*return (c1, c2) -> emptyWhere(comparator, whereEmpties).compare(keyExtractor.apply(c1),
                keyExtractor.apply(c2)); */
    }

    /* static <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, Optional<U>> keyExtractor,
            Function<Comparator<? super U>, Comparator<U>> whereEmpties) {

        return comparing(keyExtractor,
                Comparator.naturalOrder(),
                whereEmpties);
    }*/

    public static <T, U> Comparator<T> comparingEmptyFirst(
            Function<? super T, Optional<U>> keyExtractor,
            Comparator<U> comparator) {

        return comparing(keyExtractor, comparator, Comparator::nullsFirst);
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparingEmptyFirst(
            Function<? super T, Optional<U>> keyExtractor) {

        return comparingEmptyFirst(keyExtractor,
                Comparator.naturalOrder());
    }

    public static <T, U> Comparator<T> comparingEmptyLast(
            Function<? super T, Optional<U>> keyExtractor,
            Comparator<U> comparator) {

        return comparing(keyExtractor, comparator, Comparator::nullsLast);
    }

    public static <T, U extends Comparable<? super U>> Comparator<T> comparingEmptyLast(
            Function<? super T, Optional<U>> keyExtractor) {

        return comparingEmptyLast(keyExtractor,
                Comparator.naturalOrder());
    }


    static <T> Comparator<Optional<T>> emptyWhere(
            Comparator<? super T> comparator,
            Function<Comparator<? super T>, Comparator<T>> whereNulls) {

        return Comparator.comparing(opt -> opt.orElse(null), whereNulls.apply(comparator));
    }

    /*static <T extends Comparable<T>> Comparator<Optional<T>> emptyWhere(
            Function<Comparator<? super T>, Comparator<T>> whereNulls) {

        return emptyWhere(Comparator.naturalOrder(), whereNulls);
    }*/


    public static <T> Comparator<Optional<T>> emptyLast(Comparator<? super T> comparator) {
        return emptyWhere(comparator, Comparator::nullsLast);
    }

    public static <T extends Comparable<T>> Comparator<Optional<T>> emptyLast() {
        return emptyLast(Comparator.naturalOrder());
    }

    public static <T> Comparator<Optional<T>> emptyFirst(Comparator<? super T> comparator) {
        return emptyWhere(comparator, Comparator::nullsFirst);
    }

    public static <T extends Comparable<T>> Comparator<Optional<T>> emptyFirst() {
        return emptyFirst(Comparator.naturalOrder());
    }

}
