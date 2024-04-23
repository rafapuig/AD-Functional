public class WrapperUtil {

    //Misma erasure
    /*public static void printDetails(Wrapper<Object> wrapper) {
        //Aqui el codigo del metodo
    }*/

    public static void printDetails(Wrapper<?> wrapper) {
        //Se puede asignar a un Object
        Object value = wrapper.get();
        String className = null;

        if(value != null) {
            className = value.getClass().getName();
        }
        System.out.println("Clase: " + className);
        System.out.println("Valor: " + value);
    }

    //Upper-bounded wildcards: comodín con limite superior <? extends T
    /*
    Object
      ||
      \/
    Number <-- upper-bound T
      ||
      \/
    Integer
    */
    //Pedimos que el tipo del parámetro sea como mínimo T, por tanto, podrá ser de tipo T o subclases de T

    public static double sum(Wrapper<? extends Number> n1, Wrapper<? extends Number> n2) {
        Number num1 = n1.get(); //Podemos asignar a Number, tenemos la garantía que el tipo desconocido
        Number num2 = n2.get(); //por lo menos, es de tipo Number
        double sum = num1.doubleValue() + num2.doubleValue();
        return sum;
    }

    //(El método copy es un método genérico)
    //Lower-bounded wildcard <? super T>
    //Pedimos que el tipo del parámetro sea como mucho T, por tanto, podrá ser de tipo T o una superclase de T
    public static <T> void copy(Wrapper<T> source, Wrapper<? super T> dest) {
        T value = source.get(); //Si value es de tipo T
        dest.set(value); // entonces se puede asignar a cualquier variable de tipo de T o superclases de T
    }

}
