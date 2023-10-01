package model;

import java.time.LocalDate;

public class Persona {

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
