package es.rafapuig.exercises.autopista.mock;

import es.rafapuig.exercises.autopista.Coche;
import es.rafapuig.exercises.autopista.CocheProvider;

import java.util.Random;

public class CocheRandomProvider implements CocheProvider {

    private static final String[] COLORS = {"rojo", "verde", "azul", "amarillo", "negro", "gris"};
    private static final String[] MARCAS = {"Seat", "Peugeot", "Kia", "Toyota", "Audi", "BMW"};

    static Random rand = new Random();

    static String generateMatricula() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int cifra = rand.nextInt(10);
            builder.append(cifra);
        }
        builder.append("-");
        for (int i = 0; i < 3; i++) {
            int letra = rand.nextInt('A', 'Z' + 1);
            builder.append((char) letra);
        }
        return builder.toString();
    }

    static String generateModelo() {
        return MARCAS[rand.nextInt(MARCAS.length)];
    }

    static String generateColor() {
        return COLORS[rand.nextInt(COLORS.length)];
    }

    @Override
    public Coche provideCoche() {
        return new Coche(generateMatricula(), generateModelo(), generateColor());
    }
}
