//Esta clase es el ejemplo de como NO se deben hacer las cosas
public class WrapperUtilWrong {

    //Este metodo solamante sirve con objetos con valor del tipo parametrizado Object
    public static void printDetails(Wrapper<Object> wrapper) {
        // Codigo del metodo
        Object value = wrapper.get();
        String className = null;

        if(value != null) {
            className = value.getClass().getName();
        }
        System.out.println("Clase: " + className);
        System.out.println("Valor: " + value);
    }

    // ? puede ser cualquier valor para el parametro
    // Necesitamos asegurar que por lo menos se trata de un Number
    public static double sum(Wrapper<?> n1, Wrapper<?> n2) {
        Number num1 = (Number) n1.get(); //Puede fallar el casting
        Number num2 = (Number) n2.get(); //Puede fallar el casting
        double sum = num1.doubleValue() + num2.doubleValue();
        return sum;
    }

    //Metodo generico
    //No es lo más adecuado pues solo permite copiar en otro
    //Wrapper si el valor del parametro es el mismo
    public static <T> void copy(Wrapper<T> source, Wrapper<T> dest) {
        T value = source.get();
        dest.set(value);
    }



}
