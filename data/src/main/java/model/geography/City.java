package model.geography;

import java.util.Optional;
import java.util.StringJoiner;

public class City {
    private String name;
    private Optional<Long> population;

    public City(String name) {
        this(name, null);
    }

    public City(String name, Long population) {
        this.name = name;
        this.population =
                population == null ? Optional.empty() : Optional.of(population);
    }

    public String getName() {
        return name;
    }

    public Optional<Long> getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", City.class.getSimpleName() + "[", "]")
                .add(name)
                .add("population=" + population)
                .toString();
    }
}
