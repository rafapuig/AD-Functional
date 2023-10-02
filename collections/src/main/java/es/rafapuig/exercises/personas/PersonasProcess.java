package es.rafapuig.exercises.personas;


import model.people.Empleado;
import model.people.Persona;
import model.people.Empleados;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class PersonasProcess {

    public static void main(String[] args) {

        String[] result = getNombresMujeres(Empleados.EMPLEADOS);
        System.out.println(Arrays.toString(result));

        System.out.println(Arrays.toString(getNombresMujeresFunctional(Empleados.EMPLEADOS)));

        System.out.println(getSueldoMedioHombresMujeresImperative(Empleados.EMPLEADOS));
        System.out.println(getSueldoMedioHombresMujeresFunctional(Empleados.EMPLEADOS));
    }

    static String[] getNombresMujeres(List<? extends Persona> personas) {
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

    static String[] getNombresMujeresFunctional(List<? extends Persona> personas) {

        List<String> resultList = new ArrayList<>();

        Predicate<Persona> isMujer = persona -> persona.isMujer();
        Function<Persona, String> mapToName = persona -> persona.getNombre();
        UnaryOperator<String> toUpperCaseName = name -> name.toUpperCase();

        personas.forEach( persona -> {
            if (isMujer.test(persona)) {
                String nombre = mapToName.apply(persona);
                String upperCaseNombre = toUpperCaseName.apply(nombre);
                resultList.add(upperCaseNombre);
                //resultList.add(toUpperCaseName.apply(mapToName.apply(persona)));
            }
        });

        return resultList.toArray(new String[resultList.size()]);
    }

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


    static BiFunction<Persona.Sexo,List<Double>,List<Double>> addSueldoToList(Empleado empleado) {
        return (sexo, sueldos) -> {
            sueldos.add(empleado.getSueldo());
            return sueldos;
        };
    };

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

}
