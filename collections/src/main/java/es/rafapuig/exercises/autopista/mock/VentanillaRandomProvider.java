package es.rafapuig.exercises.autopista.mock;

import es.rafapuig.exercises.autopista.Peaje;
import es.rafapuig.exercises.autopista.Ventanilla;
import es.rafapuig.exercises.autopista.VentanillaProvider;

import java.util.Random;

public class VentanillaRandomProvider implements VentanillaProvider {

    static Random rand = new Random();

    @Override
    public String provideVentanilla(Peaje peaje) {
        Ventanilla[] ventanillas = peaje.getVentanillas();
        return ventanillas[rand.nextInt(ventanillas.length)].getNombre();
    }
}
