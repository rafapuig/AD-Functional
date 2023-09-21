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

    public static double sum(Wrapper<? extends Number> n1, Wrapper<? extends Number> n2) {
        Number num1 = n1.get();
        Number num2 = n2.get();
        double sum = num1.doubleValue() + num2.doubleValue();
        return sum;
    }

    //Metodo generico
    public static <T> void copy(Wrapper<T> source, Wrapper<? super T> dest) {
        T value = source.get();
        dest.set(value);
    }

}
