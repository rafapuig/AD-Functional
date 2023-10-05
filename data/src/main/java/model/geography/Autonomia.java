package model.geography;

import java.util.*;

public class Autonomia implements Comparable<Autonomia> {

    private String name;

    private Set<Provincia> provincias = new HashSet<>();
            //new TreeSet<>(Comparator.comparing(Provincia::getName));

    public Autonomia(String name) {
        this.name = name;
    }

    public Autonomia(String name, Set<Provincia> provincias) {
        this(name);
        this.provincias = provincias;
    }

    public String getName() {
        return name;
    }

    public Set<Provincia> getProvincias() {
        return provincias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autonomia)) return false;
        Autonomia autonomia = (Autonomia) o;
        return Objects.equals(name, autonomia.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Autonomia o) {
        return this.name.compareTo(o.name);
    }
}
