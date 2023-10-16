package es.rafapuig.exercises.personas;

import model.people.Empleado;
import model.people.Empleados;
import model.people.Persona;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public class EmpleadosUtil {

    public static class ComparingBySueldo implements Comparator<Empleado> {
        @Override
        public int compare(Empleado e1, Empleado e2) {
            return Double.compare(e1.getSueldo(), e2.getSueldo());
        }
    }

    public static final Comparator<Empleado> COMPARING_BY_SUEDO = new ComparingBySueldo();

    public static List<Empleado> getAllEmpleadosSortedByCriteria(
            List<Empleado> empleados, Comparator<Empleado> comparator) {

        List<Empleado> sorted = new ArrayList<>(empleados);
        sorted.sort(comparator);
        return sorted;
    }

    public static List<Empleado> filterSueldoSuperior2000(List<Empleado> empleados) {
        List<Empleado> result = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (empleado.getSueldo() >= 2000) {
                result.add(empleado);
            }
        }
        return result;
    }

    public static List<Empleado> filterAntiguedadSuperior10(List<Empleado> empleados) {
        List<Empleado> result = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (ChronoUnit.YEARS.between(empleado.getHireDate(), LocalDate.now()) > 10) {
                result.add(empleado);
            }
        }
        return result;
    }

    public interface EmpleadoPredicate {
        boolean test(Empleado empleado);
    }

    public static class EmpleadoAnteriorAño2000 implements EmpleadoPredicate {

        @Override
        public boolean test(Empleado empleado) {
            return empleado
                    .getHireDate()
                    .isAfter(LocalDate
                            .of(1999, Month.DECEMBER, 31));
        }
    }

    public static EmpleadoPredicate EMPLEADO_ANTERIOR_2000 = new EmpleadoAnteriorAño2000();

    public static List<Empleado> filterByCriteria(
            List<Empleado> empleados, EmpleadoPredicate predicate) {
        List<Empleado> result = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (predicate.test(empleado)) {
                result.add(empleado);
            }
        }
        return result;
    }


    static List<Empleado> getAllEmpleadosSortedByHireDate(List<Empleado> empleados) {
        List<Empleado> sorted = new ArrayList<>(empleados);
        sorted.sort(Comparator.comparing(Empleado::getHireDate));
        return sorted;
    }



    //------------ Sueldo medio hombres y mujeres

    static Map<Persona.Sexo, Double> getSueldoMedioHombresMujeres(List<Empleado> empleados) {

        Map<Persona.Sexo, Double> sueldosMedios = new HashMap<>();
        Map<Persona.Sexo, Integer> contador = new HashMap<>();

        for (Empleado persona : empleados) {
            Persona.Sexo sexo = persona.getSexo();
            if (!sueldosMedios.containsKey(sexo)) {
                sueldosMedios.put(sexo, 0.0);
            }
            if (!contador.containsKey(sexo)) {
                contador.put(sexo, 0);
            }
            double sueldo = persona.getSueldo();
            double acumulado = sueldosMedios.get(sexo);
            sueldosMedios.replace(sexo, acumulado, acumulado + sueldo);
            int count = contador.get(sexo);
            contador.replace(sexo, count + 1);
        }

        for (Persona.Sexo sexo : sueldosMedios.keySet()) {
            double average = sueldosMedios.get(sexo) / contador.get(sexo);
            sueldosMedios.replace(sexo, average);
        }

        return sueldosMedios;
    }

    static Map<Persona.Sexo, Double> getSueldoMedioHombresMujeresImperative(List<Empleado> empleados) {

        Map<Persona.Sexo, List<Double>> sueldosPorSexo = new HashMap<>();

        for (Empleado empleado : empleados) {
            Persona.Sexo sexo = empleado.getSexo();
            if (!sueldosPorSexo.containsKey(sexo)) {
                sueldosPorSexo.put(sexo, new ArrayList<>());
            }
            double sueldo = empleado.getSueldo();
            sueldosPorSexo.get(sexo).add(sueldo);
        }

        Map<Persona.Sexo, Double> result = new HashMap<>();

        for (Persona.Sexo sexo : sueldosPorSexo.keySet()) {
            double sum = 0.0;
            for (Double sueldo : sueldosPorSexo.get(sexo)) {
                sum += sueldo;
            }
            result.put(sexo, sum / sueldosPorSexo.get(sexo).size());
        }
        return result;
    }


    static BiFunction<Persona.Sexo, List<Double>, List<Double>> addSueldoToList(Empleado empleado) {
        return (sexo, sueldos) -> {
            sueldos.add(empleado.getSueldo());
            return sueldos;
        };
    }


    static Map<Persona.Sexo, Double> getSueldoMedioHombresMujeresFunctional(List<Empleado> empleados) {

        Map<Persona.Sexo, List<Double>> sueldosPorSexo = new HashMap<>();

        /*empleados.forEach( empleado ->
            sueldosPorSexo.compute(empleado.getSexo(),
                    (sexo1, sueldos) -> {
                        if (sueldos == null) {
                            sueldos = new ArrayList<>();
                        } else {
                            sueldos.add(empleado.getSueldo());
                        }
                        return sueldos;
                    })
        );*/

        empleados.forEach(empleado -> {
                    sueldosPorSexo.computeIfAbsent(empleado.getSexo(), sexo -> new ArrayList<>());
                    sueldosPorSexo.computeIfPresent(empleado.getSexo(), addSueldoToList(empleado));
                }
        );

        Map<Persona.Sexo, Double> result = new HashMap<>();

        sueldosPorSexo.forEach(
                (sexo, sueldos) -> {
                    result.computeIfAbsent(sexo,
                            sexoKey ->
                            {
                                //No se puede hacer con sueldos.forEach a no ser que se use AtomicReference
                                /* double acumulator = 0.0;
                                for (double sueldo : sueldos) {
                                    acumulator += sueldo;
                                }
                                return acumulator / sueldos.size();
                                */
                                AtomicReference<Double> acumulator = new AtomicReference<>(0.0);
                                sueldos.forEach(sueldo -> acumulator.updateAndGet(v -> v + sueldo));
                                return acumulator.get() / sueldos.size();
                            }

                            //Con streams seria mas simple
                            //sueldos.stream().mapToDouble(sueldo -> sueldo).sum() / sueldos.size()
                    );

                }
        );

        return result;
    }


    //-------------- Empleado peor pagado

    static Empleado getEmpleadoPeorPagado(List<Empleado> empleados) {

        Empleado peorPagado = null;

        for (Empleado empleado : empleados) {
            if (peorPagado == null) {
                peorPagado = empleado;
            } else {
                if (empleado.getSueldo() < peorPagado.getSueldo()) {
                    peorPagado = empleado;
                }
            }
        }
        return peorPagado;
    }

    static Empleado getEmpleadoPeorPagado2(List<Empleado> empleados) {

        Empleado peorPagado = null;

        for (Empleado empleado : empleados) {
            peorPagado = peorPagado == null ?
                    empleado :
                    empleado.getSueldo() < peorPagado.getSueldo() ?
                            empleado :
                            peorPagado;
        }
        return peorPagado;
    }

    static Empleado getEmpleadoPeorPagadoFunctional(List<Empleado> empleados) {

        //Ya existe en el API  Comparator.comparingDouble();
        Function<ToDoubleFunction<Empleado>, Comparator<Empleado>> comparingDouble =
                keyExtractor -> (e1, e2) -> Double.compare(keyExtractor.applyAsDouble(e1), keyExtractor.applyAsDouble(e2));

        BiFunction<Empleado, Comparator<Empleado>, Empleado> minBy = new BiFunction<>() {
            Empleado min = null;

            @Override
            public Empleado apply(Empleado empleado, Comparator<Empleado> comparator) {
                min = min == null ? empleado :
                        comparator.compare(empleado, min) < 0 ? empleado : min;
                return min;
            }
        };

        AtomicReference<Empleado> peorPagado = new AtomicReference<>();
        empleados.forEach(empleado ->
                peorPagado.set(
                        minBy.apply(
                                empleado,
                                comparingDouble.apply(Empleado::getSueldo))));

        return peorPagado.get();
    }


    //------------ Empleado mejor pagado por cada sexo

    static Map<Persona.Sexo, Empleado> getEmpleadoMejorPagadoPorSexo(List<Empleado> empleados) {

        Map<Persona.Sexo, Empleado> map = new HashMap<>();

        for (Empleado empleado : empleados) {

            Persona.Sexo sexo = empleado.getSexo();

            if (!map.containsKey(sexo)) {
                map.put(sexo, empleado);
            } else {
                Empleado mejorPagado = map.get(sexo);
                if (empleado.getSueldo() > mejorPagado.getSueldo()) {
                    map.put(sexo, empleado);
                }
            }
        }
        return map;
    }

    static Map<Persona.Sexo, Empleado> getEmpleadoMejorPagadoPorSexoFunctional(List<Empleado> empleados) {

        Map<Persona.Sexo, Empleado> map = new HashMap<>();

        empleados.forEach(
                empleado -> {
                    map.computeIfPresent(empleado.getSexo(),
                            (sexo, mejorPagado) ->
                                    empleado.getSueldo() > mejorPagado.getSueldo() ? empleado : mejorPagado);

                    map.computeIfAbsent(empleado.getSexo(), sexo -> empleado);
                }
        );
        return map;
    }

    static Map<Persona.Sexo, Empleado> getEmpleadoMejorPagadoPorSexoFunctionalMerge(List<Empleado> empleados) {

        Map<Persona.Sexo, Empleado> map = new HashMap<>();

        empleados.forEach(
                empleado -> {
                    map.merge(
                            empleado.getSexo(),
                            empleado,
                            (mejorPagado, candidato) -> //oldValue, newValue
                                    candidato.getSueldo() > mejorPagado.getSueldo() ? candidato : mejorPagado);
                }
        );
        return map;
    }

    static Map<Persona.Sexo, Empleado> getEmpleadoMejorPagadoPorSexoFunctionalCompute(List<Empleado> empleados) {

        Map<Persona.Sexo, Empleado> map = new HashMap<>();

        empleados.forEach(
                empleado -> {
                    map.compute(
                            empleado.getSexo(),
                            (sexo, mejorPagado) ->
                                    (mejorPagado == null) ?
                                            empleado :
                                            empleado.getSueldo() > mejorPagado.getSueldo() ?
                                                    empleado :
                                                    mejorPagado);

                }
        );
        return map;
    }

}
