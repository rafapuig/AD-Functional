import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

public class GenericArrayTest<T> {
    T[] elements;

    //No se pueden crear arrays de tipos genericos
    //Wrapper<String>[] gsArray = new Wrapper<String>[10]; // Error

    Wrapper<?>[] unknownArray = new Wrapper<?>[10]; // OK

    static Wrapper<String>[] gsa = (Wrapper<String>[]) Array.newInstance(Wrapper.class, 10);

    static Object[] objArray = (Object[]) gsa;

    public static void main(String[] args)
    {
        Wrapper<? extends Number>[] unknownArray = (Wrapper<? extends Number>[]) new Wrapper<?>[10];
        unknownArray[0] = new Wrapper<>(45);
        unknownArray[1] = new Wrapper<>("Hola".hashCode());

        for (Wrapper<?> elem : unknownArray) {
            //elem.set("Hola"); //Error
            if(elem != null) {
                Object e = elem.get();
                System.out.println(e);
            }
        }


        objArray[0] = new Object(); // ArrayStoreException
        gsa[0] = new Wrapper<String>("Hola");
    }

    static void genericArrayCreation() {
        Wrapper<?>[] wrappers = new Wrapper<?>[3];
        wrappers[0] = new Wrapper<>("Hola");
        wrappers[1] = new Wrapper<>("Adios");
        wrappers[2] = new Wrapper<>(34);

        for (int i = 0; i < wrappers.length; i++) {
            System.out.println("wrappers[i].get() = " + wrappers[i].get());
        }
    }

    static void genericArrayCreation2() {
        Wrapper<String>[] wrappers = (Wrapper<String>[]) new Wrapper<?>[3];
        wrappers[0] = new Wrapper<>("Hola");
        wrappers[1] = new Wrapper<>("Adios");
        //wrappers[2] = new Wrapper<>(34); //Error

        for (int i = 0; i < wrappers.length; i++) {
            if(wrappers[i] != null) {
                System.out.println("wrappers[i].get() = " + wrappers[i].get());
                wrappers[i].set("xxx");
            } else {
                wrappers[i] = new Wrapper<>("...");
            }
        }
    }

    public GenericArrayTest(int count) {
        //elements = new T[count]; //No se puede
        Class<?> type = String.class;
        try {
            type.getDeclaredConstructor(type).newInstance();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        createGenericArrayTest(String::new, 4);
    }

    public <T> void createGenericArrayTest(Supplier<T> constructor, int count) {
        //elements = new T[count]; //No se puede
        Class<?> type = String.class;

        T instance = constructor.get();
        try {
            type.getDeclaredConstructor(type).newInstance();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
