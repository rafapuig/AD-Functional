package model.people;

import java.time.LocalDate;
import java.util.StringJoiner;

public class Empleado extends Persona {
    private double sueldo;

    public Empleado(long id, String nombre, String apellidos, Sexo sexo, LocalDate nacimiento, double sueldo) {
        super(id, nombre, apellidos, sexo, nacimiento);
        this.sueldo = sueldo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Empleado.class.getSimpleName() + "[", "]")
                .add(Long.toString(getId()))
                .add(getNombre() + " " + getApellidos())
                .add("sexo=" + getSexo())
                .add("nacimiento=" + getNacimiento())
                .add("sueldo=" + sueldo)
                .toString();
    }
}
