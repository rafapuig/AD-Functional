package es.rafapuig.utils;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public final class Iterables {

    // -- iterar metodo genérico con consumer
    public static <T> void forEach(Iterable<? extends T> iterable, Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        for (T item : iterable) {
            consumer.accept(item);
        }
    }

    public static <T> void forEachRemaining(Iterator<? extends T> iterator, Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        while (iterator.hasNext()) {
            consumer.accept(iterator.next());
        }
    }

}
