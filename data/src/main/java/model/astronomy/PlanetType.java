package model.astronomy;

public enum PlanetType {

    ROCKY("Planeta Rocoso"),
    GAS_GIANT("Gigante gasesoso"),
    DWARF("Planeta Enano"),
    UNKNOWN("Desconocido");

    private String name;

    PlanetType(String name) {
        this.name = name;
    }
}
