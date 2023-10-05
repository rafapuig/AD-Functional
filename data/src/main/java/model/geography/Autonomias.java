package model.geography;

import java.util.Set;
import java.util.stream.Stream;

public class Autonomias {

    public static Autonomia VALENCIANA = new Autonomia("Comunidad Valenciana",
            Set.of(new Provincia("Castellón"),
                    new Provincia("Valencia"),
                    new Provincia("Alicante")));
    public static Autonomia ARAGON = new Autonomia("Aragón",
            Set.of(
                    Stream.of("Huesca", "Zaragoza", "Teruel")
                            .map(Provincia::new)
                            .toArray(Provincia[]::new)));
    public static Autonomia CATALUÑA = new Autonomia("Cataluña",
            Set.copyOf(
                    Stream.of("Barcelona", "Tarragona", "Lérida", "Gerona")
                            .map(Provincia::new)
                            .toList()));
}
