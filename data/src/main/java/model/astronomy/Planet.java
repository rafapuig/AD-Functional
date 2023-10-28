package model.astronomy;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class Planet extends CelestialBody {

    private final PlanetType planetType;

    private final Double orbitalPeriod;

    private Optional<Double> massInKg = Optional.empty();
    private Optional<Double> radiusInKm = Optional.empty();

    private Long distanceToSun = null;
    private final List<Satellite> satellites;


    public Planet(String name, PlanetType planetType) {
        this(name, planetType, null, Collections.emptyList());
    }
    public Planet(String name, PlanetType planetType, Double orbitalPeriod) {
        this(name, planetType, orbitalPeriod, Collections.emptyList());
    }

    public Planet(String name, PlanetType planetType, Double orbitalPeriod, List<Satellite> satellites) {
        super(name);
        this.name = name;
        this.planetType = planetType;
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = satellites;
    }

    public String getName() {
        return name;
    }

    public PlanetType getType() {
        return planetType;
    }


    public Double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public Optional<Double> getMassInKg() {
        return massInKg;
    }

    public Optional<Double> getRadiusInKm() {
        return radiusInKm;
    }

    public List<Satellite> getSatellites() {
        return satellites;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", Planet.class.getSimpleName() + "[", "]")
                .add(name)
                .add(planetType.toString());

        if(orbitalPeriod != null) joiner.add( String.format("%.3f años", orbitalPeriod));

        massInKg.ifPresent(
                m -> joiner.add(String.format("%.2E Kg", m)));

        radiusInKm.ifPresent(
                r -> joiner.add(new DecimalFormat().format(r.doubleValue()) + " Km"));

        if (!satellites.isEmpty()) {
            joiner.add("satellites=" + satellites);
        }
        return joiner.toString();
    }




    public static class Builder {
        String name;

        PlanetType planetType = PlanetType.UNKNOWN;

        Double orbitalPeriod = null;

        Optional<Double> mass = Optional.empty();
        Optional<Double> radius = Optional.empty();

        List<Satellite> satellites = new ArrayList<>();


        public Builder setType(PlanetType type) {
            planetType = type;
            return this;
        }

        public Builder setOrbitalPeriod(Double orbitalPeriod) {
            this.orbitalPeriod = orbitalPeriod;
            return this;
        }

        public Builder setMass(double massInKg) {
            mass = Optional.of(massInKg);
            return this;
        }

        public Builder setRadius(double radiusInKm) {
            radius = Optional.of(radiusInKm);
            return this;
        }

        public Builder(String name) {
            this.name = name;
        }

        public Builder addSatellite(String satelliteName) {
            satellites.add(new Satellite(satelliteName));
            return this;
        }

        public Builder addSatellites(String... satellitesNames) {
            Stream.of(satellitesNames)
                    .map(Satellite::new)
                    .collect(() -> satellites, List::add, List::addAll);
            return this;
        }

        public Planet build() {
            Planet planet = new Planet(
                    this.name, planetType, orbitalPeriod,
                    satellites.isEmpty() ? Collections.emptyList() : List.copyOf(satellites));

            this.radius.ifPresent(radius -> planet.radiusInKm = Optional.of(radius));
            this.mass.ifPresent(mass -> planet.massInKg = Optional.of(mass));
            //planet.satellites.clear();
            //planet.satellites.addAll(satellites);

            return planet;
        }
    }
}
