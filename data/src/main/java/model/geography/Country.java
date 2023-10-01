package model.geography;

import java.util.Objects;
import java.util.StringJoiner;

public class Country {

    private String iso3;
    private String name;
    private City capital;

    public Country(String iso3, String name, City capital) {
        this.iso3 = iso3;
        this.name = name;
        this.capital = capital;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Country.class.getSimpleName() + "[", "]")
                .add("'" + iso3 + "'")
                .add("'" + name + "'")
                .add("capital=" + capital)
                .toString();
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
