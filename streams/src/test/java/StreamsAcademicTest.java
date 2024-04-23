import model.academic.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamsAcademicTest {

    static Stream<Ciclo> provideCiclo() {
        return Stream.of(Ciclos.DAM, Ciclos.DAW);
    }

    static Stream<Arguments> testGetModulosCiclo() {
        return Stream.of(
                Arguments.of(Ciclos.DAM, 16),
                Arguments.of(Ciclos.DAW, 15)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetModulosCiclo(Ciclo ciclo, int expected) {
        List<Modulo> modulos = StreamsAcademic.getModulosCiclo(Modulos.MODULOS, ciclo);

        System.out.println(ciclo.nombre());
        System.out.println("--------------------------------------------------");
        modulos.forEach(modulo -> {
            System.out.print(modulo.nombre() + ": ");
            modulo.horasSemanales()
                    .ifPresentOrElse(
                            op -> System.out.print(op + " horas semanales"),
                            () -> System.out.print(modulo.horas() + " horas anuales"));
            System.out.println();
        });

        System.out.println();
        assertEquals(expected, modulos.size());
    }

    @ParameterizedTest
    @MethodSource("provideCiclo")
    void testGetModulosNames(Ciclo ciclo) {
        String[] names = StreamsAcademic.getModulosNames(Modulos.MODULOS, ciclo);

        System.out.println(ciclo.nombre());
        System.out.println("--------------------------------------------------");
        Arrays.stream(names).forEach(System.out::println);
        System.out.println();
    }


    static Stream<Arguments> providerCiclo_HorasTotalesExpected() {
        return Stream.of(
                Arguments.of(Ciclos.DAM, 2000),
                Arguments.of(Ciclos.DAW, 2000)
        );
    }

    @ParameterizedTest
    @MethodSource("providerCiclo_HorasTotalesExpected")
    void testGetHorasTotales(Ciclo ciclo, int expected) {
        int horas = StreamsAcademic.getHorasTotales(Modulos.MODULOS, ciclo);
        System.out.println("El ciclo " + ciclo.nombre() + " tiene " + horas + " horas.");

        assertEquals(expected, horas);
    }


    @Test
    void testGetHorasSemanalesPrimeroDAM() {
        long total = StreamsAcademic.getHorasSemanalesPrimeroDAM();

        System.out.println("total = " + total);
        assertEquals(30, total);
    }

    @ParameterizedTest
    @MethodSource("provideCiclo")
    void testGetHorasTotalesPrimeroCiclo(Ciclo ciclo) {
        int total = StreamsAcademic.getHorasSemanalesPrimeroCiclo(ciclo);

        System.out.println("total = " + total);
    }


    static Stream<Arguments> provideCiclo_Curso_HorasSemanalesExpected() {
        return Stream.of(
                Arguments.of(Ciclos.DAM, Curso.PRIMERO, 30),
                Arguments.of(Ciclos.DAM, Curso.SEGUNDO, 30),
                Arguments.of(Ciclos.DAW, Curso.PRIMERO, 30),
                Arguments.of(Ciclos.DAW, Curso.SEGUNDO, 30)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCiclo_Curso_HorasSemanalesExpected")
    void testGetHorasSemanales(Ciclo ciclo, Curso curso, int expected) {
        int horas = StreamsAcademic.getHorasSemanales(Modulos.MODULOS, ciclo, curso);

        System.out.println("Total horas semanales en " + curso + " de " + ciclo.abreviatura() + " = " + horas);
        assertEquals(expected, horas);
    }


    static Stream<Arguments> provideCiclo_Curso_First() {
        return Stream.of(
                Arguments.of(Ciclos.DAM, Curso.PRIMERO, Modulos.PRO),
                Arguments.of(Ciclos.DAM, Curso.SEGUNDO, Modulos.FCT),
                Arguments.of(Ciclos.DAW, Curso.PRIMERO, Modulos.DAW_PRO),
                Arguments.of(Ciclos.DAW, Curso.SEGUNDO, Modulos.DAW_FCT)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCiclo_Curso_First")
    void testGetModulosSortedByHoras(Ciclo ciclo, Curso curso, Modulo expectedFirst) {
        List<Modulo> modulos = StreamsAcademic.getModulosSortedByHoras(Modulos.MODULOS, ciclo, curso);

        System.out.println(ciclo.nombre() + " - " + curso.name());
        System.out.println("----------------------------------------------------------------");
        modulos.forEach(modulo -> System.out.println(modulo.nombre() + ": " + modulo.horas() + " horas"));
        System.out.println();

        assertEquals(expectedFirst, modulos.get(0));
    }


    @Test
    void testGetModulosByCicloMap() {
        var result = StreamsAcademic.getModulosByCicloMap(Modulos.MODULOS);

        result.forEach((key, value) -> {
            System.out.println(key.nombre());
            System.out.println("----------------------------------");
            value.forEach(modulo ->
                    System.out.println(modulo.curso() + " - " +
                            modulo.nombre() + ": " +
                            modulo.horas() + " horas"));
            System.out.println();
        });
    }

    @Test
    void testGetModulosByCursoByCicloMap() {
        var result = StreamsAcademic.getModulosByCursoByCicloMap(Modulos.MODULOS);

        result.forEach((ciclo, map) -> {
            System.out.println(ciclo.nombre());
            System.out.println("-------------------------------------------------");
            map.forEach((curso, modulos) -> {
                System.out.println(curso.name());
                modulos.forEach(modulo -> {
                    System.out.println(
                            modulo.nombre() + ": " +
                                    modulo.horas() + " horas");
                });
                System.out.println();
            });
        });

        assertThat(result.keySet(), containsInAnyOrder(Ciclos.DAM, Ciclos.DAW));
        assertThat(result.get(Ciclos.DAM).keySet(), contains(Curso.PRIMERO, Curso.SEGUNDO));
        assertThat(result.get(Ciclos.DAW).keySet(), contains(Curso.PRIMERO, Curso.SEGUNDO));
        assertThat(result.get(Ciclos.DAM).get(Curso.PRIMERO).size(), Matchers.is(7));
        assertThat(result.get(Ciclos.DAM).get(Curso.SEGUNDO).size(), Matchers.is(9));
        assertThat(result.get(Ciclos.DAW).get(Curso.PRIMERO).size(), Matchers.is(7));
        assertThat(result.get(Ciclos.DAW).get(Curso.SEGUNDO).size(), Matchers.is(8));
    }


    static Stream<Arguments> testGetHorasModulosCicloByCurso() {
        return Stream.of(
                Arguments.of(Ciclos.DAM, Map.of(Curso.PRIMERO, 960L, Curso.SEGUNDO, 1040L)),
                Arguments.of(Ciclos.DAW, Map.of(Curso.PRIMERO, 960L, Curso.SEGUNDO, 1040L))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetHorasModulosCicloByCurso(Ciclo ciclo, Map<Curso, Long> expected) {
        var result = StreamsAcademic.getHorasModulosCicloByCurso(Modulos.MODULOS, ciclo);

        System.out.print(ciclo.nombre() + ": ");
        System.out.println(result);

        assertEquals(expected, result);
        assertThat(result, Matchers.is(expected));
    }



    static Stream<Arguments> testGetNotaMediaExpediente() {
        return Stream.of(
                Arguments.of(Alumnos.ARMANDO, OptionalDouble.of(8.633)),
                Arguments.of(Alumnos.BELEN, OptionalDouble.of(6.148)),
                Arguments.of(Alumnos.ESTHER, OptionalDouble.of(7.0)),
                Arguments.of(Alumnos.AMADOR, OptionalDouble.of(6.091))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetNotaMediaExpediente(Alumno alumno, OptionalDouble expected) {
        OptionalDouble average = StreamsAcademic
                .getNotaMediaExpediente(Calificaciones.CALIFICACIONES_SAMPLE, alumno, Ciclos.DAM);

        System.out.println(average);

        assertThat(average.orElse(0), closeTo(expected.orElse(0), 0.0005));
    }

    @Test
    void testGetNotaMediaExpedientesDAM() {
        Map<Alumno, OptionalDouble> map = StreamsAcademic.
                getNotaMediaExpediente(Calificaciones.CALIFICACIONES_SAMPLE, Ciclos.DAM);


        System.out.printf("%-25s%s\n", "Alumno", "Nota media");
        System.out.println("-----------------------------------------");


        DecimalFormat df = new DecimalFormat("#.000");


        map.forEach((alumno, average) -> {
            System.out.println(
                    String.format("%-25s", alumno.nombre() + " " + alumno.apellidos()) +
                            String.format("%10s", average.isPresent() ?
                                    df.format(average.getAsDouble()) : ""));
        });

        assertThat(map.keySet(),
                contains(Alumnos.ARMANDO, Alumnos.ESTHER, Alumnos.BELEN, Alumnos.AMADOR));

        assertThat(map.get(Alumnos.ARMANDO).orElseThrow(), closeTo(8.633, 0.0005));
        assertThat(map.get(Alumnos.ESTHER).orElseThrow(), closeTo(7.0, 0.0005));
        assertThat(map.get(Alumnos.BELEN).orElseThrow(), closeTo(6.148, 0.0005));
        assertThat(map.get(Alumnos.AMADOR).orElseThrow(), closeTo(6.091, 0.0005));
    }


    static Stream<Arguments> testGetHorasPendientes() {
        return Stream.of(
                Arguments.of(Alumnos.ARMANDO, 1040),
                Arguments.of(Alumnos.BELEN, 1136),
                Arguments.of(Alumnos.ESTHER, 1844),
                Arguments.of(Alumnos.AMADOR, 1648)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetHorasPendientes(Alumno alumno, int expected) {
        int horas = StreamsAcademic.getHorasPendientes(
                Calificaciones.CALIFICACIONES_SAMPLE, Modulos.MODULOS, alumno, Ciclos.DAM);

        System.out.println(
                String.format("%-25s", alumno.nombre() + " " + alumno.apellidos()) +
                        String.format("%10s horas", NumberFormat.getNumberInstance().format(horas)));

        assertThat(horas, is(expected));
    }


    @Test
    void testGetHorasPendientesAlumnosDAM() {
        var result = StreamsAcademic
                .getHorasPendientesByCiclo2(
                        Calificaciones.CALIFICACIONES_SAMPLE,
                        Modulos.MODULOS, Ciclos.DAM
                );

        System.out.printf("%-25s%s\n", "Alumno", "Horas pendientes");
        System.out.println("-----------------------------------------");

        result.forEach((alumno, horas) -> {
            System.out.println(
                    String.format("%-25s", alumno.nombre() + " " + alumno.apellidos()) +
                            String.format("%10s", NumberFormat.getNumberInstance().format(horas)));
        });

        assertThat(result.keySet(), containsInAnyOrder(Alumnos.BELEN, Alumnos.ESTHER, Alumnos.AMADOR, Alumnos.ARMANDO));
        assertThat(result.get(Alumnos.ARMANDO), is(1040));
        assertThat(result.get(Alumnos.ESTHER), is(1844));
        assertThat(result.get(Alumnos.AMADOR), is(1648));
        assertThat(result.get(Alumnos.BELEN), is(1136));
    }


    static Stream<Arguments> testGetFilteredStream() {
        return Stream.of(
                Arguments.of(Alumnos.ARMANDO, 7),
                Arguments.of(Alumnos.BELEN, 6),
                Arguments.of(Alumnos.ESTHER, 2),
                Arguments.of(Alumnos.AMADOR, 3)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetFilteredStream(Alumno alumno, int expected) {
        Stream<Calificacion> filteredStream = StreamsAcademic.getCalificacionesSuperadasAlumnoStreamSupplier(
                Calificaciones.CALIFICACIONES_SAMPLE, alumno).get();

        int count = filteredStream.toList().size();

        System.out.println("count = " + count);

        assertThat(count, is(expected));
    }

    static Stream<Arguments> testFractionToDecimal() {
        return Stream.of(
                Arguments.of(5.6, 7, OptionalDouble.of(5.6 / 7)),
                Arguments.of(7.2, 3, OptionalDouble.of(7.2 / 3)),
                Arguments.of(1, 0, OptionalDouble.empty()));
    }

    @ParameterizedTest
    @MethodSource
    void testFractionToDecimal(double numerator, long denominator, OptionalDouble expected) {
        OptionalDouble result = StreamsAcademic.fractionToDecimal(numerator, denominator);

        System.out.println(result);
        assertThat(result, is(expected));
    }




    @Test
    void testAveragingWeighted() {
        record Tarea(String nombre, double puntos, long peso) { }

        Stream<Tarea> tareas = Stream.of(
                new Tarea("Tareas para la casa", 5.0, 20),
                new Tarea("Exámenes cortos",4.7, 25),
                new Tarea("Trabajos en grupo", 4.2, 25),
                new Tarea("Examen final", 3.5, 30)
        );

        OptionalDouble result = tareas.collect(
                StreamsAcademic.averagingWeighted4(
                        Tarea::puntos,
                        Tarea::peso
                ));

        System.out.println(result);
        assertThat(result, is(OptionalDouble.of(4.275)));
    }


}