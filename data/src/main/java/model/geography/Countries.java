package model.geography;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Countries {
    public static Country SPAIN =
            new Country("ESP", "España", 505_935,
                    new City("Madrid", 3_339_931L));

    public static Country PORTUGAL =
            new Country("POR", "Portugal",
                    new City("Lisboa"));

    public static Country ITALY =
            new Country("ITA", "Italia",
                    new City("Roma"));

    public static Country GERMANY =
            new Country("GER", "Alemania", 357_580,
                    new City("Berlin"));
    public static Country IRLAND =
            new Country("IRL", "Irlanda",
                    new City("Dublin"));
    public static Country USA =
            new Country("USA", "Estados Unidos de America",
                    new City("Washington"));

    public static List<Country> WORLD_COUNTRIES =
            List.of(SPAIN, PORTUGAL, GERMANY, IRLAND, ITALY, USA);

    static {
        SPAIN.getAutonomias().addAll(
                List.of(
                        Autonomias.VALENCIANA,
                        Autonomias.CATALUÑA,
                        Autonomias.ARAGON));
    }

}
