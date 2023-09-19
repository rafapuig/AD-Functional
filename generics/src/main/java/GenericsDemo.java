import model.Person;

import java.util.ArrayList;
import java.util.List;

public class GenericsDemo {

    public static void main(String[] args) {

        objectWrapperTest();
        genericWrapperTest();
        supersubWrapperTest();
        rawTypeTest();

        unboundedWildcardWrong(); //No es lo correcto
        unboundedWildcardTestOK();

        unboundedSumTest(); //No es lo correcto
        upperBoundedSumTest();

        lowerBoundedCopyTestWrong();
        lowerBoundedCopyTest();

        wrapperUtilTest();

        genericConstructorTest();

        genericsRuntimeClassTest();
    }



    static void objectWrapperTest() {
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

    static void genericWrapperTest() {

        Wrapper<String> greetingWrapper = new Wrapper<String>("Hola");
        greetingWrapper.set("Buenos dias"); // Espera un String como argumento
        String greeting = greetingWrapper.get(); // Casting no necesario

        //greetingWrapper.set(new Integer(100)); //Error de compilacion

        Wrapper<Integer> integerWrapper = new Wrapper<>(100);
        int id = integerWrapper.get();
        Integer id1 = integerWrapper.get();

        //integerWrapper.set("Hola"); //Error

        Wrapper<Person> personWrapper = new Wrapper<>(new Person(1, "Armando Bronca"));
        personWrapper.set(new Person(2, "Amador Denador"));
        Person person = personWrapper.get(); // No casting

    }

    static void supersubWrapperTest() {
        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        //objectWrapper = stringWrapper; // Error de compilacion
    }

    static void rawTypeTest() {
        //Uso del tipo generico Wrapper<T> "en crudo" (sin parametro)
        Wrapper rawType = new Wrapper("Hola"); //uncheched warning call

        //Uso como tipo parametrizado con el tipo String
        Wrapper<String> genericType = new Wrapper<>("Adios");

        genericType = rawType; //uchecked warning

        //rawType = genericType;
    }

    static void unboundedWildcardWrong() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        WrapperUtilWrong.printDetails(objectWrapper); //OK

        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        //WrapperUtilWrong.printDetails(stringWrapper); //Error
    }

    static void unboundedWildcardTestOK() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        WrapperUtil.printDetails(objectWrapper); //OK

        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        WrapperUtil.printDetails(stringWrapper); //No Error

        //wildCardWrapper tiene tipo de parametro desconocido
        // (la instancia parametrizada lo tiene, pero por la variable no lo podemos saber)
        Wrapper<?> wildCardWrapper = stringWrapper;
        Wrapper<?> unknownWrapper = new Wrapper<String>("Hola"); //Se puede asignar

        Object obj = unknownWrapper.get(); //OK
        //unknownWrapper.set("Hola"); // No sabemos de que tipo es T ¿No sabemos si T es String
        //unknownWrapper.set(new Object()); // No sabemos de que tipo es T ¿Todo object es un T? NO
        unknownWrapper.set(null); //Null se puede asignar a cualquier referencia
    }

    //Upper-bounded
    static void unboundedSumTest() {
        //Codigo que compila, pero semanticamente no tiene sentido y provoca una excepcion
        try {
            WrapperUtilWrong.sum(new Wrapper<Integer>(125), new Wrapper<String>("Hola"));
        } catch (ClassCastException e) {
            System.out.println(e);
        }
        //Esto ya no compila
        //WrapperUtil.sum(new Wrapper<Integer>(125), new Wrapper<String>("Hola"));
    }

    static void upperBoundedSumTest() {
        Wrapper<Integer> integerWrapper = new Wrapper<>(10);

        //Nos olvidamos de que es el parametro es un Integer pero al menos sabemos que es un Number
        Wrapper<? extends Number> numberWrapper = integerWrapper; // OK

        //numberWrapper.set(10); // No se puede asegurar el tipo T de Wrapper como Integer
        //numberWrapper.set(12.3D); // Ni como double

        Number number = null; // A una variable de tipo por refencia siempre se le puede asignar la referencia null
        //numberWrapper.set(number); // Error, no sabemos el tipo concreto T de Number del parametro
        WrapperUtil.sum(new Wrapper<>(34), new Wrapper<>(3.4D));
    }

    static void lowerBoundedCopyTestWrong() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());;
        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        //WrapperUtilWrong.copy(stringWrapper, objectWrapper); //No funciona (no mismo tipo)

        WrapperUtilWrong.copy(new Wrapper<>("Hola"), stringWrapper);
    }

    static void lowerBoundedCopyTest() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        Wrapper<String> stringWrapper = new Wrapper<>("Hola");

        WrapperUtil.copy(stringWrapper, objectWrapper); //Ahora si funciona

        WrapperUtil.copy(new Wrapper<>("Hola"), objectWrapper);
        System.out.println("objectWrapper.get() = " + objectWrapper.get());
    }

    static void wrapperUtilTest() {
        Wrapper<Integer> n1 = new Wrapper<>(10);
        Wrapper<Double> n2 = new Wrapper<>(15.6);

        //Imprimir informacion
        WrapperUtil.printDetails(n1);
        WrapperUtil.printDetails(n2);

        double sum = WrapperUtil.sum(n1, n2);
        System.out.println("Suma: " + sum);

        Wrapper<? super Number> holder = new Wrapper<Number>(46);
        System.out.println("Original:" + holder.get());

        WrapperUtil.<Double>copy(n2, holder);
        System.out.println("Despues de copiar: " + holder.get());
    }


    static void genericMethodTest() {

    }

    static void genericConstructorTest() {
        GenericConstructorTest<Number> gct =
                new <Integer>GenericConstructorTest<Number>(45);
    }

    static void inferenceTest() {
        List<String> list = new ArrayList<>();
    }

    static void genericsRuntimeClassTest() {
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
