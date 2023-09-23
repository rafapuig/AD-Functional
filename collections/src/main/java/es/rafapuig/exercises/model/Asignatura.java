package es.rafapuig.exercises.model;

import java.util.StringJoiner;

public class Asignatura {

    public enum Curso { PRIMERO, SEGUNDO }


    private String codigo;

    private Curso curso;

    private String nombre;

    private int horasSemana;

    public Asignatura(String codigo, Curso curso, String nombre, int horasSemana) {
        this.codigo = codigo;
        this.curso = curso;
        this.nombre = nombre;
        this.horasSemana = horasSemana;
    }

    public String getCodigo() {
        return codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Asignatura.class.getSimpleName() + "[", "]")
                .add("codigo='" + codigo + "'")
                .add("curso=" + curso)
                .add("nombre='" + nombre + "'")
                .add("horasSemana=" + horasSemana)
                .toString();
    }
}
