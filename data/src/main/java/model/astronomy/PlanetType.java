package model.astronomy;

public enum PlanetType {
    UNKNOWN("Desconocido"),
    ROCKY("Planeta Rocoso"),
    GAS_GIANT("Gigante Gaseoso"),
    DWARF("Planeta Enano");

    private String name;

    PlanetType(String name) {
        this.name = name;
    }
}
