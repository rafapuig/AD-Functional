package es.rafapuig.model;

import java.util.Arrays;
import java.util.StringJoiner;

public class Alumno {

    public enum OrdenApellido {PRIMER, SEGUNDO}

    private String NIA;
    private String nombre;
    private String[] apellidos = new String[OrdenApellido.values().length];
    private int edad;

    public Alumno() { }

    public Alumno(String NIA, String nombre, String[] apellidos, int edad) {
        this.NIA = NIA;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public String getNIA() {
        return NIA;
    }

    public void setNIA(String NIA) {
        this.NIA = NIA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String[] getApellidos() {
        return apellidos;
    }

    public String getApellido(OrdenApellido ordenApellido) {
        return this.apellidos[ordenApellido.ordinal()];
    }

    public void setApellidos(String... apellidos) {
        this.apellidos = apellidos;
    }

    public void setApellido(OrdenApellido ordenApellido, String apellido) {
        this.apellidos[ordenApellido.ordinal()] = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Alumno.class.getSimpleName() + "[", "]")
                .add("NIA='" + NIA + "'")
                .add("nombre='" + nombre + "'")
                .add("apellidos=" + Arrays.toString(apellidos))
                .add("edad=" + edad)
                .toString();
    }
}
