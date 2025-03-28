package es.rafapuig.exercises.autopista;

import java.util.*;
import java.util.stream.Collectors;

public class Peaje {

    public static final String EFECTIVO = "EFECTIVO";
    public static final String IMPORTE_EXACTO = "IMPORTE_EXACTO";
    public static final String TARJETA = "TARJETA";

    public static final String[] NOMBRES_VENTANILLAS = {EFECTIVO, IMPORTE_EXACTO, TARJETA};

    //private final Ventanilla ventanillaEfectivo = new Ventanilla("EFECTIVO");
    //private final Ventanilla ventanillaImporteExacto = new Ventanilla("IMPORTE_EXACTO");
    //private final Ventanilla ventanillaTarjeta = new Ventanilla("TARJETA");

    //private final Ventanilla[] ventanillas = new Ventanilla[3];

    //private final Ventanilla[] ventanillas = new Ventanilla[] {ventanillaEfectivo, ventanillaImporteExacto, ventanillaTarjeta};

    //private final Ventanilla[] ventanillas = {ventanillaEfectivo, ventanillaImporteExacto, ventanillaTarjeta};

    /*private final Ventanilla[] ventanillas = {
            new Ventanilla(EFECTIVO),
            new Ventanilla(IMPORTE_EXACTO),
            new Ventanilla(TARJETA)
    }*/

    /*private Map<String, Ventanilla> ventanillasMap = Map.of(
            EFECTIVO, new Ventanilla(EFECTIVO),
            IMPORTE_EXACTO, new Ventanilla(IMPORTE_EXACTO),
            TARJETA, new Ventanilla(TARJETA)
    );*/
    private final Map<String, Ventanilla> ventanillasMap = new HashMap<>();

    public Peaje() {
        initMap();
    }

    private void initMap() {
        List<String> nombres = List.of(NOMBRES_VENTANILLAS);

        for (String nombre : nombres) {
            ventanillasMap.put(nombre, new Ventanilla(nombre));
        }
    }

    void addCoche(Coche coche, String nombreVentanilla) {
        Ventanilla ventanilla = ventanillasMap.get(nombreVentanilla);
        ventanilla.ponerEnCola(coche);
    }

    public Coche darSalidaCoche(String nombreVentanilla) {
        Ventanilla ventanilla = ventanillasMap.get(nombreVentanilla);
        return ventanilla.quitarDeCola();
    }

    public Ventanilla[] getVentanillas() {
        return ventanillasMap.values().toArray(new Ventanilla[0]);
    }


}
