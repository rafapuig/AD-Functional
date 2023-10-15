package es.rafapuig.exercises.personas;


import model.people.Empleado;
import model.people.Persona;
import model.people.Empleados;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collector;

public class PersonasUtil {

    public static void main(String[] args) {

        getAllPersonasSorted();
        getAllEmpleadosSortedByHireDate();

        //testNombresPorSexo();

        //testPersonasCountPorSexo();

        //testEmpleadoMejorPagadoPorSexo();
    }







    public static List<? extends Persona> getAllPersonasSorted() {
        List<Persona> sorted = new ArrayList<>(Empleados.EMPLEADOS);
        sorted.sort(Comparator.naturalOrder());
        return sorted;
    }

    static List<Empleado> getAllEmpleadosSortedByHireDate() {
        List<Empleado> sorted = new ArrayList<>(Empleados.EMPLEADOS);
        sorted.sort(Comparator.comparing(Empleado::getHireDate));
        return sorted;
    }


    static String[] getNombresMujeresMayusculas(List<? extends Persona> personas) {
        List<String> resultList = new ArrayList<>();

        for (Persona persona : personas) {
            if (persona.isMujer()) {
                String nombre = persona.getNombre();
                String upperCaseNombre = nombre.toUpperCase();
                resultList.add(upperCaseNombre);
            }
        }

        return resultList.toArray(new String[resultList.size()]);
    }

    static String[] getNombresMujeresMayusculasFunctional(List<? extends Persona> personas) {

        List<String> resultList = new ArrayList<>();

        Predicate<Persona> isMujer = persona -> persona.isMujer();
        Function<Persona, String> mapToName = persona -> persona.getNombre();
        UnaryOperator<String> toUpperCaseName = name -> name.toUpperCase();

        personas.forEach(persona -> {
            if (isMujer.test(persona)) {
                String nombre = mapToName.apply(persona);
                String upperCaseNombre = toUpperCaseName.apply(nombre);
                resultList.add(upperCaseNombre);
                //resultList.add(toUpperCaseName.apply(mapToName.apply(persona)));
            }
        });

        return resultList.toArray(new String[resultList.size()]);
    }



    //---------------Nombres personas ordenados por apellidos

    static List<String> getNombresPersonasSortByApellidos(List<? extends Persona> personas) {

        List<Persona> sortedPersonas = new ArrayList<>(personas);
        sortedPersonas.sort(Comparator.comparing(Persona::getApellidos));


        List<String> nombres = new ArrayList<>();
        for (Persona persona : sortedPersonas) {
            String nombreCompleto = persona.getNombreCompleto();
            nombres.add(nombreCompleto);
        }
        return nombres;
    }

    static List<String> getNombresPersonasSortByApellidosFunctional(List<? extends Persona> personas) {

        Collector<String, List<String>, List<String>> toList = new Collector<>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<String>, String> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (left, right) -> {
                    left.addAll(right);
                    return left;
                };
            }

            @Override
            public Function<List<String>, List<String>> finisher() {
                return Function.identity();
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

        List<String> nombres = toList.supplier().get();
        sortedPersonas.forEach(persona -> {
            String nombreCompleto = map.apply(persona, Persona::getNombreCompleto);
            toList.accumulator().accept(nombres, nombreCompleto);
        });

        return toList.finisher().apply(nombres);
    }


    //--------- Nombres de personas agrupadas por sexo

    static Map<Persona.Sexo, String> getGenderToNamesMap(List<? extends Persona> personas) {
        Map<Persona.Sexo, String> map = new HashMap<>();

        for (Persona persona : personas) {
            Persona.Sexo sexo = persona.getSexo();
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
                        persona.getSexo(),
                        persona.getNombreCompleto(),
                        (oldValue, newValue) -> String.join(", ", oldValue, newValue)));
        return map;
    }


    //------------- Numero de personas por cada sexo

    static Map<Persona.Sexo, Long> getPersonasCountByGender(List<? extends Persona> personas) {
        Map<Persona.Sexo, Long> map = new HashMap<>();

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

}
