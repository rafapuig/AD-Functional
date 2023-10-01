package model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Personas {
    private static List<Persona> personas;

    public static List<Persona> getPersonas() {
        if(personas == null) {
            personas = createPersonas();
        }
        return personas;
    }

    public static List<Persona> createPersonas() {
        List<Persona> personas = PERSONAS;
        return personas;
    }

    public static Persona ARMANDO = new Persona(1, "Armando Bronca Segura",
            Persona.Sexo.HOMBRE,
            LocalDate.of(1970, Month.AUGUST, 3),
            1300);

    public static Persona BELEN = new Persona(2, "Belen Tilla",
            Persona.Sexo.MUJER,
            LocalDate.of(1983, Month.APRIL, 12),
            2100);

    public static Persona ESTHER = new Persona(3, "Esther Malgin",
            Persona.Sexo.MUJER,
            LocalDate.of(1988, Month.JULY, 5),
            1800);

    public static Persona AMADOR = new Persona(4, "Amador Denador",
            Persona.Sexo.HOMBRE,
            LocalDate.of(1994, Month.DECEMBER, 24),
            1600);

    public static Persona AITOR = new Persona(5, "Aitor Tilla",
            Persona.Sexo.HOMBRE,
            LocalDate.of(2001, Month.JANUARY, 7),
            2500);

    public static Persona SANDRA = new Persona(2, "Sandra Matica",
            Persona.Sexo.MUJER,
            LocalDate.of(1977, Month.FEBRUARY, 19),
            1400);

    public static List<Persona> PERSONAS = List.of(ARMANDO, BELEN, ESTHER, AMADOR, AITOR, SANDRA);

}
