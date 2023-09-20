package es.rafapuig.exercises.model;

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


}
