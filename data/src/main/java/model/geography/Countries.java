package model.geography;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Countries {
    public static Country SPAIN = Country.builder("ESP")
            //.setName("España")
            .setRegion(Region.SOUTHERN_EUROPE)
            .setSurface(505_935)
            .setCapital("Madrid", 3_339_931L)
            .build();

    public static Country PORTUGAL = Country.builder("POR")
            .setName("Portugal")
            .setRegion(Region.SOUTHERN_EUROPE)
            .setCapital("Lisboa")
            .build();

    public static Country ITALY = Country.builder("ITA")
            .setName("Italia")
            .setRegion(Region.SOUTHERN_EUROPE)
            .setCapital("Roma")
            .build();

    public static Country GERMANY = Country.builder("GER")
            .setName("Alemania")
            .setRegion(Region.WESTERN_EUROPE)
            .setSurface(357_580)
            .setCapital("Berlin")
            .build();

    public static Country IRLAND = Country.builder("IRL")
            .setName("Irlanda")
            .setRegion(Region.NORTHERN_EUROPE)
            .setCapital("Dublin")
            .build();

    public static Country USA =
            new Country("USA", "Estados Unidos de America", Region.NORTH_AMERICA,
                    new City("Washington"));

    public static Country ARGENTINA =
            new Country("ARG", "Argentina", Region.SOUTH_AMERICA,
                    new City("Buenos Aires"));

    public static Country BRAZIL = new Country.Builder("BRA")
            .setName("Brasil")
            .setRegion(Region.SOUTH_AMERICA)
            .setSurface(8_515_770)
            .setCapital("Brasilia").build();

    public static Country VENEZUELA = Country.builder("VEN")
            .setName("Venezuela")
            .setRegion(Region.SOUTH_AMERICA)
            .setSurface(912_050)
            .setCapital("Caracas")
            .build();

    public static  Country CHINA = Country.builder("CHN")
            .setName("China")
            .setRegion(Region.EAST_ASIA)
            .setSurface(9_562_910)
            .setCapital("Pekín")
            .build();


    public static List<Country> WORLD_COUNTRIES =
            List.of(SPAIN, PORTUGAL, GERMANY, IRLAND, ITALY, USA, ARGENTINA, BRAZIL,
                    VENEZUELA, CHINA);

    static {
        SPAIN.getAutonomias().addAll(
                List.of(
                        Autonomias.VALENCIANA,
                        Autonomias.CATALUÑA,
                        Autonomias.ARAGON));
    }

}
