package model.astronomy;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Stream;

public class Planet {

    private final String name;
    private Optional<Double> massInKg = Optional.empty();
    private Optional<Double> radiusInKm = Optional.empty();
    private final List<Satellite> satellites;

    public Planet(String name) {
        this(name, Collections.emptyList());
    }

    public Planet(String name, List<Satellite> satellites) {
        this.name = name;
        this.satellites = satellites;
    }

    public String getName() {
        return name;
    }

    public List<Satellite> getSatellites() {
        return satellites;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", Planet.class.getSimpleName() + "[", "]")
                .add(name);

        massInKg.ifPresent(
                m -> joiner.add(new DecimalFormat().format(m.doubleValue())  + " Kg"));

        radiusInKm.ifPresent(
                r -> joiner.add(new DecimalFormat().format(r.doubleValue())  + " Km"));

        if(!satellites.isEmpty()) {
            joiner.add("satellites=" + satellites);
        }
        return joiner.toString();
    }

    public static class Builder {
        String name;
        Optional<Double> mass = Optional.empty();
        Optional<Double> radius = Optional.empty();

        List<Satellite> satellites = new ArrayList<>();

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
            Planet planet = new Planet(this.name,
                    satellites.isEmpty() ? Collections.emptyList() : List.copyOf(satellites));
            this.radius.ifPresent(radius -> planet.radiusInKm = Optional.of(radius));
            this.mass.ifPresent(mass -> planet.massInKg = Optional.of(mass));
            //planet.satellites.clear();
            //planet.satellites.addAll(satellites);

            return planet;
        }
    }
}
