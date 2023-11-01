package model.geography;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.StringJoiner;

public class City {
    private final String name;
    private final Optional<Long> population;

    public City(String name) {
        this(name, null);
    }

    public City(String name, Long population) {
        this.name = name;
        this.population = Optional.ofNullable(population);
    }

    public String getName() {
        return name;
    }

    public Optional<Long> getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(
                ", ",
                City.class.getSimpleName() + "[",
                "]")
                .add(name);

        population.ifPresent(
                p -> joiner.add("poblacion=" + new DecimalFormat().format(p.longValue())));

        return joiner.toString();
    }
}
