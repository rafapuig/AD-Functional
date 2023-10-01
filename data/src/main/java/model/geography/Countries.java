package model.geography;

import java.util.List;

public class Countries {
    public static Country SPAIN =
            new Country("ESP", "España", new City("Madrid"));

    public static Country ITALY =
            new Country("ITA", "Italia", new City("Roma"));

    public static Country USA =
            new Country("USA", "Estados Unidos de America",
                    new City("Washington"));

    public static List<Country> WORLD_COUNTRIES = List.of(SPAIN, ITALY, USA);

}
