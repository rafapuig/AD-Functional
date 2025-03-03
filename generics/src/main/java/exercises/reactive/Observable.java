package exercises.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Observable<T> {

    T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        observers.forEach((o,react) -> react.accept(value));
    }

    Map<Object, Consumer<? super T>> observers = new HashMap<>();

    void observe(Object owner, Consumer<? super T> react) {
        observers.put(owner, react);
    }

    void unobserve(Object owner) {
        observers.remove(owner);
    }


}
