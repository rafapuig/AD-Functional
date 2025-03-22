package model.people;

import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.List;

import static model.people.Persona.Idioma.*;

public class Personas {

     /*private static List<Persona> personas;

    public static List<Persona> getPersonas() {
        if(personas == null) {
            personas = createPersonas();
        }
        return personas;
    }

    public static List<Persona> createPersonas() {
        List<Persona> personas = PERSONAS;
        return personas;
    }*/

    public static final Persona ARMANDO =
            new Persona(1, "Armando", "Bronca Segura",
                    Persona.Sexo.HOMBRE,
                    LocalDate.of(1970, Month.AUGUST, 3));

    public static final Persona BELEN =
            new Persona(2, "Belen", "Tilla",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1983, Month.DECEMBER, 6));

    public static final Persona ESTHER =
            new Persona(3, "Esther", "Malgin",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1988, Month.JULY, 4));

    public static final Persona AMADOR =
            new Persona(4, "Amador", "Denador",
                    Persona.Sexo.HOMBRE,
                    LocalDate.of(1994, Month.DECEMBER, 24));

    public static final Persona AITOR =
            new Persona(5, "Aitor", "Tilla",
                    Persona.Sexo.HOMBRE,
                    LocalDate.of(2001, Month.JANUARY, 7));

    public static final Persona SANDRA =
            new Persona(6, "Sandra", "Matica",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1977, Month.FEBRUARY, 19));

    public static final Persona VICTOR =
            new Persona(7, "Victor", "Nado",
                    Persona.Sexo.HOMBRE,
                    LocalDate.of(1998, Month.JUNE, 30));

    public static final Persona PEDRO =
            new Persona(8, "Pedro", "Gado",
                    Persona.Sexo.HOMBRE,
                    LocalDate.of(2002, Month.APRIL, 23));


    public static final Persona VANESA =
            new Persona(9, "Vanesa", "Tánica",
                    Persona.Sexo.MUJER,
                    LocalDate.of(2000, Month.JANUARY, 6));

    public static final Persona MARTA =
            new Persona(10, "Marta", "Baco",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1982, Month.JULY, 8));

    public static final Persona CONSUELO =
            new Persona(11, "Consuelo", "Tería",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1967, Month.APRIL, 6));

    public static List<Persona> PERSONAS =
            List.of(ARMANDO, BELEN, ESTHER, AMADOR, AITOR, SANDRA, VICTOR, PEDRO, VANESA, MARTA, CONSUELO);

    static {
        ARMANDO.setIdiomas(EnumSet.of(ESPAÑOL));
        BELEN.setIdiomas(EnumSet.of(ESPAÑOL, INGLES));
        ESTHER.setIdiomas(EnumSet.of(ESPAÑOL, INGLES));
        AMADOR.setIdiomas(EnumSet.of( ALEMAN));
        AITOR.setIdiomas(EnumSet.of(ESPAÑOL, FRANCES, INGLES));
        SANDRA.setIdiomas(EnumSet.of(ESPAÑOL, INGLES, RUSO));
        VICTOR.setIdiomas(EnumSet.of(ESPAÑOL, JAPONES));
        PEDRO.setIdiomas(EnumSet.of(ESPAÑOL, INGLES));
        VANESA.setIdiomas(EnumSet.of(ESPAÑOL, INGLES));
        MARTA.setIdiomas(EnumSet.of(ESPAÑOL, INGLES, ALEMAN, RUSO));
        CONSUELO.setIdiomas(EnumSet.of(ESPAÑOL, INGLES, ITALIANO));
    }
}
