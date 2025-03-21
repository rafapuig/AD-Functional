package es.rafapuig.exercises.personas;

import model.people.Empleados;
import model.people.Persona;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

import static model.people.Persona.Sexo;
import static model.people.Persona.Idioma;

public class PersonasUtil {

    static void printPersonas(List<? extends Persona> personas) {
        System.out.println(
                PersonasTableFormatter.DEFAULT.getTable(personas.toArray(new Persona[0])));
    }

    static void printLineSeparator() {
        System.out.println(PersonasTableFormatter.DEFAULT.getLineSeparator());
    }

    /**
     * Obtener una lista de todas las personas ordenadas por su ordenamiento natural
     * El ordenamiento natural se corresponde con la ordenación de los elementos (Persona)
     * mediante la implementación de la interfaz Comparable por la propia clase Persona
     */
    public static List<Persona> getAllPersonasSorted(List<? extends Persona> personas) {
        List<Persona> sorted = new ArrayList<>(personas);
        sorted.sort(Comparator.naturalOrder());
        // equivalente, pasar como argumento el valor null, equivale a pedir ordenación natural
        //sorted.sort(null);
        // equivalente, usar el metodo estático sort sin argumentos de la clase Collections
        //Collections.sort(sorted);
        return sorted;
    }

    static String[] getNombresMujeresMayusculas(List<? extends Persona> personas) {
        List<String> resultList = new ArrayList<>();            //Crear la lista de recoleccion

        for (Persona persona : personas) {                      // iteración externa (bucle)
            if (persona.isMujer()) {                            // filtro
                String nombre = persona.getNombre();            // mapeo
                String upperCaseNombre = nombre.toUpperCase();  // otro mapeo
                resultList.add(upperCaseNombre);                // recolección en lista
            }
        }

        String[] result = new String[resultList.size()];
        return resultList.toArray(result);
    }

    // Implementación de la misma funcionalidad que el método anterior pero esta vez mediante
    // programación funcional, tal como pide el ejercicio del tema 3
    static String[] getNombresMujeresMayusculasFunctional(List<? extends Persona> personas) {

        List<String> resultList = new ArrayList<>();

        Predicate<Persona> isMujer = persona -> persona.isMujer();
        Function<Persona, String> mapToName = persona -> persona.getNombre();
        UnaryOperator<String> toUpperCaseName = name -> name.toUpperCase();

        // El metodo forEach es un metodo de orden superior que recibe un parametro
        // de tipo Consumer<T>, en este caso un Consumer<Persona>
        personas.forEach(persona -> {
            if (isMujer.test(persona)) {    //invocar el metodo SAM de la IF Predicate: test
                String nombre = mapToName.apply(persona);   //invocar el metodo SAM apply de Function
                String upperCaseNombre = toUpperCaseName.apply(nombre);
                resultList.add(upperCaseNombre);    //acumular el elemento procesado en la lista de recoleccion
                //resultList.add(toUpperCaseName.apply(mapToName.apply(persona)));
            }
        });

        // Adaptamos el resultado recolectado de una lista a un array
        return resultList.toArray(new String[resultList.size()]);
    }


    //---------------Nombres personas ordenados por apellidos

    /**
     * Obtener el nombre completo de todas las personas ordenados por apellidos de la persona
     *
     * @param personas lista de elementos de tipo Persona (o subclase)
     * @return lista de Strings con los nombres completos de las personas
     */
    static String[] getNombresPersonasSortByApellidos(List<? extends Persona> personas) {

        //Hacer una copia de la lista proporcionada, el original no se debe modificar
        List<Persona> sortedPersonas = new ArrayList<>(personas);

        //Ordenar la lista con el método sort
        //A este método se le debe proporcionar una instancia de un objeto que implemente la
        //interfaz Comparable<T> y como la lista es de personas T es de tipo Persona

        sortedPersonas.sort(
                //Para ello creamos un tipo anónimo que implementa la interfaz Comparator<Persona>
                new Comparator<Persona>() {
                    //La interfaz Comparator es una interfaz funcional dado que solamente
                    //consta de un SAM, (tiene más métodos, pero no son abstractos)
                    //Implementamos el método compare que recibe 2 referencias a objetos Persona
                    @Override
                    public int compare(Persona o1, Persona o2) {
                        return o1.getApellidos().compareTo(o2.getApellidos());
                    }
                }
        );

        List<String> nombres = new ArrayList<>();   //Colección donde acumularemos los resultados

        for (Persona persona : sortedPersonas) {                    // iteración externa
            String nombreCompleto = persona.getNombreCompleto();    // mapeo Persona a nombre
            nombres.add(nombreCompleto);                // acumular el elemento en la colección
        }

        //Adaptamos la lista a un array de String
        return nombres.toArray(new String[0]);
    }


    static String[] getNombresPersonasSortByApellidosFunctional(List<? extends Persona> personas) {

        //Para no modificar la lista de personas proporcionada como argumento de entrada
        //Hacemos una copia de la lista en otra lista para ordenar esta segunda
        List<Persona> sorted = new ArrayList<>(personas);

        // En realidad, el método sort es un método de orden superior, ya que su parámetro de entrada
        // es una instancia de un Comparator, y Comparator es una interfaz funcional con SAM compare
        Function<Persona, String> personaApellidosExtractor = Persona::getApellidos;

        Comparator<? super Persona> byApellidosComparator =
                Comparator.comparing(personaApellidosExtractor);

        sorted.sort(byApellidosComparator);

        // Podemos crear la instancia del comparador mediante un tipo anónimo e implementar el SAM
        // utilizando la función personaApellidosExactor aplicada a los parámetros p1 y p2
        sorted.sort(new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return personaApellidosExtractor.apply(p1).compareTo(personaApellidosExtractor.apply(p2));
            }
        });

        // O en lugar de crear la instancia mediante un tipo anónimo usar una expresión lambda equivalente
        sorted.sort((p1, p2) ->
                personaApellidosExtractor.apply(p1).compareTo(personaApellidosExtractor.apply(p2)));

        // Esa misma expresión lambda es la que nos devolvería el mátodo estático comparing de Comparator
        // El método comparing es un HOM (método de orden superior / High-order Method)
        // Recibe como parámetro de entrada una instancia de la interfaz funcional : Function<T,R>
        // Y devuelve una instancia de interfaz funcional Comparator<T>, cuyo SAM compare acepta dos T
        // como argumentos de entrada y devuelve un int

        sorted.sort(Comparator.comparing(personaApellidosExtractor::apply));

        // Y más corto, si nos ahorramos el puenteo innecesario en este caso, como:
        sorted.sort(Comparator.comparing(personaApellidosExtractor));


        // version abreviada que escribiríamos en la práctica
        sorted.sort(Comparator.comparing(Persona::getApellidos));

        //Function<Persona, String> personaNombreCompletoExtractor ;

        // Función que aplica a dos parámetros Persona y Function<Persona, String) y que devuelve un String
        BiFunction<Persona, Function<Persona, String>, String> mapPersonaToNombreCompleto =
                (persona, personaNombreCompletoExtractor) ->
                        personaNombreCompletoExtractor.apply(persona);


        // Colección donde acumularemos los nombres resultantes
        List<String> nombres = new ArrayList<>();

        //Ahora utilizamos el método forEach para realizar una iteración a nivel interno por la colección
        //El método forEach recibe com argumento el código que debe aplicar sobre cada elemento en la iteración
        //El tipo del parámetro es un Consumer<T> siendo T en este caso el T de la lista, Persona
        sorted.forEach(persona -> {
            //Aplicamos la función a la persona iterada y la función que extrae su nombre completo
            // para obtener el nombre completo de la persona
            String nombreCompleto = mapPersonaToNombreCompleto.apply(persona, Persona::getNombreCompleto);
            nombres.add(nombreCompleto);
        });

        //Usamos la version HOM del método toArray que acepta una IF de tipo IntFunction<T[]>
        // Es decir, una función que se aplicará a un int como parámetro de entrada y devolverá un
        // array de elementos de tipo T y de tamaño indicado por el valor del parámétro int
        return nombres.toArray(String[]::new);
    }


    static String[] getNombresPersonasSortByApellidosCollector(List<? extends Persona> personas) {

        // Creación del tipo anónimo que implementa la interfaz Collector<T,A,R>
        // T es el tipo de elementos
        // A es el tipo del acumulador (Colección donde se van a ir añadiendo los resultados)
        // R es el tipo del resultado
        Collector<String, List<String>, String[]> toList = new Collector<>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ArrayList::new;  // El supplier proporciona el objeto colección acumulador
            }

            @Override
            public BiConsumer<List<String>, String> accumulator() {
                return List::add;   // El acumulador indica como hacer para añadir cada elemento
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left; // Combiner indica como fusionar dos acumuladores / colecciones en una
                };
            }

            @Override
            public Function<List<String>, String[]> finisher() {
                //return Function.identity(); //finisher indica como adaptar el resultado
                return (list) -> list.toArray(String[]::new);
            }

            @Override
            public Set<Characteristics> characteristics() {
                return null;
            }
        };

        BiFunction<Persona, Function<Persona, String>, String> map =
                (persona, mapper) -> mapper.apply(persona);

        List<Persona> sortedPersonas = new ArrayList<>(personas);
        sortedPersonas.sort(Comparator.comparing(Persona::getApellidos));

        List<String> nombres = toList.supplier().get(); //Obtener el objeto acumulador donde recolectar
        sortedPersonas.forEach(persona -> {
            // mapear, dada una persona y un mapper obtenemos su nombre completo
            String nombreCompleto = map.apply(persona, Persona::getNombreCompleto);
            // usar el acumulador para añadir a la colección el nuevo elemento procesado
            toList.accumulator().accept(nombres, nombreCompleto);
        });

        return toList.finisher().apply(nombres);    //Adaptamos el resultado
    }


    /**
     * Obtener una lista de personas ordenadas por su sexo
     */
    static List<Persona> getPersonasSortBySexo(List<? extends Persona> personas) {
        List<Persona> sortedPersonas = new ArrayList<>(personas);
        //sortedPersonas.sort(Comparator.comparing(Persona::getSexo));
        sortedPersonas.sort(new Comparator<Persona>() {
            @Override
            public int compare(Persona o1, Persona o2) {
                return o1.getSexo().compareTo(o2.getSexo());
            }
        });
        return sortedPersonas;
    }

    /**
     * Obtener el número de personas del sexo especificado
     */
    static int getCountPersonasBySexo(List<? extends Persona> personas, Sexo sexo) {
        int count = 0;
        for (Persona persona : personas) {
            if(persona.getSexo().equals(sexo)) {
                count++;
            }
        }
        return count;
    }

    static List<Persona> getHombresMayoresEdad(List<? extends Persona> personas) {
        List<Persona> result = new ArrayList<>();
        for (Persona persona : personas) {
            if(persona.getSexo().equals(Sexo.HOMBRE) && persona.isMayorEdad()) {
                result.add(persona);
            }
        }
        return result;
    }





    //--------- Nombres de personas agrupadas por sexo

    /**
     * Obtener, por cada sexo, un String con los nombres de las personas
     * concatenados entre sí y separados por una coma
     *
     * @param personas
     * @return un mapa de clave sexo y valor un String con la cadena de nombres para ese sexo
     */

    static Map<Sexo, String> getGenderToNamesMap(List<? extends Persona> personas) {
        Map<Sexo, String> map = new HashMap<>();

        for (Persona persona : personas) {

            Sexo sexo = persona.getSexo(); // De cada persona extraemos su sexo

            if (!map.containsKey(sexo)) { // Si es la primera persona de ese sexo...
                map.put(sexo, persona.getNombreCompleto()); //Añadimos su nombre completo y asociado con ese sexo
            } else {
                // oldValue tendrá la lista separada por comas de los nombres que se llevan
                // procesados hasta el momento en el bucle
                String oldValue = map.get(sexo);
                // Combinamos la lista momentanea con el nombre de la persona con la que estamos iterando
                String newValue = String.join(", ", oldValue, persona.getNombreCompleto());
                // Y se añade de nuevo al mapa con la clave de su sexo
                map.put(sexo, newValue);
            }
        }
        return map;
    }

    static Map<Persona.Sexo, String> getGenderToNamesMapFunctional(List<? extends Persona> personas) {
        Map<Persona.Sexo, String> map = new HashMap<>();

        personas.forEach(
                persona -> map.merge(
                        persona.getSexo(),              //la clave
                        persona.getNombreCompleto(),    //el valor que queremos asociar a la clave
                        // El tercer parámetro es una BiFunction<V,V,V> que recibe el valor actual
                        // asociado a la clave y el nuevo valor a asociar (2º param de merge)
                        // y devuelve el valor que definitivamente queremos asociar con la clave
                        // en este caso acumulamos en el string oldValue, que contiene la cadena
                        // con la lista de nombres parcial, el nuevo valor (nombre completo del
                        // elemento que estamos procesando en la iteración actual)
                        (oldValue, newValue) -> String.join(", ", oldValue, newValue)));
        return map;
    }


    //------------- Numero de personas por cada sexo

    /**
     * Obtener el número de personas por cada sexo a partir de una lista de personas
     *
     * @param personas lista de personas
     * @return un mapa cuyas claves son de tipo Sexo y el valor asociado un Long que
     * contiene el número de personas para el valor clave Sexo al que está asociado
     */

    static Map<Sexo, Long> getPersonasCountByGender(List<? extends Persona> personas) {
        Map<Sexo, Long> map = new HashMap<>();

        for (Persona persona : personas) {
            Persona.Sexo sexo = persona.getSexo();
            // Sí es la primera persona con ese sexo...
            if (!map.containsKey(sexo)) { //No habrá una entrada para ese valor clave de sexo en el mapa
                map.put(sexo, 1L); // Por tanto, el primer valor asociado con ese sexo será un 1
            } else {
                long oldCount = map.get(sexo);
                map.put(sexo, oldCount + 1L);   // Incrementar el número de personas de ese sexo en el mapa
            }
        }

        return map;
    }

    //Versión con merge
    static Map<Persona.Sexo, Long> getPersonasCountByGenderFunctional(List<? extends Persona> personas) {
        Map<Persona.Sexo, Long> map = new HashMap<>();

        personas.forEach(
                persona -> {
                    map.merge(
                            persona.getSexo(),
                            1L, //es también newCount
                            (oldCount, newCount) -> oldCount + newCount);
                }
        );

        return map;
    }

    //Version con compute
    static Map<Persona.Sexo, Long> getPersonasCountByGenderFunctional2(List<? extends Persona> personas) {
        Map<Persona.Sexo, Long> map = new HashMap<>();

        personas.forEach(
                persona -> {
                    map.compute(
                            persona.getSexo(),
                            (sexo, oldCount) -> oldCount == null ? 1L : oldCount + 1L);
                }
        );

        return map;
    }


    // ---- Hablan un idioma

    /**
     * Comprobar si todos los elementos (Persona) de la lista proporcionada
     * hablan el idioma Español
     *
     * @param personas
     * @return un valor boolean que sera true si se cumple la condición y false si no
     */
    static boolean allPersonasHablanEspañol(List<? extends Persona> personas) {

        // Iniciamos el flag que indica el resultado con el valor inicial verdadero falso
        // dependiendo de si la lista esta o no vacía
        // Si la lista está vacía all será falso indicando que si no existen personas
        // que todas hablan español es falso
        // y si hay personas partimos de suponer que todas hablarán español y tratamos de probar lo contrario
        boolean allMatch = !personas.isEmpty();

        for (Persona persona : personas) {
            if (!persona.getIdiomas().contains(Idioma.ESPAÑOL)) {
                allMatch = false; //Hemos encontrado a alguien que no cumple la condición
                break; // Podemos salir antes del bucle, puesto que "la suerte ya está echada"
                // Ya nada podrá hacer que el flag "allMatch" se ponga a true
            }
        }
        return allMatch;
    }

    static boolean allPersonasHablanEspañol_v2(List<? extends Persona> personas) {
        boolean all = false;
        for (Persona persona : personas) {
            if (!(all = persona.habla(Idioma.ESPAÑOL))) break;
        }
        return all;
    }


    static boolean allPersonasHablanEspañolFunctional_v1(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona -> persona.habla(Idioma.ESPAÑOL);

        boolean allMatch = !personas.isEmpty();

        for (Persona persona : personas) {
            if (!hablaEspañol.test(persona)) { // Aplicamos el SAM del Predicate
                allMatch = false;
                break;
            }
        }
        return allMatch;
    }


    // Extraemos la lógica del bucle y la generalizamos para cualquier lista y predicado
    static <T> boolean allMatch(List<? extends T> list, Predicate<T> predicate) {
        boolean allMatch = !list.isEmpty();

        for (T item : list) {
            if (!(allMatch = predicate.test(item))) break;
        }
        return allMatch;
    }

    static boolean allPersonasHablanEspañolFunctional_v2(List<? extends Persona> personas) {
        Predicate<Persona> hablaEspañol = persona -> persona.getIdiomas().contains(Idioma.ESPAÑOL);

        return allMatch(personas, hablaEspañol);

        //Version abreviada
        //return allMatch(personas, persona -> persona.getIdiomas().contains(Idioma.ESPAÑOL));
    }


    static boolean allPersonasHablanEspañolFunctional_v3(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona -> persona.getIdiomas().contains(Idioma.ESPAÑOL);

        //AllMatch como función local definida dentro del método
        BiFunction<List<? extends Persona>, Predicate<Persona>, Boolean> allMatch =
                (list, predicate) -> {

                    boolean all = !list.isEmpty();

                    for (Persona persona : list) {
                        if (!predicate.test(persona)) {
                            all = false;
                            break;
                        }
                    }
                    return all;
                };

        return allMatch.apply(personas, hablaEspañol);
    }


    static boolean allPersonasHablanEspañolFunctional_v4(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona ->
                persona.getIdiomas().contains(Idioma.ESPAÑOL);

        // Obtenemos la función (instancia de interfaz funcional) a partir del método allMatch
        BiFunction<List<? extends Persona>, Predicate<Persona>, Boolean> allMatch = PersonasUtil::allMatch;

        return allMatch.apply(personas, hablaEspañol);
    }


    // La implementación del método allMatch la podemos usar sin usar el bucle foreach
    // usando en su lugar el método de orden superior forEach de Iterable
    // En este caso tenemos que solucionar el problema de terminar la ejecución del forEach
    // cuando ya se ha encontrado un elemento que no cumple el predicado
    // Pero, no podemos usar la instrucción break porque estamos dentro de un bucle
    // Lo que hacemos es que el Consumer que pasamos como argumento al forEach lance un excepción,
    // llamamos al forEach en un bloque try y capturamos la excepción
    // Cuando se lanza la excepción el método forEach termina prematuramente, y la excepción
    // se eleva por la pila de llamadas al método que llamó al forEach

    static <T> boolean allMatch_forEach(List<? extends T> list, Predicate<T> predicate) {

        boolean allMatch = !list.isEmpty();

        try {
            list.forEach(item -> {
                if (!predicate.test(item)) {
                    throw new MatchException("Not matching predicate", null);
                }
            });
        } catch (MatchException e) {
            allMatch = false;
        }

        return allMatch;
    }


    static boolean allPersonasHablanEspañolFunctional_v5(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona -> persona.habla(Idioma.ESPAÑOL);

        // Obtenemos la función (instancia de interfaz funcional) a partir del método
        BiFunction<List<? extends Persona>, Predicate<Persona>, Boolean> allMatch =
                PersonasUtil::allMatch_forEach;

        return allMatch.apply(personas, hablaEspañol);
    }


    static boolean allPersonasHablanEspañolFunctional_v6(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona -> persona.habla(Idioma.ESPAÑOL);

        BiFunction<List<? extends Persona>, Predicate<Persona>, Boolean> allMatch =
                (list, predicate) -> {

                    boolean all = !list.isEmpty();
                    try {
                        list.forEach(persona -> {
                            System.out.println("Processing... " + persona.getNombreCompleto());
                            if (!predicate.test(persona)) {
                                System.out.println("Not matching predicate");
                                // No se puede hacer un break aquí pero sí lanzar uns excepción
                                throw new MatchException("Not matching predicate", null);
                            }
                        });
                    } catch (MatchException e) {
                        all = false;
                    }
                    return all;
                };

        return allMatch.apply(personas, hablaEspañol);
    }

    // Método de orden superior que devuelve un predicado parametrizado mediante una closure
    public static Predicate<Persona> hablaIdioma(final Idioma idioma) {
        // La expresión lambda captura la variable / parámetro de entrada idioma
        return persona -> persona.habla(idioma);
    }

    static boolean allHablanIdioma(List<? extends Persona> personas, Idioma idioma) {
        return allMatch(personas, hablaIdioma(idioma));
    }

}
