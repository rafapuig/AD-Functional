package exercises;

public class ClassNamePrinter {

    public static void main(String[] args) {
        print(10);
        print((short) 10);
        print((byte) 10);
        print(10L);
        print(10.f);
        print(10.2);

        print2(10);
        print2((short) 10);
        print2((byte) 10);
        print2(10L);
        print2(10.f);
        print2(10.2);
    }

    public static <T extends Number> void print(T object) {
        String className = object.getClass().getSimpleName();
        System.out.println(className);
    }

    /**
     * Código del metodo print cuando el compilador elimina el parámetro de tipo T durante la compilación
     */
    public static void print2(Number number) {
        String className = number.getClass().getSimpleName();
        System.out.println(className);
    }
}
