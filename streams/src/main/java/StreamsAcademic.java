import model.academic.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsAcademic {

    static List<Modulo> getModulosCiclo(Collection<Modulo> modulos, Ciclo ciclo) {
        return modulos.stream()
                .filter(modulo -> modulo.ciclo().equals(ciclo))
                .toList();
    }

    static String[] getModulosNames(Collection<Modulo> modulos, Ciclo ciclo) {
        return modulos.stream()
                .filter(modulo -> modulo.ciclo() == ciclo)
                .map(Modulo::nombre)
                .toArray(String[]::new);
    }

    /**
     * Devuelve la suma de las horas totales del conjunto de módulos que forman parte del ciclo
     *
     * @param modulos colección de todos módulos de cualquier ciclo
     * @param ciclo   ciclo del que se desea obtener el número de horas total
     * @return las horas totales del ciclo
     */
    static int getHorasTotales(Collection<Modulo> modulos, Ciclo ciclo) {
        return modulos.stream()
                .filter(modulo -> modulo.ciclo().equals(ciclo))
                .mapToInt(Modulo::horas)
                .sum();
    }

    static int getHorasSemanalesPrimeroDAM() {
        return Modulos.MODULOS.stream()
                .filter(modulo -> modulo.ciclo().equals(Ciclos.DAM))
                .filter(modulo -> modulo.curso() == Curso.PRIMERO)
                .mapToInt(modulo -> modulo.horasSemanales().orElse(0))
                .sum();
    }

    static int getHorasSemanalesPrimeroCiclo(Ciclo ciclo) {
        return Modulos.MODULOS.stream()
                .filter(modulo -> modulo.ciclo().equals(ciclo))
                .filter(modulo -> modulo.curso() == Curso.PRIMERO)
                .mapToInt(modulo -> modulo.horasSemanales().orElse(0))
                .sum();
    }

    static int getHorasSemanales(Collection<Modulo> modulos, Ciclo ciclo, Curso curso) {
        return modulos.stream()
                .filter(modulo -> modulo.ciclo().equals(ciclo))
                .filter(modulo -> modulo.curso() == curso)
                .mapToInt(modulo -> modulo.horasSemanales().orElse(0))
                .sum();
    }

    static List<Modulo> getModulosSortedByHoras(Collection<Modulo> modulos, Ciclo ciclo, Curso curso) {
        List<Modulo> result = modulos.stream()
                .filter(modulo -> modulo.ciclo().equals(ciclo))
                .filter(modulo -> modulo.curso().equals(curso))
                .sorted(Comparator.comparing(Modulo::horas).reversed())
                .toList();
        return result;
    }


    //Obtener un Mapa de ciclos con sus modulos (la lista de modulos ordenada por curso y luego por nombre)
    static Map<Ciclo, List<Modulo>> getModulosByCicloMap(Collection<Modulo> modulos) {
        return modulos.stream()
                .sorted(Comparator.comparing(Modulo::curso).thenComparing(Modulo::nombre))
                .collect(Collectors.groupingBy(Modulo::ciclo));
    }

    static Map<Ciclo, Map<Curso, List<Modulo>>> getModulosByCursoByCicloMap(Collection<Modulo> modulos) {
        return modulos.stream()
                //.sorted(Comparator.comparing(Modulo::curso).thenComparing(Modulo::horas))
                .collect(Collectors.groupingBy(Modulo::ciclo,
                        Collectors.groupingBy(Modulo::curso, TreeMap::new, Collectors.toList())));
    }


    static Map<Curso, Long> getHorasModulosCicloByCurso(Collection<Modulo> modulos, Ciclo ciclo) {

        return modulos.stream()
                .filter(modulo -> modulo.ciclo().equals(ciclo))
                .collect(Collectors.groupingBy(
                        Modulo::curso,
                        Collectors.summingLong(Modulo::horas)));
    }


    // --------------------- Calificaciones

    static List<Calificacion> getNotasAlumno(Collection<Calificacion> expedientes, Alumno alumno) {
        return expedientes.stream()
                .filter(expediente -> expediente.alumno().equals(alumno))
                .toList();
    }

    static OptionalDouble getNotaMediaExpedienteNo(Collection<Calificacion> calificaciones, Alumno alumno) {

        return calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .map(Calificacion::nota)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .map(Nota::getNumericValue)
                .mapToDouble(Byte::doubleValue)
                .average();
    }

    /**
     * Obtiene el valor decimal de una fracción a partir del numerodor y denominador
     * El valor es envuelto en un OptionalDouble
     * Si el denominador es cero no se puede hacer la division y se devuelve un Optional vacío
     * Si el denominador no es cero entonces se calcula de valor de la fracción
     * y se envuelve en un OptionalDecimal
     *
     * @param numerator
     * @param denominator
     * @return Un OptionalDouble con el valor decimal de la fracción
     */
    static OptionalDouble fractionToDecimal(double numerator, long denominator) {
        return denominator != 0 ?
                OptionalDouble.of(numerator / denominator) :
                OptionalDouble.empty();
    }


    /**
     * Proporciona un Supplier que entrega un Stream de elementos Calificacion donde
     * se filtran las calificaciones que pertenecen al alumno y que este ha superado
     * para ello, a partir de la colección de calificaciones se genera un Stream
     * y se le aplican sucesivos operadores intermedios de filtrado para obtener el Stream final
     *
     * @param calificaciones colección de calificaciones para generar el stream
     * @return un Supplier que proporcionara un Stream donde ya se aplican las operaciones de filtrado
     */
    static Supplier<Stream<Calificacion>> getCalificacionesSuperadasStreamSupplier(Collection<Calificacion> calificaciones) {
        return () -> calificaciones.stream()
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5);
    }


    /**
     * Proporciona un Supplier que entrega un Stream de elementos Calificacion donde
     * se filtran las calificaciones que pertenecen al alumno y que este ha superado
     * para ello, a partir de la colección de calificaciones se genera un Stream
     * y se le aplican sucesivos operadores intermedios de filtrado para obtener el Stream final
     *
     * @param calificaciones colección de calificaciones para generar el stream
     * @param alumno         alumno del que queremos quedarnos con sus calificaciones aplicando el filtro
     * @return un Supplier que proporcionara un Stream donde ya se aplican las operaciones de filtrado
     */
    static Supplier<Stream<Calificacion>> getCalificacionesSuperadasAlumnoStreamSupplier(Collection<Calificacion> calificaciones, Alumno alumno) {
        return () -> calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5);
    }


    /**
     * Obtiene la nota media del expediente de un alumno a partir de las calificaciones
     * Para calcular la nota media solamente se tienen en cuenta las asignaturas superadas
     * Cada asignatura tiene un número de horas que da un peso a la nota del módulo en el cálculo de la media
     * El cálculo de una media ponderada se obtiene
     * sumando cada nota multiplicada por el número de horas del módulo (numerador)
     * Sumando el número de horas superadas (denominador)
     * Dividiendo el numerador entre el denominador (si es distinto de cero)
     * Si el denominador es cero, lo que querrá decir que el alumno no ha superado ninguna asignatura
     * no se puede hacer la division, entonces devuelve un OptionalDouble vacío
     * Si es posible obtener el valor entonces se envuelve en un OptionalDouble y se devuelve   *
     *
     * @param calificaciones colecciones de calificaciones fuente de datos para obtener el resultado
     * @param alumno         alumno del que se quiere obtener la nota media
     * @return Un OptionalDouble que contiene el valor de la nota medio o vacío si no hay ningún módulo superado
     */

    static OptionalDouble getNotaMediaExpediente(Collection<Calificacion> calificaciones, Alumno alumno, Ciclo ciclo) {

        return calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.modulo().ciclo().equals(ciclo))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                //.peek(System.out::println)
                .collect(averagingWeighted(
                        calificacion -> calificacion.nota().orElseThrow().getNumericValue(),
                        calificacion -> calificacion.modulo().horas()));
    }


    //Version en la que se obtienen por partes la suma ponderada y el denominador
    static OptionalDouble getNotaMediaExpediente1(Collection<Calificacion> calificaciones, Alumno alumno) {

        double suma = calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .mapToDouble(c -> c.nota().orElseThrow().getNumericValue() * c.modulo().horas())
                .sum();

        long denom = calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .mapToLong(calificacion -> calificacion.modulo().horas())
                .sum();

        return denom != 0 ? OptionalDouble.of(suma / denom) : OptionalDouble.empty();
    }


    // Optimizamos la generacion del Stream filtrado por alumno y notas >= 5
    static OptionalDouble getNotaMediaExpediente2(Collection<Calificacion> calificaciones, Alumno alumno) {

        Supplier<Stream<Calificacion>> filteredStreamSupplier = () ->
                calificaciones.stream()
                        .filter(calificacion -> calificacion.alumno().equals(alumno))
                        .filter(calificacion -> calificacion.nota().isPresent())
                        .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5);

        double numerator = filteredStreamSupplier.get()
                .mapToDouble(c -> c.nota().orElseThrow().getNumericValue() * c.modulo().horas())
                .sum();

        long denominator = filteredStreamSupplier.get()
                .mapToLong(calificacion -> calificacion.modulo().horas())
                .sum();

        return fractionToDecimal(numerator, denominator);
    }


    // Lo hacemos con un teeing, asi aprovechamos el filtro
    static OptionalDouble getNotaMediaExpediente3(Collection<Calificacion> calificaciones, Alumno alumno) {

        //Obtener un mapa nota y horas acumuladas con esa nota

        return calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                calificacion -> calificacion.nota().get().getNumericValue(),
                                calificacion -> calificacion.modulo().horas(),
                                Integer::sum),
                        map ->
                                map.entrySet().stream()
                                        .collect(Collectors.teeing(
                                                Collectors.mapping(
                                                        entry -> entry.getKey().doubleValue() * entry.getValue().doubleValue(),
                                                        Collectors.reducing(0.0, Double::sum)),
                                                Collectors.reducing(
                                                        0L,
                                                        entry -> entry.getValue().longValue(),
                                                        Long::sum),
                                                (r1, r2) -> r2 != 0 ? OptionalDouble.of(r1 / r2) : OptionalDouble.empty()))));
    }


    // Creamos el Collector aparte
    static OptionalDouble getNotaMediaExpediente4(Collection<Calificacion> calificaciones, Alumno alumno) {

        //Obtener un mapa nota y horas acumuladas con esa nota
        Collector<Map.Entry<Byte, Integer>, ?, OptionalDouble> averagingWeighted = Collectors.teeing(
                Collectors.mapping(
                        entry -> entry.getKey().doubleValue() * entry.getValue().doubleValue(),
                        Collectors.reducing(0.0, Double::sum)),
                Collectors.reducing(
                        0,
                        Map.Entry::getValue,
                        Integer::sum),
                (r1, r2) -> r2 != 0 ? OptionalDouble.of(r1 / r2) : OptionalDouble.empty());


        return calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                calificacion -> calificacion.nota().get().getNumericValue(),
                                calificacion -> calificacion.modulo().horas(),
                                Integer::sum),
                        map ->
                                map.entrySet().stream()
                                        .collect(averagingWeighted)));
    }

    static OptionalDouble getNotaMediaExpediente5(Collection<Calificacion> calificaciones, Alumno alumno) {

        Collector<Map.Entry<Byte, Integer>, ?, Double> summingWeighted =
                Collectors.reducing(
                        0.0,
                        entry -> entry.getKey().doubleValue() * entry.getValue().doubleValue(),
                        Double::sum);

        Collector<Map.Entry<Byte, Integer>, ?, Long> summingEntryValues =
                Collectors.reducing(
                        0L,
                        entry -> entry.getValue().longValue(),
                        Long::sum);

        BiFunction<? super Number, ? super Number, OptionalDouble> divider =
                (r1, r2) -> r2.doubleValue() != 0 ?
                        OptionalDouble.of(r1.doubleValue() / r2.longValue()) :
                        OptionalDouble.empty();


        Collector<Map.Entry<Byte, Integer>, ?, OptionalDouble> averagingWeighted = Collectors.teeing(
                summingWeighted,
                summingEntryValues,
                divider
        );

        //Obtener un mapa nota y horas acumuladas con esa nota
        return calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                calificacion -> calificacion.nota().get().getNumericValue(),
                                calificacion -> calificacion.modulo().horas(),
                                Integer::sum),
                        map ->
                                map.entrySet().stream()
                                        .collect(averagingWeighted)));
    }

    static OptionalDouble getNotaMediaExpediente6(Collection<Calificacion> calificaciones, Alumno alumno) {

        BiFunction<? super Number, ? super Number, OptionalDouble> divider =
                (r1, r2) -> r2.doubleValue() != 0 ?
                        OptionalDouble.of(r1.doubleValue() / r2.longValue()) :
                        OptionalDouble.empty();


        Collector<Map.Entry<Byte, Integer>, ?, OptionalDouble> averagingWeighted = Collectors.teeing(
                Collectors.summingDouble(entry -> entry.getKey().doubleValue() * entry.getValue().doubleValue()),
                Collectors.summingLong(entry -> entry.getValue().longValue()),
                divider
        );

        //Obtener un mapa nota y horas acumuladas con esa nota
        return calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                calificacion -> calificacion.nota().get().getNumericValue(),
                                calificacion -> calificacion.modulo().horas(),
                                Integer::sum),
                        map ->
                                map.entrySet().stream()
                                        .collect(averagingWeighted)));
    }


    /**
     * Collector para calcular medias ponderadas de un Stream<T>
     * A partir de un elemento T puede extraerle el valor y el peso
     * mediante el valueExtractor y el weightExtractor proporcionados respectivamente
     * Se aplicarán dos operaciones de Collector a la vez sobre el mismo Stream<T> mediante un teeing
     * El primer collector suma como double la multiplicación del valor extraído por el peso extraído
     * El segundo collector suma como long los pesos obtenidos mediante el weightExtractor
     * Finalmente ambos resultados son combinados con el "merger"
     * El "merger" tiene que dividir el resultado del primero (numerador)
     * entre el resultado del segundo (denominador)
     * Y envolverlo en un OptionalDouble
     *
     * @param valueExtractor  función que se aplica al elemento T para obtener el valor
     * @param weightExtractor función que se aplica al elemento T para obtener el peso
     * @param <T>             el tipo de elementos del Stream que se va a recolectar por el collector
     * @return Un colector que calcula la media ponderada de los elementos del Stream
     */
    static <T> Collector<T, ?, OptionalDouble> averagingWeighted(
            ToDoubleFunction<T> valueExtractor, ToLongFunction<T> weightExtractor) {
        return Collectors.teeing(
                Collectors.summingDouble(
                        elem -> valueExtractor.applyAsDouble(elem) * weightExtractor.applyAsLong(elem)),
                Collectors.summingLong(weightExtractor),
                (valueWeightedSum, weightsSum) -> fractionToDecimal(valueWeightedSum, weightsSum));
    }


    static <T> Collector<T, ?, OptionalDouble> averagingWeighted2(
            ToDoubleFunction<T> valueExtractor, ToLongFunction<T> weightExtractor) {
        return Collectors.teeing(
                Collectors.mapping(
                        elem -> valueExtractor.applyAsDouble(elem) * weightExtractor.applyAsLong(elem),
                        Collectors.reducing(0.0, Double::sum)),
                Collectors.summingLong(weightExtractor),
                (numerator, denom) -> denom != 0 ?
                        OptionalDouble.of(numerator / denom) :
                        OptionalDouble.empty());
    }

    static <T> Collector<T, ?, OptionalDouble> averagingWeighted3(
            ToDoubleFunction<T> valueExtractor, ToLongFunction<T> weightExtractor) {

        Collector<T, ?, Double> summingWeighted = Collectors.summingDouble(
                elem -> valueExtractor.applyAsDouble(elem) * weightExtractor.applyAsLong(elem));

        return Collectors.teeing(
                summingWeighted,
                Collectors.summingLong(weightExtractor),
                (valueWeightedSum, weightsSum) -> fractionToDecimal(valueWeightedSum, weightsSum));
    }

    static <T, A1, A2> Collector<T, ?, OptionalDouble> averagingWeighted4(
            ToDoubleFunction<T> valueExtractor, ToLongFunction<T> weightExtractor) {


        Collector<T, ?, Double> summingWeighted = Collectors.summingDouble(
                elem -> valueExtractor.applyAsDouble(elem) * weightExtractor.applyAsLong(elem));


        Collector<T, ?, Long> summingWeights = Collectors.summingLong(weightExtractor);

        return te(summingWeighted, summingWeights, StreamsAcademic::fractionToDecimal);
    }

    static <T, A1, A2, R1, R2, R>
    Collector<T, ?, R> te(Collector<T, A1, R1> downstream1,
                          Collector<T, A2, R2> downstream2,
                          BiFunction<R1, R2, R> merger) {

        Supplier<A1> supplier1 = downstream1.supplier();
        Supplier<A2> supplier2 = downstream2.supplier();

        class PairBox {
            A1 numerator = supplier1.get();
            A2 denom = supplier2.get();

            void add(T elem) {
                downstream1.accumulator().accept(numerator, elem);
                downstream2.accumulator().accept(denom, elem);
            }

            PairBox combine(PairBox other) {
                numerator = downstream1.combiner().apply(numerator, other.numerator);
                denom = downstream2.combiner().apply(denom, other.denom);
                return this;
            }

            R get() {
                R1 r1 = downstream1.finisher().apply(numerator);
                R2 r2 = downstream2.finisher().apply(denom);
                return merger.apply(r1, r2);
            }

        }

        return Collector.of(PairBox::new, PairBox::add, PairBox::combine, PairBox::get);

    }


    /**
     * Obtiene a partir de la información que proporciona la colección de calificaciones
     * un mapa de nota media de todos los alumnos que tengan calificaciones en el ciclo
     * proporcionado como parámetro
     * Las entradas del mapa se ordenan por el valor de la nota media en orden descendente
     * Al final se obtiene un mapa cuyas claves son los alumnos y con un orden que se establece
     * por la nota media del alumno de mayor a menor
     *
     * @param calificaciones colección de calificaciones fuente de información para obtener el resultado
     * @param ciclo          ciclo del cual se quiere obtener el mapa de alumnos y su nota media
     * @return mapa de claves alumno cuyo valor asociado es la nota media de todas las asignaturas aprobadas
     */

    static Map<Alumno, OptionalDouble> getNotaMediaExpediente(Collection<Calificacion> calificaciones, Ciclo ciclo) {

        return calificaciones.stream()
                .filter(calificacion -> calificacion.modulo().ciclo().equals(ciclo))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .filter(calificacion -> !calificacion.modulo().abreviatura().equals("FCT"))
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Calificacion::alumno,
                                averagingWeighted(
                                        calificacion -> calificacion.nota().orElseThrow().getNumericValue(),
                                        calificacion -> calificacion.modulo().horas())),
                        result -> result.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue(
                                        Comparator.comparing(opt -> opt.orElse(0),
                                                Comparator.reverseOrder())))
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (e1, e2) -> e1,
                                        LinkedHashMap::new))));
    }


    /**
     * Devuelve el número de horas que al alumno le quedan pendientes para completar el ciclo
     * Un módulo se considera superado si el valor numérico de su nota es igual o superior a 5
     * Cada módulo tiene un número de horas totales que se acumularan al resultado de haber resultado superado
     *
     * @param calificaciones colección de calificaciones fuente de datos para obtener el resultado
     * @param modulos        colección de módulos fuente de información
     * @param alumno         alumno para el cual se quiere calcular el número de horas pendientes de superar
     * @param ciclo          ciclo para el cual se calculan las horas pendientes del alumno
     * @return un entero con el número de horas pendientes
     */
    static int getHorasPendientes(
            Collection<Calificacion> calificaciones, Collection<Modulo> modulos, Alumno alumno, Ciclo ciclo) {

        int horasSuperadas = calificaciones.stream()
                .filter(calificacion -> calificacion.alumno().equals(alumno))
                .filter(calificacion -> calificacion.modulo().ciclo().equals(ciclo))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .mapToInt(calificacion -> calificacion.modulo().horas())
                .sum();

        int horasTotalesCiclo = getHorasTotales(modulos, ciclo);

        return horasTotalesCiclo - horasSuperadas;
    }


    /**
     * Devuelve un Mapa con clave Alumno y valor el número de horas pendientes para completar el total de horas
     * de que consta el ciclo
     *
     * @param calificaciones colección de calificaciones fuente de información
     * @param modulos        colección de módulos fuente de datos
     * @param ciclo          ciclo del que se quiere saber cuantas horas le faltan al alumno para completarlo
     * @return mapa de alumnos asociado con el número de horas pendientes para terminar el ciclo
     */
    static Map<Alumno, Integer> getHorasPendientesByCiclo(Collection<Calificacion> calificaciones, Collection<Modulo> modulos, Ciclo ciclo) {

        int horasTotalesCiclo = modulos.stream()
                .filter(calificacion -> calificacion.ciclo().equals(ciclo))
                .mapToInt(Modulo::horas).sum();

        //int horasTotalesCiclo = getHorasTotales(Modulos.MODULOS, ciclo);

        var horasPendientesMap = calificaciones.stream()
                .filter(calificacion -> calificacion.modulo().ciclo().equals(ciclo))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .collect(Collectors.groupingBy(
                        Calificacion::alumno,
                        Collectors.collectingAndThen(
                                Collectors.summingInt(
                                        calificacion -> calificacion.modulo().horas()),
                                horasSuperadas -> horasTotalesCiclo - horasSuperadas)));

        return horasPendientesMap;
    }

    static Map<Alumno, Integer> getHorasPendientesByCiclo2(Collection<Calificacion> calificaciones, Collection<Modulo> modulos, Ciclo ciclo) {

        int horasTotalesCiclo = modulos.stream()
                .filter(calificacion -> calificacion.ciclo().equals(ciclo))
                .mapToInt(Modulo::horas).sum();

        //int horasTotalesCiclo = getHorasTotales(Modulos.MODULOS, ciclo);

        var horasPendientesMap = calificaciones.stream()
                .filter(calificacion -> calificacion.modulo().ciclo().equals(ciclo))
                .filter(calificacion -> calificacion.nota().isPresent())
                .filter(calificacion -> calificacion.nota().get().getNumericValue() >= 5)
                .map(Calificacion::alumno)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        alumno -> getHorasPendientes(calificaciones, modulos, alumno, ciclo)));

        return horasPendientesMap;
    }


}
