package model.people;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Persona implements Comparable<Persona> {

    public static final int AÑOS_MAYORIA_EDAD = 18;

    @Override
    public int compareTo(Persona other) {
        //Si la otra persona tiene una fecha de nacimiento menor (anterior) a la mia -> -1 this es menor
        //Si la otra persona nació después de this (fecha de nacimiento mayor) -> 1, this es mayor
        return other.getNacimiento().compareTo(this.getNacimiento());
    }

    public enum Sexo {HOMBRE, MUJER}

    public static enum Idioma {INGLES, ESPAÑOL, ITALIANO, FRANCES, ALEMAN, JAPONES, RUSO}

    private long id;
    private String nombre;
    private String apellidos;
    private Sexo sexo;
    private LocalDate nacimiento;
    private Set<Idioma> idiomas = EnumSet.noneOf(Idioma.class);

    public Persona(long id, String nombre, String apellidos, Sexo sexo, LocalDate nacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
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

    public String getApellidos() {
        return apellidos;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public long getEdad() {
        return ChronoUnit.YEARS.between(this.nacimiento, LocalDate.now());
    }

    public boolean isMayorEdad() {
        return Period.between(nacimiento, LocalDate.now()).getYears() >= AÑOS_MAYORIA_EDAD;
        //return getEdad() >= 18;
    }

    public boolean isHombre() {
        return this.sexo == Sexo.HOMBRE;
    }

    public boolean isMujer() {
        return this.sexo == Sexo.MUJER;
    }

    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public boolean habla(Idioma idioma) {
        return this.idiomas.contains(idioma);
    }


    protected void addFieldsToString(StringJoiner joiner) {
        joiner
                .add("id=" + id)
                .add("nombre='" + nombre + "'")
                .add("apellidos='" + apellidos + "'")
                .add("sexo=" + sexo)
                .add("nacimiento=" + nacimiento)
                .add("idiomas=" + idiomas);
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        StringJoiner joiner = new StringJoiner(", ", className + "{", "}");
        addFieldsToString(joiner);
        return joiner.toString();
    }
}
