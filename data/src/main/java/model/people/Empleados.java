package model.people;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Empleados {
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

    public static final Empleado ARMANDO =
            new Empleado(1, "Armando","Bronca Segura",
            Persona.Sexo.HOMBRE,
            LocalDate.of(1970, Month.AUGUST, 3),
            2500, LocalDate.of(1990, Month.DECEMBER, 20));

    public static final Empleado BELEN =
            new Empleado(2, "Belen","Tilla",
            Persona.Sexo.MUJER,
            LocalDate.of(1983, Month.DECEMBER, 6),
            2100, LocalDate.of(2013, Month.FEBRUARY, 23));

    public static final Empleado ESTHER =
            new Empleado(3, "Esther","Malgin",
            Persona.Sexo.MUJER,
            LocalDate.of(1988, Month.JULY, 4),
            1800, LocalDate.of(2015, Month.MAY, 4));

    public static final Empleado AMADOR =
            new Empleado(4, "Amador","Denador",
            Persona.Sexo.HOMBRE,
            LocalDate.of(1994, Month.DECEMBER, 24),
            1600, LocalDate.of(2019, Month.APRIL, 16));

    public static final Empleado AITOR =
            new Empleado(5, "Aitor","Tilla",
            Persona.Sexo.HOMBRE,
            LocalDate.of(2001, Month.JANUARY, 7),
            1300, LocalDate.of(2020, Month.DECEMBER, 30));

    public static final Empleado SANDRA =
            new Empleado(6, "Sandra","Matica",
            Persona.Sexo.MUJER,
            LocalDate.of(1977, Month.FEBRUARY, 19),
            1500, LocalDate.of(2004, Month.SEPTEMBER, 1));

    public static final Empleado VICTOR =
            new Empleado(7, "Victor","Nado",
            Persona.Sexo.HOMBRE,
            LocalDate.of(1998, Month.JUNE, 30),
            2500, LocalDate.of(2009, Month.MARCH, 11));

    public static final Empleado PEDRO =
            new Empleado(8, "Pedro","Gado",
            Persona.Sexo.HOMBRE,
            LocalDate.of(2002, Month.APRIL, 23),
            1100, LocalDate.of(2022, Month.AUGUST, 2));


    public static final Empleado VANESA =
            new Empleado(9, "Vanesa","Tánica",
                    Persona.Sexo.MUJER,
                    LocalDate.of(2000, Month.JANUARY, 6),
                    1200, LocalDate.of(2019, Month.NOVEMBER, 14));

    public static final Empleado MARTA =
            new Empleado(10, "Marta","Baco",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1982, Month.JULY, 8),
                    1700, LocalDate.of(2005, Month.FEBRUARY, 15));

    public static final Empleado CONSUELO =
            new Empleado(11, "Consuelo","Tería",
                    Persona.Sexo.MUJER,
                    LocalDate.of(1967, Month.APRIL, 6),
                    1900, LocalDate.of(1989, Month.NOVEMBER, 3));

    public static List<Empleado> EMPLEADOS =
            List.of(ARMANDO, BELEN, ESTHER, AMADOR, AITOR, SANDRA, VICTOR, PEDRO, VANESA, MARTA, CONSUELO);

}
