package es.rafapuig.exercises.personas;

import model.people.Persona;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collector;

import static model.people.Persona.Sexo;
import static model.people.Persona.Idioma;

public class PersonasUtil {

    /**
     * Obtener una lista de todas las personas ordenadas por su ordenamiento natural
     * El ordenamiento natural se corresponde con la ordenacion de los elementos (Persona)
     * mediante la implementacion de la interfaz Comparable por la propia clase Persona
     *
     * @param personas
     * @return
     */
    public static List<? extends Persona> getAllPersonasSorted(List<? extends Persona> personas) {
        List<Persona> sorted = new ArrayList<>(personas);
        sorted.sort(Comparator.naturalOrder());
        // equivalente pasar como argumento el valor null, equivale a pedir ordenacion natural
        //sorted.sort(null);
        // equivalente, usar el metodo estatico sort sin argumentos de la clase Collections
        //Collections.sort(sorted);
        return sorted;
    }

    static String[] getNombresMujeresMayusculas(List<? extends Persona> personas) {
        List<String> resultList = new ArrayList<>();            //Crear la lista de recoleccion

        for (Persona persona : personas) {                      // iteracion externa
            if (persona.isMujer()) {                            // filtro
                String nombre = persona.getNombre();            // mapeo
                String upperCaseNombre = nombre.toUpperCase();  // otro mapeo
                resultList.add(upperCaseNombre);                // recoleccion en lista
            }
        }

        return resultList.toArray(new String[resultList.size()]);
    }

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
    static List<String> getNombresPersonasSortByApellidos(List<? extends Persona> personas) {

        //Hacer una copia de la lista propocionada, el original no se puede modificar
        List<Persona> sortedPersonas = new ArrayList<>(personas);

        //Ordenar la lista con el metodo sort
        //A este metodo se le debe prpocionar una instacia de un objeto que implemente la
        //interfaz Comparable<T> y como la lista es de personas T es de tipo Persona

        sortedPersonas.sort(
                //Para ello creamos un tipo anonimo que implementa la interfaz Comparator<Persona>
                new Comparator<Persona>() {
                    //La interfaz Comparator es una interfaz funcional dado que solamente
                    //consta de un SAM, (tiene mas metodos pero no sob abstractos)
                    //Implementamos el metodo compare que recibe 2 referecias a objetos Persona
                    @Override
                    public int compare(Persona o1, Persona o2) {
                        return o1.getApellidos().compareTo(o2.getApellidos());
                    }
                }
        );

        List<String> nombres = new ArrayList<>();   //Coleccion donde acumularemos los resultados

        for (Persona persona : sortedPersonas) {                    // iteracion externa
            String nombreCompleto = persona.getNombreCompleto();    // mapeo Persona a nombre
            nombres.add(nombreCompleto);                // acumular el elemento en la coleccion
        }
        return nombres;
    }

    static List<String> getNombresPersonasSortByApellidosFunctional(List<? extends Persona> personas) {

        // Creacion del tipo anonimo que implementa la interfaz Collector<T,A,R>
        Collector<String, List<String>, List<String>> toList = new Collector<>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ArrayList::new;  // El supplier propociona el objeto coleccion
            }

            @Override
            public BiConsumer<List<String>, String> accumulator() {
                return List::add;   // El acumulador indica añadir cada elemento
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left; // Combiner indica como fusionar dos colleciones en una
                };
            }

            @Override
            public Function<List<String>, List<String>> finisher() {
                return Function.identity(); //finisher indica como adaptar el resultado
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

        List<String> nombres = toList.supplier().get(); //Obtener el objeto donde recolectar
        sortedPersonas.forEach(persona -> {
            // mapear, dada una persona obtenemos su nombre completo
            String nombreCompleto = map.apply(persona, Persona::getNombreCompleto);
            // usar el acumulador para añadir a la coleccion el nuevo elemento procesado
            toList.accumulator().accept(nombres, nombreCompleto);
        });

        return toList.finisher().apply(nombres);    //Adaptamos el resultado
    }


    //--------- Nombres de personas agrupadas por sexo

    /**
     * Obtener, por cada sexo, un String con los nombres de las personas
     * concatenados entre si y separados por una coma
     *
     * @param personas
     * @return un mapa de clave sexo y valor un String con la cadena de nombres pora ese sexo
     */

    static Map<Sexo, String> getGenderToNamesMap(List<? extends Persona> personas) {
        Map<Sexo, String> map = new HashMap<>();

        for (Persona persona : personas) {
            Sexo sexo = persona.getSexo();
            if (!map.containsKey(sexo)) {
                map.put(sexo, persona.getNombreCompleto());
            } else {
                String oldValue = map.get(sexo);
                map.put(sexo, String.join(", ", oldValue, persona.getNombreCompleto()));
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
                        // El tercer parametro es una BiFunction<V> que recibe el valor actual
                        // asociado a la clave y el nuevo valor que queremos asociar
                        // y devuelve el valor que definitivamente queremo asociar con la clave
                        // en este caso acumulamos en el string oldValue, que contiene la cadena
                        // con la lista de nombres parcial, el nuevo valor (nombre completo del
                        // elemento que estamos procesando en la iteracion actual)
                        (oldValue, newValue) -> String.join(", ", oldValue, newValue)));
        return map;
    }


    //------------- Numero de personas por cada sexo

    /**
     * Obtener el numero de personas por cada sexo a patir de una lista de personas
     * @param personas
     * @return un mapa cuyas claves son de tipo Sexo y el valor asociado un Long que
     * contiene el numero de personas para el valor clave Sexo al que esta asociado
     */

    static Map<Sexo, Long> getPersonasCountByGender(List<? extends Persona> personas) {
        Map<Sexo, Long> map = new HashMap<>();

        for (Persona persona : personas) {
            Persona.Sexo sexo = persona.getSexo();
            if (!map.containsKey(sexo)) {
                map.put(sexo, 1L);
            } else {
                long oldCount = map.get(sexo);
                map.put(sexo, oldCount + 1L);
            }
        }

        return map;
    }

    static Map<Persona.Sexo, Long> getPersonasCountByGenderFunctional(List<? extends Persona> personas) {
        Map<Persona.Sexo, Long> map = new HashMap<>();

        personas.forEach(
                persona -> {
                    map.merge(
                            persona.getSexo(),
                            1L,
                            (oldCount, newCount) -> oldCount + 1);
                }
        );

        return map;
    }

    /**
     * Comprobar si todos los elementos (Persona) de la lista proporcionada
     * hablan el idioma Español
     * @param personas
     * @return un valor boolean que sera true si se cumple la condicion y false si no
     */

    static boolean allPersonasHablanEspañol(List<? extends Persona> personas) {
        boolean all = false;
        for (Persona persona : personas) {
            if (persona.getIdiomas().contains(Idioma.ESPAÑOL)) {
                all = true;
            } else {
                all = false;
                break;
            }
        }
        return all;
    }

    static boolean allPersonasHablanEspañolFuctional_v1(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona -> persona.getIdiomas().contains(Idioma.ESPAÑOL);

        BiFunction<List<? extends Persona>, Predicate<Persona>, Boolean> allMatch =
                (list, predicate) -> {
                    boolean all = false;

                    for (Persona persona : list) {
                        if (predicate.test(persona)) {
                            all = true;
                        } else {
                            all = false;
                            break;
                        }
                    }
                    return all;
                };

        return allMatch.apply(personas, hablaEspañol);
    }


    static <T> boolean allMatch(List<? extends T> list, Predicate<T> predicate) {
        boolean all = false;

        for (T item : list) {
            if (predicate.test(item)) {
                all = true;
            } else {
                all = false;
                break;
            }
        }
        return all;
    }

    static boolean allPersonasHablanEspañolFuctional(List<? extends Persona> personas) {

        Predicate<Persona> hablaEspañol = persona -> persona.getIdiomas().contains(Idioma.ESPAÑOL);

        BiFunction<List<? extends Persona>, Predicate<Persona>, Boolean> allMatch = PersonasUtil::allMatch;

        return allMatch.apply(personas, hablaEspañol);
    }

}
