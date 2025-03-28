package es.rafapuig.model;

import java.util.List;
import java.util.Set;

public class Personas {

    public static final Persona estherMalgin =
            new Persona("Esther Malgin", "18-02-1974");

    public static final Persona aitorTilla =
            new Persona("Aitor Tilla", "23-05-1976");

    public static final Persona belenTilla =
            new Persona("Belen Tilla", "21-07-1978");

    public static final Persona armandoBronca =
            new Persona("Armando Bronca", "13-08-1982");

    public static final Persona amadorDenador =
            new Persona("Amador Denador", "15-02-1990");

    public static final Persona sandraMatica =
            new Persona("Sandra Matica", "11-01-1997");


    public static Persona[] personas =
            {aitorTilla, belenTilla, armandoBronca, sandraMatica, amadorDenador, estherMalgin};

    public static List<Persona> personaList = List.of(personas);

    public static Set<Persona> personaSet = Set.of(personas);

}
