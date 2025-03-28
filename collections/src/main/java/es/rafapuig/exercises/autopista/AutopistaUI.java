package es.rafapuig.exercises.autopista;

import es.rafapuig.exercises.autopista.mock.CocheRandomProvider;
import es.rafapuig.exercises.autopista.mock.VentanillaRandomProvider;
import es.rafapuig.exercises.autopista.ui.MostrarVentanillasUI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AutopistaUI {

    static Scanner scanner = new Scanner(System.in);

    CocheProvider cocheProvider;
    VentanillaProvider ventanillaProvider;

    Peaje peaje = new Peaje();

    public AutopistaUI(CocheProvider cocheProvider, VentanillaProvider ventanillaProvider) {
        this.cocheProvider = cocheProvider;
        this.ventanillaProvider = ventanillaProvider;
    }

    static String[] menuItems = {
            "1. Poner en cola en ventanilla",
            "2. Sacar de la cola de la ventanilla",
            "3. Mostrar colas de las ventanillas",
            "0. Salir"
    };

    void printMenu() {
        System.out.println("Autopista de peaje:");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println(menuItems[i]);
        }
    }

    int inputMenuOpcion() {
        while (true) {
            try {
                printMenu();
                System.out.print("Selecciona una opción: ");
                int opcion = scanner.nextInt();
                if (opcion >= 0 && opcion < menuItems.length) {
                    return opcion;
                }
                System.out.println("Opción no valida.");
            } catch (InputMismatchException ex) {
                System.out.println("Introduce un numero de opción");
            } finally {
                scanner.nextLine(); // Vaciar el buffer del scanner
            }
        }
    }


    void ponerEnColaEnVentanilla(Peaje peaje, CocheProvider cocheProvider) {
        System.out.println("Poner en cola en ventanilla");
        Coche coche = cocheProvider.provideCoche();
        String nombreVentanilla = ventanillaProvider.provideVentanilla(peaje);
        peaje.addCoche(coche, nombreVentanilla);
    }

    void quitarDeVentanilla(Peaje peaje) {
        System.out.println("Quitar coche de ventanilla");
        String nombreVentanilla = ventanillaProvider.provideVentanilla(peaje);
        try {
            peaje.darSalidaCoche(nombreVentanilla);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void mostrarVentanillas(Peaje peaje) {
        //MostrarVentanillasUI.INSTANCE.mostrarVentanillas(peaje.getVentanillas());
        for (Ventanilla ventanilla : peaje.getVentanillas()) {
            System.out.println(ventanilla);
        }
    }


    public static void main(String... args) {
        //CocheProvider cocheProvider = new CocheUIProvider(scanner);
        //VentanillaProvider ventanillaProvider = new VentanillaUIProvider(scanner);

        VentanillaProvider ventanillaProvider = new VentanillaRandomProvider();
        CocheProvider cocheProvider = new CocheRandomProvider();

        new AutopistaUI(cocheProvider, ventanillaProvider).run();
    }

    private void run() {
        while (true) {
            int opcion = inputMenuOpcion();
            switch (opcion) {
                case 1:
                    ponerEnColaEnVentanilla(peaje, cocheProvider);
                    break;
                case 2:
                    quitarDeVentanilla(peaje);
                    break;
                case 3:
                    mostrarVentanillas(peaje);
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }
}
