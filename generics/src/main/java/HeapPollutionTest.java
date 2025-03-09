import java.util.Arrays;

public class HeapPollutionTest {

    public static void main(String[] args) {
        testReferenceToAnObjectOfAnotherParameterizedType();
    }

    @SuppressWarnings("unchecked")
    static void testReferenceToAnObjectOfAnotherParameterizedType() {
        // Uso del tipo genérico Wrapper en crudo
        Wrapper intWrapper = new Wrapper<Integer>(45); //#1

        //@SuppressWarnings("unchecked")
        // Genera un unchecked warning porque el compilador no puede determinar
        // si intWrapper es de tipo Wrapper<String> al haber usado el tipo Wrapper en crudo
        // Compila, per contaminará el heap cuando se ejecute
        Wrapper<String> stringWrapper = intWrapper; // #2 Compila pero con un unchecked warning

        // La contaminación del heap que produce la instrucción #2
        // hace posible que se obtenga una ClassCastException al ejecutar #3
        String str = stringWrapper.get(); //#3
    }

    @SuppressWarnings("unchecked")
    static void testUncheckedCastOperation() {
        Wrapper<? extends Number> numberWrapper = new Wrapper<Long>(1L); // #1
        // Aviso de casting no comprobado (unchecked warning)
        // Compila, pero produce la contaminación del heap cuando se ejecute
        Wrapper<Short> shortWrapper = (Wrapper<Short>) numberWrapper; //#2
        short s = shortWrapper.get(); // #3 generará una ClassCastException en su ejecución
    }

}
