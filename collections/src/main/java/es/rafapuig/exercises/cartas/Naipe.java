package es.rafapuig.exercises.cartas;

import java.util.Objects;
import java.util.StringJoiner;

public class Naipe {
    private Palo palo;
    private Valor valor;

    public Naipe(Palo palo, Valor valor) {
        this.palo = palo;
        this.valor = valor;
    }

    public Palo getPalo() {
        return palo;
    }

    public Valor getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Naipe naipe)) return false;
        return palo == naipe.palo && valor == naipe.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(palo, valor);
    }

    @Override
    public String toString() {
        return valor + " de " + palo;
    }
}
