package es.rafapuig.exercises.domino;

import java.util.Objects;

/**
 * Modela una ficha de dómino, con un valor de 0 a 6 en su lado superior
 * y otro en el lado inferior
 */
public class Dominoes implements Comparable<Dominoes>{

    private final int top;
    private final int bottom;

    public Dominoes(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getPoints() {
        return top + bottom;
    }

    /**
     * Dos fichas de dominó se consideran iguales si tienen el mismo valor arriba y abajo
     * y también si le damos la vuelta a una ficha de ellas y entonces tienen el mismo valor arriba y abajo
     * @param o referencia a la otra ficha con la que se va a comparar para ver si son iguales
     * @return true si las fichas se consideran iguales, false si no
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dominoes dominoes)) return false;

        return top == dominoes.top && bottom == dominoes.bottom
                || top == dominoes.bottom && bottom == dominoes.top;
    }

    // Por contrato, dos instancias Dominoes que sean consideradas iguales
    // (lo que equivale a que el método "equals" devuelve true cuando se comparan)
    // deben devolver el mismo valor de hashCode
    // Lo que implica que como la ficha 2:3 se considera igual a la ficha 3:2
    // debemos garantizar que tanto la ficha X:Y como la Y:X devuelven el mismo valor de hash
    @Override
    public int hashCode() {
        int min = Math.min(top, bottom);
        int max = Math.max(top, bottom);
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return String.format("[%d : %d]", top, bottom);
    }

    @Override
    public int compareTo(Dominoes other) {

        // Primero comparamos si son iguales por el lado superior
        int topComparison = Integer.compare(this.top, other.top);

        // Si no son iguales los lados superiores devolvemos el valor de la comparación
        if(topComparison != 0) return topComparison;

        // Si son iguales entonces "desempatamos" comparando los lados inferiores
        return Integer.compare(bottom, other.bottom);

        //int points = this.top + this.bottom;
        //int otherPoints = o.top + o.bottom;
        //return points - otherPoints;
    }


}
