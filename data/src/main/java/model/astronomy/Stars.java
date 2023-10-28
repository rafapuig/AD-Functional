package model.astronomy;

public class Stars {

    public static Star POLARIS = new Star("Sol") {{
        this.addPlanets(Planets.SOLAR_SYSTEM_PLANETS.toArray(Planet[]::new));
    }};

    public static Star SUN = new Star("Polaris", Planets.SOLAR_SYSTEM_PLANETS);
}
