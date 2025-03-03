package exercises.reactive;

import java.io.Closeable;

class UI implements Runnable, Closeable {

    Model model;

    public UI(Model model) {
        this.model = model;
    }

    public void run() {
        model.observable.observe(this, text ->
                System.out.println(getClass().getSimpleName() + " : " + text)
        );
    }

    @Override
    public void close() {
        System.out.println("Closing...");
        model.observable.unobserve(this);
    }
}

class Model {
    public Observable<String> observable = new Observable<>();
}

public class ObservableDemo {

    public static void main(String[] args) {

        Model model = new Model();

        Runnable producer = () -> {
            try {
                model.observable.setValue("Hello");
                Thread.sleep(1000);
                model.observable.setValue("World");
                Thread.sleep(1000);
                model.observable.setValue("Goodbye");
                Thread.sleep(1000);
                model.observable.setValue("Good morning");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        };

        Runnable consumer = () -> {
            try (UI ui = new UI(model)) {
                ui.run();
                producer.run();
            }
        };

        consumer.run();
    }

}
