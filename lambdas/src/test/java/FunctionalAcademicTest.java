import model.academic.Alumnos;
import model.academic.Calificaciones;
import model.academic.Ciclos;
import model.academic.Modulos;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;


public class FunctionalAcademicTest {

    @Test
    void testGetHorasPendientesAlumnosDAM() {
        var result = FunctionalAcademic
                .getHorasPendientesByCiclo(
                        Calificaciones.CALIFICACIONES_SAMPLE,
                        Ciclos.DAM,
                        Modulos.MODULOS);

        System.out.printf("%-25s%s\n", "Alumno","Horas pendientes");
        System.out.println("-----------------------------------------");

        result.forEach((alumno, horas) -> {
            System.out.println(
                    String.format("%-25s", alumno.nombre() + " " + alumno.apellidos()) +
                            String.format( "%10s",  NumberFormat.getNumberInstance().format(horas)));
        });

        assertThat(result.keySet(), containsInAnyOrder(Alumnos.BELEN, Alumnos.ESTHER, Alumnos.AMADOR, Alumnos.ARMANDO));
        assertThat(result.get(Alumnos.ARMANDO), is(1040));
        assertThat(result.get(Alumnos.ESTHER), is(1844));
        assertThat(result.get(Alumnos.AMADOR), is(1648));
        assertThat(result.get(Alumnos.BELEN), is(1136));
    }
}
