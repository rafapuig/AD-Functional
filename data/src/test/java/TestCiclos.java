import model.academic.Ciclos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCiclos {
    @Test
    void testCiclosMap() {
        System.out.println(Ciclos.CICLOS_MAP);
        assertEquals(3, Ciclos.CICLOS_MAP.entrySet().size());

        assertEquals(Ciclos.CICLOS_BY_ABREVIATURA, Ciclos.CICLOS_MAP);
    }
}
