package model.geography;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public class Country {

    private String iso3;
    private String name;
    private City capital;
    private Optional<Integer> surfaceArea = Optional.empty();

    public Country(String iso3, String name, City capital) {
        this.iso3 = iso3;
        this.name = name;
        this.capital = capital;
    }

    public Country(String iso3, String name, int surfaceArea, City capital) {
        this(iso3, name, capital);
        this.surfaceArea = Optional.of(surfaceArea);
    }

    public String getIso3() {
        return iso3;
    }

    public String getName() {
        return name;
    }

    public City getCapital() {
        return capital;
    }

    public Optional<Integer> getSurfaceArea() {
        return surfaceArea;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", Country.class.getSimpleName() + "[", "]")
                .add("'" + iso3 + "'")
                .add("'" + name + "'");

        if(surfaceArea.isPresent()) {
            NumberFormat format = new DecimalFormat();
            String surface = format.format(surfaceArea.get());
            joiner.add(String.format("superficie=%s km^2",  surface));
        }

        joiner.add("capital=" + capital);

        return joiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return Objects.equals(iso3, country.iso3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso3);
    }
}
