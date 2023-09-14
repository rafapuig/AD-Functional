public class WrapperUtilWrong {

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

    // ? puede ser cualquier tipo en cualquier parametro de los 2
    public static double sum(Wrapper<?> n1, Wrapper<?> n2) {
        Number num1 = (Number) n1.get(); //Puede fallar el casting
        Number num2 = (Number) n2.get(); //Puede fallar el casting
        double sum = num1.doubleValue() + num2.doubleValue();
        return sum;
    }

    //Metodo generico
    public static <T> void copy(Wrapper<T> source, Wrapper<T> dest) {
        T value = source.get();
        dest.set(value);
    }



}
