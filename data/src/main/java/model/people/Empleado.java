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

    public int getAntiguedad() {
        return (int) ChronoUnit.YEARS.between(this.hireDate, LocalDate.now());
    }

    @Override
    protected void addFieldsToString(StringJoiner joiner) {
        super.addFieldsToString(joiner);
        joiner.add("sueldo: " + this.sueldo);
        joiner.add("contrato: " + this.hireDate);
    }

}
