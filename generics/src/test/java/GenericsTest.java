import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenericsTest {

    @Test
    void objectWrapperTest() {
        ObjectWrapper stringWrapper = new ObjectWrapper("Hola");
        stringWrapper.set("Adios");
        String s = (String) stringWrapper.get(); //Necesario el casting

        stringWrapper.set(Integer.valueOf(100));
        try {
            s = (String) stringWrapper.get();

        } catch (ClassCastException e) {
            System.out.println(e.getClass().getCanonicalName());
            System.out.println(e.getMessage());
        }
    }

    //La implementación de tipos genéricos es compatible hacia atrás.
    //Si una clase no genérica se reescribe para ser genérica,
    //el código existente que usa la versión no genérica sigue funcionando.
    //La versión no genérica de un tipo genérico se denomina tipo en crudo (raw)
    //Si se usan tipos en crudo el compilador genera "unchecked warnings"

    @Test
    void rawTypeTest() {
        //Uso del tipo genérico Wrapper<T> "en crudo" (sin parámetro)
        Wrapper rawType = new Wrapper("Hola"); //unchecked warning call

        //Uso como tipo parametrizado con el tipo String
        Wrapper<String> genericType = new Wrapper<>("Adios");

        //Asignar el tipo en crudo al tipo parametrizado
        genericType = rawType; //unchecked warning

        //Asignar el tipo parametrizado al tipo en crudo
        rawType = genericType;
    }


    //---- MÉTODOS Y CONSTRUCTORES GENÉRICOS ---------------

    @Test
    void genericMethodTest() {

        GenericMethodTest<String> t = new GenericMethodTest<>();
        Wrapper<Integer> iw1 = new Wrapper<>(201);
        Wrapper<Integer> iw2 = new Wrapper<>(202);

        //Especificar que Integer es el tipo del parámetro V
        t.<Integer>m1(iw1, iw2, "Hola");

        //Dejar que el compilador deduzca el tipo del parámetro V
        t.m1(iw1, iw2, "Hola");
    }

    //El compilador puede inferir el valor del parámetro de tipo
    //del constructor genérico examinando los argumentos
    //Para especificar el tipo del parámetro para un constructor
    //lo hacemos colocando el tipo dentro de <> entre el operador new
    //y el nombre del constructor
    @Test
    void genericConstructorTest() {
        //Especificar el tipo del parámetro del constructor
        GenericConstructorTest<Number> egct =
                new <Double>GenericConstructorTest(
                        Double.valueOf(3.1415));

        //Dejar que el compilador deduzca el tipo del parámetro U
        GenericConstructorTest<Number> gct =
                new <Integer>GenericConstructorTest<Number>(45);
    }


    //--- INFERENCIA DEL TIPO DEL PARÁMETRO EN LA CREACIÓN DE OBJETOS DE UN TIPO GENÉRICO --

    //En muchos casos,
    //el compilador puede inferir el valor del parámetro de tipo
    //en la expresión de creación de un objeto de tipo genérico
    //En ese caso, se usa <> (operador diamante)
    @Test
    void inferenceTest() {
        List<String> list1 = new ArrayList<String>();

        //Inferencia del tipo en la creación del objeto
        List<String> list2 = new ArrayList<>(); //<> vacío: diamante

        //No es lo mismo usar <> vacío que no poner nada
        List<String> list3 = new ArrayList(); //Uso del tipo crudo
    }


    //-------------- GENÉRICOS Y ARRAYS --------------------------------------------------

    @Test
    void testGenericItemsArray() {

        //Generar un array de elementos del tipo del parámetro T
        GenericArrayTest<Integer> ga = new GenericArrayTest<>(5);

        /*
        Mediante los métodos get y set de la clase
        si se puede obtener o establecer el valor de un elemento del array
         */
        ga.setElement(0, 100);
        System.out.println(ga.getElement(0));

        // Intento de obtener el valor de un elemento del array
        Executable code = () ->
                System.out.println(ga.getElements()[0]);

        try {
            code.execute();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        assertThrows(ClassCastException.class, code);

        // Intento de asignación de un valor a un elemento del array
        code = () ->
                ga.getElements()[1] = 2;

        try {
            code.execute();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        assertThrows(ClassCastException.class, code);


        code = () -> ga.elements[1] = Integer.valueOf(100);
        assertThrows(ClassCastException.class, code);

        code = () -> System.out.println(ga.elements[1]);
        assertThrows(ClassCastException.class, code);


        /*
        Para que se pueda crear el array de elementos del tipo T
        La información del tipo genérico la tenemos que proporcionar por via de la reflexión
        haciendo uso de la class info, para que de esta manera el valor del tipo esté disponible en runtime
         */
        GenericArrayTest<String> ga2 = new GenericArrayTest<>(String.class, 5);
        ga2.getElements()[0] = "Hola"; //Ya no causa excepción
        System.out.println(ga2.getElement(0));
    }

    /**
     * Arrays cuyo tipo de elementos es un tipo genérico (clase o interface genérica)
     * Es decir, elementos de un tipo que declara un parámetro de tipo.
     */
    @Test
    void testGenericTypeArray() {
        /*
        Los elementos del array swa son de tipo Wrapper<String>
        Wrapper es un clase genérica, por tanto, los elementos del array son de un tipo genérico
        El array es un array cuyo tipo es un tipo genérico (array de genéricos)
         */
        Wrapper<String>[] swa = null;

        /*
        No se puede crear un array de elementos de un tipo genérico (array de genéricos)
        porque el compilador no puede garantizar la seguridad de tipos en la asignación del array;
         */
        //swa = new Wrapper<String>[10]; //Error de compilación (en tiempo de ejecución equivale a new Wrapper[10] en crudo
        //swa[0] = new Wrapper<String>("Hola");

        /* Sí que está permitido crear un array de elementos con la wildcard */
        Wrapper<?>[] anotherArray = new Wrapper<?>[10]; //OK

        /* Pero entonces el array puede contener elementos genéricos de cualquier valor del tipo del parámetro */
        anotherArray[0] = new Wrapper<>("Hola");
        anotherArray[1] = new Wrapper<>(123);
        anotherArray[2] = new Wrapper<String>("Adios");
        anotherArray[3] = new Wrapper<Integer>(100);

        /*
        Podemos asignar a una variable de tipo genérico sin wildcard
        Hay que realizar un casting que no chequeará el compilador
        Pero ya podemos evitar las asignaciones que en el ejemplo anterior si se podían
         */
        Wrapper<String>[] stringWrapperArray1 = (Wrapper<String>[]) new Wrapper<?>[10]; //Un casting no chequeado
        stringWrapperArray1[0] = new Wrapper<>("Hola");
        //stringWrapperArray1[1] = new Wrapper<>(123); //Error detectado en compilación

        /*
        (También) Se puede crear un array de elementos de tipo genérico
        mediante el método newInstance de la clase Array indicando el tipo de elementos del array y el tamaño
         */

        Wrapper<String>[] stringWrapperArray =
                (Wrapper<String>[]) Array.newInstance(Wrapper.class, 10); //Unchecked warning

        stringWrapperArray[0] = new Wrapper<>("Hola");
        //stringWrapperArray[1] = new Wrapper<>(123); //Comprobación de tipos en tiempo de compilación

        Object[] objArray = stringWrapperArray;

        Executable code = () ->
                objArray[0] = new Object(); //Warning: se provocará un ArrayStoreException en runtime

        assertThrows(ArrayStoreException.class, code);


        objArray[1] = new Wrapper<>("Adios");
        objArray[2] = new Wrapper<>(123); //Podemos hacer esta asignación pero traerá problemas

        //Como no hay comprobación de tipos en tiempo de compilación este error se detecta en runtime
        code = () ->
                System.out.println(stringWrapperArray[2].get());

        assertThrows(ClassCastException.class, code);
    }



    //--- TIPO EN RUNTIME DE LOS OBJETOS GENÉRICOS ---------------

    @Test
    void genericsRuntimeClassTest() {
        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        Wrapper<Integer> integerWrapper = new Wrapper<>(45);

        Class<?> stringWrapperClass = stringWrapper.getClass();
        Class<?> integerWrapperClass = integerWrapper.getClass();

        System.out.println("Class para Wrapper<String> = " + stringWrapperClass.getName());
        System.out.println("Class para Wrapper<Integer> = " + integerWrapperClass.getName());

        System.out.println(
                "(stringWrapperClass == integerWrapperClass) = " + (stringWrapperClass == integerWrapperClass));
    }
}