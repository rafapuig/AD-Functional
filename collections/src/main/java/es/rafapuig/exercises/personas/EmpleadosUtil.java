package es.rafapuig.exercises.personas;

import model.people.Empleado;
import model.people.Persona;

import static model.people.Persona.Sexo;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collector;

public class EmpleadosUtil {


    //-------------- FILTRADO -----------------------------------------------

    public static List<Empleado> filterSueldoSuperior2000(List<Empleado> empleados) {
        List<Empleado> result = new ArrayList<>(); // Creamos la colección / acumulador del resultado
        for (Empleado empleado : empleados) {   // Iteramos la colección fuente
            if (empleado.getSueldo() >= 2000) { // Comprobamos si el elemento pasa el filtro
                result.add(empleado);           // Si lo cumple, lo acumulamos al resultado
            }
        }
        return result;
    }

    /**
     * Obtiene una lista de empleados cuya antigüedad es superior a 10 años
     *
     * @param empleados
     * @return
     */
    // En este método vamos a obtener una lista filtrada de empleados cambiando el criterio respecto
    // del método anterior, la estructura del código es prácticamente la misma que antes
    // solamente se diferencian en la condición especificada en la sentencia if
    public static List<Empleado> filterAntiguedadSuperior10(List<Empleado> empleados) {
        List<Empleado> result = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (empleado.getAntiguedad() > 10) {    //Solamente cambia el filtro respecto al código anterior
                result.add(empleado);
            }
        }
        return result;
    }

    // Podemos encapsular el código de un filtro o predicado y hacerlo intercambiable por otros
    // si el código de estos filtros se define implementando una interfaz común
    public interface EmpleadoPredicate {
        boolean test(Empleado empleado);
    }

    // Por ejemplo, para filtrar a los empleados contratados antes del año 2000
    // Implementamos la interfaz EmpleadoPredicate mediante la clase EmpleadoAnteriorAño2000
    public static class EmpleadoAnteriorAño2000 implements EmpleadoPredicate {

        @Override
        public boolean test(Empleado empleado) {
            return empleado
                    .getHireDate()
                    .isAfter(LocalDate
                            .of(1999, Month.DECEMBER, 31));
        }
    }

    // Ahora, creamos una instancia de la clase EmpleadoAnteriorAño2000
    public static EmpleadoPredicate EMPLEADO_ANTERIOR_2000 = new EmpleadoAnteriorAño2000();

    /**
     * Obtiene una lista de empleados filtrada aplicando el criterio que determina el tipo concreto
     * del objeto pasado como una instancia que implementa la interfaz EmpleadoPredicate
     *
     * @param empleados
     * @param predicate referencia a un objeto que implementa la interfaz EmpleadoPredicate
     * @return
     */
    // Y ya podemos escribir el método que obtiene una lista filtrada según sea la clase de la instancia
    // que implementa la interfaz EmpleadoPredicate pasada como argumento del segundo parámetro
    // La estructura del código, que crea la colección que acumula el resultado, la iteración de los
    // elementos de la colección origen y el filtrado permanecen.
    // La firma del método se amplía con segundo parámetro que parametriza el comportamiento del método
    // Es decir, especifica el código concreto con el que se aplicará el filtrado.
    public static List<Empleado> filterBy(List<Empleado> empleados, EmpleadoPredicate predicate) {
        List<Empleado> result = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (predicate.test(empleado)) { //LLamada al método test de interfaz EmpleadoPredicate (filtrado)
                result.add(empleado);
            }
        }
        return result;
    }


    // ------------------------------- ORDENACIÓN ------------------------------------------------

    /**
     * Obtener la lista de empleados ordenados por fecha de contratación por la empresa
     *
     * @param empleados
     * @return
     */
    static List<Empleado> getAllEmpleadosSortedByHireDate_Imperative(List<Empleado> empleados) {
        //Creamos una copia de la lista, para no modificar la original
        List<Empleado> sorted = new ArrayList<>(empleados);

        Comparator<Empleado> byHireDateComparator = new Comparator<Empleado>() {
            @Override
            public int compare(Empleado e1, Empleado e2) {
                return e1.getHireDate().compareTo(e2.getHireDate());
            }
        };

        sorted.sort(byHireDateComparator); //Ordenamos la lista con el comparador de empleados
        return sorted;
    }

    static List<Empleado> getAllEmpleadosSortedByHireDate_Functional(List<Empleado> empleados) {
        List<Empleado> sorted = new ArrayList<>(empleados);
        sorted.sort(Comparator.comparing(Empleado::getHireDate));
        return sorted;
    }


    //------- Ordenar por un comparador (personalizar para ordenar por sueldo)

    public static class ComparingBySueldo implements Comparator<Empleado> {
        @Override
        public int compare(Empleado e1, Empleado e2) {
            return Double.compare(e1.getSueldo(), e2.getSueldo());
        }
    }

    public static final Comparator<Empleado> COMPARING_BY_SUELDO = new ComparingBySueldo();

    public static List<Empleado> getAllEmpleadosSortedByCriteria(
            List<Empleado> empleados, Comparator<Empleado> comparator) {

        List<Empleado> sorted = new ArrayList<>(empleados);
        sorted.sort(comparator);
        return sorted;
    }


    // ----------- AGRUPAMIENTO (Mapas) ----------------------------------------

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
    //El método devuelve una expresión lambda de tipo BiFunction
    // que ha capturado por el ámbito lexico el valor del parámetro de entrada "empleado"
    // del método addSueldoToList
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


    static Map<Persona.Sexo, Double> getSueldoMedioHombresMujeresFunctional_v2(List<Empleado> empleados) {

        Collector<Empleado, Map<Sexo, List<Double>>, Map<Sexo, Double>> sueldosPorSexoCollector = new Collector<Empleado, Map<Sexo, List<Double>>, Map<Sexo, Double>>() {
            @Override
            public Supplier<Map<Sexo, List<Double>>> supplier() {
                return HashMap::new;
            }

            @Override
            public BiConsumer<Map<Sexo, List<Double>>, Empleado> accumulator() {
                return (map, empleado) -> {
                    map.computeIfAbsent(empleado.getSexo(), sexo -> new ArrayList<Double>());
                    map.computeIfPresent(empleado.getSexo(), addSueldoToList(empleado));
                };
            }

            @Override
            public BinaryOperator<Map<Sexo, List<Double>>> combiner() {
                return null;
            }

            @Override
            public Function<Map<Sexo, List<Double>>, Map<Sexo, Double>> finisher() {

                return sueldosPorSexo -> {
                    Map<Sexo, Double> result = new HashMap<>();

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
                };
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of();
            }
        };

        var listSueldosBySexoMap = sueldosPorSexoCollector.supplier().get();

        empleados.forEach(empleado ->
                sueldosPorSexoCollector.accumulator().accept(listSueldosBySexoMap, empleado));

        return sueldosPorSexoCollector.finisher().apply(listSueldosBySexoMap);


    }


    // ------------- FIND (Min, Max) ------------------------------------------------

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

    /**
     * Devuelve entre dos elementos de tipo T cuál de ellos se considera menor en función de un comparador
     *
     * @param a          primer elemento
     * @param b          segundo elemento
     * @param comparator comparador que servirá para determinar cuál de ellos es menor
     * @param <T>
     * @return el menor de ambos datos
     */
    static <T> T minBy(T a, T b, Comparator<T> comparator) {
        return comparator.compare(a, b) <= 0 ? a : b;
    }

    // Tenemos tambien BinaryOperator.minBy

    static <T> BinaryOperator<T> minBy(Comparator<T> comparator) {
        return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
    }


    static Empleado getEmpleadoMinimoSegun(List<Empleado> empleados, Comparator<Empleado> comparator) {

        Empleado minimo = null;

        for (Empleado empleado : empleados) {
            minimo = minimo == null ?
                    empleado :
                    minBy(minimo, empleado, comparator); // Aplicamos el comparador para obtener el mínimo
        }
        return minimo;
    }

    static Empleado getEmpleadoMinimoSegun2(List<Empleado> empleados, Comparator<Empleado> comparator) {

        Empleado minimo = null;

        for (Empleado empleado : empleados) {
            minimo = minimo == null ?
                    empleado :
                    minBy(comparator).apply(empleado, minimo); // Aplicamos el comparador para obtener el mínimo
        }
        return minimo;
    }

    static Empleado getEmpleadoPeorPagado3(List<Empleado> empleados) {
        return getEmpleadoMinimoSegun(
                empleados,
                Comparator.comparing(new Function<Empleado, Double>() {
                    @Override
                    public Double apply(Empleado empleado) {
                        return empleado.getSueldo();
                    }
                }));
    }

    // --Funcional


    static Empleado getEmpleadoPeorPagadoFunctional(List<Empleado> empleados) {

        //Ya existe en el API: Comparator.comparingDouble();
        // Una función de orden superior, recibe una función (ToDouble) y devuelve una función (Comparator)
        Function<ToDoubleFunction<Empleado>, Comparator<Empleado>> comparingDouble =
                keyExtractor ->
                        (e1, e2) -> Double.compare(
                                keyExtractor.applyAsDouble(e1),
                                keyExtractor.applyAsDouble(e2));

        // Esto no se recomienda en PF
        // El resultado de esta función no depende exclusivamente de los datos de entrada
        // Esta función tiene estado (memoria), y se considera, por tanto, impura
        // La instancia de tipo anónimo que implementa la interfaz BiFunction consta
        // del campo min que almacena el estado de la instancia y que puede mutar mediante
        // las llamadas al método apply
        BiFunction<Empleado, Comparator<Empleado>, Empleado> minBy = new BiFunction<>() {
            Empleado min = null;

            @Override
            public Empleado apply(Empleado empleado, Comparator<Empleado> comparator) {
                if (empleado == null) return min;

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

        Empleado x = minBy.apply(null, null);
        System.out.println(x.getNombreCompleto() + "<------");

        return peorPagado.get();
    }

    static Empleado getEmpleadoPeorPagadoFunctional_v2(List<Empleado> empleados) {
        return getEmpleadoMinimoSegun(
                empleados,
                Comparator.comparingDouble(Empleado::getSueldo)); // Pasamos una referencia a método
    }

    // En el tema de Stream API veremos que se puede hacer simplemente con esto
    static Optional<Empleado> getEmpleadoPeorPagadoStreams(List<Empleado> empleados) {
        return empleados.stream()
                .min(Comparator.comparingDouble(Empleado::getSueldo));
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

    //En version Closure, devuelve la expresión lambda
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

        BiFunction<Double, Empleado, Double> acc =
                (d, e) -> d + e.getSueldo();

        BiFunction<Double, Empleado, Function<Empleado, Double>> accumulator =
                (d, e) -> (e1) -> d + e.getSueldo() + e1.getSueldo();

        empleados.forEach(empleado -> {
            accumulator.apply(0.0, empleado).apply(empleado);
        });

    }


}
