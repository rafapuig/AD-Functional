public class GenericsRuntimeClassTest {

    public static void main(String[] args) {
        // Creamos dos objetos del tipo genérico Wrapper
        Wrapper<String> stringWrapper = new Wrapper<>("Hola"); // Uno parametrizado con String
        Wrapper<Integer> integerWrapper = new Wrapper<>(45);    //Otro parametrizado con Integer

        // Obtenemos el objeto Class de ambos objetos
        Class<?> stringWrapperClass = stringWrapper.getClass();
        Class<?> integerWrapperClass = integerWrapper.getClass();

        // Imprimimos el nombre de ambos
        System.out.println("Class para Wrapper<String> = " + stringWrapperClass.getName());
        System.out.println("Class para Wrapper<Integer> = " + integerWrapperClass.getName());

        // Comprobamos que el objeto Class que representa su tipo en ejecución es el mismo
        // No importa que hayan sido parametrizados con valor del parámetro de tipo T diferente
        System.out.println(
                "(stringWrapperClass == integerWrapperClass) = " + (stringWrapperClass == integerWrapperClass));
    }

}
