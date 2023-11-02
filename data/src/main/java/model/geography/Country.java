package model.geography;

import java.util.*;

public class Country implements Comparable<Country> {
    private final String iso3;
    private final String name;
    private final Region region;
    private final City capital;
    private Area surface;
    private Long population;

    private final Set<Autonomia> autonomias = new TreeSet<>();

    public Set<Autonomia> getAutonomias() {
        return autonomias;
    }


    public Country(String iso3, String name, Region region, City capital) {
        this.iso3 = iso3;
        this.name = name;
        this.region = region;
        this.capital = capital;
    }

    public Country(String iso3, String name, Region region, Area surface, Long population, City capital) {
        this(iso3, name, region, capital);
        this.surface = surface;
        this.population = population;
    }

    public String getIso3() {
        return iso3;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public Continent getContinent() {
        return region.getContinent();
    }

    public City getCapital() {
        return capital;
    }

    public Optional<Area> getSurface() {
        return Optional.ofNullable(this.surface);
    }

    public Long getPopulation() {
        return this.population;
    }

    public Optional<Double> getDensity() {
        if (getPopulation() == null) return Optional.empty();
        if (this.surface == null) return Optional.empty();
        return Optional.ofNullable(this.population / (double) this.surface.value());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", Country.class.getSimpleName() + "[", "]")
                .add("'" + iso3 + "'")
                .add("'" + name + "'");

        if (getSurface().isPresent()) {
            //NumberFormat format = new DecimalFormat();
            //String surface = format.format(getSurfaceArea().get().value());
            //joiner.add(String.format("superficie=%s km^2", surface));
            joiner.add(getSurface().get().toString());
        }

        if (capital != null) {
            joiner.add("capital=" + capital);
        }

        return joiner.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Country country)) return false;
        return Objects.equals(iso3, country.iso3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso3);
    }

    @Override
    public int compareTo(Country other) {
        return Comparator.comparing(
                        Country::getName,
                        Comparator.nullsLast(Comparator.naturalOrder()))
                .compare(this, other);
        //return this.getName().compareTo(other.getName());
    }

    public static Builder builder(String ISO3) {
        Objects.requireNonNull(ISO3, "El código ISO3 no puede ser nulo dado que identifica el pais");
        return new Builder(ISO3);
    }


    public static class Builder {
        String ISO3;
        String name;
        Region region;
        City capital;
        Area surfaceArea;

        Long population;

        protected Builder(String ISO3) {
            this.ISO3 = ISO3;
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Builder setRegion(Region region) {
            this.region = region;
            return this;
        }

        Builder setCapital(City city) {
            this.capital = city;
            return this;
        }

        Builder setCapital(String cityName) {
            return setCapital(new City(cityName));
        }

        Builder setCapital(String cityName, long population) {
            return setCapital(new City(cityName, population));
        }

        Builder setSurface(int surfaceArea) {
            this.surfaceArea = new Area(surfaceArea);
            return this;
        }

        Builder setPopulation(long population) {
            this.population = population;
            return this;
        }

        Country build() {
            Objects.requireNonNull(name, "El pais debe tener un nombre");
            return new Country(ISO3, name, region, surfaceArea, population, capital);
        }
    }

}
