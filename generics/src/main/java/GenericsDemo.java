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

        /////genericConstructorTest();

        //////genericsRuntimeClassTest();
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

        //greetingWrapper.set(new Integer(100)); //Error de compilación

        Wrapper<Integer> integerWrapper = new Wrapper<>(100);
        int id = integerWrapper.get().intValue();
        Integer id1 = integerWrapper.get();

        //integerWrapper.set("Hola"); //Error

        Wrapper<Person> personWrapper = new Wrapper<>(new Person(1, "Armando Bronca"));
        personWrapper.set(new Person(2, "Amador Denador"));
        Person person = personWrapper.get(); // No casting
    }

    //Aunque String es subclase de Object
    //y se puede asignar una referencia String a una variable Object
    //Entre un Wrapper<String> y un Wrapper<Object> no hay ninguna relación
    //Y no se puede asignar un Wrapper<String> a un Wrapper<Object> aunque String derive de Object

    static void supersubWrapperTest() {
        String gretting = "Hola";
        Object object = gretting;   //upcasting siempre es válido

        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        //objectWrapper = stringWrapper; // Error de compilacion, no es upcasting
    }

    static void rawTypeTest() {
        //Uso del tipo generico Wrapper<T> "en crudo" (sin parametro)
        Wrapper rawType = new Wrapper("Hola"); //unchecked warning call

        //Uso como tipo parametrizado con el tipo String
        Wrapper<String> genericType = new Wrapper<>("Adios");

        genericType = rawType; //unchecked warning

        //rawType = genericType;
    }

    //El método printDetails espera un Wrapper<Object>
    //Si se proporciona como argumento de llamada un Wrapper de cualquier otro tipo (String, por ejemplo)
    //Se produce un error de compilación
    static void unboundedWildcardWrong() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        WrapperUtilWrong.printDetails(objectWrapper); //OK

        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        //WrapperUtilWrong.printDetails(stringWrapper); //Error de compilación
    }

    static void unboundedWildcardTestOK() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object());
        WrapperUtil.printDetails(objectWrapper); //OK

        Wrapper<String> stringWrapper = new Wrapper<>("Hola");
        WrapperUtil.printDetails(stringWrapper); //No Error

        //La variable wildCardWrapper es de tipo genérico de comodín.
        //wildCardWrapper tiene tipo de parámetro desconocido (Comodín / wildcard)
        // (la instancia parametrizada lo tiene (tipo), pero por la variable no lo podemos saber)
        Wrapper<?> wildCardWrapper = stringWrapper; //Ahora T pasa a ser desconocido
        Wrapper<?> unknownWrapper = new Wrapper<String>("Hola"); //Se puede asignar


        //String str = unknownWrapper.get(); //Error compilación. No se sabe de que tipo es T
        Object obj = unknownWrapper.get(); //OK, sea lo que sea T seguro que se puede asignar a Object
        //unknownWrapper.set("Hola"); // No sabemos de que tipo es T - No sabemos si T es String
        //unknownWrapper.set(new Object()); // No sabemos de que tipo es T ¿Todo object es un T? NO
        unknownWrapper.set(null); //OK, Null se puede asignar a cualquier referencia
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

        //numberWrapper.set(10); // No se puede asegurar el tipo T de numberWrapper como Integer
        //numberWrapper.set(12.3D); // Ni como double

        Number number = null; // A una variable de tipo por referencia siempre se le puede asignar la referencia null
        number = 3.12;
        //numberWrapper.set(number); // Error, no sabemos el tipo concreto T de numberWrapper
        // (p.e. T podría ser Integer y no todo Number es un Integer)

        WrapperUtil.sum(new Wrapper<>(34), new Wrapper<>(3.4D));
    }

    static void lowerBoundedCopyTestWrong() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object()); //destination
        Wrapper<String> stringWrapper = new Wrapper<>("Hola");  //source

        //WrapperUtilWrong.copy(stringWrapper, objectWrapper); //No funciona (no mismo tipo T)

        WrapperUtilWrong.copy(new Wrapper<>("Hola"), stringWrapper);
    }

    static void lowerBoundedCopyTest() {
        Wrapper<Object> objectWrapper = new Wrapper<>(new Object()); //destino
        Wrapper<String> stringWrapper = new Wrapper<>("Hola");  //origen

        WrapperUtil.copy(stringWrapper, objectWrapper); //Ahora sí funciona

        WrapperUtil.copy(new Wrapper<>("Hola"), objectWrapper);
        System.out.println("objectWrapper.get() = " + objectWrapper.get());
    }

    static void wrapperUtilTest() {
        Wrapper<Integer> n1 = new Wrapper<>(10);
        Wrapper<Double> n2 = new Wrapper<>(15.6);

        //Imprimir información
        WrapperUtil.printDetails(n1);
        WrapperUtil.printDetails(n2);

        double sum = WrapperUtil.sum(n1, n2);
        System.out.println("Suma: " + sum);

        Wrapper<? super Number> holder = new Wrapper<Number>(46);
        System.out.println("Original:" + holder.get());

        WrapperUtil.<Double>copy(n2, holder);
        System.out.println("Despues de copiar: " + holder.get());
    }


}
