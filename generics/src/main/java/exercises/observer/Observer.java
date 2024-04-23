package exercises.observer;

@FunctionalInterface
public interface Observer<T> {

    void update(T oldValue, T newValue);

}
