import model.people.Empleado;
import model.people.Empleados;
import model.people.Persona;

import java.time.Month;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOperatorsDemo {

    public static void main(String[] args) {
        //testPeek();
        //testForEachGetFemaleDetails();
        //testForEachIncreaseFemalesIncome();
        //testMapGetEmployeesNames();
        //testMapIntStream();
        //testFlatMapNumbersAndSquares();
        //testFlatMapCountNumberOfAs();
        //testFilterOnlyMales();
        //testFilterMalesIncomeMoreThan2000();
        testDropWhileOrdered();
        //testDropWhileUnordered();
        //testReduceSumaSalariosWithOtherReduceMethodCombinerSequential();
        //testReduceSumaSalariosWithOtherReduceMethodCombinerParallel();
        //testReduceToMaxInteger();
        //testReduceToEmpleadoMaxSueldo();
        //testCollectPeopleNames();
        //testCollectorsToCollection();
        //testSortedByApellidos();
        //testSummaryStats();
        //testSummaryStatsOnSueldos();
        //testMinBySueldo();
        //testCollectToMapIDToNames();
        testCollectToMapGenderToNamesWrong();
        //testCollectToMapGenderToNamesRight();
        //testCollectToMapGenderToNamesWithMapSupplier();
        //testCollectToMapCountByGender();
        //testCollectToMapEmpleadoMejorPagadoPorSexo();
        //testPartitionedByMaleGender();
        //testNamesPartitionedByMaleGender();

        //testCalendarWrong();
        //testCalendar();

        //testMakingOver2400();
        //testFlatMapping();

        testCheckAllHombres();
        testCheckAnyBornIn1970();
        testFindAnyHombre();
        testFindFirstHombre();
    }

    // El operador peek devuelve el stream sin "tocarlo", solamente realiza la accion
    // que se le indica mediante el Consumer proporcionado
    // Sirve para depurar operaciones con streams
    static void testPeek() {
        int sum = IntStream.of(1, 2, 3, 4, 5)
                .peek(value -> System.out.println("Tomando entero: " + value))
                .filter(n -> n % 2 == 1)
                .peek(value -> System.out.println("Filtrado entero: " + value))
                .map(n -> n * n)
                .peek(value -> System.out.println("Mapeado entero: " + value))
                .reduce(0, Integer::sum);

        System.out.println("Suma = " + sum);
    }

    //------------forEach ---------------------------------------------------------

    // El operador forEach realiza una accion por cada elemento del stream de entrada
    // La accion se proprciona mediante un Consumer
    static void testForEachGetFemaleDetails() {
        Empleados.EMPLEADOS.stream()
                .filter(Persona::isMujer)
                .forEach(System.out::println);  //referencia a método instancia explicita
    }

    static void testForEachIncreaseFemalesIncome() {
        List<Empleado> empleados = List.copyOf(Empleados.EMPLEADOS);
        System.out.println("Antes de aumento salarial");
        empleados.forEach(System.out::println);

        //Incrementar el salario de las mujeres un 20%
        empleados.stream()
                .filter(Persona::isMujer)
                .forEach(e -> e.setSueldo(e.getSueldo() * 1.2));

        System.out.println("Después del aumento salarial");
        empleados.forEach(System.out::println);
    }

    //---------MAP --------------------------------------
    // El operador map devuelve un stream resultado de aplicar la operación
    // proporcionada mediante un Function denominado mapper
    // a cada elemento del stream de entrada
    // El stream resultante contiene el mismo número de elementos que el resultante
    static void testMapGetEmployeesNames() {
        Empleados.EMPLEADOS.stream()
                .map(Persona::getNombreCompleto)
                .forEach(System.out::println);
    }

    static void testMapIntStream() {
        IntUnaryOperator squareInt = n -> n * n;

        IntStream.rangeClosed(1, 4)
                .map(squareInt) //IntUnaryOperator
                .forEach(System.out::println); //IntConsumer

        IntStream.rangeClosed(1, 4)
                .mapToDouble(n -> n / 2.0) //IntToDoubleFunction
                .forEach(System.out::println);

        IntStream.rangeClosed(1, 4)
                .mapToLong(n -> n + 1_000_000L) //IntToLongFunction
                .forEach(System.out::println);

        IntStream.range(0, Empleados.EMPLEADOS.size())
                .mapToObj(n -> Empleados.EMPLEADOS.get(n)) //IntFunction<? extends Empleado>
                .forEach(System.out::println);

    }

    // ----------- FlatMap --------------------------------------------
    // Este operador realiza primero una operación de mapeo mediante una función mapper
    // que se aplica a cada elemento del stream de entrada
    // El resultado de aplicar la función como resultado genera otro stream
    // De este modo lo que tenemos como resultado de aplicar la operación para cada elemento
    // es un stream de streams (cada elemento del stream es a su vez un stream)
    // Es una operación 1 a muchos
    // Por eso, en segundo lugar se realiza una operación de aplanamiento (flat)
    // Esta operación va uniendo todos los streams (del stream de streams)
    // en un único stream final
    static void testFlatMapNumbersAndSquares() {
        Stream.of(1, 2, 3)
                .flatMap(n -> Stream.of(n, n * n))
                .forEach(System.out::println);
    }

    static void testFlatMapCountNumberOfAs() {

        //Contar el número de letras 'a' que hay en total en todas las palabras
        long count = Stream.of("Rafael", "Ramon", "Raul", "Emilio")
                .map(String::chars)
                .flatMap(intStream -> intStream.mapToObj(n -> (char) n))
                .filter(ch -> ch == 'A' || ch == 'a')
                .count();

        count = Stream.of("Rafael", "Ramon", "Raul", "Emilio")
                .flatMap(name ->
                        IntStream.range(0, name.length())
                                .mapToObj(name::charAt))
                .filter(ch -> ch == 'A' || ch == 'a')
                .count();

        System.out.println("Numero de A's = " + count);
    }

    // -----------------Filter ---------------------------------------------

    static void testFilterOnlyMales() {
        Empleados.EMPLEADOS.stream()
                .filter(Persona::isHombre)
                .map(Persona::getNombreCompleto)
                .forEach(System.out::println);
    }

    static void testFilterMalesIncomeMoreThan2000() {
        Empleados.EMPLEADOS.stream()
                .filter(Persona::isHombre)
                .filter(empleado -> empleado.getSueldo() > 2000.0)
                .map(Persona::getNombreCompleto)
                .forEach(System.out::println);

        //Peor opcion dado que es menor paralelizable el proceso de filtrado
        Empleados.EMPLEADOS.stream()
                .filter(empleado -> empleado.isHombre() && empleado.getSueldo() > 2000.0)
                .map(Persona::getNombreCompleto)
                .forEach(System.out::println);
    }

    static void testDropWhileOrdered() {
        Stream.of(1, 2, 3, 4, 5, 6, 7)
                .dropWhile(n -> n < 5)
                .forEach(System.out::println);
    }

    static void testDropWhileUnordered() {

        Stream.of(1, 5, 6, 2, 3, 4, 7)
                .dropWhile(n -> n < 5)
                .forEach(System.out::println);
    }

    // ----------------Reduce -----------------------------------
    static void testReduceImperativeStyle() {
        //Crear un lista de enteros
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        //Declarar un acumulador, llamarlo sum e inicializarlo con un cero
        int sum = 0;
        for (int num : numbers) {
            //Acumular el resultado parcial en sum
            //Combinar el resultado parcial en sum con el siguiente elemento
            sum = sum + num;
        }
        //El último resultado parcial es el resultado de la reducción
        System.out.println("sum = " + sum);
        //Si la lista hubiera estado vacía el valor inicial sería directamente el resultado
    }

    static void testReduceSumaSalariosImperativeStyle() {
        //Declarar el acumulador e inicializarlo a cero
        double sum = 0;
        for (Empleado empleado : Empleados.EMPLEADOS) {
            //Mapear el empleado a su sueldo double
            double sueldo = empleado.getSueldo();
            //Acumular el resultado parcial en sum
            sum = sum + sueldo;
        }
        System.out.println("sum = " + sum);
    }

    static void testReduceSumIntegers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        System.out.println("sum = " + sum);
    }

    static void testReduceSumaSalarios() {
        double sum = Empleados.EMPLEADOS.stream()
                .map(Empleado::getSueldo)
                .reduce(0.0, Double::sum);

        System.out.println("sum = " + sum);
    }

    static void testReduceSumaSalariosWithOtherReduceMethod() {
        //<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner)
        // U es Double
        // T es Empleado

        double sum = Empleados.EMPLEADOS.stream()
                .reduce(0.0,
                        (partialSum, empleado) -> partialSum + empleado.getSueldo(),
                        Double::sum);

        System.out.println("sum = " + sum);
    }

    static void testReduceSumaSalariosWithOtherReduceMethodCombiner() {
        // No se llama nunca al combiner
        double sum = Empleados.EMPLEADOS.stream()
                .reduce(0.0,
                        (partialSum, empleado) -> partialSum + empleado.getSueldo(),
                        (a, b) -> {
                            System.out.println("Combiner llamado: a = " + a + " b = " + b);
                            return a + b;
                        });

        System.out.println("sum = " + sum);
    }

    static void testReduceSumaSalariosWithOtherReduceMethodCombinerSequential() {
        // No se llama nunca al combiner
        double sum = Empleados.EMPLEADOS.stream()
                .reduce(0.0,
                        (partialSum, empleado) -> {
                            double accumulated = partialSum + empleado.getSueldo();
                            System.out.println(
                                    Thread.currentThread().getName() +
                                            " - Accumulator : partialSum = " +
                                            partialSum + ", empleado = " + empleado +
                                            ", acumulado = " + accumulated);
                            return accumulated;
                        },
                        (a, b) -> {
                            double combined = a + b;
                            System.out.println(
                                    Thread.currentThread().getName() +
                                            " - Combiner: a " + a + ", b = " + b +
                                            ", combined = " + combined);
                            return combined;
                        });

        System.out.println("sum = " + sum);
    }

    static void testReduceSumaSalariosWithOtherReduceMethodCombinerParallel() {
        // No se llama nunca al combiner
        double sum = Empleados.EMPLEADOS.parallelStream()
                .reduce(0.0,
                        (partialSum, empleado) -> {
                            double accumulated = partialSum + empleado.getSueldo();
                            System.out.println(
                                    Thread.currentThread().getName() +
                                            " - Accumulator : partialSum = " +
                                            partialSum + ", empleado = " + empleado +
                                            ", acumulado = " + accumulated);
                            return accumulated;
                        },
                        (a, b) -> {
                            double combined = a + b;
                            System.out.println(
                                    Thread.currentThread().getName() +
                                            " - Combiner: a " + a + ", b = " + b +
                                            ", combined = " + combined);
                            return combined;
                        });

        System.out.println("sum = " + sum);
    }

    static void testReduceToMaxInteger() {
        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5)
                .reduce(Integer::max);

        if (max.isPresent()) {
            System.out.println("Máximo = " + max.get());
        } else {
            System.out.println("no hay max");
        }
    }

    static void testReduceToMaxIntegerEmptyStream() {
        Optional<Integer> max = Stream.<Integer>empty()
                .reduce(Integer::max);

        if (max.isPresent()) {
            System.out.println("Máximo = " + max.get());
        } else {
            System.out.println("no hay max");
        }
    }

    static void testReduceToEmpleadoMaxSueldo() {
        Optional<Empleado> empleado = Empleados.EMPLEADOS.stream()
                .reduce((e1, e2) -> e1.getSueldo() > e2.getSueldo() ? e1 : e2);

        if (empleado.isPresent()) {
            System.out.println("Empleado con mayor sueldo: " + empleado.get());
        } else {
            System.out.println("No se puede obtener el empleado con mayor sueldo");
        }

    }

    //------ Reducciones especializadas sum, min, max, count

    static void testSumSpecializedReduction() {
        //Obtiene la suma de los sueldos de todos los empleados
        double totalIncome = Empleados.EMPLEADOS.stream()
                .mapToDouble(Empleado::getSueldo)
                .sum();

        System.out.println("Ingresos totales: " + totalIncome);
    }

    static void testMaxEmpleadoSueldoStream() {
        //Obtenemos el empleado que más gana
        Optional<Empleado> empleado = Empleados.EMPLEADOS.stream()
                .max(Comparator.comparingDouble(Empleado::getSueldo));

        if (empleado.isPresent()) {
            System.out.println("Empleado con mayor sueldo: " + empleado.get());
        } else {
            System.out.println("No se puede obtener el empleado con mayor sueldo");
        }
    }

    static void testMaxSueldoDoubleStream() {
        // Aquí solamente obtenemos el sueldo del empleado que más gana
        OptionalDouble income = Empleados.EMPLEADOS.stream()
                .mapToDouble(Empleado::getSueldo)
                .max();

        if (income.isPresent()) {
            System.out.println("Mayor sueldo: " + income.getAsDouble());
        } else {
            System.out.println("No se puede obtener el mayor sueldo");
        }
    }

    static void testCountEmpleados() {
        long empleadosCount = Empleados.EMPLEADOS.stream()
                .count();

        System.out.println("Cuantos empleados  = " + empleadosCount);
    }

    static void testCountEmpleados2() {
        long empleadosCount = Empleados.EMPLEADOS.stream()
                .mapToLong(empleado -> 1L)
                .sum();

        System.out.println("Cuantos empleados  = " + empleadosCount);
    }

    static void testCountEmpleados3() {
        long empleadosCount = Empleados.EMPLEADOS.stream()
                .mapToLong(empleado -> 1L)
                .reduce(0L, Long::sum);

        System.out.println("Cuantos empleados  = " + empleadosCount);
    }

    static void testCountEmpleados4() {
        long empleadosCount = Empleados.EMPLEADOS.stream()
                .reduce(0L,
                        (partialCount, empleado) -> partialCount + 1L,
                        Long::sum);

        System.out.println("Cuantos empleados  = " + empleadosCount);
    }


    //-----------Collectors

    //Supongamos que tenemos un stream de Personas y queremos coleccionar sus nombres
    //en un ArrayList<String>.

    static void testCollectPeopleNames() {
        //1) Necesitamos un supplier que nos proporcione el ArratList<String> donde almacenar los nombres
        // Usando una expresión lambda
        Supplier<ArrayList<String>> supplier = () -> new ArrayList<String>();
        // O Usando una referencia a constructor
        Supplier<ArrayList<String>> supplier1 = ArrayList::new;

        //2) Crear un acumulador que recibe 2 argumentos
        //El primero: El contenedor proporcionado por el supplier
        //El segundo: El elemento correspondiente del stream en la iteración
        //El acumulador en este caso simplemente debe añadir el nombre a la lista

        //Usando una expresión lambda
        BiConsumer<ArrayList<String>, String> accumulator = (list, name) -> list.add(name);
        //O Usando una referencia a método
        BiConsumer<ArrayList<String>, String> accumulator1 = ArrayList::add;

        //3) Necesitamos un combiner que combinará los resultados acumulados en dos ArrayList en uno
        // El combiner se usa cuando se colecciona en paralelo
        //Usando una expresión lambda
        BiConsumer<ArrayList<String>, ArrayList<String>> combiner = (list1, list2) -> list1.addAll(list2);
        //Usando una referencia a método
        BiConsumer<ArrayList<String>, ArrayList<String>> combiner1 = ArrayList::addAll;

        //Ya podemos usar el método collect para coleccionar los nombres de todas las personas en la lista

        List<String> names = Empleados.EMPLEADOS
                .stream()
                .map(Persona::getNombreCompleto)
                .collect(ArrayList::new,    //supplier
                        ArrayList::add,     //accumulator
                        ArrayList::addAll); //combiner

        //Podemos hacer algo similar para coleccionar en un Set o un Map

        System.out.println(names);
    }

    static void testCollectorsToList() {
        List<String> names = Empleados.EMPLEADOS
                .stream()
                .map(Persona::getNombreCompleto)
                .collect(Collectors.toList()); //Se puede reemplazar simplemente por toList()

        System.out.println(names);
    }

    static void testCollectorsToSet() {
        Set<String> uniqueNames = Empleados.EMPLEADOS
                .stream()
                .map(Persona::getNombreCompleto)
                .collect(Collectors.toSet());

        System.out.println(uniqueNames); //Como es un Set no habrá duplicados
    }

    // toCollection() recibe un Supplier lo que nos permite especificar el tipo especifico de coleccion
    // Si en vez de toSet usamos toCollection y el supplier proporciona un TreeSet entonces
    // los datos del set estarán ordenados (sorted)
    static void testCollectorsToCollection() {
        Set<String> uniqueSortedNames = Empleados.EMPLEADOS
                .stream()
                .map(Persona::getNombreCompleto)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println(uniqueSortedNames);
    }

    static void testSorted() {
        List<String> sortedNames = Empleados.EMPLEADOS
                .stream()
                .map(Persona::getNombreCompleto)
                .sorted()
                .collect(Collectors.toList());

        System.out.println(sortedNames);
    }

    static void testSortedByApellidos() {
        List<String> sortedNames = Empleados.EMPLEADOS
                .stream()
                .sorted(Comparator.comparing(Persona::getApellidos))
                .map(Persona::getNombreCompleto)
                .collect(Collectors.toList());

        System.out.println(sortedNames);
    }

    static void testCollectorsCounting() {
        long count = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.counting()); // Se puede reemplazar por count()

        System.out.println("Empleados count = " + count);
    }


    // ---- Resumen de estadísticas ------------------------------------
    static void testSummaryStats() {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(100);
        stats.accept(500);
        stats.accept(400);
        //Obtener los estadísticos
        long count = stats.getCount();
        double sum = stats.getSum();
        double min = stats.getMin();
        double avg = stats.getAverage();
        double max = stats.getMax();
        System.out.printf("count=%d, sum=%.2f, min=%.2f, max=%.2f average=%.2f\n",
                count, sum, min, max, avg);
    }

    static void testSummaryStatsOnSueldos() {
        DoubleSummaryStatistics incomeStats = Empleados.EMPLEADOS
                .stream()
                .map(Empleado::getSueldo)
                .collect(DoubleSummaryStatistics::new,
                        DoubleSummaryStatistics::accept,
                        DoubleSummaryStatistics::combine);

        System.out.println(incomeStats);

    }

    static void testSummaryStatsOnSueldosCollectors() {
        DoubleSummaryStatistics incomeStats = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.summarizingDouble(Empleado::getSueldo));

        System.out.println(incomeStats);
    }

    static void testAveragingSueldos() {
        double averageIncome = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.averagingDouble(Empleado::getSueldo));

        System.out.println("Sueldo medio = " + averageIncome);
    }

    static void testMinBySueldo() {
        Optional<Empleado> empleado = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.<Empleado>minBy(Comparator.comparing(Empleado::getSueldo)));
        // se puede reemplazar por .min(Comparator.comparing(Empleado::getSueldo));

        empleado.ifPresent(
                emp -> System.out.println("Empleado con el sueldo menor = " + emp));
    }

    //------- Collectors.toMap() --------------------------------------
    static void testCollectToMapIDToNames() {
        Map<Long, String> idToNameMap = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.toMap(
                        Persona::getId,
                        Persona::getNombreCompleto));

        System.out.println(idToNameMap);
    }


    // Este método es incorrecto porque el colector creado con el método toMap
    // no sabe qué hacer cuando se encuentra con una clave duplicada
    // Si eso sucede, lanza una excepción IllegalStateException
    // En el ejemplo, aparecerán claves duplicadas dado que el atributo que hace de clave
    // es el sexo de las personas y en la colección hay personas del mismo sexo
    static void testCollectToMapGenderToNamesWrong() {
        try {
            Map<Persona.Sexo, String> genderToNamesMap = Empleados.EMPLEADOS
                    .stream()
                    .collect(Collectors.toMap(
                            Persona::getSexo,
                            Persona::getNombreCompleto));
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // La solución es usar una version del método toMap que recibe un tercer parámetro
    // El tercer parámetro sirve para indicar como resolver "colisiones"
    // entre dos valores asociados a la misma clave
    // oldValue hace referencia al valor asociado con la clave que ya existe en el map
    // newValue hace referencia al valor que ha sido proporcionado al colector y que
    // debe asociarse con la misma clave que el oldValue
    // la mergeFuncion es un BinaryOperator<U> donde U es el tipo de los valores
    // que se encarga de resolver el conflicto y devolver el valor que quedará finalmente
    // asociado con la clave
    static void testCollectToMapGenderToNamesRight() {

        Map<Persona.Sexo, String> genderToNamesMap = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.toMap(
                        Persona::getSexo,               //keyMapper
                        Persona::getNombreCompleto,     //valueMapper
                        (oldValue, newValue) ->
                                String.join(", ", oldValue, newValue))); //mergeFunction

        System.out.println(genderToNamesMap);
    }


    // Otra version de sobrecarga del método toMap permite especificar un cuarto parámetro
    // Este parámetro nos permite especificar el tipo concreto de Map que debe usar el colector
    // El parámetro es de tipo Supplier y su objeto es proporcionar al ser invocado una instancia
    // de un objeto que implemente la interfaz Map
    // En el ejemplo, usamos como Supplier una referencia a constructor por defecto de la clase TreeMap
    // Con este logramos que el Map esté ordenado por orden natural de las claves
    static void testCollectToMapGenderToNamesWithMapSupplier() {
        Map<Persona.Sexo, String> genderSortedToNamesMap = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.toMap(Persona::getSexo,
                        Persona::getNombreCompleto,
                        (oldValue, newValue) ->
                                String.join(", ", oldValue, newValue),
                        TreeMap::new)); //mapFactory

        System.out.println(genderSortedToNamesMap);
    }

    static void testCollectToMapCountByGender() {
        Map<Persona.Sexo, Long> countByGender = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.toMap(
                        Persona::getSexo,
                        empleado -> 1L,
                        (oldCount, newCount) -> oldCount + 1));
        System.out.println(countByGender);
    }

    // En este ejemplo usamos como valueMapper el método estático identity de la interfaz Function
    // el valueMapper es una Function<T,U> donde
    // T es el tipo de los elementos del stream y U el tipo de las claves del Map
    // Dado que el streams es de empleados y los valores del map también lo son
    // En este caso T es U, es decir la función recibe un T (Empleado) y devuelve un U (empleado)
    // La interfaz Function cuenta con um método estático identity() que
    // devuelve exactamente el mismo valor que ha recibido como argumento de entrada
    static void testCollectToMapEmpleadoMejorPagadoPorSexo() {

        BinaryOperator<Empleado> mejorPagadoEntreDos =
                (mejorPagado, candidato) ->
                        candidato.getSueldo() > mejorPagado.getSueldo() ?
                                candidato : mejorPagado;

        Map<Persona.Sexo, Empleado> mejorPagadoPorSexo = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.toMap(
                        Persona::getSexo,   //keyMapper
                        Function.identity(),    //valueMapper
                        (mejorPagado, candidato) ->
                                candidato.getSueldo() > mejorPagado.getSueldo() ?
                                        candidato : mejorPagado));

        System.out.println(mejorPagadoPorSexo);
    }

    //--------- partitioningBy ----------------------------

    // El colector generado a partir del método partitioningBy genera un Map<Boolean, List<T>>
    // Es decir divide en 2 grupos los elementos de tipo T del stream de entrada
    // En grupo están los que cumplen la condición indicada en el Predicate y el valor de la clave es true
    // En el segundo grupo están los que no la cumplen y el valor de la clave en el Map para este grupo es false

    static void testPartitionedByMaleGender() {
        Map<Boolean, List<Persona>> partitionedByMaleGender =
                Empleados.EMPLEADOS
                        .stream()
                        .collect(Collectors.partitioningBy(Persona::isHombre));

        System.out.println(partitionedByMaleGender);
    }

    // El colector devuelto por partitionBy se puede encadenar con un siguiente colector (downstream)
    // que recoge como entrada los elementos que resulten de aplicar la partición
    // y aplica otra operación de recolección/reducción
    static void testNamesPartitionedByMaleGender() {
        Map<Boolean, String> partitionedByMaleGender =
                Empleados.EMPLEADOS
                        .stream()
                        .collect(Collectors.partitioningBy(
                                Persona::isHombre,
                                Collectors.mapping( //downstream
                                        Persona::getNombreCompleto,
                                        Collectors.joining(", ")))); // segundo downstream

        System.out.println(partitionedByMaleGender);
    }

    static void testCollectionAndThenUnmodifiableList() {
        List<String> names = Empleados.EMPLEADOS
                .stream()
                .map(Persona::getNombreCompleto)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList)); //Se consigue lo mismo utilizando directamente .toList()

        System.out.println(names);

    }

    static void testCalendarWrong() {
        Map<Month, String> dobCalendar = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.groupingBy(
                        p -> p.getNacimiento().getMonth(),
                        Collectors.mapping(
                                Persona::getNombreCompleto,
                                Collectors.joining(", "))));

        dobCalendar.entrySet().forEach(System.out::println);
    }

    static void testCalendar() {
        Map<Month, String> dobCalendar = Empleados.EMPLEADOS
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                p -> p.getNacimiento().getMonth(),
                                Collectors.mapping(
                                        Persona::getNombreCompleto,
                                        Collectors.joining(", "))),
                        result -> {
                            //Añadir los meses faltantes
                            Arrays.stream(Month.values())
                                    .forEach( month -> result.putIfAbsent(month, "Nadie"));
                            /* for (Month m : Month.values()) {
                                result.putIfAbsent(m, "Nadie");
                            }*/
                            //Devuelve un map no modificable y ordenado
                            return Collections.unmodifiableMap(
                                    new TreeMap<>(result)
                            );
                        }));

        dobCalendar.entrySet().forEach(System.out::println);
    }

    static void testMakingOver2400() {
        Map<Persona.Sexo, List<Empleado>> makingOver2400 =
                Empleados.EMPLEADOS.stream()
                        .collect(Collectors.groupingBy(
                                Persona::getSexo,   //classifier
                                Collectors.filtering(   //downstream
                                        e -> e.getSueldo() > 2400,  //Predicate
                                        Collectors.toList())));     //downstream

        System.out.println(makingOver2400);
    }

    static void testFlatMapping() {
        Map<Persona.Sexo, Set<Persona.Idioma>> langByGender =
                Empleados.EMPLEADOS.stream()
                        .collect(Collectors.groupingBy(
                                Persona::getSexo,
                                Collectors.flatMapping(
                                        e -> e.getIdiomas().stream(),
                                        Collectors.toSet())));

        System.out.println(langByGender);
    }

    //--- BUSQUEDA Y MATCHING -----------------------------------------------

    static void testCheckAllHombres() {
        boolean allHombres = Empleados.EMPLEADOS
                .stream()
                .allMatch(Persona::isHombre);
        System.out.println("Todos Hombres? " + allHombres);
    }


    static void testCheckAnyBornIn1970() {
        boolean anyoneBornIn1970 = Empleados.EMPLEADOS
                .stream()
                .anyMatch(empleado -> empleado.getNacimiento().getYear() == 1970);
        System.out.println("Alguien nació en 1970 ? " + anyoneBornIn1970);
    }

    static void testFindAnyHombre() {
        Optional<? extends Persona> anyMale = Empleados.EMPLEADOS
                .stream()
                .findAny();

        if (anyMale.isPresent()) {
            System.out.println("Algun hombre = " + anyMale.get());
        } else {
            System.out.println("No hay hombres.");
        }
    }


    static void testFindFirstHombre() {
        Optional<? extends Persona> anyMale = Empleados.EMPLEADOS
                .stream()
                .findFirst();

        if (anyMale.isPresent()) {
            System.out.println("Primer hombre = " + anyMale.get());
        } else {
            System.out.println("No hay hombres.");
        }
    }


}
