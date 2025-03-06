package exercises.observer;

public class ObservableDemo {

    public static void main(String[] args) {
        example1();
        example2();
        example3();
        example4();
        example5();
    }

    private static void example1() {
        Observable<String> text = new Observable<>();

        text.registerObserver((oldValue, newValue) ->
                System.out.println("Old: " + oldValue + " New: " + newValue));

        text.set("Hola");
        text.set("Adios");
    }

    private static void example2() {
        Observable<String> text = new Observable<>();

        text.registerObserver(new Observer<String>() {

            @Override
            public void update(String oldValue, String newValue) {
                System.out.println("Old: " + oldValue + " New: " + newValue);
            }
        });

        text.set("Hola");
        text.set("Adios");
    }

    //Metodo de callback (respuesta) a la actualización del valor observable de tipo String
    static void text_updated(String oldValue, String newValue) {
        System.out.println("Old: " + oldValue + " New: " + newValue);
    }

    //Llamar al método de callback en la implementación del método update del tipo anónimo
    private static void example3() {
        Observable<String> text = new Observable<>();

        text.registerObserver(new Observer<String>() {

            @Override
            public void update(String oldValue, String newValue) {
                text_updated(oldValue, newValue);
            }
        });

        text.set("Hola");
        text.set("Adios");
    }

    //Usar una expresión lambda que llame al método de callback
    private static void example4() {
        Observable<String> text = new Observable<>();

        text.registerObserver((oldValue, newValue) -> text_updated(oldValue, newValue));

        text.set("Hola");
        text.set("Adios");
    }

    //Usar una referencia a método como argumento de llamada al método register del Observable
    private static void example5() {
        Observable<String> text = new Observable<>();

        text.registerObserver(ObservableDemo::text_updated);

        text.set("Hola");
        text.set("Adios");
    }



    private static void example00() {
        Person person = new Person("Rafael", 18);

        person.getObservableName().registerObserver((oldValue, newValue) ->
                System.out.println("Old name: " + oldValue + " New name: " + newValue));

        person.getObservableAge().registerObserver((oldValue, newValue) ->
                System.out.println("Old age: " + oldValue + " New age: " + newValue));

        person.setName("Rafa");
        person.setAge(22);
        person.setAge(47);
    }
}
