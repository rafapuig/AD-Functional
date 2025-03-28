package es.rafapuig.exercises.autopista.ui;

import es.rafapuig.exercises.autopista.Coche;
import es.rafapuig.exercises.autopista.CocheProvider;

import java.util.Scanner;

public class CocheUIProvider implements CocheProvider {

    private final Scanner scanner;

    public CocheUIProvider(Scanner scanner) {
        this.scanner = scanner;
    }

     private String inputMatricula() {
        System.out.print("Introduce un número de matricula: ");
        return scanner.nextLine();
    }

    private String inputModelo() {
        System.out.print("Introduce un modelo: ");
        return scanner.nextLine();
    }

    private String inputColor() {
        System.out.print("Introduce una color: ");
        return scanner.nextLine();
    }

    private Coche inputCoche() {
        System.out.println("Introduce datos del coche: ");
        String matricula = inputMatricula();
        String modelo = inputModelo();
        String color = inputColor();
        return new Coche(matricula, modelo, color);
    }

    @Override
    public Coche provideCoche() {
        return inputCoche();
    }
}
