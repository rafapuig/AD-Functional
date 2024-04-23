import java.lang.reflect.Array;

public class GenericArrayTest<T> {

    //Un array de elementos de tipo del parámetro T de la clase genérica
    public T[] elements;

    public GenericArrayTest(int howMany) {

        //Intento de crear un array de elementos del tipo T
        //elements = new T[howMany]; //Error de compilación, T no se conoce en runtime

        /*
        TODAS las referencias al parámetro de tipo genérico son borradas
        cuando el código se compila
        Un array necesita saber su tipo cuando se crea
        Y esta información de tipo no está disponible
         */

        elements = (T[]) new Object[howMany];
    }

    public GenericArrayTest(Class<T> type, int howMany) {
        elements = (T[]) Array.newInstance(type, howMany);
    }

    public T[] getElements() {
        return elements;
    }

    public void setElement(int index, T value) {
        elements[index] = value;
    }

    public T getElement(int index) {
        return elements[index];
    }
}
