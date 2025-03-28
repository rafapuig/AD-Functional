package es.rafapuig.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class Persona implements Comparable<Persona>, Cloneable {

    private String nombre;
    private LocalDate fechaNacimiento;


    protected static LocalDate fromString(String fechaNacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fechaNacimiento, formatter);
    }


    public Persona(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona(String nombre, String fechaNacimiento) {
        this(nombre, fromString(fechaNacimiento));
    }

    /**
     * Es posible la clonación superficial dado que los atributos de Persona son inmutables
     * y se pueden compartir
     */
    public Persona tryClone() throws CloneNotSupportedException {
        return (Persona) super.clone();
    }

    @Override
    public Persona clone() {
        try {
            return tryClone();
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
        setFechaNacimiento(fromString(fechaNacimiento));
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        return new StringJoiner(", ", "{", "}")
                .add("'" + nombre + "'")
                .add(fechaNacimiento.format(formatter))
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



    private static final Comparator<Persona> byEdadComparator = Comparator.comparing(Persona::getFechaNacimiento);
    private static final Comparator<Persona> byEdadComparatorLambdaExpr =
            (p1, p2) -> p1.getFechaNacimiento().compareTo(p2.getFechaNacimiento());
    private static final Comparator<Persona> byEdadComparatorAnon = new ByEdadComparator();

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
