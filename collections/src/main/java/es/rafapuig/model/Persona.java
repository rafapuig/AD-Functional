package es.rafapuig.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class Persona implements Comparable<Persona>, Cloneable {
    private String nombre;
    private LocalDate fechaNacimiento;

    public Persona(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona(String nombre, String fechaNacimiento) {
        this(nombre, LocalDate.parse(fechaNacimiento));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clone = super.clone();

        return new Persona(this.getNombre(), this.getFechaNacimiento());
        //return super.clone();
    }

    public Persona cloneMe() {
        try {
            return (Persona) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }





    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        setFechaNacimiento(LocalDate.parse(fechaNacimiento));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Persona.class.getSimpleName().substring(0, 0) + "{", "}")
                .add("'" + nombre + "'")
                .add(fechaNacimiento.format(DateTimeFormatter.ofPattern("d-MMM-yyyy")))
                .toString();
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return Objects.equals(nombre, persona.nombre) && Objects.equals(fechaNacimiento, persona.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, fechaNacimiento);
    }

    @Override
    public int compareTo(Persona other) {
        return this.getNombre().compareTo(other.getNombre());
    }

    private static Comparator<Persona> byEdadComparator = Comparator.comparing(Persona::getFechaNacimiento);
    private static Comparator<Persona> byEdadComparatorLambdaExpr =
            (p1, p2) -> p1.getFechaNacimiento().compareTo(p2.getFechaNacimiento());
    private static Comparator<Persona> byEdadComparatorAnon = new ByEdadComparator();

    public static Comparator<Persona> getByEdadComparator() {
        return Comparator.comparing(Persona::getFechaNacimiento);
    }

    public int compareByEdad(Persona other) {
        return getByEdadComparator().compare(this, other);
        //return Comparator.comparing(Persona::getFechaNacimiento).compare(this, other);
    }

    public static class ByEdadComparator implements Comparator<Persona> {

        @Override
        public int compare(Persona o1, Persona o2) {
            return o1.getFechaNacimiento().compareTo(o2.getFechaNacimiento());
        }
    }
}
