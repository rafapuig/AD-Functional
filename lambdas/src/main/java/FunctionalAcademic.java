import model.academic.Alumno;
import model.academic.Calificacion;
import model.academic.Ciclo;
import model.academic.Modulo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FunctionalAcademic {


    static int getHorasTotales(Collection<Modulo> modulos, Ciclo ciclo) {

        int suma = 0;

        for (Modulo modulo : modulos) {
            if (modulo.ciclo().equals(ciclo)) {
                int horas = modulo.horas();
                suma = suma + horas;
            }
        }

        return suma;
    }

    static Predicate<Calificacion> calificatesModuloOfCiclo(Ciclo ciclo) {
        return calificacion -> calificacion.modulo().ciclo().equals(ciclo);
    }

    static Predicate<Calificacion> hasNota() {
        return calificacion -> calificacion.nota().isPresent();
    }

    static Predicate<Calificacion> isAprobado() {
        return hasNota().and(cal -> cal.nota().orElseThrow().getNumericValue() >= 5);
    }

    public static Map<Alumno, Integer> getHorasPendientesByCiclo(
            Collection<Calificacion> calificaciones, Ciclo ciclo, Collection<Modulo> modulos) {

        int horasTotales = getHorasTotales(modulos, ciclo);

        Map<Alumno, Integer> alumnoHorasPendientesMap = new HashMap<>();

        calificaciones.forEach(calificacion -> {
            if (calificatesModuloOfCiclo(ciclo).test(calificacion) && isAprobado().test(calificacion)) {

                alumnoHorasPendientesMap.merge(
                        calificacion.alumno(),
                        calificacion.modulo().horas(),
                        Integer::sum
                );
            }
        });

        // And then ... horasSuperadas -> horasTotales - horasSuperadas

        alumnoHorasPendientesMap.forEach((alumno, horasSuperadas) -> {
            alumnoHorasPendientesMap.replace(alumno, horasSuperadas, horasTotales - horasSuperadas);
        });


        return alumnoHorasPendientesMap;
    }


}
