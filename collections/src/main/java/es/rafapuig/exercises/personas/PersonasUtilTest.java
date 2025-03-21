package es.rafapuig.exercises.personas;

import model.people.Empleados;
import model.people.Persona;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static es.rafapuig.exercises.personas.PersonasUtil.*;

public class PersonasUtilTest {

    public static void main(String[] args) {
        new PersonasUtilTest().run();
    }

    void run() {
        testPrintPersonas();
        testGetAllPersonasSorted();
        testGetNombresMujeresMayusculas();
        testGetNombresPersonasSortByApellidos();
        testNombresPorSexo();
        testPersonasCountPorSexo();
        testAllPersonasHablanEspañol();
        testAllHablanIdioma();
    }


    @Test
    void testPrintPersonas() {
        printPersonas(Empleados.EMPLEADOS);
    }


    @Test
    void testGetAllPersonasSorted() {
        System.out.println("\nTodas las personas ordenadas de forma natural:");
        printLineSeparator();

        List<? extends Persona> personasSorted = getAllPersonasSorted(Empleados.EMPLEADOS);

        printPersonas(personasSorted);

        System.out.println("\n(Total: " + personasSorted.size() + " personas)");
    }

    @Test
    void testGetNombresMujeresMayusculas() {
        System.out.println("\nNombres en mayúsculas de todas las mujeres:");
        printLineSeparator();

        String[] result = getNombresMujeresMayusculas(Empleados.EMPLEADOS);
        System.out.println(Arrays.toString(result));

        System.out.println(
                Arrays.toString(
                        getNombresMujeresMayusculasFunctional(Empleados.EMPLEADOS)));

        System.out.println("\n(Total: " + result.length + " nombres)");
    }


    @Test
    void testGetNombresPersonasSortByApellidos() {
        System.out.println("\nNombres en mayúsculas ordenados por apellidos:");
        System.out.println("------------------------------------------------------");


        List<String> nombres =
                List.of(getNombresPersonasSortByApellidos(Empleados.EMPLEADOS));

        System.out.println(nombres);

        System.out.println(
                Arrays.toString(
                        getNombresPersonasSortByApellidosFunctional(Empleados.EMPLEADOS)));

        System.out.println(
                Arrays.toString(
                        getNombresPersonasSortByApellidosCollector(Empleados.EMPLEADOS)));

        System.out.println("\n(Total: " + nombres.size() + " nombres)");
    }


    @Test
    void testGetPersonasSortBySexo() {
        System.out.println("\nTodas las personas ordenadas por sexo:");
        printLineSeparator();
        List<? extends Persona> personasSorted = getPersonasSortBySexo(Empleados.EMPLEADOS);
        printPersonas(personasSorted);
    }

    @Test
    void testGetCountPersonasBySexo() {
        List<? extends Persona> personas = Empleados.EMPLEADOS;
        testGetPersonasSortBySexo();
        System.out.println("\nTotal de personas por sexo:");
        int mujeresCount = getCountPersonasBySexo(personas, Persona.Sexo.MUJER);
        int hombresCount = getCountPersonasBySexo(personas, Persona.Sexo.HOMBRE);
        System.out.println("(Total: " + mujeresCount + " mujeres)");
        System.out.println("(Total: " + hombresCount + " hombres)");
    }

    @Test
    void testGetHombresMayoresEdad() {
        List<? extends Persona> personas = Empleados.EMPLEADOS;
        System.out.println("\nPersonas hombres mayores edad:");
        printLineSeparator();
        List<Persona> result = getHombresMayoresEdad(personas);

        printPersonas(result);

    }



    //--------------------------------

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

        System.out.println(
                allPersonasHablanEspañolFunctional_v1(Empleados.EMPLEADOS));

        System.out.println(
                allPersonasHablanEspañolFunctional_v2(Empleados.EMPLEADOS));

        System.out.println(
                allPersonasHablanEspañolFunctional_v3(Empleados.EMPLEADOS));

        System.out.println(
                allPersonasHablanEspañolFunctional_v4(Empleados.EMPLEADOS));
    }

    static void testAllHablanIdioma() {

        Persona.Idioma idioma = Persona.Idioma.INGLES;

        System.out.println("\nComprobar si todas las hablan idioma:" + idioma);

        System.out.println(allHablanIdioma(Empleados.EMPLEADOS, idioma));
    }
}
