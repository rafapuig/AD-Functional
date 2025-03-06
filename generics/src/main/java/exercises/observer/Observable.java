package exercises.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    T value;
    List<Observer<T>> observers = new ArrayList<>();

    public Observable() { }

    public Observable(T value) {
        this.value = value;
    }

    T get() {
        return value;
    }

    void set(T value) {
        if (this.value != value) {
            T oldValue = this.value;
            this.value = value;
            notifyObservers(oldValue, value);
        }
    }

    protected void notifyObservers(T oldValue, T newValue) {
        observers.forEach(observer -> observer.update(oldValue, newValue));
    }

    void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }
}
