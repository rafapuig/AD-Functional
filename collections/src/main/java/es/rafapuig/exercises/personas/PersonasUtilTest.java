package es.rafapuig.exercises.personas;

import model.people.Empleados;
import model.people.Persona;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static es.rafapuig.exercises.personas.PersonasUtil.*;

public class PersonasUtilTest {

    public static void main(String[] args) {
        testGetAllPersonasSorted();
        testGetNombresMujeresMayusculas();
        testGetNombresPersonasSortByApellidos();
        testNombresPorSexo();
        testPersonasCountPorSexo();
        testAllPersonasHablanEspañol();
    }

    static void testGetAllPersonasSorted() {
        System.out.println("\nTodas las personas ordenadas de forma natural:");
        System.out.println("------------------------------------------------------");

        List<? extends Persona> personasSorted = getAllPersonasSorted();

        for (Persona p : personasSorted) {
            String nombre = p.getNombreCompleto();
            LocalDate nacimiento = p.getNacimiento();
            long edad = p.getEdad();
            System.out.println(nombre + ", "
                    + "nacido el " + nacimiento + ", "
                    + "tiene " + edad + " años");
        }
        System.out.println("\n(Total: " + personasSorted.size() + " personas)");
    }

    static void testGetNombresMujeresMayusculas() {
        System.out.println("\nNombres en mayusculas de todas las mujeres:");
        System.out.println("------------------------------------------------------");

        String[] result = getNombresMujeresMayusculas(Empleados.EMPLEADOS);
        System.out.println(Arrays.toString(result));

        System.out.println(
                Arrays.toString(
                        getNombresMujeresMayusculasFunctional(Empleados.EMPLEADOS)));

        System.out.println("\n(Total: " + result.length + " nombres)");
    }

    static void testGetNombresPersonasSortByApellidos() {
        System.out.println("\nNombres en mayusculas ordenados por apellidos:");
        System.out.println("------------------------------------------------------");


        List<String> nombres =
                getNombresPersonasSortByApellidos(Empleados.EMPLEADOS);

        System.out.println(nombres);

        System.out.println(
                getNombresPersonasSortByApellidosFunctional(Empleados.EMPLEADOS));

        System.out.println("\n(Total: " + nombres.size() + " nombres)");
    }

    static void testNombresPorSexo() {
        System.out.println("\nNombres de personas por sexo:");
        System.out.println("------------------------------------------------------");
        System.out.println(
                getGenderToNamesMap(Empleados.EMPLEADOS));

        System.out.println(
                getGenderToNamesMapFunctional(Empleados.EMPLEADOS));
    }

    static void testPersonasCountPorSexo() {
        System.out.println("\nNumero de personas agrupadas por sexo:");
        System.out.println("------------------------------------------------------");

        System.out.println(
                getPersonasCountByGender(Empleados.EMPLEADOS));
        System.out.println(
                getPersonasCountByGenderFunctional(Empleados.EMPLEADOS));
    }

    static void testAllPersonasHablanEspañol() {
        System.out.println("\nComprobar si todas las personas hablan español:");
        System.out.println("------------------------------------------------------");

        System.out.println(
                allPersonasHablanEspañol(Empleados.EMPLEADOS));
    }
}
