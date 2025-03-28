package es.rafapuig.exercises.autopista;

import java.util.StringJoiner;

public class Coche {

    private final String matricula;
    private final String modelo;
    private final String color;

    public Coche(String matricula, String modelo, String color) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
    }

    public String getMatricula() {
        return matricula;
    }
    public String getModelo() {
        return modelo;
    }
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Coche.class.getSimpleName() + "{", "}")
                .add("'" + matricula + "'")
                .add("'" + modelo + "'")
                .add("'" + color + "'")
                .toString();
    }
}
