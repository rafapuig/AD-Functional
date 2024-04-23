package exercises.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    T value;
    List<Observer<T>> observers = new ArrayList<>();

    T get() {
        return value;
    }

    void set(T value) {
        if (this.value != value) {
            T oldValue = this.value;
            this.value = value;
            observers.forEach(observer -> observer.update(oldValue, value));
        }
    }

    void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }
}
