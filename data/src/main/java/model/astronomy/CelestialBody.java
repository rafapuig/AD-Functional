package model.astronomy;

public abstract class CelestialBody implements Comparable<CelestialBody> {
    protected String name;
    public String getName() {
        return name;
    }

    public CelestialBody(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(CelestialBody other) {
        return this.name.compareTo(other.name);
    }
}
