import model.academic.Nota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Class for Nota Acedemica")
public class TestNota {

    @Test
    void testNota() {
        assertEquals(Nota.MH.toString(), Nota.of(11).toString());
        assertEquals(Nota.NE.toString(), Nota.of(null).toString());
        assertEquals(Nota.CERO.toString(), Nota.of(0).toString());
        assertEquals(Nota.CINCO.toString(), Nota.of(5).toString());
        assertEquals(Nota.DIEZ.toString(), Nota.of(10).toString());
    }

    @Test
    void testNotas() {
        List<Nota> notas = List.of(
                Nota.of(7),
                Nota.of(null),
                Nota.of(11));

        System.out.println(notas);
    }

}
