import model.academic.Modulos;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestModulos {

    @Test
    void testModuloBuilder() {
        assertEquals(Modulos.BDA.horas(), 5 * 32);
        assertEquals(Modulos.BDA.horasSemanales().getAsInt(), 5);
        assertEquals(400, Modulos.FCT.horas());
        assertEquals(OptionalInt.empty(), Modulos.FCT.horasSemanales());
    }

    @Test
    void testModulosSet() {
        System.out.println(Modulos.MODULOS);
    }

    @Test
    void testModulosMap() {
        Modulos.MODULOS_MAP.forEach((key, value) ->
                System.out.println(
                        key.ciclo().abreviatura() + "/" +
                                key.abreviatura() + " - " +
                                value.nombre()))
        ;

    }
}
