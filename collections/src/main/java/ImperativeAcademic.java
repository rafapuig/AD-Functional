import model.academic.Alumno;
import model.academic.Calificacion;
import model.academic.Ciclo;
import model.academic.Modulo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ImperativeAcademic {

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

    static Map<Alumno, Integer> getHorasPendientesByCiclo(
            Collection<Calificacion> calificaciones, Collection<Modulo> modulos, Ciclo ciclo) {

        int horasTotales = getHorasTotales(modulos, ciclo);

        Map<Alumno, Integer> alumnoHorasPendientesMap = new HashMap<>();

        for (Calificacion calificacion : calificaciones) {
            if (!calificacion.modulo().ciclo().equals(ciclo)) continue;
            if (calificacion.nota().isEmpty()) continue;
            if (!(calificacion.nota().orElseThrow().getNumericValue() >= 5)) continue;

            if (!alumnoHorasPendientesMap.containsKey(calificacion.alumno())) {
                alumnoHorasPendientesMap.put(calificacion.alumno(), calificacion.modulo().horas());
            } else {
                int partial = alumnoHorasPendientesMap.get(calificacion.alumno());
                alumnoHorasPendientesMap.put(calificacion.alumno(),
                        partial + calificacion.modulo().horas());
            }

            /*alumnoHorasPendientesMap.merge(
                    calificacion.alumno(),
                    calificacion.modulo().horas(),
                    (partial, horas) -> partial + horas
            );*/
        }

        // And then ... horasSuperadas -> horasTotales - horasSuperadas

        for (Map.Entry<Alumno, Integer> entry : alumnoHorasPendientesMap.entrySet()) {
            int horasSuperadas = entry.getValue();
            entry.setValue(horasTotales - horasSuperadas);
        }

        return alumnoHorasPendientesMap;
    }

}
