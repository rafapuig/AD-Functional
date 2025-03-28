package es.rafapuig.exercises.autopista.ui;

import es.rafapuig.exercises.autopista.Ventanilla;

public class MostrarVentanillasUI {

    public static MostrarVentanillasUI INSTANCE = new MostrarVentanillasUI();

    public void mostrarVentanillas(Ventanilla[] ventanillas) {
        for (Ventanilla ventanilla : ventanillas) {
            System.out.println(ventanilla);
        }
    }

}
