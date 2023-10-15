package model.people;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.StringJoiner;

public class Empleado extends Persona {
    private double sueldo;

    private LocalDate hireDate;

    public Empleado(long id, String nombre, String apellidos, Sexo sexo,
                    LocalDate nacimiento, double sueldo, LocalDate fechaContrato) {
        super(id, nombre, apellidos, sexo, nacimiento);
        this.sueldo = sueldo;
        this.hireDate = fechaContrato;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public long getAntiguedad() {
        return ChronoUnit.YEARS.between(this.hireDate, LocalDate.now());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Empleado.class.getSimpleName() + "[", "]")
                .add(Long.toString(getId()))
                .add(getNombre() + " " + getApellidos())
                .add("sexo=" + getSexo())
                .add("nacimiento=" + getNacimiento())
                .add("sueldo=" + sueldo)
                .add("contrato=" + hireDate)
                .toString();
    }
}
