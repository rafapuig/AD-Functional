package es.rafapuig.exercises.autopista;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.StringJoiner;

public class Ventanilla {

    private final String nombre;
    private final Queue<Coche> colaCoches;

    public Ventanilla(String nombre) {
        this.nombre = nombre;
        this.colaCoches = new LinkedList<Coche>();
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Pone un coche al final de la Cola
     */
    public void ponerEnCola(Coche coche) {
        colaCoches.add(coche); // Delegar en la cola la inserción del coche en el final de cola
    }

    /**
     * Extrae un coche del principio de la cola
     */
    public Coche quitarDeCola() {
        try {
            return colaCoches.remove();// Delegar para que la cola devuelva el coche de la cabeza de la cola
        } catch (NoSuchElementException ex) {
            throw new IllegalStateException("No se puede quitar de la cola ningún coche porque está vacía", ex);
        }
    }

    public Coche getPrimerCoche() {
        return colaCoches.peek();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", nombre + "{", "}")
                .add("Cola =" + colaCoches)
                .toString();
    }
}
