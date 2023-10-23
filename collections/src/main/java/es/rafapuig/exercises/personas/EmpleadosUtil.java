package es.rafapuig.exercises.personas;

import model.people.Empleado;
import model.people.Persona;

import static model.people.Persona.Sexo;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

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

    static Map<Sexo, Double> getSueldoMedioHombresMujeres(List<Empleado> empleados) {

        Map<Sexo, Double> sueldosMedios = new HashMap<>();
        Map<Sexo, Integer> contador = new HashMap<>();

        for (Empleado persona : empleados) {
            Sexo sexo = persona.getSexo();
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

    static Map<Sexo, Double> getSueldoMedioHombresMujeresImperative(List<Empleado> empleados) {

        Map<Sexo, List<Double>> sueldosPorSexo = new HashMap<>();

        for (Empleado empleado : empleados) {
            Sexo sexo = empleado.getSexo();
            if (!sueldosPorSexo.containsKey(sexo)) {
                sueldosPorSexo.put(sexo, new ArrayList<>());
            }
            double sueldo = empleado.getSueldo();
            sueldosPorSexo.get(sexo).add(sueldo);
        }

        Map<Sexo, Double> result = new HashMap<>();

        for (Sexo sexo : sueldosPorSexo.keySet()) {
            double sum = 0.0;
            for (Double sueldo : sueldosPorSexo.get(sexo)) {
                sum += sueldo;
            }
            result.put(sexo, sum / sueldosPorSexo.get(sexo).size());
        }
        return result;
    }


    //Esto es una closure
    //La funcion devuelve una expresion lambda que ha capturado por el ambito lexico el valor del parametro
    //de entrada empleado del metodo addSueldoToList
    static BiFunction<Sexo, List<Double>, List<Double>> addSueldoToList(Empleado empleado) {
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


    static <T, K extends Comparable<K>> T maxBy(Function<T, K> keyExtractor, T t1, T t2) {
        return (keyExtractor.apply(t1)).compareTo(keyExtractor.apply(t2)) > 0 ? t1 : t2;
    }

    //En version Closure, devuelve la expresion lambda
    static <T, K extends Comparable<K>> BiFunction<T, T, T> maxBy(Function<T, K> keyExtractor) {
        return (t1, t2) -> (keyExtractor.apply(t1)).compareTo(keyExtractor.apply(t2)) > 0 ? t1 : t2;
    }

    static Map<Persona.Sexo, Empleado> getEmpleadoMejorPagadoPorSexoFunctional(List<Empleado> empleados) {

        Map<Persona.Sexo, Empleado> map = new HashMap<>();

        empleados.forEach(
                empleado -> {
                    map.computeIfPresent(empleado.getSexo(),
                            (sexo, mejorPagado) -> maxBy(Empleado::getSueldo, empleado, mejorPagado));

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
                                    //maxBy(Empleado::getSueldo, mejorPagado, candidato));
                                    maxBy(Empleado::getSueldo).apply(mejorPagado, candidato));
                    //candidato.getSueldo() > mejorPagado.getSueldo() ? candidato : mejorPagado);
                }
        );
        return map;
    }

    /**
     * Obtiene el empleado mejor pagado por cada sexo mediante programación funcional y
     * haciendo uso del método de orden superior compute() de la clase Map<K,V>
     *
     * @param empleados
     * @return
     */

    static Map<Persona.Sexo, Empleado> getEmpleadoMejorPagadoPorSexoFunctionalCompute(List<Empleado> empleados) {

        //Esta función recibe una función que recibe un Empleado y devuelve un Double
        //Y devuelve un BinaryOperator
        //Es una expresión lambda que genera otra expresión lambda
        Function<ToDoubleFunction<Empleado>, BinaryOperator<Empleado>> max =
                (keyExtractor) -> (e1, e2) -> Double.compare(
                        keyExtractor.applyAsDouble(e1), keyExtractor.applyAsDouble(e2)) > 0 ? e1 : e2;

        Map<Persona.Sexo, Empleado> map = new HashMap<>();

        empleados.forEach(
                empleado -> {
                    map.compute(
                            empleado.getSexo(),
                            (sexo, mejorPagado) -> // entradas: la clave y valor actual asociado a la clave
                                    (mejorPagado == null) ? //Si el valor actual es null es porque no existe
                                            empleado :  //el nuevo valor asociado a la clave es el empleado
                                            //Si no, será el mejor pagado entre empleado y el mejorPagado
                                            /* empleado.getSueldo() > mejorPagado.getSueldo() ?
                                                    empleado :
                                                    mejorPagado*/
                                            //maxBy(Empleado::getSueldo).apply(mejorPagado, empleado)
                                            max.apply(Empleado::getSueldo).apply(mejorPagado, empleado));

                }
        );
        return map;
    }

    //TODO
    static void getSumaSueldos(List<Empleado> empleados) {

        BiFunction<Double, Empleado, Function<Empleado, Double>> accumulator =
                (d, e) -> (e1) -> d + e.getSueldo() + e1.getSueldo();

        empleados.forEach(empleado -> {
            accumulator.apply(0.0, empleado).apply(empleado);
        });

    }


}
