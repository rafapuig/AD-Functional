package model;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Persona {

    private static List<Persona> PERSONAS_SAMPLE = createPersonas();

    {
        PERSONAS_SAMPLE = createPersonas();
    }

    public static List<Persona> createPersonas() {
        Persona armando = new Persona(1, "Armando Bronca Segura",
                Sexo.HOMBRE,
                LocalDate.of(1970, Month.AUGUST, 3),
                1300);

        Persona belen = new Persona(2, "Belen Tilla",
                Sexo.MUJER,
                LocalDate.of(1983, Month.APRIL, 12),
                2100);

        Persona esther = new Persona(3, "Esther Malgin",
                Sexo.MUJER,
                LocalDate.of(1988, Month.JULY, 5),
                1800);

        Persona amador = new Persona(4, "Amador Denador",
                Sexo.HOMBRE,
                LocalDate.of(1994, Month.DECEMBER, 24),
                1600);

        Persona aitor = new Persona(5, "Aitor Tilla",
                Sexo.HOMBRE,
                LocalDate.of(2001, Month.JANUARY, 7),
                2500);

        Persona sandra = new Persona(2, "Sandra Matica",
                Sexo.MUJER,
                LocalDate.of(1977, Month.FEBRUARY, 19),
                1400);

        List<Persona> personas = List.of(armando, belen, esther, amador, aitor, sandra);
        return personas;
    }

    public static enum Sexo {
        HOMBRE,
        MUJER
    }

    private long id;
    private String nombre;
    private Sexo sexo;
    private LocalDate nacimiento;
    private double sueldo;

    public Persona(long id, String nombre, Sexo sexo, LocalDate nacimiento, double sueldo) {
        this.id = id;
        this.nombre = nombre;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.sueldo = sueldo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public boolean isHombre() {
        return this.sexo == Sexo.HOMBRE;
    }

    public boolean isMujer() {
        return this.sexo == Sexo.MUJER;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", sexo=" + sexo +
                ", nacimiento=" + nacimiento +
                ", sueldo=" + sueldo +
                '}';
    }
}
