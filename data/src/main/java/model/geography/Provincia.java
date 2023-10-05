package model.geography;

import java.util.Objects;
import java.util.StringJoiner;

public class Provincia {

    private String name;

    public Provincia(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provincia provincia)) return false;
        return Objects.equals(name, provincia.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Provincia.class.getSimpleName() + "[", "]")
                .add("'" + name + "'")
                .toString();
    }
}
