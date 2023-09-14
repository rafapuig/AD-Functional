package es.rafapuig.model;

import java.util.List;

public class Personas {
    public static Persona aitorTilla = new Persona("Aitor Tilla", "1976-05-23");
    public static Persona belenTilla = new Persona("Belen Tilla", "1978-07-21");
    public static Persona armandoBronca = new Persona("Armando Bronca", "1982-08-13");
    public static Persona sandraMatica = new Persona("Sandra Matica", "1997-01-11");
    public static Persona amadorDenador = new Persona("Amador Denador", "1990-02-15");
    public static Persona estherMalgin = new Persona("Esther Malgin", "1974-02-28");

    public static Persona[] personas =
            {aitorTilla, belenTilla, armandoBronca, sandraMatica, amadorDenador, estherMalgin};

    public static List<Persona> personaList = List.of(personas);
}
