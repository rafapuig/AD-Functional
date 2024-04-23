import functional.Mapper;
import org.junit.jupiter.api.Test;

public class FunctionalTest {

    public static <T> void printMapping(T[] from, int[] to) {
        for (int i = 0; i < from.length; i++) {
            System.out.println(from[i] + " mapeado a " + to[i]);
        }
    }

    //--- INTERFACES FUNCIONALES GENÉRICOS

    //Métodos para probar la interfaz funcional genérica Mapper

    @Test
    void mapperTest1() {
        System.out.println("Mapear nombres a su longitud:");

        String[] names = {"Rafael", "Raul", "Emilio", "Ramon"};

        int[] lengthMapping = Mapper.mapToInt(
                names,  //Lista de Strings
                name -> name.length() //Expresión lambda, entra un String sale un int
        );

        printMapping(names, lengthMapping);
    }

    @Test
    void mapperTest2() {
        System.out.println("Mapear enteros a sus cuadrados:");

        Integer[] numbers = {1, 2, 3, 4};

        int[] countMapping = Mapper.mapToInt(numbers, n -> n * n);

        printMapping(numbers, countMapping);
    }

}
