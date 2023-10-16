package model.people;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Persona implements Comparable<Persona> {
    @Override
    public int compareTo(Persona other) {
       return other.getNacimiento().compareTo(this.getNacimiento());
    }

    public static enum Sexo { HOMBRE, MUJER }
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Persona.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("nombre='" + nombre + "'")
                .add("apellidos='" + apellidos + "'")
                .add("sexo=" + sexo)
                .add("nacimiento=" + nacimiento)
                .add("idiomas=" + idiomas)
                .toString();
    }
}
