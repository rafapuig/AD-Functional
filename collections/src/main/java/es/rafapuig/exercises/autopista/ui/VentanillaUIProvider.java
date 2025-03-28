package es.rafapuig.exercises.autopista.ui;

import es.rafapuig.exercises.autopista.Peaje;
import es.rafapuig.exercises.autopista.Ventanilla;
import es.rafapuig.exercises.autopista.VentanillaProvider;

import java.util.InputMismatchException;
import java.util.Scanner;

public class VentanillaUIProvider implements VentanillaProvider {

    Scanner scanner;

    public VentanillaUIProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    String[] getNombresVentanillas(Peaje peaje) {
        Ventanilla[] ventanillas = peaje.getVentanillas();
        String[] nombres = new String[ventanillas.length];
        for (int i = 0; i < ventanillas.length; i++) {
            nombres[i] = ventanillas[i].getNombre();
        }
        return nombres;
    }

    void printVentanillasNames(String[] nombres) {
        for (int i = 0; i < nombres.length; i++) {
            System.out.println("%d - %s".formatted(i + 1, nombres[i]));
        }
    }


    String inputVentanilla(Peaje peaje) {
        String[] nombres = getNombresVentanillas(peaje);
        while (true) {
            try {
                System.out.println("Elige ventanilla: ");
                printVentanillasNames(nombres);
                int opcion = scanner.nextInt();
                if (opcion >= 0 && opcion < nombres.length) {
                    return nombres[opcion - 1];
                }
                System.out.println("Opción no valida.");
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.out.println("Introduce un numero de opción");
            }
        }
    }

    @Override
    public String provideVentanilla(Peaje peaje) {
        return inputVentanilla(peaje);
    }
}
