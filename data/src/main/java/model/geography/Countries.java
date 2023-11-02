package model.geography;

import java.util.List;

public class Countries {
    public static Country SPAIN = Country.builder("ESP")
            .setName("España")
            .setRegion(Region.SOUTHERN_EUROPE)
            .setSurface(505_935)
            .setPopulation(47_519_628)
            .setCapital("Madrid", 3_339_931L)
            .build();

    public static Country RUSIA = Country.builder("RUS")
            .setName("Rusia")
            .setRegion(Region.EASTER_EUROPE)
            .setSurface(17_098_250)
            .setPopulation(144_444_359)
            .setCapital("Moscú", 10_450_109L)
            .build();

    public static Country PORTUGAL = Country.builder("POR")
            .setName("Portugal")
            .setRegion(Region.SOUTHERN_EUROPE)
            .setSurface(92_226)
            .setPopulation(10_247_605)
            .setCapital("Lisboa", 537_616L)
            .build();

    public static Country ITALY = Country.builder("ITA")
            .setName("Italia")
            .setRegion(Region.SOUTHERN_EUROPE)
            .setSurface(301_340)
            .setPopulation(58_870_763)
            .setCapital("Roma", 2_303_894L)
            .build();

    public static Country GERMANY = Country.builder("GER")
            .setName("Alemania")
            .setRegion(Region.WESTERN_EUROPE)
            .setSurface(357_580)
            .setPopulation(83_294_633)
            .setCapital("Berlin", 3_456_559L )
            .build();

    public static Country FRANCE = Country.builder("FRA")
            .setName("Francia")
            .setRegion(Region.WESTERN_EUROPE)
            .setSurface(549_087)
            .setPopulation(64_756_584)
            .setCapital("Paris", 2_103_085L )
            .build();

    public static Country UK = Country.builder("GBR")
            .setName("Reino Unido")
            .setRegion(Region.NORTHERN_EUROPE)
            .setSurface(243_610)
            .setPopulation(67_736_802)
            .setCapital("Londres", 7_840_009L)
            .build();

    public static Country IRELAND = Country.builder("IRL")
            .setName("Irlanda")
            .setRegion(Region.NORTHERN_EUROPE)
            .setSurface(70_280)
            .setPopulation(5_056_935)
            .setCapital("Dublin", 551_768L)
            .build();

    public static Country CANADA = Country.builder("CAN")
            .setName("Canadá")
            .setRegion(Region.NORTH_AMERICA)
            .setSurface(9_984_670)
            .setPopulation(38_781_292)
            .setCapital("Ottawa", 853_920L)
            .build();



    public static Country USA = Country.builder("USA")
            .setName("Estados Unidos de America")
            .setRegion(Region.NORTH_AMERICA)
            .setSurface(9_831_510)
            .setPopulation(339_996_564)
            .setCapital("Washington, D.C.", 622_079L)
            .build();

    public static Country MEXICO = Country.builder("MEX")
            .setName("Mexico")
            .setRegion(Region.NORTH_AMERICA)
            .setSurface(1_964_375)
            .setPopulation(128_455_567)
            .setCapital("Ciudad de Mexico", 13_042_337L)
            .build();

    public static Country ARGENTINA = Country.builder("ARG")
            .setName("Argentina")
            .setRegion(Region.SOUTH_AMERICA)
            .setSurface(2_780_400)
            .setPopulation(45_773_884)
            .setCapital("Buenos Aires", 13_735_799L)
            .build();


    public static Country BRAZIL = new Country.Builder("BRA")
            .setName("Brasil")
            .setRegion(Region.SOUTH_AMERICA)
            .setSurface(8_515_770)
            .setPopulation(216_422_446)
            .setCapital("Brasilia",2_294_077L)
            .build();

    public static Country VENEZUELA = Country.builder("VEN")
            .setName("Venezuela")
            .setRegion(Region.SOUTH_AMERICA)
            .setSurface(912_050)
            .setPopulation(28_838_499)
            .setCapital("Caracas")
            .build();


    public static Country COLOMBIA = Country.builder("COL")
            .setName("Colombia")
            .setRegion(Region.SOUTH_AMERICA)
            .setSurface(1_141_749)
            .setPopulation(52_085_168)
            .setCapital("Bogotá",7_135_912L)
            .build();

    public static Country CHINA = Country.builder("CHN")
            .setName("China")
            .setRegion(Region.EAST_ASIA)
            .setSurface(9_562_910)
            .setPopulation(1_425_671_352)
            .setCapital("Pekín", 12_238_285L)
            .build();

    public static Country JAPAN = Country.builder("JPN")
            .setName("Japón")
            .setRegion(Region.EAST_ASIA)
            .setSurface(377_970)
            .setPopulation(123_294_513)
            .setCapital("Tokio", 8_211_601L )
            .build();

    public static Country INDIA = Country.builder("IND")
            .setName("India")
            .setRegion(Region.SOUTH_ASIA)
            .setSurface(3_287_259)
            .setPopulation(1_428_627_663)
            .setCapital("Nueva Delhi", 336_059L)
            .build();

    public static Country INDONESIA = Country.builder("IDN")
            .setName("Indonesia")
            .setRegion(Region.SOUTHEAST_ASIA)
            .setSurface(1_913_580)
            .setPopulation(277_534_123L)
            .setCapital("Jakarta", 9_063_304L)
            .build();

    public static Country BANGLADESH = Country.builder("BGD")
            .setName("Bangladesh")
            .setRegion(Region.SOUTH_ASIA)
            .setSurface(147_630)
            .setPopulation(172_954_319L)
            .setCapital("Daca", 10_955_651L)
            .build();

    public static Country TURKEY = Country.builder("TUR")
            .setName("Turquía")
            .setRegion(Region.WESTERN_ASIA)
            .setSurface(785_350)
            .setPopulation(85_816_199)
            .setCapital("Ankara", 3_685_432L)
            .build();

    public static Country MOROCCO = Country.builder("MAR")
            .setName("Marruecos")
            .setRegion(Region.NORTHERN_AFRICA)
            .setSurface(710_850)
            .setPopulation(37_840_044)
            .setCapital("Rabat", 618_065L)
            .build();

    public static Country NIGERIA = Country.builder("NGA")
            .setName("Nigeria")
            .setRegion(Region.WESTERN_AFRICA)
            .setSurface(923_770)
            .setPopulation(223_804_632)
            .setCapital("Abuja", 683_636L)
            .build();

    public static Country EGYPT = Country.builder("EGY")
            .setName("Egipto")
            .setRegion(Region.NORTHERN_AFRICA)
            .setSurface(1_001_450)
            .setPopulation(112_716_599)
            .setCapital("Cairo", 8_611_610L)
            .build();

    public static Country AUSTRALIA = Country.builder("AUS")
            .setName("Australia")
            .setRegion(Region.AUSTRALIA_NEW_ZEALAND)
            .setSurface(7_741_220)
            .setPopulation(26_439_112L)
            .setCapital("Canberra", 390_945L)
            .build();

    public static Country NEW_ZEALAND = Country.builder("NZL")
            .setName("Nueva Zelanda")
            .setRegion(Region.AUSTRALIA_NEW_ZEALAND)
            .setSurface(267_710)
            .setPopulation(5_228_100)
            .setCapital("Wellington",388_124)
            .build();


    public static List<Country> WORLD_COUNTRIES =
            List.of(SPAIN, PORTUGAL, GERMANY, IRELAND, UK, ITALY, RUSIA, FRANCE,
                    USA, MEXICO, CANADA, ARGENTINA, BRAZIL, VENEZUELA,COLOMBIA,
                    CHINA, INDIA, TURKEY, JAPAN, INDONESIA, BANGLADESH,
                    MOROCCO, NIGERIA, EGYPT,
                    AUSTRALIA, NEW_ZEALAND);

    static {
        SPAIN.getAutonomias().addAll(
                List.of(
                        Autonomias.VALENCIANA,
                        Autonomias.CATALUÑA,
                        Autonomias.ARAGON));
    }

}
